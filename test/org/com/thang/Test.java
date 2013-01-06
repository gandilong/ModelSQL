package org.com.thang;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.com.thang.model.Dept;
import org.com.thang.model.User;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long s=System.currentTimeMillis();
        //List<User> users=(List<User>)QueryUtils.query(User.class);//new User().list();
       // for(User u:users){
        	//System.out.println(u.getUserName());
        //}
        User u=new User();
        u.setUserName("aaa");
       
        u.select();
       
       Dept d=new Dept();
       d.setUser(u);
	       try {
			System.out.println(BeanUtils.getProperty(d, "user"));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

}
