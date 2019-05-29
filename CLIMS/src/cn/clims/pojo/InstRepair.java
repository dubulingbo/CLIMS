package cn.clims.pojo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class InstRepair extends Instrument {
	private String assignId;   //仪器调拨库存编号
	private Integer instrumentId;
	private String dept;
	private String address;
	private String repairDetail;
	private Date bookDate;
	private String applyPerson;
	private String applyPhone;
	private String repairImgPath;
	private Integer repairmanId;
	private Integer status;
	
	private Integer isConfirm; //标志院系管理员是否能对本次维修进行确认，   默认为《两天后》开始确认
	private List<Repairman> repairmanList; //用于前端显示，该仪器下的所有维修员信息
	
	private Integer assId;  //仪器调拨表的主键
	
	private List<String> rImgList;
	
	public String getAssignId() {
		return assignId;
	}
	public void setAssignId(String assignId) {
		this.assignId = assignId;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRepairDetail() {
		return repairDetail;
	}
	public void setRepairDetail(String repairDetail) {
		this.repairDetail = repairDetail;
	}
	public Date getBookDate() {
		return bookDate;
	}
	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}
	public String getApplyPerson() {
		return applyPerson;
	}
	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}
	public String getApplyPhone() {
		return applyPhone;
	}
	public void setApplyPhone(String applyPhone) {
		this.applyPhone = applyPhone;
	}
	public String getRepairImgPath() {
		return repairImgPath;
	}
	public void setRepairImgPath(String repairImgPath) {
		this.repairImgPath = repairImgPath;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsConfirm() {
		isConfirm = 0;
		if(this.getCheckDate() != null){
			Date cur = new Date();
			if(cur.getTime() - this.getCheckDate().getTime() > 2*24*60*60*1000){
				isConfirm = 1;
			}
		}
		
		return isConfirm;
	}
	public void setIsConfirm(Integer isConfirm) {
		this.isConfirm = isConfirm;
	}
	public Integer getRepairmanId() {
		return repairmanId;
	}
	public void setRepairmanId(Integer repairmanId) {
		this.repairmanId = repairmanId;
	}
	public List<Repairman> getRepairmanList() {
		return repairmanList;
	}
	public void setRepairmanList(List<Repairman> repairmanList) {
		this.repairmanList = repairmanList;
	}
	public List<String> getrImgList() {
		if(this.getRepairImgPath().equals("")){
			rImgList = null;
		}else{
			rImgList = Arrays.asList(getRepairImgPath().split("#"));
		}
		return rImgList;
	}
	public void setrImgList(List<String> rImgList) {
		this.rImgList = rImgList;
	}
	
	public static void main(String[] args) {
		String str = "/statics/uploadfiles/1557598435258_repairImg.png#/statics/uploadfiles/1557598819544_repairImg.jpg#";
		
		List<String> list = Arrays.asList(str.split("#"));
		System.out.println(list.size());
		for(String s : list)
			System.out.println(s);
	}
	public Integer getAssId() {
		return assId;
	}
	public void setAssId(Integer assId) {
		this.assId = assId;
	}
	
}
