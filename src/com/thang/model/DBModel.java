package com.thang.model;

import java.util.HashMap;

import com.thang.model.sql.SQL;
import com.thang.utils.ActionValues;


public class DBModel extends HashMap<Class<?>, Model> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 根据传入的对象值来插入数据据。如果ID为空则自动增涨。
	 * @param obj
	 * @return
	 */
	public String getInsertSQL(Object obj){
		if(null!=obj&&!exist(obj.getClass())){
			load(obj);
		}else{
			updateValues(obj);
		}
		return SQL.InsertSQL.sql(get(obj.getClass()));
	}
	
	public String getDeleteSQL(Class<?> model,String id){
		if(null!=model&&!exist(model)){
			loadModel(model);
		}
		updateID(model, id);
		return SQL.DeleteSQL.sql(get(model));
	}
	
	public String getUpdateSQL(Object obj){
		if(null!=obj&&!exist(obj.getClass())){
			load(obj);
		}else{
			updateValues(obj);
		}
		return SQL.UpdateSQL.sql(get(obj.getClass()));
	}
	
	public String getSelectSQL(Class<?> model,String id){
		if(null!=model&&!exist(model)){
			loadModel(model);
		}
		updateID(model, id);
		return SQL.SelectSQL.sql(get(model));
	}
	
	public String getSelectSQL(Class<?> model,Condition condition){
		if(null!=model&&!exist(model)){
			loadModel(model);
		}
		return SQL.SelectSQL.sql(get(model),condition);
	}
	/**
	 * 得到指定类的字段数据。
	 * @param clsName
	 * @return
	 */
	public Model getModel(String clsName){
		return get(clsName);
	}
	
	/**
	 * 加载数据实体对象,假如数据实体存在，则更新数据实体。
	 * @param obj
	 */
    public void load(Object obj){
    	if(!exist(obj.getClass())){
    		put(obj.getClass(), new Model(obj));	
    	}else{
    		updateValues(obj);
    	}
	}
	
    /**
     * 加载数据实体类
     * @param cls
     */
	public void loadModel(Class<?> cls){
		if(!exist(cls)){
			put(cls, new Model(cls));	
		}
	}
	
	public void updateValues(ActionValues values){
		//model.add(field);
	}
	
	public void updateValues(Object obj){
		get(obj.getClass()).update(obj);
	}
	
	public void updateID(Class<?> model,String id){
		get(model).setId(id);
	}
	
	/**
	 * 卸载数据实体类
	 * @param cls
	 */
	public void unloadModel(Class<?> cls){
		remove(cls);
	}
	
	public void clean(Class<?> cls){
		this.clear();
	}
	
	/**
	 * 工具方法，判断实体类的数据信息是否存在。
	 * @param cls
	 * @return
	 */
	public boolean exist(Class<?> cls){
		if(null!=get(cls)){
			return true;
		}
		return false;
	}
	
}
