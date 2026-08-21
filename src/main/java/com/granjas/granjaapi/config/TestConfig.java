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
		
		Farm f1 = new Farm(null, "1", 40000);
		Farm f2 = new Farm(null, "2", 40000);
		Farm f3 = new Farm(null, "426", 12000);
		
		farmRepository.saveAll(Arrays.asList(f1, f2, f3));
		
		Batch b1 = new Batch(null, Instant.now(), Instant.now().plus(30, ChronoUnit.DAYS), 40000, 32543, 7243, BatchStatus.CLOSED, f1);
		Batch b2 = new Batch(null, Instant.now(), null, 40000, null, 150, BatchStatus.ACTIVE, f2);
		Batch b3 = new Batch(null, Instant.now().minus(50, ChronoUnit.DAYS), Instant.now().minus(5, ChronoUnit.DAYS), 12000, 11450, 550, BatchStatus.CLOSED, f3);

		batchRepository.saveAll(Arrays.asList(b1, b2, b3));
		
		DailyLog dl1 = new DailyLog(null, 12, 100, 112.43, 0.132, 122.54, 224.0, Instant.now().plus(12, ChronoUnit.DAYS), b2);
		DailyLog dl2 = new DailyLog(null, 25, 42, 452.10, 0.450, 2500.0, 5200.0, Instant.now().plus(25, ChronoUnit.DAYS), b2);
		DailyLog dl3 = new DailyLog(null, 38, 12, 2100.50, 2.150, 4800.0, 9600.0, Instant.now().minus(7, ChronoUnit.DAYS), b3);
		
		dailyLogRepository.saveAll(Arrays.asList(dl1, dl2, dl3));
		
		Weighing w1 = new Weighing(null, 1, 10, 12.0, dl2);
		Weighing w2 = new Weighing(null, 4, 10, 15.2, dl1);
		Weighing w3 = new Weighing(null, 6, 10, 11.5, dl3);
		Weighing w4 = new Weighing(null, 3, 10, 11.3, dl2);
		Weighing w5 = new Weighing(null, 2, 10, 15.0, dl2);
		
		weighingRepository.saveAll(Arrays.asList(w1, w2, w3, w4, w5));
	
	}
}
