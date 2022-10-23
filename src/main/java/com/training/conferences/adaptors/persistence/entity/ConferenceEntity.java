package com.training.conferences.adaptors.persistence.entity;

import com.training.conferences.exception.ConferenceException;
import com.training.conferences.exception.IncorrectConferenceDate;
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
  @SequenceGenerator(
      name = "conference_id_seq",
      sequenceName = "conference_id_seq",
      allocationSize = 1)
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

  @SneakyThrows
  public void validate() {

    if (name.isEmpty()) throw new ConferenceException("Name cannot be empty");
    if (theme.isEmpty()) throw new ConferenceException("Theme cannot be empty");

    if (endDate.isBefore(startDate))
      throw new IncorrectConferenceDate("The " + "conference cant be finished unless its start");

    if (startDate.isBefore(LocalDate.now()))
      throw new IncorrectConferenceDate("The conference cant be started before today");

    if (participantCount < 100)
      throw new ConferenceException("participantCount cannot be less 1000");
  }
}
