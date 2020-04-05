package cn.clims.service.uploadtemp;

import java.util.List;

import cn.clims.pojo.UploadTemp;



public interface UploadTempService {
	/**
	 * getList
	 */
	public List<UploadTemp> getList(UploadTemp uploadTemp) throws Exception;
	/**
	 * add
	 */
	public int add(UploadTemp uploadTemp) throws Exception;
	/**
	 * delete
	 */
	public int delete(UploadTemp uploadTemp) throws Exception;
}
