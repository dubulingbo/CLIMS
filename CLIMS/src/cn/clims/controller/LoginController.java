package cn.clims.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.clims.pojo.User;
import cn.clims.service.user.UserService;
import cn.clims.tools.Constants;

import net.sf.json.JSONObject;

@Controller
public class LoginController extends BaseController{
	
	private Logger logger = Logger.getLogger(LoginController.class);
	
	@Resource
	private UserService userService;
	
	
	
	@RequestMapping(value="/login.html",method=RequestMethod.POST)
	@ResponseBody
	public Object login(HttpSession session,@RequestParam String user){
		if(user == null || user.equals("")){
			System.out.println("前端请求的数据为空！");
			return "nodata";
		}else{
			try {
				JSONObject userObject = JSONObject.fromObject(user);
				@SuppressWarnings("static-access")
				User userObj = (User)userObject.toBean(userObject,User.class);
				logger.debug("当前请求登录的用户名为："+userObj.getUserCode()+",密码为："+userObj.getUserPassword());
				if(userService.userCodeIsExist(userObj)==0){
					return "nologincode";
				}else{
					User _user = userService.getLoginUser(userObj);
					if(_user != null){
						//当前用户存到session中
						session.setAttribute(Constants.SESSION_USER, _user);
						return "success"+_user.getUserRole();
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
	

	@RequestMapping("/logout.html")
	public String logout(HttpSession session){
		session.removeAttribute(Constants.SESSION_USER);
		session.removeAttribute(Constants.SESSION_BASE_MODEL);
		
		session.invalidate();
		this.setCurrentUser(null);
		return "redirect:/";
		
	}
	
	/**
	 * 系统主页
	 * @return
	 */
	@RequestMapping(value="/index.html")
	public String index(){
		return "index";
	}
	
//	@RequestMapping(value="/test.html")
//	public ModelAndView test(HttpSession session){
//		Map<String,Object> model = new HashMap<>();
//		if(pageInit(session,model))
//			return new ModelAndView("test",model);
//		else 
//			return new ModelAndView("redirect:/index.html");
//	}
}
