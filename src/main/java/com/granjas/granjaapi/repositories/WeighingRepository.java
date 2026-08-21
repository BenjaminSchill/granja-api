package com.granjas.granjaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.granjas.granjaapi.entities.Weighing;

public interface WeighingRepository extends JpaRepository<Weighing, Long>{

}
