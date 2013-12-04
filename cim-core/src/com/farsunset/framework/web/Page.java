package com.farsunset.framework.web;

import java.util.ArrayList;
import java.util.List;
public class Page {
	private int count;
	public  int size=10;
	private int currentPage = 1;
	private int countPage;
	private String order;
	private List<?> dataList;
    public int getCount() {
		return count;
	}

	public void setCount(int count) {
		
		countPage = (count-1)/size+1;
		this.count = count;
	}

	public int getCurrentPage() {
	 return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getCountPage() {
		
		countPage = (count-1)/size+1;
		return countPage;
	}
	
	public boolean hasNextPage(){
		return currentPage<getCountPage();
	}
	public boolean isFristPage(){
		return currentPage==1;
	}
	public boolean isLastPage(){
		return currentPage==getCountPage();
	}

	public List<?> getDataList() {
		if(dataList== null)
			return new ArrayList<Object>();
		return dataList;
	}

	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}

 
}