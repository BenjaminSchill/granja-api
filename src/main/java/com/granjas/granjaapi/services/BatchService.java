package com.granjas.granjaapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.granjas.granjaapi.entities.Batch;
import com.granjas.granjaapi.repositories.BatchRepository;
import com.granjas.granjaapi.services.exceptions.ResourceNotFoundException;

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
		return batchRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found. Id " + id));
	}
	
	public Batch insert(Batch batch) { 
		return batchRepository.save(batch);
	}
	
	public void delete(Long id) { 
		batchRepository.deleteById(id);
	}
}
