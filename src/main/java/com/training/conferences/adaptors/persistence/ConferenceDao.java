package com.training.conferences.adaptors.persistence;

import com.training.conferences.adaptors.persistence.entity.ConferenceEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface ConferenceDao extends JpaRepository<ConferenceEntity, Long> {

  @NonNull
  Optional<ConferenceEntity> findById(@NonNull Long id);

  @Query(
      nativeQuery = true,
      value =
          "SELECT * FROM conference c WHERE (c.start_date >= :from"
              + " AND c.start_date <= :to) OR (c.end_date >= :from AND c"
              + ".end_date <= :to)")
  Optional<ConferenceEntity> findOverlap(LocalDate from, LocalDate to);
}
