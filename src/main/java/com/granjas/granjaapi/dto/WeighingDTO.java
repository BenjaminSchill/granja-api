package com.granjas.granjaapi.dto;

import java.io.Serializable;

import com.granjas.granjaapi.entities.Weighing;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class WeighingDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotNull(message = "Weighing point is mandatory")
	@Positive(message = "Weighing point must be greater than zero")
	private Integer weighingPoint;
	
	@NotNull(message = "Total in box is mandatory")
	@Positive(message = "Total in box must be greater than zero")
	private Integer totalInBox;
	
	@NotNull(message = "Weight in box is mandatory")
	@Positive(message = "Weight in box must be greater than zero")
	private Double weightInBox;
	
	@NotNull(message = "Daily log id is mandatory")
	@Positive(message = "Daily log id must be greater than zero")
	private Long dailyLogId;
	
	public WeighingDTO() { 
	}
	
	public WeighingDTO(Weighing weighing) { 
		this.id = weighing.getId();
		this.weighingPoint = weighing.getWeighingPoint();
		this.totalInBox = weighing.getTotalInBox();
		this.weightInBox = weighing.getWeightInBox();
		this.dailyLogId = (weighing.getDailyLog() != null) ? weighing.getDailyLog().getId() : null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getWeighingPoint() {
		return weighingPoint;
	}

	public void setWeighingPoint(Integer weighingPoint) {
		this.weighingPoint = weighingPoint;
	}

	public Integer getTotalInBox() {
		return totalInBox;
	}

	public void setTotalInBox(Integer totalInBox) {
		this.totalInBox = totalInBox;
	}

	public Double getWeightInBox() {
		return weightInBox;
	}

	public void setWeightInBox(Double weightInBox) {
		this.weightInBox = weightInBox;
	}

	public Long getDailyLogId() {
		return dailyLogId;
	}

	public void setDailyLogId(Long dailyLogId) {
		this.dailyLogId = dailyLogId;
	}
}
