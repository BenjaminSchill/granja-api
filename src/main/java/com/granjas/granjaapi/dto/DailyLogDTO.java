package com.granjas.granjaapi.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.granjas.granjaapi.entities.DailyLog;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class DailyLogDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotNull(message = "Age is mandatory")
	@Positive(message = "Age must be greather than zero")
	private Integer age; 
	
	@PositiveOrZero(message = "Daily mortality must be zero or positive")
	private Integer dailyMortality;
	
	@NotNull(message = "Total weight is mandatory")
	@Positive(message = "Total weight must be greather than zero")
	private Double totalWeight;
	
	@NotNull(message = "Average weight is mandatory")
	@Positive(message = "Average weight mus be greater than zero")
	private Double averageWeight;
	
	@Positive(message = "Feed consumption must be greater than zero")
	private Double feedConsumption;
	
	@Positive(message = "Water consumption must be greater than zero")
	private Double waterConsumption;
	
	private Instant date;
	
	@NotNull(message = "The batch is mandatory")
	@Valid
	private BatchDTO batch;
	
	private List<WeighingDTO> weighings = new ArrayList<>();
	
	public DailyLogDTO() { 
	}
	
	public DailyLogDTO(DailyLog dailyLog) { 
		this.id = dailyLog.getId();
		this.age = dailyLog.getAge();
		this.dailyMortality = dailyLog.getDailyMortality();
		this.totalWeight = dailyLog.getTotalWeight();
		this.averageWeight = dailyLog.getAverageWeight();
		this.feedConsumption = dailyLog.getFeedConsumption();
		this.waterConsumption = dailyLog.getWaterConsumption();
		this.date = dailyLog.getDate();
		
		this.batch = (dailyLog.getBatch() != null) ? new BatchDTO(dailyLog.getBatch()) : null;
	
		this.weighings = dailyLog.getWeighings().stream().map(x -> new WeighingDTO(x)).toList();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getDailyMortality() {
		return dailyMortality;
	}

	public void setDailyMortality(Integer dailyMortality) {
		this.dailyMortality = dailyMortality;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Double getAverageWeight() {
		return averageWeight;
	}

	public void setAverageWeight(Double averageWeight) {
		this.averageWeight = averageWeight;
	}

	public Double getFeedConsumption() {
		return feedConsumption;
	}

	public void setFeedConsumption(Double feedConsumption) {
		this.feedConsumption = feedConsumption;
	}

	public Double getWaterConsumption() {
		return waterConsumption;
	}

	public void setWaterConsumption(Double waterConsumption) {
		this.waterConsumption = waterConsumption;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public BatchDTO getBatch() {
		return batch;
	}

	public void setBatch(BatchDTO batch) {
		this.batch = batch;
	}

	public List<WeighingDTO> getWeighings() {
		return weighings;
	}
}
