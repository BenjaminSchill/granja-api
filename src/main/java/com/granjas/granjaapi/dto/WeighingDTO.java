package com.granjas.granjaapi.dto;

import java.io.Serializable;

import com.granjas.granjaapi.entities.Weighing;

public class WeighingDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer weighingPoint;
	private Integer totalInBox;
	private Double weightInBox;
	
	public WeighingDTO() { 
	}
	
	public WeighingDTO(Weighing weighing) { 
		this.id = weighing.getId();
		this.weighingPoint = weighing.getWeighingPoint();
		this.totalInBox = weighing.getTotalInBox();
		this.weightInBox = weighing.getWeightInBox();
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
}
