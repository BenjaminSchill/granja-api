package com.granjas.granjaapi.config;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.granjas.granjaapi.entities.Batch;
import com.granjas.granjaapi.entities.Farm;
import com.granjas.granjaapi.entities.enums.BatchStatus;
import com.granjas.granjaapi.repositories.BatchRepository;
import com.granjas.granjaapi.repositories.FarmRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{
	
	private final BatchRepository batchRepository;
	private final FarmRepository farmRepository;

	public TestConfig(FarmRepository farmRepository, BatchRepository batchRepository) { 
		this.farmRepository = farmRepository;
		this.batchRepository = batchRepository;
	}
	

	@Override
	public void run(String... args) throws Exception {
		
		Farm f1 = new Farm(null, "1", 40000);
		Farm f2 = new Farm(null, "2", 40000);
		Farm f3 = new Farm(null, "426", 12000);
		
		farmRepository.saveAll(Arrays.asList(f1, f2, f3));
		
		Batch b1 = new Batch(null, Instant.now(), Instant.now().plus(30, ChronoUnit.DAYS), 40000, 32543, 7243, BatchStatus.CLOSED, f1);
		Batch b2 = new Batch(null, Instant.now(), null, 40000, null, 150, BatchStatus.ACTIVE, f2);
		Batch b3 = new Batch(null, Instant.now().minus(50, ChronoUnit.DAYS), Instant.now().minus(5, ChronoUnit.DAYS), 12000, 11450, 550, BatchStatus.CLOSED, f3);

		batchRepository.saveAll(Arrays.asList(b1, b2, b3));
	}
}
