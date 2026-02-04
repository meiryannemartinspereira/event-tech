package com.eyetech.events.repositories;

import java.sql.Date;

import org.springframework.data.jpa.domain.Specification;

import com.eyetech.events.model.Event;

import jakarta.persistence.criteria.JoinType;

public class EventSpecification {

    public static Specification<Event> upcomingEvents(Date today) {
        return (root, query, cb) ->
            cb.greaterThanOrEqualTo(root.get("date"), today);
    }

    public static Specification<Event> titleContains(String title) {
        return (root, query, cb) ->
            cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Event> dateEquals(Date date) {
        return (root, query, cb) ->
            cb.equal(root.get("date"), date);
    }

    public static Specification<Event> cityEquals(String city) {
        return (root, query, cb) ->
            cb.equal(root.join("address", JoinType.LEFT).get("city"), city);
    }
    
}
