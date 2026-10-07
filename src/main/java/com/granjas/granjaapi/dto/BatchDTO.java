package com.granjas.granjaapi.dto;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.granjas.granjaapi.entities.Batch;
import com.granjas.granjaapi.entities.enums.BatchStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonPropertyOrder({
	"id",
	"entryDateTime",
	"exitDateTime",
	"status",
	"totalUponArrival",
	"totalWhenLeft",
	"averageInitialWeight",
	"totalOfDeaths",
	"totalFeedConsumption",
	"totalWaterConsumption",
	"farm"
})
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
	private Double totalFeedConsumption;
	private Double totalWaterConsumption;
	private Double averageInitialWeight;
	private Double sampleTotalWeight;
	private Double samplePercentage;
	private Integer sampleBirdsCount;
	private Integer sampleBoxesCount;
	private Integer birdsPerBox;
	private Double weightPerEmptyBox;

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
		this.totalFeedConsumption = batch.getTotalFeedConsumption();
		this.totalWaterConsumption = batch.getTotalWaterConsumption();
		this.averageInitialWeight = batch.getAverageInitialWeight();
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

	public Double getSampleTotalWeight() {
		return sampleTotalWeight;
	}

	public void setSampleTotalWeight(Double sampleTotalWeight) {
		this.sampleTotalWeight = sampleTotalWeight;
	}

	public Double getSamplePercentage() {
		return samplePercentage;
	}

	public void setSamplePercentage(Double samplePercentage) {
		this.samplePercentage = samplePercentage;
	}

	public Integer getSampleBirdsCount() {
		return sampleBirdsCount;
	}

	public void setSampleBirdsCount(Integer sampleBirdsCount) {
		this.sampleBirdsCount = sampleBirdsCount;
	}

	public Integer getSampleBoxesCount() {
		return sampleBoxesCount;
	}

	public void setSampleBoxesCount(Integer sampleBoxesCount) {
		this.sampleBoxesCount = sampleBoxesCount;
	}

	public Integer getBirdsPerBox() {
		return birdsPerBox;
	}

	public void setBirdsPerBox(Integer birdsPerBox) {
		this.birdsPerBox = birdsPerBox;
	}

	public Double getWeightPerEmptyBox() {
		return weightPerEmptyBox;
	}

	public void setWeightPerEmptyBox(Double weightPerEmptyBox) {
		this.weightPerEmptyBox = weightPerEmptyBox;
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
