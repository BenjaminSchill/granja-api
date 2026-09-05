package com.granjas.granjaapi.entities;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_weighing")
public class Weighing implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer weighingPoint;
	private Integer totalInBox;
	private Double weightInBox;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "daily_log_id")
	private DailyLog dailyLog;
	
	public Weighing() {
	}

	public Weighing(Long id, Integer weighingPoint, Integer totalInBox, Double weightInBox, DailyLog dailyLog) {
		this.id = id;
		this.weighingPoint = weighingPoint;
		this.totalInBox = totalInBox;
		this.weightInBox = weightInBox;
		this.dailyLog = dailyLog;
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

	public DailyLog getDailyLog() {
		return dailyLog;
	}

	public void setDailyLog(DailyLog dailyLog) {
		this.dailyLog = dailyLog;
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
		Weighing other = (Weighing) obj;
		return Objects.equals(id, other.id);
	}
}
