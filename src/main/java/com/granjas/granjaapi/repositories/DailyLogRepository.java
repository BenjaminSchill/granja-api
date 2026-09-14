package com.granjas.granjaapi.repositories;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.granjas.granjaapi.entities.Batch;
import com.granjas.granjaapi.entities.DailyLog;

public interface DailyLogRepository extends JpaRepository<DailyLog, Long> {
	
	@Query("SELECT obj FROM DailyLog obj JOIN FETCH obj.weighings")
	List<DailyLog> findAllWithWeighings();
	
	@EntityGraph(attributePaths = {"weighings", "batch.farm"})
	Optional<DailyLog> findById(Long id);

	List<DailyLog> findByBatch(com.granjas.granjaapi.entities.Batch batch);
	
	Optional<DailyLog> findByBatchAndDate(Batch batch, Instant date);
}
