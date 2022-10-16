package com.training.conferences.adaptors.api.dto;

import com.training.conferences.adaptors.persistence.ReportType;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class CreateNewTalkDto {
    @NotBlank
    @NonNull
    private String Name;

    @NotBlank
    @NonNull
    private String Description;

    @NotBlank
    @NonNull
    private String SpeakerName;

    @NonNull
    private ReportType ReportType;
}
