package com.thang.pojo;

import com.thang.model.mate.Column;
import com.thang.model.mate.Table;

@Table("sys_user_info")
public class User {

	private long id;
	
	@Column("user_name")
	private String uname;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	
}
