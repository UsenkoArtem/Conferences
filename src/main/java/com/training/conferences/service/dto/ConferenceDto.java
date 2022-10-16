package com.training.conferences.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ConferenceDto {
    private Long id;
    private String Name;
    private String Theme;
    private LocalDate StartDate;
    private LocalDate EndDate;
    private Integer ParticipantCount;
}
