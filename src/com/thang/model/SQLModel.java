package com.thang.model;

public class SQLModel {

	private Model mo=null;
	
	public SQLModel(Class<?> modelType){
		mo=new Model(modelType);
	}

	public Model getModel() {
		return mo;
	}

}
