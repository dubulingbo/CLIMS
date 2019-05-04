package cn.clims.dao.location;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.clims.pojo.Location;

public interface LocationMapper {
	
	
	public List<Location> getLocationList()throws Exception;

	public Location getLocationById(@Param("id")Integer id)throws Exception;
	
	
}
