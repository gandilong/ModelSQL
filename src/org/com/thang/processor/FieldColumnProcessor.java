package org.com.thang.processor;

import org.apache.commons.dbutils.RowProcessor;

public interface FieldColumnProcessor extends RowProcessor{

	/**
	 * 将实体类中的字段名称转换为相应列名的算法
	 * @param filed
	 * @return String
	 */
	public String filedToColumn(Class<?> model,String field);
	
	/**
	 * 将列名转换为相应实体类中的字段名称的算法
	 * @param filed
	 * @return String
	 */
	public String columnToField(Class<?> model,String column);
	
}
