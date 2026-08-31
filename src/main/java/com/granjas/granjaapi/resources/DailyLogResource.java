package com.granjas.granjaapi.resources;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.granjas.granjaapi.entities.DailyLog;
import com.granjas.granjaapi.services.DailyLogService;

@RestController
@RequestMapping(value = "/daily-logs")
public class DailyLogResource {
	
	private final DailyLogService dailyLogService;

	public DailyLogResource(DailyLogService dailyLogService) { 
		this.dailyLogService = dailyLogService;
	}
	
	@GetMapping
	public ResponseEntity<List<DailyLog>> findAll() { 
		List<DailyLog> list = dailyLogService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<DailyLog> findById(@PathVariable Long id) { 
		DailyLog dailyLog = dailyLogService.findById(id);
		return ResponseEntity.ok().body(dailyLog);
	}
	
	@PostMapping
	public ResponseEntity<DailyLog> insert(@RequestBody DailyLog dailyLog) { 
		dailyLog = dailyLogService.insert(dailyLog);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	            .buildAndExpand(dailyLog.getId()).toUri();
		return ResponseEntity.created(uri).body(dailyLog);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) { 
		dailyLogService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
