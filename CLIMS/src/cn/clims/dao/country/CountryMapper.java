package cn.clims.dao.country;

import java.util.List;

import cn.clims.pojo.Country;


public interface CountryMapper {
	public List<Country> getCountryList()throws Exception;
}
