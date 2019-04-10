package cn.clims.service.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.clims.dao.user.UserMapper;
import cn.clims.pojo.User;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	
	@Override
	public User getLoginUser(User user) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.getLoginUser(user);
	}
	
	@Override
	public int userCodeIsExist(User user) throws Exception {
		return userMapper.userCodeIsExist(user);
	}


	@Override
	public int modifyUser(User user) throws Exception {
		return userMapper.modifyUser(user);
	}

}
