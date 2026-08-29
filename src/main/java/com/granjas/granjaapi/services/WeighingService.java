package com.granjas.granjaapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
		Optional<Weighing> obj = weighingRepository.findById(id);
		return obj.get();
	}
	
	public Weighing insert(Weighing weighing) { 
		DailyLog dailyLog = dailyLogRepository.findByIdWithWeighings(weighing.getDailyLog().getId()).get();
		double totalWeight = 0.0;
		int totalChickens = 0; 
		
		for (Weighing w : dailyLog.getWeighings()) {
			totalWeight += w.getWeightInBox();
			totalChickens += w.getTotalInBox();
		}
		totalWeight += weighing.getWeightInBox();
		totalChickens += weighing.getTotalInBox();
		
		double averageWeight = totalWeight / (double) totalChickens;
		dailyLog.setAverageWeight(averageWeight);
		dailyLog.setTotalWeight(totalWeight);
		
		dailyLogRepository.save(dailyLog);
		
		return weighingRepository.save(weighing);
	}
}
