package com.thang.model;

import com.thang.executor.DBExecutor;

public class Page {
	private long pageNum=0;//总页数
	private long pageNow=1;//当前页数
	private long pageSize=15;//每页多少条数据
	private long total=0;//总条数
	
	private Class<?> model=null;
	
	private static DBExecutor dbe=new DBExecutor();
	
	public Page(Class<?> model){
		this.model=model;
		init();
	};
	
	public Page(long pageNow,Class<?> model) {
		this.pageNow = pageNow;
		this.model=model;
		init();
	}
	
	public Page(long pageNow, long pageSize,Class<?> model) {
		this.pageNow = pageNow;
		this.pageSize = pageSize;
		this.model=model;
		init();
	}
	
	
	public void init(){
		if(null!=model){
		    total=dbe.num(model);
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

	public void setTotal(long total) {
		this.total = total;
	}
    
	
}
