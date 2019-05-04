package cn.clims.service.location;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.clims.dao.location.LocationMapper;
import cn.clims.pojo.Location;

@Service
public class LocationServiceImpl implements LocationService {
	
	@Resource
	private LocationMapper locationMapper;

	@Override
	public List<Location> getLocationList() throws Exception {
		return locationMapper.getLocationList();
	}

	@Override
	public Location getLocationById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return locationMapper.getLocationById(id);
	}

}
