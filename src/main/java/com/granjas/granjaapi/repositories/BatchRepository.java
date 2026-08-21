package com.granjas.granjaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.granjas.granjaapi.entities.Batch;

public interface BatchRepository extends JpaRepository<Batch, Long>{

}
