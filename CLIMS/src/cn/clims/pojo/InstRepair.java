package cn.clims.pojo;

@SuppressWarnings("serial")
public class InstRepair extends Instrument {
	private Integer instrumentId;
	private String dept;
	private Integer locNo;
	private String repairman;
	private String repairmanTel;
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
	public String getRepairman() {
		return repairman;
	}
	public void setRepairman(String repairman) {
		this.repairman = repairman;
	}
	public String getRepairmanTel() {
		return repairmanTel;
	}
	public void setRepairmanTel(String repairmanTel) {
		this.repairmanTel = repairmanTel;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
