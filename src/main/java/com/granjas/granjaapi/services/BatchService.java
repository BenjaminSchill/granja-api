package com.granjas.granjaapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.granjas.granjaapi.dto.BatchDTO;
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
	
	public List<BatchDTO> findAll() { 
		List<Batch> list = batchRepository.findAll();
		return list.stream().map(x -> new BatchDTO(x)).toList();
	}
	
	public BatchDTO findById(Long id) { 
		Batch entity = batchRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found. Id " + id));
		return new BatchDTO(entity);
	}
	
	public BatchDTO insert(Batch batch) { 
		batch = batchRepository.save(batch);
		return new BatchDTO(batch);
	}
	
	@Transactional
	public BatchDTO update(Long id, Batch batch) { 
		Batch entity = batchRepository.getReferenceById(id);
		entity.setStatus(batch.getStatus());
		entity.setTotalUponArrival(batch.getTotalUponArrival());
		entity = batchRepository.save(entity);
		return new BatchDTO(entity);
	}
	
	public void delete(Long id) { 
		batchRepository.deleteById(id);
	}
}
