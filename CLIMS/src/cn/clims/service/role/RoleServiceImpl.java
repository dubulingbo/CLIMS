package cn.clims.service.role;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.clims.dao.role.RoleMapper;
import cn.clims.pojo.Role;

@Service
public class RoleServiceImpl implements RoleService {
	@Resource
	RoleMapper roleMapper;
	
	@Override
	public List<Role> getRoleList() throws Exception {
		// TODO Auto-generated method stub
		return roleMapper.getRoleList();
	}

}
