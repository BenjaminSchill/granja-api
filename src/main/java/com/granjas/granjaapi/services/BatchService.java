package com.granjas.granjaapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.granjas.granjaapi.entities.Batch;
import com.granjas.granjaapi.repositories.BatchRepository;
import com.granjas.granjaapi.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

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
	
	@Transactional
	public Batch update(Long id, Batch batch) { 
		Batch entity = batchRepository.getReferenceById(id);
		entity.setStatus(batch.getStatus());
		entity.setTotalUponArrival(batch.getTotalUponArrival());
		return batchRepository.save(entity);
	}
	
	public void delete(Long id) { 
		batchRepository.deleteById(id);
	}
}
