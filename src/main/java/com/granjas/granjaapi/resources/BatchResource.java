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
	public ResponseEntity<List<Batch>> findAll() { 
		List<Batch> list = batchService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Batch> findById(@PathVariable Long id) { 
		Batch batch = batchService.findById(id);
		return ResponseEntity.ok().body(batch);
	}
	
	@PostMapping
	public ResponseEntity<Batch> insert(@RequestBody Batch batch) { 
		batch = batchService.insert(batch);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(batch.getId()).toUri();
		return ResponseEntity.created(uri).body(batch);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Batch> update(@PathVariable Long id, @RequestBody Batch batch) { 
		batch = batchService.update(id, batch);
		return ResponseEntity.ok().body(batch);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) { 
		batchService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
