package com.thang.utils;

import java.util.HashMap;

public class ActionValues extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public boolean isNull(String key){
		if(null==get(key)){
			return true;
		}
		return false;
	}
	
	public boolean isEmpty(String key){
		if(null!=get(key)||"".equals(String.valueOf(get(key)).trim())){
			return false;
		}
		return true;
	}
	
	public boolean isNotNull(String key){
		return !isNull(key);
	}
	
	public boolean isNotEmpty(String key){
		return !isEmpty(key);
	}
	
	public String getString(String key){
		return String.valueOf(get(key));
	}
	
	public static ActionValues getInstance(Object bean){
		ActionValues values=null;
	
		return values;
	}
	
}
