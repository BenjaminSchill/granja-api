package com.granjas.granjaapi.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
