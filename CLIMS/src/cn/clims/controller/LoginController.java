package cn.clims.controller;

import java.text.SimpleDateFormat;
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

import cn.clims.pojo.Affiche;
import cn.clims.pojo.Function;
import cn.clims.pojo.Menu;
import cn.clims.pojo.User;
import cn.clims.service.affiche.AfficheService;
import cn.clims.service.function.FunctionService;
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
	@Resource
	private RedisAPI redisAPI;
	@Resource
	private FunctionService functionService;
	@Resource
	private AfficheService afficheService;
	
	
	/**
	 * 用户登录验证
	 * @param session
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/login.html",method=RequestMethod.POST)
	@ResponseBody
	public Object login(HttpSession session,@RequestParam String user){
		if(user == null || user.equals("")){
			logger.info("前端请求的数据为空！");
			return "nodata";
		}else{
			try {
				JSONObject userObject = JSONObject.fromObject(user);
				@SuppressWarnings("static-access")
				User userObj = (User)userObject.toBean(userObject,User.class);
				logger.info("当前请求登录的用户名为："+userObj.getUserCode()+",密码为："+userObj.getUserPassword());
				if(userService.userCodeIsExist(userObj) == 0){
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
	
	
	/**
	 * 进入相应角色下的主页
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/main.html")
	public ModelAndView main(HttpSession session){
		logger.info("进入主页！");
		
		
		User user = this.getCurrentUser();
		//Menu list
		List<Menu> mList = null; 
		
		
		if(user != null){
			
			Map<String,Object> model = new HashMap<String,Object>();
			model.put("user", user);
			List<Affiche> afficheList = null;
			afficheList = this.getAfficheList();
			
			/**
			 * key:menuList+roleId --eg:"menuList2"
			 */
			//redis里有没有数据
			if(!redisAPI.exists("menuList"+user.getUserRole())){//没有数据
				
				//根据当前用户获取菜单列表mList
				mList = getMenuByCurrentUser(user.getUserRole());
				//转化为json
				if(mList != null){
					String jsonString = JSONArray.fromObject(mList).toString();
					logger.info("jsonString : "+jsonString);
					model.put("mList", jsonString);
					redisAPI.set("menuList"+user.getUserRole(),40*60 ,jsonString);  //设置缓存时间为40分钟
				}
				
			} else { //redis有数据,直接从redis缓存数据库里取出功能列表
				String redisMenuListKeyString = redisAPI.get("menuList"+user.getUserRole());
				logger.info("menuList from redis: "+redisMenuListKeyString);
				if(redisMenuListKeyString !=null && !redisMenuListKeyString.equals("")){
					model.put("mList", redisMenuListKeyString);
				}else{
					return new ModelAndView("redirect:/");
				}
			}
			//"Role"+user.getUserRole()+"UrlList"
			//if(!redisAPI.exists("Role"+user.getUserRole()+"UrlList")){
				try {
					//get all role url list to redis
					List<Function> functionList = this.getAllHrefByCurrentUser(user.getUserRole());
					if(functionList != null){
						StringBuffer sBuffer = new StringBuffer();
						for(Function f : functionList){
							sBuffer.append(f.getFunctionUrl());
						}
						redisAPI.set("Role"+user.getUserRole()+"UrlList",45*60, sBuffer.toString());
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			//}
			model.put("afficheList", afficheList);
			
			session.setAttribute(Constants.SESSION_BASE_MODEL, model);
			return new ModelAndView("main",model);
		}
		return new ModelAndView("redirect:/");
	}
	
	
	/**
	 * 根据当前用户的角色id获取功能列表（对应的菜单  -- 二级菜单）
	 * @param roleId
	 * @return
	 */
	private List<Menu> getMenuByCurrentUser(Integer userRole) {
		List<Menu> mList = new ArrayList<>();
		try {
			List<Function> mFuncList = functionService.getMainFunctionList(userRole);
			if(mFuncList != null){
				for(Function function : mFuncList){
					Menu menu = new Menu();
					menu.setMainMenu(function);
					List<Function> sFuncList = functionService.getSubFunctionList(function);
					if(sFuncList != null){
						menu.setSubMenus(sFuncList);
					}
					mList.add(menu);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mList;
	}


	private List<Affiche> getAfficheList() {
		List<Affiche> affList = null;
		Affiche affiche = new Affiche();  //主页显示近十条公告
		affiche.setStartPageNo(0);
		affiche.setPageSize(10);
		try {
			affList = afficheService.getPortalAfficheList(affiche);
			/**
			 * 接下来对title进行包装 
			 * 申请调拨：publisher + 在(xxx-时间)前申请仪器调拨   ---xx年xx个月xx天前 / 刚刚
			 * 审核仪器调拨：publisher + 审核了（xxx-申请人）的仪器调拨申请
			 * 已调拨仪器：publisher + 已调拨（xxx-申请人）申请的仪器
			 * 未调拨仪器：publisher + 未调拨（xxx-申请人）申请的仪器
			 */
			for(Affiche aff : affList){
				if(aff.getCode() == Constants.AFFICHE_TYPE_1){
					
					Date now = new Date();
					
					long daysBetween = (now.getTime() - aff.getPublishDate().getTime())/(24*60*60*1000);
					if(daysBetween > 10){
						aff.setContent(aff.getPublisher()+" 于"+
								new SimpleDateFormat("yyyy-MM-dd").format(aff.getPublishDate())+
								"申请了仪器调拨.");
					}else if(daysBetween > 0){
						aff.setContent(aff.getPublisher()+" 于"+daysBetween+"天前申请了仪器调拨.");
					}else{
						long hoursBetween = (now.getTime() - aff.getPublishDate().getTime())/(60*60*1000);
						if(hoursBetween > 0){
							aff.setContent(aff.getPublisher()+" 于"+hoursBetween+"小时前申请了仪器调拨.");
						}else {
							long minsBetween = (now.getTime() - aff.getPublishDate().getTime())/(60*1000);
							if(minsBetween > 5){
								aff.setContent(aff.getPublisher()+" 于"+minsBetween+"分钟前申请了仪器调拨.");
							}else{
								aff.setContent(aff.getPublisher()+" 刚刚申请了仪器调拨.");
							}
						}
					}
				}else if(aff.getCode() == Constants.AFFICHE_TYPE_2 ||
						 aff.getCode() == Constants.AFFICHE_TYPE_3 ||
						 aff.getCode() == Constants.AFFICHE_TYPE_4 ||
						 aff.getCode() == Constants.AFFICHE_TYPE_5){
					
				}else{
					logger.info("公告类型读取到其他类型！");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			affList = null;
		}
		return affList;
	}

	
	/**
	 * 根据当前用户的角色id获取所有请求链接
	 * @param roleId
	 * @return
	 */
	private List<Function> getAllHrefByCurrentUser(Integer roleId) {
		List<Function> menuList = new ArrayList<>();
		try {
			menuList = functionService.getAllHrefByRoleId(roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menuList;
	}
	
	
	/**
	 * 退出系统
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout.html")
	public ModelAndView logout(HttpSession session){
		session.removeAttribute(Constants.SESSION_USER);
		session.removeAttribute(Constants.SESSION_BASE_MODEL);
		
		session.invalidate();
		this.setCurrentUser(null);
		return new ModelAndView("redirect:/");
		
	}
	
	
	/**
	 * 没有权限，返回报错页面
	 * @return
	 */
	@RequestMapping(value="/401.html")
	public ModelAndView noRole(){
		return new ModelAndView("401");
	}
}
