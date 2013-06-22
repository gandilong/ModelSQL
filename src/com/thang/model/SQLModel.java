package com.thang.model;

import java.util.HashMap;
import java.util.Map;

public class SQLModel {

	private Model mo=null;
	private Map<String,Object> models=new HashMap<String,Object>();
	
	public SQLModel(Class<?> modelType){
		models.put("model",mo=new Model(modelType));
	}
	
	public SQLModel(Class<?> modelType,Object model){
		//如果model是字符串类型，则为sid的值
		if(model instanceof String){
			models.put("model", mo=new Model(modelType));
			models.put("id", String.valueOf(model));
			mo.setId(String.valueOf(model));
			return;
		}
	    
		//如果model是数值类型，则为id的值
		if(model instanceof Long){
		    models.put("model",mo=new Model(modelType));
		    models.put("id",String.valueOf(Long.valueOf(String.valueOf(model))));
		    mo.setId(String.valueOf(Long.valueOf(String.valueOf(model))));
		    return;
		}
		
		if(model instanceof Condition){
			if(null!=model){
			   models.put("condition", model);
			}
			models.put("model", mo=new Model(modelType));
			return;
		}
		
		if(null==model){
			models.put("model", mo=new Model(modelType));
			return;
		}
		
		models.put("model",mo=new Model(model));
	}

	public Map<String, Object> getModels() {
		return models;
	}

	public void setModels(Map<String, Object> models) {
		this.models = models;
	}

	public Model getModel() {
		return mo;
	}

}
