package cn.clims.dao.user;

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
}
