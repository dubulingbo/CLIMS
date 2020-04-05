package cn.clims.service.information;

import java.util.List;

import cn.clims.pojo.Information;

public interface InformationService {

	public int getInformationCount()throws Exception;

	public List<Information> getInformationList(Information info)throws Exception;

	public int addInformation(Information addInfo) throws Exception;

	public int modifyInformationFileInfo(Information information) throws Exception;

	public int modifyInformation(Information information) throws Exception;

	public Information getInformation(Information information) throws Exception;

	public int deleteInformation(Information information) throws Exception;

	public List<Information> getList(Information information) throws Exception;

	public int count(Information info) throws Exception;

}
