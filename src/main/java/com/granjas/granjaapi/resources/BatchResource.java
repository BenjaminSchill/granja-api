package com.granjas.granjaapi.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
