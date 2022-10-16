package com.traning.conferences.adaptors.api.dto;

import com.traning.conferences.adaptors.api.validation.SequenceFieldsMatch;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@SequenceFieldsMatch(first = "StartDate", second = "EndDate", message = "The conference cant be finished unless its start")
public class UpdateConferenceDto {
    @NonNull
    @FutureOrPresent(message = "The start date of the conference should be not earlier 30 day from the current moment")
    private LocalDate StartDate;

    @NonNull
    private LocalDate EndDate;

    @Min(value = 100, message = "The participant count")
    @NonNull
    private Integer ParticipantCount;
}
