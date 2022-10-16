package com.traning.conferences.adaptors.persistence;

import com.traning.conferences.adaptors.persistence.entity.ConferenceEntity;
import com.traning.conferences.service.dto.ConferenceDto;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface ConferenceDao extends JpaRepository<ConferenceEntity, Long> {

    @NonNull Optional<ConferenceEntity> findById(@NonNull Long id);

    @Query(nativeQuery = true, value = "SELECT c.name FROM conference c WHERE (c.start_date >= ?1 AND c.start_date <= ?2) OR (c.end_date >= ?1 AND c.end_date <= ?2)")
    Optional<String> findOverlap(LocalDate from, LocalDate to);
}