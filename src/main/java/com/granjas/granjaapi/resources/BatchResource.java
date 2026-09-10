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

import com.granjas.granjaapi.dto.BatchDTO;
import com.granjas.granjaapi.entities.Batch;
import com.granjas.granjaapi.services.BatchService;

@RestController
@RequestMapping(value = "/batches")
public class BatchResource {
	
	private final BatchService batchService;

	public BatchResource(BatchService batchService) { 
		this.batchService = batchService;
	}
	
	@GetMapping
	public ResponseEntity<List<BatchDTO>> findAll() { 
		List<BatchDTO> list = batchService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<BatchDTO> findById(@PathVariable Long id) { 
		BatchDTO batch = batchService.findById(id);
		return ResponseEntity.ok().body(batch);
	}
	
	@PostMapping
	public ResponseEntity<BatchDTO> insert(@RequestBody Batch batch) { 
		BatchDTO batchDTO = batchService.insert(batch);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(batchDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(batchDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<BatchDTO> update(@PathVariable Long id, @RequestBody Batch batch) { 
		BatchDTO entity = batchService.update(id, batch);
		return ResponseEntity.ok().body(entity);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) { 
		batchService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
