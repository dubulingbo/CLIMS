package cn.clims.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.StringUtils;

import cn.clims.pojo.DataDictionary;
import cn.clims.pojo.Dept;
import cn.clims.pojo.Information;
import cn.clims.pojo.Role;
import cn.clims.pojo.UploadTemp;
import cn.clims.pojo.User;
import cn.clims.service.dataDictionary.DataDictionaryService;
import cn.clims.service.dept.DeptService;
import cn.clims.service.information.InformationService;
import cn.clims.service.instrument.InstrumentService;
import cn.clims.service.role.RoleService;
import cn.clims.service.uploadtemp.UploadTempService;
import cn.clims.service.user.UserService;
import cn.clims.tools.Constants;
import cn.clims.tools.HtmlEncode;
import cn.clims.tools.JsonDateValueProcessor;
import cn.clims.tools.MD5;
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
	
	@Resource 
	private InformationService informationService;
	
	@Resource
	private DataDictionaryService dataDictionaryService;
	
	@Resource
	private InstrumentService instrumentService;

	@Resource
	private UploadTempService uploadTempService;

/*********************************用户管理模块开发**************************************/
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
				String userPassword = MD5.encrypt(password.substring(password.length()-6));
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
	
	
	
	
	
	
	
/*********************************资讯管理模块开发**************************************/
	@RequestMapping(value="/infoManage.html")
	@SuppressWarnings("unchecked")
	public ModelAndView infoManage(HttpSession session, Model model,
			@RequestParam(value="pageIndex",required=false)String pageIndex){
		Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			//获取dicList
			DataDictionary dic = new DataDictionary();
			dic.setTypeCode("INFO_TYPE");
			List<DataDictionary> dicList = null;
			try {
				dicList = dataDictionaryService.getDicListByTypeCode(dic);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			//page分页列表
			PageSupport page = new PageSupport();
			Information info = new Information();
			System.out.println("pageIndex====================== " + pageIndex);
			try {
				page.setTotalCount(informationService.getInformationCount());
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
				info.setStartPageNo((page.getCurrentPageNo() - 1)*page.getPageSize());
				info.setPageSize(page.getPageSize());
				List<Information> infoList = null;
				try {
					infoList = informationService.getInformationList(info);
				} catch (Exception e) {
					e.printStackTrace();
					infoList = null;
				}
				page.setItems(infoList);
			}else{
				page.setItems(null);
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("dicList", dicList);
			model.addAttribute("page", page);
			return new ModelAndView("admin/infoManage");
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/addInformation.html")
	public ModelAndView addInfo(HttpSession session,Model model){
		Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null){
			return new ModelAndView("redirect:/");
		}else{
			//获取dicList
			DataDictionary dic = new DataDictionary();
			dic.setTypeCode("INFO_TYPE");
			List<DataDictionary> dicList = null;
			try {
				dicList = dataDictionaryService.getDicListByTypeCode(dic);
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("dicList", dicList);
			return new ModelAndView("admin/addInformation");
		}
	}
	
	@RequestMapping(value = "/information/uploadInfoFile.html",
			produces={"text/html;charset=UTF-8"}, method=RequestMethod.POST)  
	@ResponseBody
    public Object uploadInfoFile(@RequestParam(value ="uploadInformationFile",required = false) MultipartFile uploadInformationFile, 
    		@RequestParam(value ="uploadInformationFile",required = false) MultipartFile uploadInformationFileM, 
    					 HttpServletRequest request,HttpSession session){
        if(uploadInformationFile == null && uploadInformationFileM != null)
        	uploadInformationFile = uploadInformationFileM;
        if(uploadInformationFile != null){
        	String path = request.getSession().getServletContext().
        			getRealPath("statics"+File.separator+"infofiles");
        	String oldFileName = uploadInformationFile.getOriginalFilename();
        	System.out.println("==========oldFileName : " + oldFileName);
            String prefix=FilenameUtils.getExtension(oldFileName);
            int filesize = 50*1024*1024;
            if(uploadInformationFile.getSize() >  filesize){//上传大小不得超过 50M
            	return Constants.FILEUPLOAD_ERROR_3;
            }else{
            	String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_info."+prefix;  
                File targetFile = new File(path, fileName);  
                if(!targetFile.exists()){  
                    targetFile.mkdirs();  
                }  
                //保存  
                try {  
                	uploadInformationFile.transferTo(targetFile);  
                	//add file info to uploadtemp
                	User sessionUser =  ((User)session.getAttribute(Constants.CURRENT_USER));
                	UploadTemp uploadTemp = new UploadTemp();
                	uploadTemp.setUploader(sessionUser.getUserCode());
                	uploadTemp.setUploadType("info");
                	uploadTemp.setUploadFilePath(File.separator + "statics" + File.separator + "infofiles" + File.separator + fileName );
                	uploadTempService.add(uploadTemp);
                	String url = oldFileName + "[[[]]]" + request.getContextPath()+"/statics/infofiles/"+fileName + "size:"+uploadInformationFile.getSize();
                    return url;
                } catch (Exception e) {
                    e.printStackTrace();
                    return Constants.FILEUPLOAD_ERROR_1;
                }
            }
        }
        return Constants.FILEUPLOAD_ERROR_5;
    }
	
	@RequestMapping(value="/information/delInfoFile.html",method=RequestMethod.POST)
	@ResponseBody
	public Object delInfoFile( HttpServletRequest request,HttpSession session,@RequestParam String filePath,
			@RequestParam(value="flag",required=false)String flag){
		if(StringUtils.isNullOrEmpty(filePath) || StringUtils.isNullOrEmpty(flag)){
			return "nodata";
		}else{
			try {
				String path = request.getSession().getServletContext().getRealPath("/");
				File file = new File(path + filePath);
				int f = Integer.valueOf(flag); //flag = 1 --添加资讯信息中的删除文件；   flag = 2 --修改资讯信息中的删除文件
				if(file.exists()){
					file.delete();
				}
				if(f == 2){
					Information information = new Information();
					information.setTypeName(filePath);
					information.setFileName("");
					information.setFilePath("#");
					information.setFileSize(0d);
					System.out.println("本次从clims_infomation表中删除的记录数为 ： " + informationService.modifyInformationFileInfo(information));
				}
				UploadTemp uploadTemp = new UploadTemp();
				filePath = filePath.replaceAll("/", File.separator+File.separator);
				uploadTemp.setUploadFilePath(filePath);
				System.out.println("本次从clims_uploadTemp表中删除的记录数为 ： " + uploadTempService.delete(uploadTemp));
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
			return "success";
		}
	}
	
	@RequestMapping(value="/addInfoSave.html",method=RequestMethod.POST)
	public ModelAndView addInfoSave(HttpSession session,
			@ModelAttribute("addInfo") Information addInfo){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("/ulogin");
		}else{
			System.out.println("addInfoSave==========");
			System.out.println("\ttitle : "+addInfo.getTitle());
			System.out.println("\tcontent : "+addInfo.getContent());
			System.out.println("\tstate : "+addInfo.getState());
			System.out.println("\ttypeId : "+addInfo.getTypeId());
			
			try {
				//处理标题
				if(null != addInfo.getTitle() && !addInfo.getTitle().equals("")){
					System.out.println("======= addInformation HtmlEncode.htmlEncode(information.getTitle())================" + HtmlEncode.htmlEncode(addInfo.getTitle()));
					addInfo.setTitle(HtmlEncode.htmlEncode(addInfo.getTitle()));
				}
				User sessionUser =  ((User)session.getAttribute(Constants.CURRENT_USER));
				addInfo.setUploadDate(new Date());
				UploadTemp uploadTemp = new UploadTemp();
            	uploadTemp.setUploader(sessionUser.getUserCode());
            	uploadTemp.setUploadType("info");
            	uploadTemp.setUploadFilePath(addInfo.getFilePath().replaceAll("/", File.separator+File.separator));
            	System.out.println("已成功删除图片记录的条数为   : " + uploadTempService.delete(uploadTemp));  //删除之前缓存的图片记录
				informationService.addInformation(addInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/backend/admin/infoManage.html");
		}
	}

	@RequestMapping("/information/modifyInfoState.html")
	@ResponseBody
	public Object modifyInfoState(HttpSession session,@RequestParam String inforState){
		if(StringUtils.isNullOrEmpty(inforState)){
			return "nodata";
		}else{
			try {
				JSONObject informationObject = JSONObject.fromObject(inforState);
				Information information =  (Information)JSONObject.toBean(informationObject, Information.class);
				information.setPublisher(((User)session.getAttribute(Constants.CURRENT_USER)).getUserCode());
				information.setPublishDate(new Date());
				informationService.modifyInformation(information);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
	}
	
	@RequestMapping(value="/viewInformation.html", 
			produces = {"text/html;charset=UTF-8"},method=RequestMethod.POST)
	@ResponseBody
	public Object viewInfo(HttpSession session,@RequestParam Integer id){
		String result = "";
		if(null == id || "".equals(id)){
			result =  "nodata";
		}else{
			try {
				Information information = new Information();
				information.setId(id);
				information = informationService.getInformation(information);
				if(null != information && information.getTitle() != null){
					information.setTitle(HtmlEncode.htmlDecode(information.getTitle()));
					JsonConfig jsonConfig = new JsonConfig();
					jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor("yyyy-MM-dd HH:mm"));
					result =  JSONObject.fromObject(information,jsonConfig).toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
				result =  "failed";
			}
		}
		return result;
	}
	
	@RequestMapping(value="/deleteInformation.html",method=RequestMethod.POST)
	@ResponseBody
	public Object delInfo(HttpServletRequest request,HttpSession session,@RequestParam Integer id){
		
		if(null == id || "".equals(id)){
			return "nodata";
		}else{
			try {
				Information information = new Information();
				information.setId(id);
				Information _information = new Information();
				_information = informationService.getInformation(information);
				if(null != _information){
					String path = request.getSession().getServletContext().getRealPath("/");  
					_information.setFilePath(_information.getFilePath().replace("/", File.separator+File.separator));
					File file = new File(path + _information.getFilePath());
					if(file.exists()){
						file.delete();
					}
					informationService.deleteInformation(information);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
			return "success";
		}
	}
	
	//modifyInformation.html
	@RequestMapping(value="/modifyInformation.html",method=RequestMethod.POST)
	public ModelAndView modifyInfo(@ModelAttribute("modifyInformation") Information information,HttpSession session){
		if(session.getAttribute(Constants.SESSION_BASE_MODEL) == null){
			return new ModelAndView("redirect:/");
		}else{
			try {
//				User sessionUser =  ((User)session.getAttribute(Constants.CURRENT_USER));
//				information.setPublisher(sessionUser.getUserCode());
//				information.setPublishDate(new Date(System.currentTimeMillis()));
				//information.setState(1);
				information.setUploadDate(new Date());
				if(null != information.getTitle() && !information.getTitle().equals("")){
					System.out.println("======= modifyInformation HtmlEncode.htmlEncode(information.getTitle())================" + 
								HtmlEncode.htmlEncode(information.getTitle()));
					information.setTitle(HtmlEncode.htmlEncode(information.getTitle()));
				}
				informationService.modifyInformation(information);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("redirect:/backend/admin/infoManage.html");
	}
	
	
	
	
	
	
	
	
}
