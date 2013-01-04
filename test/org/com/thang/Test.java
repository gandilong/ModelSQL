package org.com.thang;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

import javax.management.Descriptor;

import org.apache.commons.beanutils.PropertyUtils;
import org.com.thang.executor.DBConfig;
import org.com.thang.gener.sql.SelectSQLGener;
import org.com.thang.model.Person;
import org.com.thang.model.User;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        //SelectSQLGener select =new SelectSQLGener(Person.class);
       User u=new User().select("9972340c-b9c4-4e43-b952-b6026a737e66");
       //u=u.select("9972340c-b9c4-4e43-b952-b6026a737e66");
       System.out.println(u.getId());
       System.out.println(u.getUserName());
       System.out.println(u.getLoginName());
       u.delete();
		//System.out.println(DBConfig.class.getResource("").getPath());
		//System.out.println(System.getProperty("user.dir"));
		//System.out.println(DBConfig.class.getClassLoader().getResourceAsStream("dbconfig.properties"));
		//System.out.println(DBConfig.class.getResourceAsStream("../../../../dbconfig.properties"));
        //PropertyDescriptor[] des= PropertyUtils.getPropertyDescriptors(Person.class);
        
        //for(PropertyDescriptor pd:des){
        	//System.out.println(pd.getDisplayName());
        	//System.out.println(pd.getName());
        //}
        
       
	}

}
