package com.eyetech.events.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eyetech.events.model.Cupon;

public interface CuponRepository extends JpaRepository<Cupon, Long>{
    
}
