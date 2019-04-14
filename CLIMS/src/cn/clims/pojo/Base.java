package cn.clims.pojo;

public class Base {
	private Integer startPageNo;
	private Integer pageSize;
	private Integer id;
	
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
