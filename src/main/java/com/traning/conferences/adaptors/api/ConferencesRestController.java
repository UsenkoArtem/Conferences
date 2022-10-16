package com.traning.conferences.adaptors.api;

import com.traning.conferences.adaptors.api.dto.CreateNewConferenceDto;
import com.traning.conferences.adaptors.api.dto.CreateNewTalkDto;
import com.traning.conferences.adaptors.api.dto.UpdateConferenceDto;
import com.traning.conferences.service.ConferencesService;
import com.traning.conferences.service.TalksService;
import com.traning.conferences.service.dto.ConferenceDto;
import com.traning.conferences.service.dto.TalkDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/conferences", produces = {"application/json"})
@RequiredArgsConstructor
public class ConferencesRestController {

    private final ConferencesService conferencesService;
    private final TalksService talksService;

    @PostMapping
    long createConference(@Validated @RequestBody CreateNewConferenceDto conferencesDto) {
        return conferencesService.addConference(conferencesDto);
    }

    @GetMapping
    List<ConferenceDto> getAllConference() {
        return conferencesService.getAllConferences();
    }

    @PutMapping("{conference_id}")
    long updateConference(@Validated @RequestBody UpdateConferenceDto conferencesDto, @PathVariable("conference_id") Long conferencesId) {
        return conferencesService.updateConference(conferencesId, conferencesDto);
    }

    @PostMapping("{conference_id}/talks")
    long addNewTalk(@Validated @RequestBody CreateNewTalkDto createNewTalkDto, @PathVariable("conference_id") Long conferencesId) {
        return talksService.addTalkToConference(conferencesId, createNewTalkDto);
    }

    @GetMapping("{conference_id}/talks")
    List<TalkDto> getAllConferenceTalks(@PathVariable("conference_id") Long conferencesId) {
        return talksService.getAllConferenceTalks(conferencesId);
    }
}
