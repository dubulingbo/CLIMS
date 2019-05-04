package cn.clims.service.location;

import java.util.List;

import cn.clims.pojo.Location;

public interface LocationService {
	public List<Location> getLocationList()throws Exception;
	
	public Location getLocationById(Integer id)throws Exception;
}
