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
		Batch entity = new Batch(null, Instant.now(), null, null, null, null, null, null, null);	
		entity.setTotalUponArrival(dto.getTotalUponArrival());
		entity.setTotalWhenLeft(null);
		entity.setTotalOfDeaths(null);
		entity.setStatus(dto.getStatus());
		entity.setAverageInitialWeight(this.calculateInitialWeight(dto, entity));
		
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
		entity.setAverageInitialWeight(this.calculateInitialWeight(dto, entity));
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
	
	private Double calculateInitialWeight(BatchDTO dto, Batch entity) {
		
		Double netWeight = dto.getSampleTotalWeight();
		
		if (netWeight == null) { 
			return null;
		}
		
		if (dto.getSampleBoxesCount() != null && dto.getWeightPerEmptyBox() != null) { 
			netWeight = dto.getSampleTotalWeight() - (dto.getSampleBoxesCount() * dto.getWeightPerEmptyBox()); 
		}
		
		int optionsCount = 0;
		
		if (dto.getSamplePercentage() != null) { 
			optionsCount++;
		}
		
		if (dto.getSampleBirdsCount() != null) { 
			optionsCount++;
		}
		
		if (dto.getBirdsPerBox() != null) { 
			optionsCount++;
		}
		
		if (optionsCount != 1) { 
			throw new BusinessRuleException("You must select and provide data for exactly one sampling modality");
		}
		
		double totalHeavyChicks = 0.0;
		
		if (dto.getSamplePercentage() != null) { 
			totalHeavyChicks = dto.getTotalUponArrival() * (dto.getSamplePercentage() / 100.0);
		}
		else if (dto.getSampleBirdsCount() != null) { 
			totalHeavyChicks = dto.getSampleBirdsCount();
		}
		else if (dto.getBirdsPerBox() != null) { 
			if (dto.getSampleBoxesCount() == null)  {
				throw new BusinessRuleException("Sample boxes count is mandatory for this modality");
			}
			totalHeavyChicks = dto.getBirdsPerBox() * dto.getSampleBoxesCount();
		}
		
		double averageInitialWeight = netWeight / totalHeavyChicks;
		return averageInitialWeight;
	}
	
	@Transactional
	public void closeBatch(Long id) {
		Batch batch = batchRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Batch not found. Id " + id));
			
		batch.setStatus(BatchStatus.CLOSED);
		batchRepository.save(batch);
	}
}
