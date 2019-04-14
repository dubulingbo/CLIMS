package cn.clims.dao.function;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.clims.pojo.Function;

public interface FunctionMapper {
	/**
	 * 获得相应角色下的功能列表
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<Function> getFunctionListByRoleId(@Param(value="roleId") Integer roleId)throws Exception;
}
