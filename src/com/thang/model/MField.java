package com.thang.model;

public class MField {

	private String fieldName=null;
	private String columnName=null;
	private int fieldType=0;//0代表字符串类型,1代表数字类型，
	private String fieldValue="";
	private Class<?> foreignKey=null;
	private boolean orderBy=false;
	
	public MField(String fieldName,String columnName,int fieldType,String fieldValue){
		this.fieldName=fieldName;
		this.columnName=columnName;
		this.fieldType=fieldType;
		this.fieldValue=fieldValue;
	}
	
	public MField(String fieldName,String columnName,int fieldType,String fieldValue,Class<?> fkey){
		this.fieldName=fieldName;
		this.columnName=columnName;
		this.fieldType=fieldType;
		this.fieldValue=fieldValue;
		this.foreignKey=fkey;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getFieldType() {
		return fieldType;
	}

	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public Class<?> getForeignKey() {
		return foreignKey;
	}
	public void setForeignKey(Class<?> foreignKey) {
		this.foreignKey = foreignKey;
	}
	public boolean isOrderBy() {
		return orderBy;
	}
	public void setOrderBy(boolean orderBy) {
		this.orderBy = orderBy;
	}
	
}
