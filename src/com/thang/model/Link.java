package com.thang.model;

public enum Link {

	EQUAL("="),
	NOT_EQUAL("<>"),
	LESS_THAN("<"),
	GRATE_THAN(">"),
	LESS_EQUAL("<="),
	GRATE_EQUAL(">="),
	IN(" IN "),
	LIKE(" LIKE"),
	NOT_IN(" NOT IN ");
	
	private String value="=";
	
	private Link(String value){
		this.value=value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	public String value(){
		return this.value;
	}
	
}
