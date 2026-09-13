package com.granjas.granjaapi.dto;

import java.io.Serializable;
import java.time.Instant;

import com.granjas.granjaapi.entities.Batch;
import com.granjas.granjaapi.entities.enums.BatchStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BatchDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Instant entryDateTime;
	private Instant exitDateTime;
	
	@NotNull(message = "Total upon arrival is mandatory")
	@Positive(message = "Total upon arrival must be greater than zero")
	private Integer totalUponArrival;
	
	private Integer totalWhenLeft;
	private Integer totalOfDeaths;
	
	@NotNull(message = "Batch status is mandatory")
	private BatchStatus status;
	
	@NotNull(message = "The farm is mandatory")
	private FarmDTO farm;
	
	public BatchDTO() { 
	}
	
	public BatchDTO(Batch batch)  { 
		this.id = batch.getId();
		this.entryDateTime = batch.getEntryDateTime();
		this.exitDateTime = batch.getExitDateTime();
		this.totalUponArrival = batch.getTotalUponArrival();
		this.totalWhenLeft = batch.getTotalWhenLeft();
		this.totalOfDeaths = batch.getTotalOfDeaths();
		this.status = batch.getStatus();
		
		this.farm = (batch.getFarm() != null) ? new FarmDTO(batch.getFarm()) : null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getEntryDateTime() {
		return entryDateTime;
	}

	public void setEntryDateTime(Instant entryDateTime) {
		this.entryDateTime = entryDateTime;
	}

	public Instant getExitDateTime() {
		return exitDateTime;
	}

	public void setExitDateTime(Instant exitDateTime) {
		this.exitDateTime = exitDateTime;
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

	public BatchStatus getStatus() {
		return status;
	}

	public void setStatus(BatchStatus status) {
		this.status = status;
	}

	public FarmDTO getFarm() {
		return farm;
	}

	public void setFarm(FarmDTO farm) {
		this.farm = farm;
	}
}
