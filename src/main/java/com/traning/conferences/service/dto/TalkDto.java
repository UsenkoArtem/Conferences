package com.traning.conferences.service.dto;

import com.traning.conferences.adaptors.persistence.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TalkDto {
    private Long id;
    private String TalkName;
    private String SpeakerName;
    private String Description;
    private ReportType reportType;
}
