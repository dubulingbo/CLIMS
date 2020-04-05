package cn.clims.dao.uploadtemp;

import java.util.List;

import cn.clims.pojo.UploadTemp;


/**
 * UploadTempMapper
 * @author DubLBo
 * @date 2019-5-23
 */
public interface UploadTempMapper {
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
