package cn.clims.pojo;

import java.util.Date;

@SuppressWarnings("serial")
public class InstScrap extends Instrument {
	private Integer instrumentId;
	private String assignId;   //调拨库存编号
	private String dept;
	private String address;   //报废地址
	private String scrapDetail;  //报废简述
	private String dealer;  //处理者
	private String dealerTel;
	private Date dealDate;  //处理日期 ，即报废时间
	private Integer scrapNumber;
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
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	public String getDealerTel() {
		return dealerTel;
	}
	public void setDealerTel(String dealerTel) {
		this.dealerTel = dealerTel;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public Integer getScrapNumber() {
		return scrapNumber;
	}
	public void setScrapNumber(Integer scrapNumber) {
		this.scrapNumber = scrapNumber;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAssignId() {
		return assignId;
	}
	public void setAssignId(String assignId) {
		this.assignId = assignId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getScrapDetail() {
		return scrapDetail;
	}
	public void setScrapDetail(String scrapDetail) {
		this.scrapDetail = scrapDetail;
	}
}
