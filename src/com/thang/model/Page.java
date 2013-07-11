package com.thang.model;

import com.thang.executor.DBExecutor;

public class Page {
	private long pageNum=0;//总页数
	private long pageNow=1;//当前页数
	private long pageSize=15;//每页多少条数据
	private long total=0;//总条数
	
	
	public Page(){}
	
	public Page(long pageNow) {
		this.pageNow = pageNow;
	}
	
	public Page(long pageNow, long pageSize,Class<?> model) {
		this.pageNow = pageNow;
		this.pageSize = pageSize;
		
	}
	
	
	public void setTotal(long total){
		if(0!=total){
		    this.total=total;
		    pageNum=(total/pageSize)+(total%pageSize==0?0:1);
		}
	}

	public long getPageNow() {
		if(pageNow>=pageNum){
			pageNow=pageNum;
		}
		return pageNow;
	}
	public void setPageNow(long pageNow) {
		if(pageNow<1){
			pageNow=1;
		}
		this.pageNow = pageNow;
	}
	public long getPageNum() {
		return pageNum;
	}
	public void setPageNum(long pageNum) {
		this.pageNum = pageNum;
	}
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	   
	
}
