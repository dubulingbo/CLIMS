package cn.clims.service.dept;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.clims.dao.dept.DeptMapper;
import cn.clims.pojo.Dept;

@Service
public class DeptServiceImpl implements DeptService{
	
	@Resource
	private DeptMapper deptMapper;
	
	@Override
	public List<Dept> getDeptList() throws Exception {
		return deptMapper.getDeptList();
	}

}
