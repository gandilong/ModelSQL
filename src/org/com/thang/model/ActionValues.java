package org.com.thang.model;

import java.util.HashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.com.thang.utils.ModelUtils;
import org.com.thang.utils.StrUtils;

public class ActionValues extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public boolean isNull(String key){
		if(null==get(key)){
			return true;
		}
		return false;
	}
	
	public boolean isEmpty(String key){
		if(null!=get(key)&&"".equals(String.valueOf(get(key)).trim())){
			return true;
		}
		return false;
	}
	
	public boolean isNotNull(String key){
		return !isNull(key);
	}
	
	public boolean isNotEmpty(String key){
		return !isEmpty();
	}
	
	public static ActionValues copyFromObject(Object bean){
		ActionValues values=null;
		try{
		    if(null!=bean){
			    values=new ActionValues();
			    String[] filedNames=ModelUtils.getFieldNames(bean.getClass());                                                                                                                                                                                                                                                                                                                                                                                                     
			    for(String name:filedNames){
			    	if(StrUtils.isNotEmpty(BeanUtils.getProperty(bean, name))){
			    		values.put(name,BeanUtils.getProperty(bean, name));
			    	}
			    }
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return values;
	}
	
}
