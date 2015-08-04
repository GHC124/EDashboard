package com.ghc.edashboard.web.util.karaoke;

import java.util.List;

public class SongJson {
	String status;
	int total;
	List<Song> data;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<Song> getData() {
		return data;
	}
	public void setData(List<Song> data) {
		this.data = data;
	}	
	
}
