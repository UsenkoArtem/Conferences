package com.traning.conferences.adaptors.persistence.entity;

import com.traning.conferences.adaptors.persistence.ReportType;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Table(name = "talk")
public class TalkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "talk_id_seq")
    @SequenceGenerator(name = "talk_id_seq", sequenceName = "talk_id_seq", allocationSize = 1)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(nullable = false)
    private String description;

    @NonNull
    @Column(nullable = false)
    private String SpeakerName;

    @NonNull
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ReportType reportType;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conference_id", nullable = false)
    private ConferenceEntity conferenceEntity;
}
