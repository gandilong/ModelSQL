package org.com.thang.model.mate;

public @interface Field {

	public String column(); 
	
	public String type() default "varchar(36)";
	
}
