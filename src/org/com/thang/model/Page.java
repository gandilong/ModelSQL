package org.com.thang.model;

public class Page {

	private long pageNum=0;//总页数
	private long pageNow=1;//当前页数
	private long pageSize=15;//每页多少条数据
	
	private String orderBy="id";
	private String order="desc";
	
	public static final Page defaultPage=new Page();

	public Page(){};
	
	public Page(long pageNow, long pageSize) {
		this.pageNow = pageNow;
		this.pageSize = pageSize;
	}
	
	public Page(long pageNow, long pageSize, String orderBy,String order) {
		this.pageNow = pageNow;
		this.pageSize = pageSize;
		this.orderBy = orderBy;
		this.order = order;
	}

	public long getPageNow() {
		return pageNow;
	}
	public void setPageNow(long pageNow) {
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
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}
