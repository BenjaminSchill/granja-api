package com.granjas.granjaapi.entities.enums;

public enum BatchStatus {
	ACTIVE(1),
	CLOSED(2);
    
	private int code;
	private BatchStatus(int code) { 
		this.code = code;
	}
	
	public int getCode() { 
		return code;
	}
	
	public static BatchStatus valueOf(int code) { 
		for (BatchStatus value : BatchStatus.values()) { 
			if (value.getCode() == code) { 
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid BatchStatus code");
	}
}
