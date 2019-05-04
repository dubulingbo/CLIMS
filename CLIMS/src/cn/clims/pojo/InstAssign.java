package cn.clims.pojo;

@SuppressWarnings("serial")
public class InstAssign extends Instrument {
	private Integer instrumentId;
	private String dept;
	private Integer locNo;
	private Integer assignNumber;
	private String assignManager;
	private String managerTel;
	private Integer status;
	
	
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
	public String getManagerTel() {
		return managerTel;
	}
	public void setManagerTel(String managerTel) {
		this.managerTel = managerTel;
	}
}
