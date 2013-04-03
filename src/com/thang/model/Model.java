package com.thang.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import com.thang.utils.reflect.ModelUtils;

public class Model extends ArrayList<MField> {

	private static final long serialVersionUID = 1L;

	
	private int size=0;
	private String id=null;
	private String tableName=null;
	private StringBuffer sber=new StringBuffer();
	
	public Model(Class<?> cls){
		loadModel(cls);
	}
	
	public Model(Object obj){
		load(obj);
	}
	
	/**
	 * 加载字段信息。
	 * @param obj
	 */
	public void load(Object obj){
		clear();
		setTableName(ModelUtils.getTableName(obj.getClass()));
		Field[] fields=obj.getClass().getDeclaredFields();
		int type=0;
		for(Field field:fields){
			if(ModelUtils.isNumber(field)){
				type=1;
			}
			if("id".equalsIgnoreCase(field.getName())){
				id=String.valueOf(ModelUtils.getProperty(obj,field.getName()));
			}
			add(new MField(field.getName(),ModelUtils.getColumnName(field),type,String.valueOf(ModelUtils.getProperty(obj,field.getName()))));
		}
		size=size();
	}
	
	/**
	 * 加载字段信息。
	 * @param cls
	 */
	public void loadModel(Class<?> cls){
		setTableName(ModelUtils.getTableName(cls));
		Field[] fields=cls.getDeclaredFields();
		int type=0;
		for(Field field:fields){
			if(ModelUtils.isNumber(field)){
				type=1;
			}
			add(new MField(field.getName(),ModelUtils.getColumnName(field),type,null));
		}
		size=size();
	}
	
	/**
	 * 更新字段值。
	 * @param obj
	 */
	public void update(Object obj){
		Iterator<MField> it=iterator();
		MField mf=null;
		while(it.hasNext()){
			mf=it.next();
			mf.setFieldValue(String.valueOf(ModelUtils.getProperty(obj, mf.getFieldName())));
		}
	}
	
	/**
	 * 得到指定字段对象。
	 * @param fieldName
	 * @return
	 */
	public MField getMField(String fieldName){
		Iterator<MField> it=iterator();
		MField mf=null;
		while(it.hasNext()){
			mf=it.next();
			if(fieldName.equalsIgnoreCase(mf.getFieldName())){
				return mf;
			}
		}
		return null;
	}
	
	/**
	 * 得到ID值。
	 * @return
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		Iterator<MField> it=iterator();
		MField mf=null;
		while(it.hasNext()){
			mf=it.next();
			if("id".equalsIgnoreCase(mf.getFieldName())){
				mf.setFieldValue(id);
			}
		}
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public StringBuffer getSber() {
		return sber;
	}

	public int getSize() {
		return size;
	}
	
}
