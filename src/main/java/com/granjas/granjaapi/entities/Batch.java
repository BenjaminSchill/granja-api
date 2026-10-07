	package com.granjas.granjaapi.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.granjas.granjaapi.entities.enums.BatchStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_batch")
public class Batch implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant entryDateTime;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant exitDateTime; 
	private Integer totalUponArrival;
	private Integer totalWhenLeft;
	private Integer totalOfDeaths;
	private Double totalFeedConsumption;
	private Double totalWaterConsumption;
	private Double averageInitialWeight;
	
	@Enumerated(EnumType.STRING)
	private BatchStatus status; 
	
	@ManyToOne
	@JoinColumn(name = "farm_id")
	private Farm farm;
	
	public Batch() { 
	}

	public Batch(Long id, Instant entryDateTime, Instant exitDateTime, Integer totalUponArrival,
			Integer totalWhenLeft, Integer totalOfDeaths, Double averageInitialWeight,
			Double totalFeedConsumption, Double totalWaterConsumption, BatchStatus status, Farm farm) {
	
		this.id = id;
		this.entryDateTime = entryDateTime;
		this.exitDateTime = exitDateTime;
		this.totalUponArrival = totalUponArrival;
		this.totalWhenLeft = totalWhenLeft;
		this.totalOfDeaths = totalOfDeaths;
		this.totalFeedConsumption = totalFeedConsumption;
		this.totalWaterConsumption = totalWaterConsumption;
		this.averageInitialWeight = averageInitialWeight;
		this.status = status;
		this.farm = farm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTotalUponArrival() {
		return totalUponArrival;
	}

	public void setTotalUponArrival(Integer totalUponArrival) {
		this.totalUponArrival = totalUponArrival;
	}

	public Integer getTotalWhenLeft() {
		return totalWhenLeft;
	}

	public void setTotalWhenLeft(Integer totalWhenLeft) {
		this.totalWhenLeft = totalWhenLeft;
	}

	public Integer getTotalOfDeaths() {
		return totalOfDeaths;
	}

	public void setTotalOfDeaths(Integer totalOfDeaths) {
		this.totalOfDeaths = totalOfDeaths;
	}
	
	public Double getTotalFeedConsumption() {
		return totalFeedConsumption;
	}

	public void setTotalFeedConsumption(Double totalFeedConsumption) {
		this.totalFeedConsumption = totalFeedConsumption;
	}

	public Double getTotalWaterConsumption() {
		return totalWaterConsumption;
	}

	public void setTotalWaterConsumption(Double totalWaterConsumption) {
		this.totalWaterConsumption = totalWaterConsumption;
	}

	public Double getAverageInitialWeight() {
		return averageInitialWeight;
	}

	public void setAverageInitialWeight(Double averageInitialWeight) {
		this.averageInitialWeight = averageInitialWeight;
	}

	public BatchStatus getStatus() {
		return status;
	}

	public void setStatus(BatchStatus status) {
		this.status = status;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public Instant getEntryDateTime() {
		return entryDateTime;
	}

	public Instant getExitDateTime() {
		return exitDateTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Batch other = (Batch) obj;
		return Objects.equals(id, other.id);
	}
	
	public void close() { 
		this.status = BatchStatus.CLOSED;
		this.exitDateTime = Instant.now();
	}
	
	public void compileMetrics(List<DailyLog> logs) { 
		int totalDeaths = 0;
		double totalFeed = 0.0;
		double totalWater = 0.0;
		
		for (DailyLog dl : logs) { 
			if (dl.getDailyMortality() != null) { 
				totalDeaths += dl.getDailyMortality();
			}
			if (dl.getFeedConsumption() != null) { 
				totalFeed += dl.getFeedConsumption();
			}
			if (dl.getWaterConsumption() != null) { 
				totalWater += dl.getWaterConsumption();
			}
		}
		
		this.totalOfDeaths = totalDeaths;
		this.totalFeedConsumption = totalFeed;
		this.totalWaterConsumption = totalWater;
		this.totalWhenLeft = (this.totalUponArrival != null) ? (this.totalUponArrival - totalDeaths) : 0;
	}
}
