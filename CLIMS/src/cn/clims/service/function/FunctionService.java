package cn.clims.service.function;

import java.util.List;

import cn.clims.pojo.Function;

public interface FunctionService {
	
	public List<Function> getFunctionListByRoleId(Integer roleId) throws Exception;
	
}
