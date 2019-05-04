package cn.clims.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.clims.pojo.User;
import cn.clims.service.dept.DeptService;
import cn.clims.service.user.UserService;
import cn.clims.tools.Constants;
import net.sf.json.JSONObject;

@Controller
public class UserController extends BaseController {
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	@Resource
	private DeptService deptService;
	
	/**
	 * 修改个人密码
	 * @param userJson
	 * @return
	 */
	@RequestMapping(value="/modifyPwd.html",method=RequestMethod.POST)
	@ResponseBody
	public Object modifyPassword(@RequestParam String userJson){
		logger.debug("正在修改密码。。。");
		User sessionUser = this.getCurrentUser();
		if(userJson == null || userJson.equals("")){
			return "nodata";
		}else{
			User user = (User)JSONObject.toBean(JSONObject.fromObject(userJson),User.class);
			user.setId(sessionUser.getId());
			user.setUserCode(sessionUser.getUserCode());
			try {
				if(userService.getLoginUser(user) != null){
					user.setUserPassword(user.getUserPassword2());
					user.setModifyBy(sessionUser.getId());
					user.setModifyDate(new Date());
					
					userService.modifyUser(user);
				}else{
					return "oldpwdwrong";  //输入的原密码错误
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
		return "success";
	}
	
	
	/**
	 * 进入完善个人信息页面
	 * @param session
	 * @param deptService 
	 * @return
	 */
	@RequestMapping("/modifyInfo.html")
	public ModelAndView modifyInfo(HttpSession session,Model model){
		
		logger.debug("进入完善个人信息页面========");
		@SuppressWarnings("unchecked")
		Map<String,Object> baseModel = (Map<String, Object>) session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null || getCurrentUser() == null){  //会话已关闭，请重新登录
			return new ModelAndView("redirect:/");
		}else{
			try {
				User user = new User();
				user.setId(this.getCurrentUser().getId());
				user = userService.getUserById(user);
				model.addAllAttributes(baseModel);
				model.addAttribute("deptList",deptService.getDeptList());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return new ModelAndView("redirect:/");
			}
			return new ModelAndView("modifyInfo");
		}
	}
	
	
	/**
	 * 执行完善个人信息保存操作
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/modifyInfoSave.html",method=RequestMethod.POST)
	public ModelAndView modifyInfoSave(HttpSession session,@ModelAttribute("mUser") User mUser){
		logger.debug("正在执行完善个人信息保存操作========");
		if(session.getAttribute(Constants.SESSION_BASE_MODEL)==null || getCurrentUser() == null){
			return new ModelAndView("redirect:/");
		} else {
			try {
				logger.info("perfectUser======== user : "+mUser);
				logger.info("id:"+mUser.getId()+
							 "\tuserCode:"+mUser.getUserCode()+
							 "\tuserPassword:"+mUser.getUserPassword()+
							 "\tuserName:"+mUser.getUserName()+
							 "\tidCard:"+mUser.getIdCard()+
							 "\tphone:"+mUser.getPhone());
				mUser.setUserCode(null);
				mUser.setUserPassword(null);
				mUser.setModifyBy(this.getCurrentUser().getId());
				mUser.setModifyDate(new Date());
				userService.modifyUser(mUser);
	
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return new ModelAndView("redirect:/");
			}
		}
		return new ModelAndView("redirect:/backend/admin/main.html");
	}
	
}
