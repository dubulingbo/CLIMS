package cn.clims.pojo;


/**
 * 仪器库存类  -- 对应 clims_instStock 数据表
 * @author DubLBo
 * @date 2019-4-19
 */
@SuppressWarnings("serial")
public class InstStock extends Instrument{
	private Integer instrumentId;
	private String dept;
	private String stockManager;
	private String managerTel;
	private Integer locNo;
	private Integer stockNumber;
	
	
	


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


	public String getStockManager() {
		return stockManager;
	}


	public void setStockManager(String stockManager) {
		this.stockManager = stockManager;
	}


	public String getManagerTel() {
		return managerTel;
	}


	public void setManagerTel(String managerTel) {
		this.managerTel = managerTel;
	}


	public Integer getLocNo() {
		return locNo;
	}


	public void setLocNo(Integer locNo) {
		this.locNo = locNo;
	}


	public Integer getStockNumber() {
		return stockNumber;
	}


	public void setStockNumber(Integer stockNumber) {
		this.stockNumber = stockNumber;
	}


	


	
	
	
}
