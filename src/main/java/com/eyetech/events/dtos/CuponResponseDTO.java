package com.eyetech.events.dtos;

import java.math.BigDecimal;
import java.sql.Date;

public record CuponResponseDTO(
    String code,
    BigDecimal discount,
    Date valid
) {}
