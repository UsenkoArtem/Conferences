package com.traning.conferences.service;

import com.traning.conferences.adaptors.api.dto.CreateNewConferenceDto;
import com.traning.conferences.adaptors.api.dto.CreateNewTalkDto;
import com.traning.conferences.adaptors.api.dto.UpdateConferenceDto;
import com.traning.conferences.service.dto.ConferenceDto;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ConferencesService {
    long addConference(CreateNewConferenceDto conference);

    List<ConferenceDto> getAllConferences();

    long updateConference(Long conferencesId, UpdateConferenceDto conferencesDto);

}
