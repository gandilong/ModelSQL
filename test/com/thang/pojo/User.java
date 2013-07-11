package com.thang.pojo;

import com.thang.model.mate.Table;

/**
 * @author gandilong
 */
@Table("sys_users")
public class User {

	private long id;
	
	private String name;
	
	 
	private String descn;
	
	 
	private String loginid;
	
	private String passwd;
	private String email;
        private String region;
	
	private String status;
        private String unitId;
        private String phone;
        private String isAutoAudit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsAutoAudit() {
        return isAutoAudit;
    }

    public void setIsAutoAudit(String isAutoAudit) {
        this.isAutoAudit = isAutoAudit;
    }
	
	
	
	
	
}
