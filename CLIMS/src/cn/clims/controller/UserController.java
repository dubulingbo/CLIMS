package cn.clims.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.StringUtils;


import cn.clims.pojo.Information;

import cn.clims.pojo.User;
import cn.clims.service.dept.DeptService;
import cn.clims.service.information.InformationService;
import cn.clims.service.user.UserService;
import cn.clims.tools.Constants;
import cn.clims.tools.MD5;
import cn.clims.tools.PageSupport;

import net.sf.json.JSONObject;

@Controller
public class UserController extends BaseController {
	//private Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	@Resource
	private DeptService deptService;
	@Resource
	private InformationService informationService;
	
	
	/**
	 * 修改个人密码
	 * @param userJson
	 * @return
	 */
	@RequestMapping(value="/backend/modifyPwd.html",method=RequestMethod.POST)
	@ResponseBody
	public Object modifyPassword(@RequestParam String userJson){
		User sessionUser = this.getCurrentUser();
		if(userJson == null || userJson.equals("")){
			return "nodata";
		}else{
			User user = (User)JSONObject.toBean(JSONObject.fromObject(userJson),User.class);
			user.setId(sessionUser.getId());
			user.setUserCode(sessionUser.getUserCode());
			try {
				if(userService.getLoginUser(user) != null){
					user.setUserPassword(MD5.encrypt(user.getUserPassword2()));
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
	@RequestMapping(value="/backend/modifyInfo.html")
	public ModelAndView modifyInfo(HttpSession session,Model model){
		@SuppressWarnings("unchecked")
		Map<String,Object> baseModel = (Map<String, Object>) session.getAttribute(Constants.SESSION_BASE_MODEL);
		
		try {
			System.out.println("即将修改的用户的ID为 : "+this.getCurrentUser().getId());
			
			//session.setAttribute(Constants.SESSION_BASE_MODEL, baseModel);
			model.addAttribute("user", userService.getUserById(this.getCurrentUser()));
			model.addAttribute("deptList",deptService.getDeptList());
			model.addAllAttributes(baseModel);
			return new ModelAndView("modifyInfo");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/");
		}
	}
	
	
	/**
	 * 执行完善个人信息保存操作
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/backend/modifyInfoSave.html",method=RequestMethod.POST)
	public ModelAndView modifyInfoSave(HttpSession session,@ModelAttribute("mUser") User mUser){
		System.out.println("正在执行完善个人信息保存操作========");
		try {
			System.out.println("id:"+mUser.getId()+
						 "\tuserCode:"+mUser.getUserCode()+
						 "\tuserPassword:"+mUser.getUserPassword()+
						 "\tuserName:"+mUser.getUserName()+
						 "\tidCard:"+mUser.getIdCard()+
						 "\tphone:"+mUser.getPhone());
			//用户名、密码不能更新
			mUser.setUserCode(null);
			mUser.setUserPassword(null);
			mUser.setModifyBy(this.getCurrentUser().getId());
			mUser.setModifyDate(new Date());
			userService.modifyUser(mUser);
			
			//更新session里的当前用户信息
			session.setAttribute(Constants.CURRENT_USER, userService.getUserById(mUser));
			
			return new ModelAndView("redirect:/main.html");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/");
		}
	}
	
	
	
	@RequestMapping(value="/backend/datumDownload.html")
	public ModelAndView datumDownload(HttpSession session,Model model){
		@SuppressWarnings("unchecked")
		Map<String,Object> baseModel = (Map<String, Object>) session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			Information info = new Information();
			info.setState(1); //查找已发布的
			List<Information> infoListhot = null;
			info.setStartPageNo(0);
			info.setPageSize(10);
			try {
				infoListhot = informationService.getList(info);  //查找前十条最热的下载榜
			} catch (Exception e) {
				e.printStackTrace();
			}	
			model.addAllAttributes(baseModel);
			model.addAttribute("infoListhot", infoListhot);
			return new ModelAndView("backend/datumDownload");
		}
	}
	
	@RequestMapping(value="/backend/loadInfoDownload.html")
	public ModelAndView loadInfoDownload(HttpSession session,Model model,
			@RequestParam(value="pageIndex",required=false) String pageIndex){
		@SuppressWarnings("unchecked")
		Map<String,Object> baseModel = (Map<String, Object>) session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			Information info = new Information();
			info.setState(1); //查找已发布的
			//page分页列表
			PageSupport page = new PageSupport();
			try {
				page.setTotalCount(informationService.count(info));
			} catch (Exception e) {
				e.printStackTrace();
				page.setTotalCount(0);
			}
			
			if(page.getTotalCount() > 0){
				
				if(!StringUtils.isNullOrEmpty(pageIndex)){
					page.setCurrentPageNo(Integer.valueOf(pageIndex));
				}else{
					page.setCurrentPageNo(1);
				}
					
				
				//设置当前页号，控制首尾页
				if(page.getCurrentPageNo() <= 0){
					page.setCurrentPageNo(1);
				}
				
				if(page.getCurrentPageNo() > page.getTotalPageCount()){
					page.setCurrentPageNo(page.getTotalPageCount());
				}
				
				//mysql -- 分页查询limit ?,? （第一个参数下标从零开始）
				info.setStartPageNo((page.getCurrentPageNo() -1)*page.getPageSize());
				info.setPageSize(page.getPageSize());
				System.out.println("此次将查询从 "+
						(page.getCurrentPageNo()-1)*page.getPageSize()+"开始的 "+
						 page.getPageSize()+" 条数据！");
				
				try {
					page.setItems(informationService.getList(info));
				} catch (Exception e) {
					e.printStackTrace();
					page.setItems(null);
				}
			}else{
				page.setItems(null);
			}	
			model.addAllAttributes(baseModel);
			model.addAttribute("page", page);
			return new ModelAndView("template");
		}
	}

	
	
	
}
