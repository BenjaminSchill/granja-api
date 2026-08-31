package com.granjas.granjaapi.services;

import java.util.List;
import java.util.Optional;

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
	
	public Batch findById(Long id) { 
		Optional<Batch> obj = batchRepository.findById(id);
		return obj.get();
	}
	
	public Batch insert(Batch batch) { 
		return batchRepository.save(batch);
	}
	
	public void delete(Long id) { 
		batchRepository.deleteById(id);
	}
}
