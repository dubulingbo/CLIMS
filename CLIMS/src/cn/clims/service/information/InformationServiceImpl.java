package cn.clims.service.information;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.clims.dao.information.InformationMapper;
import cn.clims.pojo.Information;

@Service
public class InformationServiceImpl implements InformationService {
	
	@Resource
	private InformationMapper informationMapper;
	
	
	@Override
	public int getInformationCount() throws Exception {
		return informationMapper.getInformationCount();
	}


	@Override
	public List<Information> getInformationList(Information info) throws Exception {
		return informationMapper.getInformationList(info);
	}


	@Override
	public int addInformation(Information addInfo) throws Exception {
		
		return informationMapper.addInformation(addInfo);
	}


	@Override
	public int modifyInformationFileInfo(Information information) throws Exception {
		return informationMapper.modifyInformationFileInfo(information);
	}


	@Override
	public int modifyInformation(Information information) throws Exception {
		return informationMapper.modifyInformation(information);
	}


	@Override
	public Information getInformation(Information information) throws Exception {
		return informationMapper.getInformation(information);
	}


	@Override
	public int deleteInformation(Information information) throws Exception {
		return informationMapper.deleteInformation(information);
	}


	@Override
	public List<Information> getList(Information information) throws Exception {
		// TODO Auto-generated method stub
		return informationMapper.getList(information);
	}


	@Override
	public int count(Information info) throws Exception {
		return informationMapper.count(info);
	}

}
