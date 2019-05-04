package cn.clims.service.function;

import java.util.List;

import javax.annotation.Resource;

import cn.clims.dao.function.FunctionMapper;
import org.springframework.stereotype.Service;

import cn.clims.pojo.Function;

@Service
public class FunctionServiceImpl implements FunctionService {
	@Resource
	private FunctionMapper functionMapper;
	
	@Override
	public List<Function> getSubFunctionList(Function function) throws Exception {
		return functionMapper.getSubFunctionList(function);
	}

	@Override
	public List<Function> getFunctionListByRoleId2() throws Exception {
		// TODO Auto-generated method stub
		return functionMapper.getFunctionListByRoleId2();
	}

	@Override
	public List<Function> getAllHrefByRoleId(Integer roleId) throws Exception {
		// TODO Auto-generated method stub
		return functionMapper.getAllHrefByRoleId(roleId);
	}

	@Override
	public List<Function> getMainFunctionList(Integer userRole) throws Exception {
		// TODO Auto-generated method stub
		return functionMapper.getMainFunctionList(userRole);
	}

}
