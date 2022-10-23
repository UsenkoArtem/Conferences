package com.training.conferences.service;

import com.training.conferences.adaptors.api.dto.CreateNewTalkDto;
import com.training.conferences.service.dto.TalkDto;

import java.util.List;

public interface TalksService {

  long addTalkToConference(Long conferencesId, CreateNewTalkDto createNewTalkDto);

  List<TalkDto> getAllConferenceTalks(Long conferencesId);
}
