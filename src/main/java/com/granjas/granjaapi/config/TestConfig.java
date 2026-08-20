package com.granjas.granjaapi.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.granjas.granjaapi.entities.Farm;
import com.granjas.granjaapi.repositories.FarmRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{
	
	private final FarmRepository farmRepository;

	public TestConfig(FarmRepository farmRepository) { 
		this.farmRepository = farmRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		
		Farm f1 = new Farm(null, "1", 40000);
		Farm f2 = new Farm(null, "2", 40000);
		Farm f3 = new Farm(null, "426", 12000);
		
		farmRepository.saveAll(Arrays.asList(f1, f2, f3));
		
	}
}
