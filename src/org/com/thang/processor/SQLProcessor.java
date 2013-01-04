package org.com.thang.processor;

import org.com.thang.model.ActionValues;
import org.com.thang.utils.IndexValues;


public interface SQLProcessor {

	public String process(Class<?> model,StringBuffer sql,ActionValues values);
	
}
