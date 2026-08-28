package com.granjas.granjaapi.resources;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.granjas.granjaapi.entities.Weighing;
import com.granjas.granjaapi.services.WeighingService;

@RestController
@RequestMapping(value = "/weighings")
public class WeighingResource {

	private final WeighingService weighingService;

	public WeighingResource(WeighingService weighingService) { 
		this.weighingService = weighingService;
	}
	
	@GetMapping
	public ResponseEntity<List<Weighing>> findAll() { 
		List<Weighing> list = weighingService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping
	public ResponseEntity<Weighing> insert(@RequestBody Weighing weighing) { 
		weighing = weighingService.insert(weighing);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	            .buildAndExpand(weighing.getId()).toUri();
		return ResponseEntity.created(uri).body(weighing);
	}
}
