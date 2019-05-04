package cn.clims.service.function;

import java.util.List;

import cn.clims.pojo.Function;

public interface FunctionService {
	
	public List<Function> getSubFunctionList(Function function) throws Exception;
	
	public List<Function> getFunctionListByRoleId2()throws Exception;

	public List<Function> getAllHrefByRoleId(Integer roleId)throws Exception;

	public List<Function> getMainFunctionList(Integer userRole)throws Exception;
	
}
