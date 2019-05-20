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

import cn.clims.pojo.Dept;
import cn.clims.pojo.Role;
import cn.clims.pojo.User;
import cn.clims.service.dept.DeptService;
import cn.clims.service.role.RoleService;
import cn.clims.service.user.UserService;
import cn.clims.tools.Constants;
import cn.clims.tools.JsonDateValueProcessor;
import cn.clims.tools.PageSupport;
import cn.clims.tools.SQLTools;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/backend/admin")
public class AdminController extends BaseController {
	//private Logger logger = Logger.getLogger(AdminController.class);
	
	@Resource
	private UserService userService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private DeptService deptService;
	

	
	/**
	 * 查看用户列表（分頁查詢）
	 * @param model
	 * @param session
	 * @param s_userCode
	 * @param s_roleId
	 * @param pageIndex
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value="/userManage.html")
	public ModelAndView userManage(Model model,HttpSession session,
			@RequestParam(value="s_userCode",required=false) String s_userCode,
			@RequestParam(value="s_roleId",required=false) String s_roleId,
			@RequestParam(value="pageIndex",required=false) String pageIndex){
		System.out.println("进入用户管理页面！");
		Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			//获取roleList and deptList
			List<Role> roleList = null;
			List<Dept> deptList = null;
			try {
				roleList = roleService.getRoleList();
				deptList = deptService.getDeptList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//获取用户列表或用作模糊查询----設置查詢條件，放入user對象中
			User user = new User();
			if(null != s_userCode){
				user.setUserCode(SQLTools.transfer(s_userCode));
			}
			if(!StringUtils.isNullOrEmpty(s_roleId)){
				user.setUserRole(Integer.valueOf(s_roleId));
			}else{
				user.setUserRole(null);
			}
			
			
			System.out.println("pageIndex====================== " + pageIndex);
			
			//page分页列表
			PageSupport page = new PageSupport();
			try {
				page.setTotalCount(userService.getUserCount(user));
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
				user.setStartPageNo((page.getCurrentPageNo() -1)*page.getPageSize());
				user.setPageSize(page.getPageSize());
				System.out.println("此次将查询从 "+
						(page.getCurrentPageNo()-1)*page.getPageSize()+"开始的 "+
						 page.getPageSize()+" 条数据！");
				List<User> userList = null;
				try {
					userList = userService.getUserList(user);
				} catch (Exception e) {
					e.printStackTrace();
					userList = null;
					if(page == null){
						page = new PageSupport();
						page.setItems(null);
					}
				}
				page.setItems(userList);
			}else{
				page.setItems(null);
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("page", page);
			model.addAttribute("roleList", roleList);
			model.addAttribute("deptList", deptList);
			//用于回显
			model.addAttribute("s_userCode", s_userCode);
			model.addAttribute("s_roleId", s_roleId);
			return new ModelAndView("admin/userManage");
		}
	}
	
	@RequestMapping(value="/userCodeIsExist.html",method=RequestMethod.POST)
	@ResponseBody
	public Object userCodeIsExist(@RequestParam(value="userCode",required=false)String userCode,
			@RequestParam(value="id",required=false)String id){
		System.out.println("userCodeIsExist userCode = "+userCode+"  id = "+id);
		
		String result = "failed";
		User _user = new User();
		_user.setUserCode(userCode);
		if(!id.equals("-1")){ //修改操作进行同名验证
			_user.setId(Integer.valueOf(id));
		}
		
		try {
			if(userService.userCodeIsExist(_user) == 0){
				result = "only";
			}else{
				result = "repeat";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/addUser.html",method=RequestMethod.POST)
	public ModelAndView addUser(HttpSession session,
			@ModelAttribute("addUser") User addUser){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				System.out.println("addUser========"+addUser);
				String password = addUser.getUserPassword();
				String userPassword = password.substring(password.length()-6);
				addUser.setUserPassword(userPassword);
				addUser.setCreationDate(new Date());
				addUser.setCreatedBy(this.getCurrentUser().getId());
				
				userService.addUser(addUser);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return new ModelAndView("redirect:/backend/admin/userManage.html");
		}
	}
	
	@RequestMapping(value="/viewUser.html",produces={"text/html;charset=UTF-8"},method=RequestMethod.POST)
	@ResponseBody
	public Object viewUser(@RequestParam(value="id",required=false)String id){
		String cjson = "";
		if(id.equals("") || id==null){
			cjson = "nodata";
		}else{     
			try {
				User user = new User();
				user.setId(Integer.valueOf(id));
				
				user = userService.getUserById(user);
				//user对象里有日期，所有有日期的属性，都要按照此日期格式进行json转换（对象转json）
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
				JSONObject jo = JSONObject.fromObject(user,jsonConfig);
				cjson = jo.toString();
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return "failed";
			}
		}
		return cjson;
	}
	
	@RequestMapping(value="/perfectUser.html",method=RequestMethod.POST)
	public ModelAndView perfectUser(HttpSession session,@ModelAttribute("modifyUser")User modifyUser){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL)==null || getCurrentUser() == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
				modifyUser.setModifyBy(getCurrentUser().getId());
				modifyUser.setModifyDate(new Date());
				userService.modifyUser(modifyUser);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return new ModelAndView("redirect:/backend/admin/userManage.html");
	}
	
	@RequestMapping(value="/deleteUser.html",produces="text/html;charset=UTF-8",method=RequestMethod.POST)
	@ResponseBody
	public Object deleteUser(@RequestParam(value="delId",required=false)String delId,
			HttpSession session){
		String result = "failed";
		if(session.getAttribute(Constants.SESSION_BASE_MODEL)==null || getCurrentUser()==null){
			return new ModelAndView("redirect:/");
		}
		try {
			User delUser = new User();
			delUser.setId(Integer.valueOf(delId));
			delUser = userService.getUserById(delUser);
			//只能删除比自己级别低的用户
			if(delUser.getUserRole()<=this.getCurrentUser().getUserRole()){
				return "noallow";
			}else{
				if(userService.deleteUser(delUser) == 1){
					return "success";
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	
}
