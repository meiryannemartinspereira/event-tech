package com.eyetech.events.dtos;

import java.math.BigDecimal;
import java.sql.Date;

public record CuponRequestDTO(String code, BigDecimal discount, Date valid) {
    
}
