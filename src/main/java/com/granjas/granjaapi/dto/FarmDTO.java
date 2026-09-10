package com.granjas.granjaapi.dto;

import java.io.Serializable;

import com.granjas.granjaapi.entities.Farm;

public class FarmDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Integer capacity;
	
	public FarmDTO() { 
	}
	
	public FarmDTO(Farm entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.capacity = entity.getCapacity();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
}
