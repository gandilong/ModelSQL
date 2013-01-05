package org.com.thang.utils;

import org.apache.commons.lang.StringUtils;

public class StrUtils extends StringUtils{

	private static String temp=null;
	
	public static String out(Object obj){
		System.out.println(temp=String.valueOf(obj.toString()));
		return temp;
	}
	
	/**
	 * 判断字符串不为NULL
	 * @param value
	 * @return
	 */
	public static boolean isNotNull(String value){
		boolean result=false;
		if(null!=value){
			result=true;
		}
		return result;
	}
	

	/**
	 * 判断字符串不为空
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(String value){
		boolean result=false;
		if(isNotNull(value)&&!"".equals(value)){
			result=true;
		}
		return result;
	}
	
	
	/**
	 * 去掉下划线,并转为小写
	 * @param tname
	 * @return
	 */
	public static String dropUnderline(String tname){
		StringBuffer columnName=new StringBuffer();
		tname=tname.toLowerCase();
		
		char[] tnames=tname.toCharArray();
		boolean _show=false;
		for(int i=0;i<tnames.length;i++){
			if("_".equals(String.valueOf(tnames[i]))){
				_show=true;
				continue;
			}
			if(_show){
				columnName.append(String.valueOf(Character.toUpperCase(tnames[i])));
				_show=false;
			}else{
				columnName.append(String.valueOf(tnames[i]));
			}
		}
		return columnName.toString();
	}
	
	/**
	 * 增加下划线,并转为大写
	 * @param tname
	 * @return
	 */
	public static String addUnderline(String tname){
		String columnName="";
		
		char[] tnames=tname.toCharArray();
		boolean _show=false;
		for(int i=0;i<tnames.length;i++){
			if(!Character.isLowerCase(tnames[i])&&i>0){
				_show=true;
			}
			if(_show){
				columnName+="_"+tnames[i];
				_show=false;
			}else{
				columnName+=tnames[i];
			}
		}
		return columnName.toLowerCase();
	}
	
	/**
	 * 首字母大写
	 * @param value
	 * @return
	 */
	public static String upperHead(String value){
		return capitalize(value);
	}
	
}
