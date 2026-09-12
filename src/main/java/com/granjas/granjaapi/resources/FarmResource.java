package com.granjas.granjaapi.resources;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.granjas.granjaapi.dto.FarmDTO;
import com.granjas.granjaapi.services.FarmService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/farms")
public class FarmResource {
	
	private final FarmService farmService;

	public FarmResource(FarmService farmService) { 
		this.farmService = farmService;
	}
	
	@GetMapping
	public ResponseEntity<List<FarmDTO>> findAll() { 
		List<FarmDTO> list = farmService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<FarmDTO> findById(@PathVariable Long id) { 
		FarmDTO farm = farmService.findById(id);
		return ResponseEntity.ok().body(farm);
	}
	
	@PostMapping
	public ResponseEntity<FarmDTO> insert(@Valid @RequestBody FarmDTO dto) { 
		FarmDTO farmDTO = farmService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	            .buildAndExpand(farmDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(farmDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<FarmDTO> update(@PathVariable Long id, @Valid @RequestBody FarmDTO dto) { 
		FarmDTO entity = farmService.update(id, dto);
		return ResponseEntity.ok().body(entity);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		farmService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
