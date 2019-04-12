package cn.clims.service.user;

import java.util.List;

import cn.clims.pojo.User;

public interface UserService {
	
	public User getLoginUser(User user)throws Exception;
	public int userCodeIsExist(User user)throws Exception;
	public int modifyUser(User user)throws Exception;
	public List<User> getUserList(User user)throws Exception;
	public int getUserCount(User user)throws Exception;
	
}
