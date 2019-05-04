package cn.clims.pojo;

@SuppressWarnings("serial")
public class Location extends Base {
	private String locName;  //所在地点名称或房间号
	private Integer deptNo;  //所在楼房编号
	private String locDesc;
	
	
	public String getLocName() {
		return locName;
	}
	public void setLocName(String locName) {
		this.locName = locName;
	}
	public Integer getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(Integer deptNo) {
		this.deptNo = deptNo;
	}
	public String getLocDesc() {
		return locDesc;
	}
	public void setLocDesc(String locDesc) {
		this.locDesc = locDesc;
	}
}
