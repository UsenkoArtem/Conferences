package com.training.conferences.adaptors.api.dto;

import com.training.conferences.adaptors.api.validation.SequenceFieldsMatch;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@SequenceFieldsMatch(
    first = "StartDate",
    second = "EndDate",
    message = "The conference cant be finished unless its start")
public class UpdateConferenceDto {

  @FutureOrPresent(
      message =
          "The start date of the conference should be not earlier 30 day from the current moment")
  private LocalDate StartDate;

  @FutureOrPresent(
      message =
          "The start date of the conference should be not earlier 30 day from the current moment")
  private LocalDate EndDate;

  @Min(value = 100, message = "The participant count")
  private Integer ParticipantCount;
}
