package org.com.thang.utils;

public class StringUtils {

	public static final StringBuffer sber=new StringBuffer();
	
	public static String join(String[] values,String link){
		sber.delete(0, sber.length());
		for(String value:values){
            if(0!=sber.length()){
            	sber.append(link);
            }
			sber.append(value);
		}
		return sber.toString();
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
		return columnName;
	}
	
}
