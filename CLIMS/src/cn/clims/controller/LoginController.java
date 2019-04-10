package cn.clims.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.clims.pojo.User;
import cn.clims.service.user.UserService;
import cn.clims.tools.Constants;
import cn.clims.tools.RedisAPI;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class LoginController extends BaseController{
	
	private Logger logger = Logger.getLogger(LoginController.class);
	
	@Resource
	private UserService userService;
	
	
	@RequestMapping(value="/login.html",method=RequestMethod.POST)
	@ResponseBody
	public Object login(HttpSession session,@RequestParam String user){
		logger.debug("login========== ");
		if(user == null || user.equals("")){
			System.out.println("前端请求的数据为空！");
			return "nodata";
		}else{
			try {
				JSONObject userObject = JSONObject.fromObject(user);
				@SuppressWarnings("static-access")
				User userObj = (User)userObject.toBean(userObject,User.class);
				System.out.println("当前请求登录的用户名为："+userObj.getUserCode()+",密码为："+userObj.getUserPassword());
				if(userService.userCodeIsExist(userObj)==0){
					return "nologincode";
				}else{
					User _user = userService.getLoginUser(userObj);
					if(_user != null){
						//当前用户存到session中
						session.setAttribute(Constants.SESSION_USER, _user);
						return "success";
					}else{
						return "pwderror";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
	}
	
	@RequestMapping(value="/main.html")
	public ModelAndView main(HttpSession session){
		
		User user = this.getCurrentUser();
		logger.debug(user.getUserCode()+"欢迎进入主页！");
		return new ModelAndView("main");
		//menu list
//		List<Menu> mList = null;
//		
//		if(user != null){
//			Map<String,Object> model = new HashMap<>();
//			model.put("user", user);
//			
//			/**
//			 * key:menuList+roleId --eg:"menuList2"
//			 */
//			//redis里有没有数据
//			if(redisAPI.exist("menuList"+user.getRoleId())){//redis有数据,直接从redis缓存数据库里取出功能列表
//				String redisMenuListKeyString = redisAPI.get("menuList"+user.getRoleId());
//				logger.debug("menuList from redis: "+redisMenuListKeyString);
//				if(redisMenuListKeyString !=null && redisMenuListKeyString != ""){
//					model.put("mList", redisMenuListKeyString);
//				}else{
//					return new ModelAndView("redirect:/");
//				}
//			} else { //没有数据
//				//根据当前用户获取菜单列表mList
//				mList = getFuncByCurrentUser(user.getRoleId());
//				//转化为json
//				if(mList != null){
//					String jsonString = JSONArray.fromObject(mList).toString();
//					logger.debug("jsonString : "+jsonString);
//					model.put("mList", jsonString);
//					redisAPI.set("menuList"+user.getRoleId(), jsonString);
//				}
//			}
//			session.setAttribute(Constants.SESSION_BASE_MODEL, model);
//			return new ModelAndView("main",model);
//		}
//		return new ModelAndView("redirect:/");
	}
	
	/**
	 * 根据当前用户的角色id获取功能列表
	 * @param roleId
	 * @return
	 */
//	private List<Menu> getFuncByCurrentUser(Integer roleId) {
//		List<Menu> menuList = new ArrayList<>();
//		Authority au = new Authority();
//		au.setRoleId(roleId);
//		try {
//			List<Function> mList = functionService.getMainFunctionList(au);
//			if(mList != null){
//				for(Function f : mList){
//					Menu menu = new Menu();
//					menu.setMainMenu(f);
//					f.setRoleId(roleId);
//					List<Function> subList = functionService.getSubFunctionList(f);
//					if(subList != null){
//						menu.setSubMenus(subList);
//					}
//					menuList.add(menu);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return menuList;
//	}
	
	@RequestMapping("/logout.html")
	public String logout(HttpSession session){
		session.removeAttribute(Constants.SESSION_USER);
		session.invalidate();
		this.setCurrentUser(null);
		return "index";
		
	}
	
	
}
