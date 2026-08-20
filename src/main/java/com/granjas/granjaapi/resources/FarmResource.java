package com.granjas.granjaapi.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.granjas.granjaapi.entities.Farm;
import com.granjas.granjaapi.services.FarmService;

@RestController
@RequestMapping(value = "/farms")
public class FarmResource {
	
	private final FarmService farmService;

	public FarmResource(FarmService farmService) { 
		this.farmService = farmService;
	}
	
	@GetMapping
	public ResponseEntity<List<Farm>> findAll() { 
		List<Farm> list = farmService.findAll();
		return ResponseEntity.ok().body(list);
	}
}
