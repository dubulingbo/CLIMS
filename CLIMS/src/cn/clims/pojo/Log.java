package cn.clims.pojo;

import java.util.Date;

@SuppressWarnings("serial")
public class Log extends Base {
	private String logName;
	private String logDesc;
	private Date occurDate;
	
	
	public String getLogName() {
		return logName;
	}
	public void setLogName(String logName) {
		this.logName = logName;
	}
	public String getLogDesc() {
		return logDesc;
	}
	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}
	public Date getOccurDate() {
		return occurDate;
	}
	public void setOccurDate(Date occurDate) {
		this.occurDate = occurDate;
	}
}
