package org.com.thang.utils;

import java.util.HashMap;

public class IndexValues extends HashMap<String, Integer> {

	
	private static final long serialVersionUID = 1L;
	
	public void index(String key,Integer index){
		put(key, index);
	}

}
