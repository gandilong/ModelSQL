package org.com.thang.processor;

public interface FieldColumnTypeProcessor {

	/**
	 * 只处理两种数据类型：字符型/非字符型
	 * @param model
	 * @param field
	 * @return
	 */
	public String valueFormat(Class<?> model,String fieldName,String fieldValue);
	
}
