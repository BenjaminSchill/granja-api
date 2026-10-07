package com.granjas.granjaapi.config;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.granjas.granjaapi.entities.Batch;
import com.granjas.granjaapi.entities.DailyLog;
import com.granjas.granjaapi.entities.Farm;
import com.granjas.granjaapi.entities.Weighing;
import com.granjas.granjaapi.entities.enums.BatchStatus;
import com.granjas.granjaapi.repositories.BatchRepository;
import com.granjas.granjaapi.repositories.DailyLogRepository;
import com.granjas.granjaapi.repositories.FarmRepository;
import com.granjas.granjaapi.repositories.WeighingRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{
	
	private final WeighingRepository weighingRepository;
	private final DailyLogRepository dailyLogRepository;
	private final BatchRepository batchRepository;
	private final FarmRepository farmRepository;

	public TestConfig(FarmRepository farmRepository, BatchRepository batchRepository,
			DailyLogRepository dailyLogRepository, WeighingRepository weighingRepository) { 
		this.farmRepository = farmRepository;
		this.batchRepository = batchRepository;
		this.dailyLogRepository = dailyLogRepository;
		this.weighingRepository = weighingRepository;
	}
	

	@Override
	public void run(String... args) throws Exception {
	
	}
}
