package cn.clims.dao.species;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.clims.pojo.Species;


public interface SpeciesMapper {
	public List<Species> getSpeciesList()throws Exception;
	public Species getSpeciesById(@Param("id") Integer id)throws Exception;
}
