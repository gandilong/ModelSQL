package com.thang;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.thang.executor.DBExecutor;
import com.thang.model.Condition;
import com.thang.pojo.Role;
import com.thang.pojo.User;

public class DBETest {

    private DBExecutor dbe=null;	
	
	@Before
	public void setUp() throws Exception {
		dbe=new DBExecutor();
	}

	//@Test
	public void testInsert() {
		User user=new User();
		//user.setId(new Date().getTime());
		user.setUserName("gandilong");
		user.setSex("0");
		user.setOpt("好的");
		dbe.insert(user);
	}

	//@Test
	public void testDeleteClassOfQLong() {
		dbe.delete(User.class, 5);
	}

	//@Test
	public void testDeleteClassOfQString() {
		dbe.delete(User.class, 4);
	}

	//@Test
	public void testList() {
		
		List<User> users=dbe.listDesc(User.class,"id,user_name");
		for(User u:users){
			System.out.println(u.getUserName());	
		}
		
	}
	
    //@Test
	public void testListCondition() {
    	Condition cod=new Condition(User.class);
    	//cod.getPage().setPageNow(1);
    	cod.eq("userName", "gandilong");
    	cod.eq("sex", "1");
    	cod.or();
    	cod.eq("opt", "abc");
			List<User> users=dbe.list(User.class,cod);
			
			for(User u:users){
				System.out.println(u.getUserName());	
			}
			
		}


	//@Test
	public void testGetClassOfTLong() {
		User u=dbe.get(User.class, 1);
		
		//System.out.println(u.getDeptId());
		
		
		System.out.println(u.toString());
	}

	//@Test
	public void testGetClassOfTString() {
		User u=dbe.get(User.class, "8");
	}

	//@Test
	public void testUpdate() {
		User u=new User();
		u.setId(2);
		//u.setUserName("gandilong");
		//u.setLoginName("admin");
		//u.setLoginPass("su");
		u.setSex("1");
		//u.setOpt("abcdddddd");
		dbe.update(u);
	}

	//@Test
	public void testGetDataSource() {
		long a=dbe.num(User.class);
		System.out.println(a);
	}

	@Test
	public void testSetDataSource() {
		List<Role> roles=dbe.list(Role.class);
		for(Role r:roles){
			System.out.println(r.toString());
		}
	}

}
