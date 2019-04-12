package cn.clims.controller;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.clims.pojo.Function;
import cn.clims.pojo.User;
import cn.clims.service.function.FunctionService;
import cn.clims.tools.Constants;
import cn.clims.tools.RedisAPI;
import net.sf.json.JSONArray;

public class BaseController {
	
	private Logger logger = Logger.getLogger(BaseController.class);
	
	@Resource
	private RedisAPI redisAPI;
	
	@Resource
	private FunctionService functionService;
	
	//抽取当前用户
	private User currentUser;
	
	public User getCurrentUser(){
		if(this.currentUser == null){
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession();
			if(session != null){
				currentUser = (User) session.getAttribute(Constants.SESSION_USER);
				logger.debug("当前用户为："+currentUser.getUserCode());
			} else {
				currentUser = null;
			}
		}
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	/**
	 * 日期格式化
	 */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder){
		dataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport(){
			@Override
			public void setAsText(String value){
				try {
					setValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					setValue(null);
				}
			}
			@Override
			public String getAsText() {
				return new SimpleDateFormat("yyyy-MM-dd").format((Date)getValue());
			}
		});
	}
	
	
	
	public boolean pageInit(HttpSession session,Map<String,Object> model){
		User user = getCurrentUser();
		//function list
		List<Function> mList = null;
		
		if(user != null){
			
			model.put("user", user);
			
			/**
			 * key:menuList+roleId --eg:"menuList2"
			 */
			//redis里有没有数据
			if(redisAPI.exists("menuList"+user.getUserRole())){//redis有数据,直接从redis缓存数据库里取出功能列表
				String redisMenuListKeyString = redisAPI.get("menuList"+user.getUserRole());
				logger.debug("menuList from redis: "+redisMenuListKeyString);
				if(redisMenuListKeyString !=null && redisMenuListKeyString != ""){
					model.put("mList", redisMenuListKeyString);
				}else{
					return false;
				}
			} else { //没有数据
				//根据当前用户获取菜单列表mList
				mList = getFuncByCurrentUser(user.getUserRole());
				//转化为json
				if(mList != null){
					String jsonString = JSONArray.fromObject(mList).toString();
					logger.debug("jsonString : "+jsonString);
					model.put("mList", jsonString);
					redisAPI.set("menuList"+user.getUserRole(),60*60 ,jsonString);
				}
			}
			session.setAttribute(Constants.SESSION_BASE_MODEL, model);
			return true;
		}
		return false;
	}
	
	
	/**
	 * 根据当前用户的角色id获取功能列表
	 * @param roleId
	 * @return
	 */
	private List<Function> getFuncByCurrentUser(Integer roleId) {
		List<Function> menuList = new ArrayList<>();

		try {
			menuList = functionService.getFunctionListByRoleId(roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menuList;
	}
}
