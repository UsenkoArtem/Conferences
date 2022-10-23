package com.training.conferences.adaptors.persistence;

import com.training.conferences.adaptors.persistence.entity.TalkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalkDao extends JpaRepository<TalkEntity, Long> {}
