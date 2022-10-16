package com.training.conferences.service;

import com.training.conferences.adaptors.api.dto.CreateNewConferenceDto;
import com.training.conferences.adaptors.api.dto.UpdateConferenceDto;
import com.training.conferences.service.dto.ConferenceDto;

import java.util.List;


public interface ConferencesService {
    long addConference(CreateNewConferenceDto conference);

    List<ConferenceDto> getAllConferences();

    long updateConference(Long conferencesId, UpdateConferenceDto conferencesDto);

}
