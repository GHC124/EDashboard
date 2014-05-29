/**
 * DataGrid.java
 *
 *	
 */
package com.ghc.edashboard.web.form;

import java.util.List;

/**
 * 
 */
public class DataGrid<E> {
	private int totalPages;
	private int currentPage;
	private long totalRecords;
	private List<E> data;
	
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<E> getData() {
		return data;
	}
	public void setData(List<E> data) {
		this.data = data;
	}
}
