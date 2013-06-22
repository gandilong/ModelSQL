package com.thang;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.thang.executor.DBExecutor;
import com.thang.pojo.User;

public class DBETest {

    private DBExecutor dbe=null;	
	
	@Before
	public void setUp() throws Exception {
		dbe=new DBExecutor();
	}

	@Test
	public void testInsert() {
		User user=new User();
		user.setId(new Date().getTime());
		user.setUname("good");
		dbe.insert(user);
	}

	@Test
	public void testDeleteClassOfQLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteClassOfQString() {
		fail("Not yet implemented");
	}

	@Test
	public void testList() {
		List<User> users=dbe.list(User.class,null);
		for(User u:users){
			System.out.println(u.getUname());	
		}
		
	}

	@Test
	public void testGetClassOfTLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClassOfTString() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDataSource() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDataSource() {
		fail("Not yet implemented");
	}

}
