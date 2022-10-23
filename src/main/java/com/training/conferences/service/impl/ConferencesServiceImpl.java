package com.training.conferences.service.impl;

import com.training.conferences.adaptors.api.dto.CreateNewConferenceDto;
import com.training.conferences.adaptors.api.dto.UpdateConferenceDto;
import com.training.conferences.adaptors.persistence.ConferenceDao;
import com.training.conferences.adaptors.persistence.entity.ConferenceEntity;
import com.training.conferences.exception.InvalidConferenceIdException;
import com.training.conferences.exception.OverlappingConferenceException;
import com.training.conferences.exception.UniqueConferenceNameException;
import com.training.conferences.service.ConferencesService;
import com.training.conferences.service.dto.ConferenceDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
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
    var conferenceEntity =
        new ConferenceEntity(
            conference.getName(),
            conference.getTheme(),
            conference.getStartDate(),
            conference.getEndDate(),
            conference.getParticipantCount());
    try {

      var overlapConferenceName =
          conferencesDao.findOverlap(conference.getStartDate(), conference.getEndDate());

      if (overlapConferenceName.isPresent())
        throw new OverlappingConferenceException(
            MessageFormat.format(
                "Conference with name: {0} overlap your dates: {1}--{2}",
                overlapConferenceName.get().getName(),
                conference.getStartDate(),
                conference.getEndDate()));

      ConferenceEntity save = conferencesDao.save(conferenceEntity);
      return save.getId();

    } catch (DataIntegrityViolationException e) {
      throw new UniqueConferenceNameException(
          MessageFormat.format("Conference with name: {0} already exist", conference.getName()));
    }
  }

  public List<ConferenceDto> getAllConferences() {
    return toDto(conferencesDao.findAll().stream());
  }

  @SneakyThrows
  @Override
  public long updateConference(Long conferencesId, UpdateConferenceDto updateConferenceDto) {
    Optional<ConferenceEntity> conference = conferencesDao.findById(conferencesId);
    if (conference.isEmpty())
      throw new InvalidConferenceIdException(
          MessageFormat.format("Cannot find conference by id: {0}", conferencesId));

    ConferenceEntity conferenceEntity = conference.get();

    if (updateConferenceDto.getParticipantCount() != null)
      conferenceEntity.setParticipantCount(updateConferenceDto.getParticipantCount());
    if (updateConferenceDto.getEndDate() != null)
      conferenceEntity.setEndDate(updateConferenceDto.getEndDate());
    if (updateConferenceDto.getStartDate() != null)
      conferenceEntity.setStartDate(updateConferenceDto.getStartDate());

    conferenceEntity.validate();

    var overlapConferenceName =
        conferencesDao.findOverlap(conferenceEntity.getStartDate(), conferenceEntity.getEndDate());

    if (overlapConferenceName.isPresent() && !Objects.equals(overlapConferenceName.get().getId(), conferencesId))
      throw new OverlappingConferenceException(
          MessageFormat.format(
              "Conference with name: {0} overlap your dates: {1}--{2}",
              overlapConferenceName.get().getName(),
              conferenceEntity.getStartDate(),
              conferenceEntity.getEndDate()));

    conferencesDao.save(conferenceEntity);

    return conferenceEntity.getId();
  }

  private List<ConferenceDto> toDto(Stream<ConferenceEntity> books) {
    return books.map(ConferencesServiceImpl::toDto).collect(toList());
  }

  public static ConferenceDto toDto(ConferenceEntity conferenceEntity) {
    return new ConferenceDto(
        conferenceEntity.getId(),
        conferenceEntity.getName(),
        conferenceEntity.getTheme(),
        conferenceEntity.getStartDate(),
        conferenceEntity.getEndDate(),
        conferenceEntity.getParticipantCount());
  }
}
