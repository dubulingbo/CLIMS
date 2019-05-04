package cn.clims.service.species;

import java.util.List;


import cn.clims.pojo.Species;


public interface SpeciesService {
	
	public List<Species> getSpeciesList()throws Exception;
	
	public Species getSpeciesById(Integer id)throws Exception;
}
