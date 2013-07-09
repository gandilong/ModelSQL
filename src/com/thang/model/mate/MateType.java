package com.thang.model.mate;

public enum MateType {

	EQUALS(0),
	NotEQUALS(1),
	LIKE(3),
	NotLIKE(4);
	
	
	private int type;
	
	private MateType(int type){
		this.type=type;
	}
	
	
	public int value(){
		return this.type;
	}
	
}
