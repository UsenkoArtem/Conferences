package com.traning.conferences.adaptors.persistence;


import com.traning.conferences.adaptors.persistence.entity.TalkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TalkDao extends JpaRepository<TalkEntity, Long> {
}
