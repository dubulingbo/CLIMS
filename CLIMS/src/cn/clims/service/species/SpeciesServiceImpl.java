package cn.clims.service.species;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.clims.dao.species.SpeciesMapper;
import cn.clims.pojo.Species;

@Service
public class SpeciesServiceImpl implements SpeciesService {
	
	@Resource
	private SpeciesMapper speciesMapper;
	
	@Override
	public List<Species> getSpeciesList() throws Exception {
		
		return speciesMapper.getSpeciesList();
	}

	@Override
	public Species getSpeciesById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return speciesMapper.getSpeciesById(id);
	}

}
