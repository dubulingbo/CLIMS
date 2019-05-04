package cn.clims.pojo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Base implements Serializable{
	private Integer startPageNo;
	private Integer pageSize;
	protected Integer id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStartPageNo() {
		return startPageNo;
	}
	public void setStartPageNo(Integer startPageNo) {
		this.startPageNo = startPageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
