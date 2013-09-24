package com.thang.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import com.thang.executor.DBExecutor;
import com.thang.utils.reflect.ModelUtils;

public class SQLModel extends ArrayList<MField> {

	private static final long serialVersionUID = 1L;
	
	private int size=0;
	private String tableName=null;
	private String primaryFieldName=null;
	private String primaryFieldValue=null;
	private String[] fieldNames=null;
	private String[] columnNames=null;
	private StringBuffer sber=new StringBuffer();
	
	private long pageNum=0;//总页数
	private long pageNow=1;//当前页数
	private long pageSize=15;//每页多少条数据
	private long total=0;//总条数
	
	public boolean toPage=true;//进行分页
	
	private Object modelObj;
	private Class<?> modelClass;
	private Condition condition;
	
	public SQLModel(Class<?> cls){
		modelClass=cls;
		loadModel(cls);
		condition=new Condition(this);
		condition.setModel(this);
	}
	
	public SQLModel(Class<?> cls,long id){
		setPrimaryFieldValue(String.valueOf(id));//设置主键值
		modelClass=cls;
		loadModel(cls);
		condition=new Condition(this);
		condition.setModel(this);
	}
	
	public SQLModel(Class<?> cls,String id){
		setPrimaryFieldValue(id);//设置主键值
		modelClass=cls;
		loadModel(cls);
		condition=new Condition(this);
		condition.setModel(this);
	}
	
	public SQLModel(Object obj){
		modelObj=obj;
		modelClass=obj.getClass();
		load(obj);
		condition=new Condition(this);
		condition.setModel(this);
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
	 * @param bean
	 */
	public void update(Object bean){
		Iterator<MField> it=iterator();
		MField mf=null;
		while(it.hasNext()){
			mf=it.next();
			mf.setFieldValue(String.valueOf(ModelUtils.getProperty(bean, mf.getFieldName())));
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

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public Class<?> getModelClass() {
		return modelClass;
	}

	public Object getModelObj() {
		return modelObj;
	}

	public void toPage(){
            if("mysql".equalsIgnoreCase(DBExecutor.getDatabase())){
                getCondition().getCdtion().append(" LIMIT ");
                getCondition().getCdtion().append(getPageNow()<=1?0:(getPageNow()-1)*getPageSize());
                getCondition().getCdtion().append(",");
                getCondition().getCdtion().append(getPageNow()<=1?getPageSize():getPageNow()*getPageSize());
            }else if("oracle".equalsIgnoreCase(DBExecutor.getDatabase())){
                
            }else if("sqlserver".equalsIgnoreCase(DBExecutor.getDatabase())){
            
            }
	}

	public boolean isToPage() {
		return toPage;
	}

	public void setToPage(boolean toPage) {
		this.toPage = toPage;
	}
	public void setTotal(long total){
		if(0!=total){
		    this.total=total;
		    pageNum=(total/pageSize)+(total%pageSize==0?0:1);
		}
	}

	public long getPageNow() {
		if(pageNow>=pageNum){
			pageNow=pageNum;
		}
		return pageNow;
	}
	public void setPageNow(long pageNow) {
		if(pageNow<1){
			pageNow=1;
		}
		this.pageNow = pageNow;
	}
	public long getPageNum() {
		return pageNum;
	}
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}
}
