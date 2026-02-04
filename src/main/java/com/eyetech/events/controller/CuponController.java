package com.eyetech.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eyetech.events.dtos.CuponRequestDTO;
import com.eyetech.events.model.Cupon;
import com.eyetech.events.services.CuponService;

@RestController
@RequestMapping("/api/cupon")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<Cupon> addCuponsToEvent(@PathVariable Long eventId, @RequestBody CuponRequestDTO cuponDto) {
        Cupon cupon = cuponService.addCuponToEvent(eventId, cuponDto);

        return ResponseEntity.ok(cupon);
    }
    
}
