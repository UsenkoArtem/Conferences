package com.training.conferences.adaptors.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Table(name = "conference")
public class ConferenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conference_id_seq")
    @SequenceGenerator(name = "conference_id_seq", sequenceName = "conference_id_seq", allocationSize = 1)
    private Long id;

    @NonNull
    @Column(unique = true, nullable = false)
    private String name;

    @NonNull
    @Column(nullable = false)
    private String theme;

    @NonNull
    @Column(nullable = false)
    private LocalDate startDate;

    @NonNull
    @Column(nullable = false)
    private LocalDate endDate;

    @NonNull
    @Column(nullable = false)
    private Integer participantCount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "conference_id")
    private Set<TalkEntity> talks;
}
