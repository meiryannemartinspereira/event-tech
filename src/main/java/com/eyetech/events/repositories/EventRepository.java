package com.eyetech.events.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.eyetech.events.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event>{
    @EntityGraph(attributePaths = {"cupons", "address"})
    Optional<Event> findWithDetailsById(Long id);
}