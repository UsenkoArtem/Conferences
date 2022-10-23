package com.training.conferences.adaptors.api;

import com.training.conferences.adaptors.api.dto.CreateNewConferenceDto;
import com.training.conferences.adaptors.api.dto.UpdateConferenceDto;
import com.training.conferences.service.ConferencesService;
import com.training.conferences.service.dto.ConferenceDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "conferences")
@RestController
@Validated
@RequestMapping(
    path = "/conferences",
    produces = {"application/json"})
@RequiredArgsConstructor
public class ConferencesRestController {

  private final ConferencesService conferencesService;

  @PostMapping
  long createConference(@RequestBody CreateNewConferenceDto conferencesDto) {
    return conferencesService.addConference(conferencesDto);
  }

  @GetMapping
  List<ConferenceDto> getAllConference() {
    return conferencesService.getAllConferences();
  }

  @PutMapping("{conference_id}")
  long updateConference(
      @RequestBody UpdateConferenceDto updateConferenceDto,
      @PathVariable("conference_id") Long conferencesId) {
    return conferencesService.updateConference(conferencesId, updateConferenceDto);
  }
}
