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
	public List<Function> getSubFunctionList(Function function)throws Exception;
	
	/**
	 * 未完善个人信息，不能使用其他功能，只能先补全个人信息，方能使用本系统
	 * @return
	 * @throws Exception
	 */
	public List<Function> getFunctionListByRoleId2()throws Exception;

	public List<Function> getAllHrefByRoleId(@Param(value="roleId") Integer roleId)throws Exception;

	public List<Function> getMainFunctionList(@Param(value="userRole") Integer userRole)throws Exception;
}
