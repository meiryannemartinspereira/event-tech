package com.eyetech.events.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eyetech.events.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{}