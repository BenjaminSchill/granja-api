package com.granjas.granjaapi.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.granjas.granjaapi.entities.DailyLog;
import com.granjas.granjaapi.entities.Weighing;
import com.granjas.granjaapi.repositories.DailyLogRepository;
import com.granjas.granjaapi.repositories.WeighingRepository;

@Service
public class WeighingService {
	
	private final DailyLogRepository dailyLogRepository;
	private final WeighingRepository weighingRepository;

	public WeighingService(WeighingRepository weighingRepository, DailyLogRepository dailyLogRepository) { 
		this.weighingRepository = weighingRepository;
		this.dailyLogRepository = dailyLogRepository;
	}
	
	public List<Weighing> findAll() { 
		return weighingRepository.findAll();
	}
	
	public Weighing findById(Long id) { 
		return weighingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Weighing not found"));
	}
	
	@Transactional
	public Weighing insert(Weighing weighing) { 
		weighing = weighingRepository.save(weighing);
		updateDailyLogCalculations(weighing.getDailyLog().getId());
		return weighing;
	}
	
	@Transactional
	public void delete(Long id) { 
		Weighing weighing = weighingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Weighing not found"));
		
		Long dailyLogId = weighing.getDailyLog().getId();
		
		weighingRepository.delete(weighing);
		
		updateDailyLogCalculations(dailyLogId);
	}
	
	private void updateDailyLogCalculations(Long dailyLogId) { 
		DailyLog dailyLog = dailyLogRepository.findById(dailyLogId)
				.orElseThrow(() -> new RuntimeException("DailyLog not found"));
		
		double totalWeight = 0.0;
		int totalChickens = 0; 
		
		for (Weighing w : dailyLog.getWeighings()) {
			totalWeight += w.getWeightInBox();
			totalChickens += w.getTotalInBox();
		}
		
		if (totalChickens > 0) {
		    double averageWeight = totalWeight / (double) totalChickens;
		    dailyLog.setAverageWeight(averageWeight);
		    dailyLog.setTotalWeight(totalWeight);
		}
		else {
		    dailyLog.setAverageWeight(0.0);
		    dailyLog.setTotalWeight(0.0);
		}
		dailyLogRepository.save(dailyLog);
	}
}
