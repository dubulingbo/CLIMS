package cn.clims.service.country;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.clims.dao.country.CountryMapper;
import cn.clims.pojo.Country;

@Service
public class CountryServiceImpl implements CountryService {

	@Resource
	private CountryMapper coutryMapper;
	
	@Override
	public List<Country> getCountryList() throws Exception {
		// TODO Auto-generated method stub
		return coutryMapper.getCountryList();
	}

}
