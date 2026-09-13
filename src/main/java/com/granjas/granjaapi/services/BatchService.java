package com.granjas.granjaapi.services;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.granjas.granjaapi.dto.BatchDTO;
import com.granjas.granjaapi.entities.Batch;
import com.granjas.granjaapi.entities.Farm;
import com.granjas.granjaapi.entities.enums.BatchStatus;
import com.granjas.granjaapi.repositories.BatchRepository;
import com.granjas.granjaapi.services.exceptions.BusinessRuleException;
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
	
	public BatchDTO insert(BatchDTO dto) { 
		Batch entity = new Batch(null, Instant.now(), null, null, null, null, null, null);	
		entity.setTotalUponArrival(dto.getTotalUponArrival());
		entity.setTotalWhenLeft(null);
		entity.setTotalOfDeaths(null);
		entity.setStatus(dto.getStatus());
		
		if (dto.getFarm() != null) { 
			Farm farm = new Farm();
			farm.setId(dto.getFarm().getId());
			entity.setFarm(farm);
		}
		
		entity = batchRepository.save(entity);
		return new BatchDTO(entity);
	}
	
	@Transactional
	public BatchDTO update(Long id, BatchDTO dto) { 
		Batch entity = batchRepository.getReferenceById(id);
		
		if (entity.getStatus() == BatchStatus.CLOSED) { 
			throw new BusinessRuleException("Business Rule Error: Cannot modify data of a CLOSED batch");
		}
		
		entity.setStatus(dto.getStatus());
		entity.setTotalUponArrival(dto.getTotalUponArrival());
		entity = batchRepository.save(entity);
		return new BatchDTO(entity);
	}
	
	public void delete(Long id) { 
		Batch entity = batchRepository.getReferenceById(id);
		
		if (entity.getStatus() == BatchStatus.CLOSED) { 
			throw new BusinessRuleException("Business Rule Error: Cannot modify data of a CLOSED batch");
		}
		
		batchRepository.deleteById(id);
	}
}
