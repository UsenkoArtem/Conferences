package com.traning.conferences.service.impl;

import com.traning.conferences.adaptors.api.dto.CreateNewConferenceDto;
import com.traning.conferences.adaptors.api.dto.UpdateConferenceDto;
import com.traning.conferences.adaptors.persistence.ConferenceDao;
import com.traning.conferences.adaptors.persistence.entity.ConferenceEntity;
import com.traning.conferences.exception.OverlappingConferenceException;
import com.traning.conferences.exception.UniqueConferenceNameException;
import com.traning.conferences.service.ConferencesService;
import com.traning.conferences.service.dto.ConferenceDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.awt.dnd.InvalidDnDOperationException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


@RequiredArgsConstructor
@Service
public class ConferencesServiceImpl implements ConferencesService {

    private final ConferenceDao conferencesDao;

    @SneakyThrows
    @Override
    public long addConference(CreateNewConferenceDto conference) {
        var conferenceEntity = new ConferenceEntity(conference.getName(), conference.getTheme(), conference.getStartDate(), conference.getEndDate(), conference.getParticipantCount());
        try {

            var overlapConferenceName = conferencesDao.findOverlap(conference.getStartDate(), conference.getEndDate());

            if (overlapConferenceName.isPresent())
                throw new OverlappingConferenceException(MessageFormat.format("Conference with name: {0} overlap your dates: {1}--{2}", overlapConferenceName.get(), conference.getStartDate(), conference.getEndDate()));

            ConferenceEntity save = conferencesDao.save(conferenceEntity);
            return save.getId();

        } catch (DataIntegrityViolationException e) {
            throw new UniqueConferenceNameException(MessageFormat.format("Conference with name: {0} already exist", conference.getName()));
        }
    }

    public List<ConferenceDto> getAllConferences() {
        return toDto(conferencesDao.findAll().stream());
    }

    @Override
    public long updateConference(Long conferencesId, UpdateConferenceDto conferencesDto) {
        Optional<ConferenceEntity> conference = conferencesDao.findById(conferencesId);
        if (conference.isEmpty())
            throw new InvalidDnDOperationException();

        ConferenceEntity conferenceEntity = conference.get();

        conferenceEntity.setParticipantCount(conferencesDto.getParticipantCount());
        conferenceEntity.setEndDate(conferencesDto.getEndDate());
        conferenceEntity.setStartDate(conferencesDto.getStartDate());
        conferencesDao.save(conferenceEntity);

        return conferenceEntity.getId();
    }

    private List<ConferenceDto> toDto(Stream<ConferenceEntity> books) {
        return books.map(ConferencesServiceImpl::toDto)
                .collect(toList());
    }


    public static ConferenceDto toDto(ConferenceEntity conferenceEntity) {
        return new ConferenceDto(conferenceEntity.getId(), conferenceEntity.getName(), conferenceEntity.getTheme(), conferenceEntity.getStartDate(), conferenceEntity.getEndDate(), conferenceEntity.getParticipantCount());
    }
}
