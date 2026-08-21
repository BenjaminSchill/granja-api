package com.granjas.granjaapi.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
