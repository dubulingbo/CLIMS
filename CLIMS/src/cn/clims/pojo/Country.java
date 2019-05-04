package cn.clims.pojo;

@SuppressWarnings("serial")
public class Country extends Base {
	private String countryAbbr;
	private String countryName;
	
	
	public String getCountryAbbr() {
		return countryAbbr;
	}
	public void setCountryAbbr(String countryAbbr) {
		this.countryAbbr = countryAbbr;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
}
