package com.traning.conferences.service;

import com.traning.conferences.adaptors.api.dto.CreateNewTalkDto;
import com.traning.conferences.service.dto.TalkDto;

import java.util.List;


public interface TalksService {

    long addTalkToConference(Long conferencesId, CreateNewTalkDto createNewTalkDto);
    List<TalkDto> getAllConferenceTalks(Long conferencesId);
}
