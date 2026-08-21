package com.granjas.granjaapi.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_daily_log")
public class DailyLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	private Integer age; 
	private Integer dailyMortality;
	private Double totalWeight;
	private Double averageWeight;
	private Double feedConsumption;
	private Double waterConsumption;
	private Instant date;
	
	@ManyToOne
	@JoinColumn(name = "batch_id")
	private Batch batch;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "dailyLog")
	private List<Weighing> weighings = new ArrayList<>();
	
	public DailyLog() { 
	}

	public DailyLog(Long id, Integer age, Integer dailyMortality, Double totalWeight, Double averageWeight,
			Double feedConsumption, Double waterConsumption, Instant date, Batch batch) {
		super();
		this.id = id;
		this.age = age;
		this.dailyMortality = dailyMortality;
		this.totalWeight = totalWeight;
		this.averageWeight = averageWeight;
		this.feedConsumption = feedConsumption;
		this.waterConsumption = waterConsumption;
		this.date = date;
		this.batch = batch;
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

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public List<Weighing> getWeighings() {
		return weighings;
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
		DailyLog other = (DailyLog) obj;
		return Objects.equals(id, other.id);
	}
}
