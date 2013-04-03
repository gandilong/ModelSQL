package com.thang.pojo;

import com.thang.model.mate.Column;
import com.thang.model.mate.Table;

@Table("sys_user_info")
public class User {

	private String id;
	@Column("user_name")
	private String uname;
	@Column("type")
	private long type;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public long getType() {
		return type;
	}
	public void setType(long type) {
		this.type = type;
	}
	
}
