package cn.clims.pojo;

@SuppressWarnings("serial")
public class InstAssign extends Instrument {
	private String assignNo;   //调拨仪器之后的编号
	private Integer instrumentId;
	private String dept;
	private Integer locNo;
	private Integer assignNumber;
	private Integer assignManagerId;
	private String managerTel;
	private Integer status;
	private Integer opacifyToAdmin;
	
	
	
	private String assignManager;  //院系管理员的姓名
	
	private String reason;  //用来记录拒绝的原因
	
	
	public String getAssignNo() {
		return assignNo;
	}
	public void setAssignNo(String assignNo) {
		this.assignNo = assignNo;
	}
	public Integer getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(Integer instrumentId) {
		this.instrumentId = instrumentId;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public Integer getLocNo() {
		return locNo;
	}
	public void setLocNo(Integer locNo) {
		this.locNo = locNo;
	}
	public Integer getAssignNumber() {
		return assignNumber;
	}
	public void setAssignNumber(Integer assignNumber) {
		this.assignNumber = assignNumber;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAssignManager() {
		return assignManager;
	}
	public void setAssignManager(String assignManager) {
		this.assignManager = assignManager;
	}
	public Integer getAssignManagerId() {
		return assignManagerId;
	}
	public void setAssignManagerId(Integer assignManagerId) {
		this.assignManagerId = assignManagerId;
	}
	public String getManagerTel() {
		return managerTel;
	}
	public void setManagerTel(String managerTel) {
		this.managerTel = managerTel;
	}
	public Integer getOpacifyToAdmin() {
		return opacifyToAdmin;
	}
	public void setOpacifyToAdmin(Integer opacifyToAdmin) {
		this.opacifyToAdmin = opacifyToAdmin;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
