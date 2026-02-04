package com.eyetech.events.dtos;

import java.sql.Date;
import java.util.List;

public record EventDetailResponseDTO(
    Long id,
    String title,
    String description,
    Date date,
    Boolean remote,
    String eventUrl,
    String image,
    String city,
    List<CuponResponseDTO> cupons
) {}
