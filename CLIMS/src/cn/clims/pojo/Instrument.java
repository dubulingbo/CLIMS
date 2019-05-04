package cn.clims.pojo;

import java.util.Date;

/**
 * 仪器基本信息类  -- 对应clims_instrument数据表
 * @author DubLBo
 * @date 2019-4-19
 */

@SuppressWarnings("serial")
public class Instrument extends Base {
	private String instrumentName; //仪器名称     --主键1
	private String instrumentNo;   //仪器编号
	private String instrumentType; //仪器型号     --主键2
	private Integer classNo;  //类别号
	private Double price; //单价
	private String manufacturer;  //生产商
	private Date productionDate; //生产日期
	private String country;      //国别
	private Integer number; //总数量=库存+报废+调拨
	private String note;  //备注
	
	
	private Integer createdBy;
	private Date creationDate;
	private Integer lastModifyBy;
	private Date lastModifyDate;
	
	private String createdByName;
	
	
	

	private String className;  //类别名称
	
	//实现按价格范围查询
	private Double lowPrice;
	private Double highPrice;
	
	//实现按生产日期的范围查找
	private Date startDate;
	private Date endDate;
	
	
	public String getInstrumentName() {
		return instrumentName;
	}
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}
	public String getInstrumentNo() {
		return instrumentNo;
	}
	public void setInstrumentNo(String instrumentNo) {
		this.instrumentNo = instrumentNo;
	}
	public String getInstrumentType() {
		return instrumentType;
	}
	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
	}
	public Integer getClassNo() {
		return classNo;
	}
	public void setClassNo(Integer classNo) {
		this.classNo = classNo;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public Date getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Integer getLastModifyBy() {
		return lastModifyBy;
	}
	public void setLastModifyBy(Integer lastModifyBy) {
		this.lastModifyBy = lastModifyBy;
	}
	public Date getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public Double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}
	public Double getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	
	
}
