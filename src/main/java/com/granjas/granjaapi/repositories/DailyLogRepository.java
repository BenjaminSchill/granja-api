package com.granjas.granjaapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.granjas.granjaapi.entities.DailyLog;

public interface DailyLogRepository extends JpaRepository<DailyLog, Long> {
	
	@Query("SELECT obj FROM DailyLog obj JOIN FETCH obj.weighings")
	List<DailyLog> findAllWithWeighings();
	
	@Query("SELECT obj FROM DailyLog obj JOIN FETCH obj.weighings WHERE obj.id = :id")
	Optional<DailyLog> findByIdWithWeighings(Long id);
}
