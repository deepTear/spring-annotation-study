package com.mouchina.web.base.page;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {

	private int total = 0;
	
	private int currentPage = 1;
	
	private int pageSize = 10;

	private List<T> data = new ArrayList<>();
	
	@SuppressWarnings("unused")
	private boolean hasNextPage;
	
	public Page(int currentPage,int total,List<T> data){
		this.total = total;
		this.currentPage = currentPage;
		if(data != null){
			this.data = data;
		}
		this.hasNextPage = (currentPage * pageSize) < total;
	}
	
	public Page(int currentPage,int pageSize,int total,List<T> data){
		this.total = total;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		if(data != null){
			this.data = data;
		}
		this.hasNextPage = (currentPage * pageSize) < total;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean hasNextPage() {
		return (currentPage * pageSize) < total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
