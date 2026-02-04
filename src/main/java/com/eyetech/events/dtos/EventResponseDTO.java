package com.eyetech.events.dtos;

import java.util.Date;

public record EventResponseDTO(
    Long id,
    String title,
    String description,
    Date date,
    Boolean remote,
    String eventUrl,
    String image) {}
