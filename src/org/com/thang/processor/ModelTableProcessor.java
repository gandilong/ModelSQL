package org.com.thang.processor;

public interface ModelTableProcessor {

	/**
	 * 将实体类名转换为相应表名的算法
	 * @param modelName
	 * @return String
	 */
	public String modelToTable(Class<?> model);

	/**
	 * 将表名转换为相应实体类名的算法
	 * @param modelName
	 * @return String
	 */
	public String tableToModel(Class<?> model);
	
}
