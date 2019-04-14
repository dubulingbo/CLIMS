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
	public List<Function> getFunctionListByRoleId(Integer roleId) throws Exception {
		return functionMapper.getFunctionListByRoleId(roleId);
	}

}
