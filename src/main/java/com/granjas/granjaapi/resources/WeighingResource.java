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

import com.granjas.granjaapi.dto.WeighingDTO;
import com.granjas.granjaapi.services.WeighingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/weighings")
public class WeighingResource {

	private final WeighingService weighingService;

	public WeighingResource(WeighingService weighingService) { 
		this.weighingService = weighingService;
	}
	
	@GetMapping
	public ResponseEntity<List<WeighingDTO>> findAll() { 
		List<WeighingDTO> list = weighingService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<WeighingDTO> findById(@PathVariable Long id) { 
		WeighingDTO weighing = weighingService.findById(id);
		return ResponseEntity.ok().body(weighing);
	}
	
	@PostMapping
	public ResponseEntity<WeighingDTO> insert(@Valid @RequestBody WeighingDTO dto) { 
		WeighingDTO weighingDTO = weighingService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	            .buildAndExpand(weighingDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(weighingDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<WeighingDTO> update(@PathVariable Long id, @Valid @RequestBody WeighingDTO weighing) { 
		WeighingDTO entity = weighingService.update(id, weighing);
		return ResponseEntity.ok().body(entity);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) { 
		weighingService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
