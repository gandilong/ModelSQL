package com.thang.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import com.thang.utils.reflect.ModelUtils;

public class Model extends ArrayList<MField> {

	private static final long serialVersionUID = 1L;

	
	private int size=0;
	private String tableName=null;
	private String primaryFieldName=null;
	private String primaryFieldValue=null;
	private String[] fieldNames=null;
	private String[] columnNames=null;
	private StringBuffer sber=new StringBuffer();
	
	public Model(Class<?> cls){
		loadModel(cls);
	}
	
	public Model(Class<?> cls,long id){
		setPrimaryFieldValue(String.valueOf(id));
		loadModel(cls);
	}
	
	public Model(Class<?> cls,String id){
		setPrimaryFieldValue(id);
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
		Field idField=ModelUtils.getPrimaryKey(obj.getClass());
		primaryFieldName=idField.getName();
		for(Field field:fields){
			if(ModelUtils.isNumber(field)){
				type=1;
			}else{
				type=0;	
			}
			
			if(primaryFieldName.equalsIgnoreCase(field.getName())){
				primaryFieldValue=String.valueOf(ModelUtils.getProperty(obj,primaryFieldName));
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
		Field idField=ModelUtils.getPrimaryKey(cls);
		primaryFieldName=idField.getName();
		for(Field field:fields){
			if(ModelUtils.isNumber(field)){
				type=1;
			}else{
				type=0;
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
	 * @param name
	 * @return
	 */
	public MField getMField(String name){
		Iterator<MField> it=iterator();
		MField mf=null;
		while(it.hasNext()){
			mf=it.next();
			if(name.equalsIgnoreCase(mf.getFieldName())||name.equalsIgnoreCase(mf.getColumnName())){
				return mf;
			}
		}
		return null;
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
	

	public void setPrimaryFieldValue(String primaryFieldValue) {
		this.primaryFieldValue = primaryFieldValue;
	}

	/**
	 * 得到主键字段名
	 * @return
	 */
	public String getPrimaryFieldName(){
		return this.primaryFieldName;
	}
	
	/**
	 * 得到主键字段值
	 * @return
	 */
	public String getPrimaryFieldValue(){
		return this.primaryFieldValue;
	}

	public String[] getFieldNames() {
		if(null==fieldNames){
			fieldNames=new String[size];
			Iterator<MField> mfs=iterator();
			MField mf=null;
			int i=0;
			while(mfs.hasNext()){
				mf=mfs.next();
				fieldNames[i++]=mf.getFieldName();
			}
		}
		return fieldNames;
	}
	
	public String[] getColumnNames(){
		if(null==columnNames){
			columnNames=new String[size];
			Iterator<MField> mfs=iterator();
			MField mf=null;
			int i=0;
			while(mfs.hasNext()){
				mf=mfs.next();
				columnNames[i++]=mf.getColumnName();
			}
		}
		return columnNames;
	}

	
}
