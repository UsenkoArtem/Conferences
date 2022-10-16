package com.traning.conferences.service.impl;

import com.traning.conferences.adaptors.api.dto.CreateNewTalkDto;
import com.traning.conferences.adaptors.persistence.ConferenceDao;
import com.traning.conferences.adaptors.persistence.TalkDao;
import com.traning.conferences.adaptors.persistence.entity.ConferenceEntity;
import com.traning.conferences.adaptors.persistence.entity.TalkEntity;
import com.traning.conferences.exception.EnoughTalkOnConferenceException;
import com.traning.conferences.exception.InvalidConferenceIdException;
import com.traning.conferences.exception.TalkAlreadyExistInConferenceException;
import com.traning.conferences.exception.TalkLess30DaysException;
import com.traning.conferences.service.TalksService;
import com.traning.conferences.service.dto.TalkDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


@RequiredArgsConstructor
@Service
@Transactional
public class TalksServiceImpl implements TalksService {

    private final ConferenceDao conferencesDao;
    private final TalkDao talkDao;

    @SneakyThrows
    @Override
    public long addTalkToConference(Long conferencesId, CreateNewTalkDto createNewTalkDto) {
        Optional<ConferenceEntity> conference = conferencesDao.findById(conferencesId);

        if (conference.isEmpty())
            throw new InvalidConferenceIdException(MessageFormat.format("Cannot find conference by id :{0}", conferencesId));

        ConferenceEntity conferenceEntity = conference.get();

        var conferenceTalk = conferenceEntity.getTalks().stream().toList();

        var duplicateTalk = conferenceTalk.stream().map(TalkEntity::getName).filter(s -> s.equals(createNewTalkDto.getName())).findFirst();

        if (duplicateTalk.isPresent())
            throw new TalkAlreadyExistInConferenceException(MessageFormat.format("Talk {0} already exist in conference {1}", createNewTalkDto.getName(), conferenceEntity.getName()));


        int size = conferenceTalk.stream().filter(talkEntity -> talkEntity.getSpeakerName().equals(createNewTalkDto.getSpeakerName())).toList().size();

        if (size > 3)
            throw new EnoughTalkOnConferenceException(MessageFormat.format("Enough talk, no more 3 speech by speaker. Speaker name={0}", createNewTalkDto.getSpeakerName()));

        LocalDate startDate = conferenceEntity.getStartDate();

        if (ChronoUnit.DAYS.between(LocalDate.now(), startDate) <= 30)
            throw new TalkLess30DaysException(MessageFormat.format("Conference {0} start less 30 days", conferenceEntity.getName()));

        var taskEntity = new TalkEntity(createNewTalkDto.getName(), createNewTalkDto.getDescription(), createNewTalkDto.getSpeakerName(), createNewTalkDto.getReportType(), conferenceEntity);

        var save = talkDao.save(taskEntity);
        return save.getId();
    }

    @SneakyThrows
    @Override
    public List<TalkDto> getAllConferenceTalks(Long conferencesId) {
        Optional<ConferenceEntity> conference = conferencesDao.findById(conferencesId);

        if (conference.isEmpty())
            throw new InvalidConferenceIdException("Cannot find conference by id :" + conferencesId);

        return toDto(conference.get().getTalks().stream());
    }

    private List<TalkDto> toDto(Stream<TalkEntity> books) {
        return books.map(TalksServiceImpl::toDto)
                .collect(toList());
    }


    public static TalkDto toDto(TalkEntity talkEntity) {
        return new TalkDto(talkEntity.getId(), talkEntity.getName(), talkEntity.getSpeakerName(), talkEntity.getDescription(), talkEntity.getReportType());
    }
}
