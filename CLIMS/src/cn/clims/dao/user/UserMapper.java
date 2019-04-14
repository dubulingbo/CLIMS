package cn.clims.dao.user;

import java.util.List;

import cn.clims.pojo.User;

public interface UserMapper {
	/**
	 * 获取当前登录用户
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User getLoginUser(User user)throws Exception;
	
	/**
	 * 用于判断当前登录用户是否存在
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int userCodeIsExist(User user)throws Exception;
	
	/**
	 * 用于更改user表里的一个或多个字段内容
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int modifyUser(User user)throws Exception;
	
	/**
	 * 获取用户列表（用于分页显示）
	 * @return
	 * @throws Exception
	 */
	public List<User> getUserList(User user)throws Exception;
	
	public int getUserCount(User user)throws Exception;

	public int addUser(User addUser)throws Exception;

	public User getUserById(User user)throws Exception;

	public int deleteUser(User delUser)throws Exception;
}
