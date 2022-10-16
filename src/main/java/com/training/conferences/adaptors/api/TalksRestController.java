package com.training.conferences.adaptors.api;

import com.training.conferences.adaptors.api.dto.CreateNewTalkDto;
import com.training.conferences.service.TalksService;
import com.training.conferences.service.dto.TalkDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "talks")
@RestController
@Validated
@RequestMapping(path = "/conferences", produces = {"application/json"})
@RequiredArgsConstructor
public class TalksRestController {

    private final TalksService talksService;

    @PostMapping("{conference_id}/talks")
    long addNewTalk(@Validated @RequestBody CreateNewTalkDto createNewTalkDto, @PathVariable("conference_id") Long conferencesId) {
        return talksService.addTalkToConference(conferencesId, createNewTalkDto);
    }

    @GetMapping("{conference_id}/talks")
    List<TalkDto> getAllConferenceTalks(@PathVariable("conference_id") Long conferencesId) {
        return talksService.getAllConferenceTalks(conferencesId);
    }
}
