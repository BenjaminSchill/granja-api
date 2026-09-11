package com.granjas.granjaapi.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.granjas.granjaapi.dto.FarmDTO;
import com.granjas.granjaapi.entities.Farm;
import com.granjas.granjaapi.repositories.FarmRepository;
import com.granjas.granjaapi.services.exceptions.DatabaseException;
import com.granjas.granjaapi.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class FarmService {
	
	private final FarmRepository farmRepository;

	public FarmService(FarmRepository farmRepository) { 
		this.farmRepository = farmRepository;
	}
	
	public List<FarmDTO> findAll() { 
		List<Farm> list = farmRepository.findAll();
		return list.stream().map(x -> new FarmDTO(x)).toList();
	}
	
	public FarmDTO findById(Long id) {
		Farm entity = farmRepository.findById(id)
		.orElseThrow(() -> new ResourceNotFoundException("Resource not found. Id " + id));
		return new FarmDTO(entity);
	}
	
	public FarmDTO insert(FarmDTO dto) { 
		Farm entity = new Farm();
		entity.setName(dto.getName());
		entity.setCapacity(dto.getCapacity());
		entity = farmRepository.save(entity);
		return new FarmDTO(entity);
	}
	
	@Transactional
	public FarmDTO update(Long id, FarmDTO dto) { 
		Farm entity = farmRepository.getReferenceById(id);
		entity.setName(dto.getName());
		entity.setCapacity(dto.getCapacity());
		entity = farmRepository.save(entity);
		return new FarmDTO(entity);
	}
	
	public void delete(Long id) { 
		try { 
			farmRepository.deleteById(id);
		} 
		catch (DataIntegrityViolationException e) { 
			throw new DatabaseException(e.getMessage());
		}
	}
}
