package org.com.thang;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.List;

import javax.management.Descriptor;

import org.apache.commons.beanutils.PropertyUtils;
import org.com.thang.executor.DBConfig;
import org.com.thang.gener.sql.SelectSQLGener;
import org.com.thang.model.Dept;
import org.com.thang.model.Person;
import org.com.thang.model.User;
import org.com.thang.utils.QueryUtils;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        //SelectSQLGener select =new SelectSQLGener(Person.class);
		long s=System.currentTimeMillis();
        List<User> users=(List<User>)QueryUtils.query(User.class);//new User().list();
        for(User u:users){
        	System.out.println(u.getUserName());
        }
        User u=new User().select("f0a18a76-eaa0-4a37-ad42-36fc35910fad");
       //System.out.println(System.currentTimeMillis()-s);
       //u.delete();
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
