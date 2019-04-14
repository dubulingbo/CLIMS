package cn.clims.dao.role;

import java.util.List;

import cn.clims.pojo.Role;

public interface RoleMapper {
	
	/**
	 * 获取角色列表
	 * @return
	 * @throws Exception
	 */
	public List<Role> getRoleList()throws Exception;
}
