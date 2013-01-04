package org.com.thang.utils;

import java.lang.reflect.Field;

import org.apache.commons.beanutils.BeanUtils;
import org.com.thang.model.ActionValues;
import org.com.thang.model.Person;
import org.com.thang.processor.StringProcessor;
import org.com.thang.processor.common.FCProcessor;
import org.com.thang.processor.common.FCTProcessor;
import org.com.thang.processor.common.MTProcessor;

public class ModelUtils {

	public static Field getField(Class<?> modelClass,String fieldName){
		Field field=null;
		if(null!=modelClass&&StringUtils.isNotEmpty(fieldName)){
			try{
			    field=modelClass.getDeclaredField(fieldName);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return field;
	}
	
	public static int getFiledType(Class<?> modelClass,String fieldName){
		int num=0;
		Field field=getField(modelClass, fieldName);
		if(null!=field){
			Class<?> type=field.getType();
			if(char.class.equals(type)||String.class.equals(type)){
				num=0;
			}else if(int.class.equals(type)||Integer.class.equals(type)){
				num=1;
			}else if(long.class.equals(type)||Long.class.equals(type)){
				num=2;
			}else if(double.class.equals(type)){
				num=3;
			}
		}
		return num;
	}
	
	/**
	 * 得到实体对象中的所有字段
	 * @param model
	 * @return String[]
	 */
	public static String[] getFieldNames(Class<?> modelClass){
		String[] names=null;
		if(null!=modelClass){
		    Field[] fields=modelClass.getDeclaredFields();
		    names=new String[fields.length];
		    int i=0;
		    for(Field f:fields){
		    	names[i]=f.getName();
		    	i++;
		    }
		}
		return names;
	}
	
	/**
	 * 得到实体对象中的所有字段
	 * @param model
	 * @return String[]
	 */
	public static String[] getColumnNames(Class<?> model){
		String[] names=getFieldNames(model);
		if(null!=names&&names.length>0){
			StringProcessor processor=new StringProcessor(){

				public String processor(String str) {
					return StringUtils.addUnderline(str);
				}
			};
			
			for(int i=0;i<names.length;i++){
				names[i]=processor.processor(names[i]);
			}
			
		}
		return names;
	}

	public static boolean isString(Class<?> model,String fieldName){
		boolean result=false;
		Class<?> fieldType=ModelUtils.getField(model, fieldName).getType();
		if(fieldType.equals(String.class)||fieldType.equals(char.class)){
			result=true;
		}
		 return result;
	}
	
	/**
	 * 从实体对象中Copy值到ActionValues中
	 * @param bean
	 * @return
	 */
	public static ActionValues toActionValues(Object pojo){
		ActionValues values=null;
		try{
		    if(null!=pojo){
			    values=new ActionValues();
			    String[] filedNames=getFieldNames(pojo.getClass());
			    for(String name:filedNames){
				    values.put(name,BeanUtils.getProperty(pojo, name));
			    }
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return values;
	}
	
	public static String valueFormat(Class<?> model,String fieldName,String fieldValue){
		return FCTProcessor.getInstance().valueFormat(model,fieldName,fieldValue);
	}
	
	public static String[] valueFormat(Class<?> model,String fieldName,String[] fieldValues){
		int length=fieldValues.length;
		for(int i=0;i<length;i++){
			fieldValues[i]=FCTProcessor.getInstance().valueFormat(model,fieldName,fieldValues[i]);
		}
		
		return fieldValues;
	}
	
	/**
	 * 得到实体对象中的字段
	 * @param model
	 * @return "'abc'"
	 */
	public static String getFieldNames(Class<?> model,StringProcessor processor){
		String[] names=null;
		if(null!=model){
		    Field[] fieldsClass=model.getDeclaredFields();
		    names=new String[fieldsClass.length];
		    int i=0;
		    for(Field f:fieldsClass){
		    	names[i]=processor.processor(f.getName());
		    	i++;
		    }
		}
		return StringUtils.join(names,"");
	}
	
	public static String getColumnName(Class<?> model,String field){
		return FCProcessor.getInstance().filedToColumn(model, field);
	}
	
	/**
	 * 根据model类得到对应的表名，默认是根据约定把表名按驼峰法转换出表名。
	 * @param model
	 * @return
	 */
	public static String getTableName(Class<?> model){
		return MTProcessor.getInstance().modelToTable(model);
	}
	
	
	public static void main(String[] args) {
		Field age=ModelUtils.getField(Person.class,"age");
		System.out.println(age.getType().equals(int.class));
	}
	
}
