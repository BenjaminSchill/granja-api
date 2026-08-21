package com.granjas.granjaapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.granjas.granjaapi.entities.Batch;
import com.granjas.granjaapi.repositories.BatchRepository;

@Service
public class BatchService {
	
	private final BatchRepository batchRepository;

	public BatchService(BatchRepository batchRepository) { 
		this.batchRepository = batchRepository;
	}
	
	public List<Batch> findAll() { 
		return batchRepository.findAll();
	}
	
	public Batch insert(Batch batch) { 
		return batchRepository.save(batch);
	}
}
