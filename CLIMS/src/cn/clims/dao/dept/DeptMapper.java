package cn.clims.dao.dept;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.clims.pojo.Dept;

public interface DeptMapper {
	
	/**
	 * 获取所在单位列表（全部）
	 * @return
	 * @throws Exception
	 */
	public List<Dept> getDeptList()throws Exception;
	
	public Integer getDnoByDname(@Param("deptName")String deptName)throws Exception;
}
