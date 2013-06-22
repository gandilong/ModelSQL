package com.thang.utils.lang;

import java.util.UUID;

public class UUIDUtils {

	/**
	 * 随机产生一个UUID
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 根据字符串来得到一个UUID
	 * @param name
	 * @return
	 */
	public static String getUUID(String name){
		return UUID.fromString(name).toString();
	}
	
	/**
	 * 得到指定数量的UUID
	 * @param num
	 * @return String[]
	 */
	public static String[] getUUID(int num){
		String[] uuids=null;
		if(num>0){
			uuids=new String[num];
		    for(int i=0;i<num;i++){
			    uuids[i]=getUUID();
		    }
		}
		return uuids;
	}
	
}
