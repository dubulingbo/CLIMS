package cn.clims.pojo;

@SuppressWarnings("serial")
public class Repairman extends Base {
	private String repairmanName;
	private String phone;
	private String sex;
	private Integer profession;
	
	public String getRepairmanName() {
		return repairmanName;
	}
	public void setRepairmanName(String repairmanName) {
		this.repairmanName = repairmanName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getProfession() {
		return profession;
	}
	public void setProfession(Integer profession) {
		this.profession = profession;
	}
	
}
