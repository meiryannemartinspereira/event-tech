package com.eyetech.events.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eyetech.events.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    
}
