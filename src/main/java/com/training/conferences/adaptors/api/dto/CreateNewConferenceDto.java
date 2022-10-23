package com.training.conferences.adaptors.api.dto;

import com.training.conferences.adaptors.api.validation.SequenceFieldsMatch;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@SequenceFieldsMatch(
    first = "StartDate",
    second = "EndDate",
    message = "The conference cant be finished unless its start")
public class CreateNewConferenceDto {
  @NotBlank(message = "Field name cannot be empty")
  @NonNull
  private String Name;

  @NotBlank(message = "Field name cannot be empty")
  @NonNull
  private String Theme;

  @NonNull() @FutureOrPresent private LocalDate StartDate;

  @NonNull private LocalDate EndDate;

  @Min(value = 100, message = "The participant count must be more 100")
  @NonNull()
  private Integer ParticipantCount;
}
