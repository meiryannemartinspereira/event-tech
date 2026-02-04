package com.eyetech.events.dtos;

import java.sql.Date;

public record CuponRequestDTO(String code, Integer discount, Date valid) {
    
}
