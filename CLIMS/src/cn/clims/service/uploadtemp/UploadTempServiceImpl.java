package cn.clims.service.uploadtemp;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.clims.dao.uploadtemp.UploadTempMapper;
import cn.clims.pojo.UploadTemp;

@Service
public class UploadTempServiceImpl implements UploadTempService {

	@Resource
	private UploadTempMapper mapper;
	
	public List<UploadTemp> getList(UploadTemp uploadTemp) throws Exception {
		return mapper.getList(uploadTemp);
	}

	public int add(UploadTemp uploadTemp) throws Exception {
		return mapper.add(uploadTemp);
	}

	public int delete(UploadTemp uploadTemp) throws Exception {
		return mapper.delete(uploadTemp);
	}

}
