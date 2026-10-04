package com.granjas.granjaapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.granjas.granjaapi.entities.Weighing;

public interface WeighingRepository extends JpaRepository<Weighing, Long>{
	
	List<Weighing> findByDailyLogId(Long DailyLogId);
	
}
