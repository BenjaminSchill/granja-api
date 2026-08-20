package com.granjas.granjaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.granjas.granjaapi.entities.Farm;

public interface FarmRepository extends JpaRepository<Farm, Long> {

}
