package com.thang.model.mate;

public enum MateType {

	EQUALS(0),
	LIKE(1);
	
	private int type;
	
	private MateType(int type){
		this.type=type;
	}
	
	
	public int value(){
		return this.type;
	}
	
}
