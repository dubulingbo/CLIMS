package cn.clims.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import cn.clims.pojo.Affiche;
import cn.clims.pojo.InstAssign;
import cn.clims.pojo.InstRepair;
import cn.clims.pojo.InstScrap;
import cn.clims.pojo.InstStock;
import cn.clims.pojo.Instrument;
import cn.clims.pojo.User;
import cn.clims.service.country.CountryService;
import cn.clims.service.dept.DeptService;
import cn.clims.service.instrument.InstrumentService;
import cn.clims.service.species.SpeciesService;
import cn.clims.service.user.UserService;
import cn.clims.tools.Constants;
import cn.clims.tools.InstrumentNoGenerator;
import cn.clims.tools.JsonDateValueProcessor;
import cn.clims.tools.PageSupport;
import cn.clims.tools.SQLTools;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

@Controller
public class InstrumentController extends BaseController{
	//private Logger logger = Logger.getLogger(InstrumentController.class);
	
	@Resource
	private InstrumentService instrumentService;
	@Resource
	private UserService userService;
	@Resource
	private SpeciesService speciesService;
	@Resource
	private DeptService deptService;
	@Resource
	private CountryService countryService;
	
	
	/**
	 * 进入仪器库存管理页面（分页操作）
	 */
	@RequestMapping(value="/backend/admin/instrumentManage.html")
	public ModelAndView instrumentManage(Model model,HttpSession session,
			@RequestParam(value="s_instrumentName",required=false) String s_instrumentName,
			@RequestParam(value="s_instrumentNo",required=false) String s_instrumentNo,
			@RequestParam(value="s_classNo",required=false) String s_classNo,
			@RequestParam(value="s_dept",required=false) String s_dept,
			@RequestParam(value="pageIndex",required=false) String pageIndex){
		@SuppressWarnings("unchecked")
		Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		
		//获取仪器列表或用作模糊查询----設置查詢條件，放入instrument對象中
		InstStock instStock = new InstStock();
		instStock.setStockNumber(0);  //库存数量大于零
		if(!StringUtils.isNullOrEmpty(s_instrumentName)){
			instStock.setInstrumentName(SQLTools.transfer(s_instrumentName));
		}
		if(!StringUtils.isNullOrEmpty(s_instrumentNo)){
			instStock.setInstrumentNo(SQLTools.transfer(s_instrumentNo));
		}
		if(!StringUtils.isNullOrEmpty(s_classNo)){
			instStock.setClassNo(Integer.valueOf(s_classNo));
		}else{
			instStock.setClassNo(null);
		}
		
		//获取分页对象
		PageSupport page = new PageSupport();
		try {
			page.setTotalCount(instrumentService.getInstStockCount(instStock));
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
			instStock.setStartPageNo((page.getCurrentPageNo() -1)*page.getPageSize());
			instStock.setPageSize(page.getPageSize());

			try {
				page.setItems(instrumentService.getInstStockList(instStock));
				//获取speciesList and deptList and countryList
				model.addAttribute("speciesList", speciesService.getSpeciesList());
				model.addAttribute("deptList", deptService.getDeptList());
				model.addAttribute("countryList", countryService.getCountryList());
			} catch (Exception e) {
				e.printStackTrace();
				page.setItems(null);
			}
			
		}else{
			page.setItems(null);
		}
		model.addAllAttributes(baseModel);
		model.addAttribute("page", page);
		//用于回显
		model.addAttribute("s_instrumentName", s_instrumentName);
		model.addAttribute("s_instrumentNo", s_instrumentNo);
		model.addAttribute("s_classNo", s_classNo);
		model.addAttribute("s_dept", s_dept);
		return new ModelAndView("admin/instrumentManage");
	}
	
	/**
	 * 添加仪器库存信息
	 */
	@RequestMapping(value="/backend/admin/addInstrument.html",method=RequestMethod.POST)
	public ModelAndView addInstrument(@ModelAttribute("addInstrument") InstStock addInstStock){
		System.out.println("正在保存刚刚提交的仪器信息！");
		try{
			String year = new SimpleDateFormat("yyyy").format(addInstStock.getProductionDate());
			System.out.println("该仪器的出厂年份为：" + year);
			String instrumentNo = "";
			do{
				instrumentNo = InstrumentNoGenerator.generateStockNo(
						year, addInstStock.getCreatedBy(), addInstStock.getClassNo());
			}while(instrumentService.instrumentNoIsExist(instrumentNo) != 0);
			addInstStock.setCreationDate(new Date());
			addInstStock.setCreatedBy(this.getCurrentUser().getId());
			addInstStock.setInstrumentNo(instrumentNo);
			addInstStock.setStockNumber(addInstStock.getNumber());
			instrumentService.cl_addInstStock(addInstStock);
			return new ModelAndView("redirect:/backend/admin/instrumentManage.html");	
		}catch(Exception e){
			e.printStackTrace();
			return new ModelAndView("redirect:/");
		}
	}
	
	/**
	 * 判断仪器是否存在，即判断 instrumentName+instrumentType;
	 */
	@RequestMapping(value="/backend/admin/instrumentIsExist.html",
			produces={"text/html;charset=UTF-8"},method=RequestMethod.POST)
	@ResponseBody
	public Object instrumentIsExist(
			@RequestParam(value="instrumentName",required=false) String instrumentName,
			@RequestParam(value="instrumentType",required=false) String instrumentType){
		if(StringUtils.isNullOrEmpty(instrumentName) || StringUtils.isNullOrEmpty(instrumentType)){
			return "empty";
		}else{
			try {
				Instrument instrument = new Instrument();
				instrument.setInstrumentName(instrumentName);
				instrument.setInstrumentType(instrumentType);
				if(instrumentService.instrumentIsExist(instrument) == 0)
					return "noexist";
				return "exist";
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
	}
	/**
	 * 查看仪器基本信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/backend/viewInstrument.html",produces={"text/html;charset=UTF-8"},method=RequestMethod.POST)
	@ResponseBody
	public Object viewInstrument(@RequestParam(value="id",required=false) String id){
		System.out.println("viewInstrument=========== id : " + id);
		String cjson = "";
		if(StringUtils.isNullOrEmpty(id)){
			cjson = "nodata";
		}else{
			try {
				//所有有日期的属性，都要按照此日期格式进行json转换（对象转json）
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd"));
				JSONObject jo = JSONObject.fromObject(
						instrumentService.getInstStockByInstId(Integer.valueOf(id)),jsonConfig);
				cjson = jo.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
		return cjson;
	}
	

	
	/**
	 * 进入仪器设备分类统计页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/backend/admin/instrumentClassify.html")
	public ModelAndView instrumentClassify(Model model,HttpSession session){
		Map<String,Object> baselModel = (Map<String, Object>) session.getAttribute(Constants.SESSION_BASE_MODEL);
		
			
			
		model.addAllAttributes(baselModel);
		return new ModelAndView("admin/instrumentClassify");	
	}
	
	/**
	 * 进入仪器调拨管理页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/backend/admin/instAssignManage.html")
	public ModelAndView instAssignManage(Model model,HttpSession session,
			@RequestParam(value="pageIndex",required=false) String pageIndex){
		Map<String,Object> baselModel = (Map<String, Object>) session.getAttribute(Constants.SESSION_BASE_MODEL);
		//系统管理员只能查看（对自己可见）的仪器调拨申请
		InstAssign assign = new InstAssign();
		assign.setOpacifyToAdmin(Constants.OPACIFY_TO_ADMIN_2);
		assign.setPartitionNo(1);
		PageSupport page = new PageSupport();
		
		try {
			page.setTotalCount(instrumentService.getInstAssignCount(assign));
		} catch (Exception e) {
			e.printStackTrace();
			page.setTotalCount(0);
		}
		if(page.getTotalCount() > 0){
			if(!StringUtils.isNullOrEmpty(pageIndex)){
				page.setCurrentPageNo(Integer.valueOf(pageIndex));
			}else{page.setCurrentPageNo(1);}
			
			//设置当前页号，控制首尾页
			if(page.getCurrentPageNo() <= 0){
				page.setCurrentPageNo(1);
			}
			if(page.getCurrentPageNo() > page.getTotalPageCount()){
				page.setCurrentPageNo(page.getTotalPageCount());
			}
			
			//mysql -- 分页查询limit ?,? （第一个参数下标从零开始）
			assign.setStartPageNo((page.getCurrentPageNo() - 1)*page.getPageSize());
			assign.setPageSize(page.getPageSize());
			try {
				page.setItems(instrumentService.getInstAssignList(assign));
			} catch (Exception e) {
				e.printStackTrace();
				page.setItems(null);
			}
		}else {
			page.setItems(null);
		}
		model.addAttribute("page", page);
		model.addAllAttributes(baselModel);
		return new ModelAndView("admin/instAssignManage");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 进入仪器调拨申请页面
	 */
	@RequestMapping(value="/backend/normaluser/instrumentAssignApply.html")
	public ModelAndView instrumentAssignApply(Model model,HttpSession session,
			@RequestParam(value="s_instrumentName",required=false) String s_instrumentName,
			@RequestParam(value="s_instrumentNo",required=false) String s_instrumentNo,
			@RequestParam(value="s_classNo",required=false) String s_classNo,
			@RequestParam(value="pageIndex",required=false) String pageIndex){
		@SuppressWarnings("unchecked")
		Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		
		//获取库存表信息，且当前可申请的数量必须大于零
		InstStock instStock = new InstStock();
		instStock.setStockNumber(0);
		if(!StringUtils.isNullOrEmpty(s_instrumentName)){
			instStock.setInstrumentName(SQLTools.transfer(s_instrumentName));
		}
		if(!StringUtils.isNullOrEmpty(s_instrumentNo)){
			instStock.setInstrumentNo(SQLTools.transfer(s_instrumentNo));
		}
		if(!StringUtils.isNullOrEmpty(s_classNo)){
			instStock.setClassNo(Integer.valueOf(s_classNo));
		}else{
			instStock.setClassNo(null);
		}
		//获取分页对象
		PageSupport page = new PageSupport();
		try {
			page.setTotalCount(instrumentService.getInstStockCount(instStock));
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
			
			if(page.getCurrentPageNo() <= 0){
				page.setCurrentPageNo(1);
			}
			if(page.getCurrentPageNo() > page.getTotalPageCount()){
				page.setCurrentPageNo(page.getTotalPageCount());
			}
			
			instStock.setStartPageNo((page.getCurrentPageNo()-1)*page.getPageSize());
			instStock.setPageSize(page.getPageSize());
			
			try {
				page.setItems(instrumentService.getInstStockList(instStock));
				//获取speciesList
				model.addAttribute("speciesList", speciesService.getSpeciesList());
			} catch (Exception e) {
				e.printStackTrace();
				page.setItems(null);
			}
		}else{
			page.setItems(null);
		}
		model.addAllAttributes(baseModel);
		model.addAttribute("page", page);
		//用于回显
		model.addAttribute("s_instrumentName", s_instrumentName);
		model.addAttribute("s_instrumentNo", s_instrumentNo);
		model.addAttribute("s_classNo", s_classNo);
		model.addAttribute("pageIndex", pageIndex);
		
		try {
			//获取已申请调拨的列表-- createdBy设置为当前用户   status=[7~10] 
			InstAssign assign = new InstAssign();
			assign.setCreatedBy(this.getCurrentUser().getId());
			System.out.println("当前用户的id为："+this.getCurrentUser().getId());
			assign.setPartitionNo(1);
			model.addAttribute("my_assignApplyList", instrumentService.getInstAssignList(assign));
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("my_assignApplyList", null);
		}
		
		return new ModelAndView("normalUser/instrumentAssignApply");
	}
	/**
	 * 保存普通使用者的调拨申请
	 */
	@RequestMapping(value="/backend/normaluser/instrumentAssignSave.html",method=RequestMethod.POST)
	@ResponseBody
	public Object instrumentAssignSave(@RequestParam String instAssign){
		System.out.println("正在保存本次调拨申请！");
		if(StringUtils.isNullOrEmpty(instAssign)){
			return "nodata";
		}else{
			try {
				JSONObject assignObj = JSONObject.fromObject(instAssign);
				InstAssign instAssign2 = (InstAssign)JSONObject.toBean(assignObj,InstAssign.class);
				instAssign2.setDept(this.getCurrentUser().getDept());
				instAssign2.setCreatedBy(this.getCurrentUser().getId());
				instAssign2.setCreationDate(new Date());
				instAssign2.setStatus(Constants.INSTRUMENT_STATUS_1);  //将该条记录设置成  申请调拨  状态
				instAssign2.setOpacifyToAdmin(Constants.OPACIFY_TO_ADMIN_1);
				instAssign2.setLocNo(this.getCurrentUser().getLocNo());
				Affiche aff = new Affiche();
				aff.setCode(Constants.INSTRUMENT_STATUS_1);
				aff.setTypeId(Constants.AFFICHE_TYPE_1);
				aff.setTitle("申请仪器调拨");
				aff.setContent(this.getCurrentUser().getUserName()+"于"+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())+"申请仪器调拨.");
				aff.setPublishDate(new Date());
				aff.setPublisher(this.getCurrentUser().getUserName());
				instrumentService.cl_addInstAssign(instAssign2,aff);
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
			return "success";
		}
	}
	
	
	/**
	 * 进入仪器维修申请页面
	 */
	@RequestMapping(value="/backend/normaluser/instrumentRepairApply.html")
	public ModelAndView instrumentRepairApply(Model model,HttpSession session,
			@RequestParam(value="s_instrumentNo",required=false) String s_instrumentNo,
			@RequestParam(value="pageIndex",required=false) String pageIndex){
		System.out.println("进入仪器维修申请页面！");
		@SuppressWarnings("unchecked")
		Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		
		//获取调拨表信息，且createdBy = 当前用户的ID  &&  status = 7(已调拨/使用中)  &&  maintainable = "yes" && 调拨表的p2分区 
		InstAssign instAssign = new InstAssign();
		instAssign.setCreatedBy(this.getCurrentUser().getId());
		instAssign.setStatus(Constants.INSTRUMENT_STATUS_7);
		instAssign.setMaintainable("yes");
		instAssign.setPartitionNo(2);
		
		if(!StringUtils.isNullOrEmpty(s_instrumentNo)){
			instAssign.setInstrumentNo(SQLTools.transfer(s_instrumentNo));
		}
		
		//获取分页对象
		PageSupport page = new PageSupport();
		try {
			page.setTotalCount(instrumentService.getInstAssignCount(instAssign));
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
			
			if(page.getCurrentPageNo() <= 0){
				page.setCurrentPageNo(1);
			}
			if(page.getCurrentPageNo() > page.getTotalPageCount()){
				page.setCurrentPageNo(page.getTotalPageCount());
			}
			
			instAssign.setStartPageNo((page.getCurrentPageNo()-1)*page.getPageSize());
			instAssign.setPageSize(page.getPageSize());
			
			try {
				page.setItems(instrumentService.getInstAssignList(instAssign));
				model.addAttribute("deptList", deptService.getDeptList());
			} catch (Exception e) {
				e.printStackTrace();
				page.setItems(null);
			}
		}else{
			page.setItems(null);
		}
		model.addAllAttributes(baseModel);
		model.addAttribute("page", page);
		try {
			//获取已申维修的列表-- createdBy设置为当前用户   status=[7~10] 
			InstRepair repair = new InstRepair();
			repair.setCreatedBy(this.getCurrentUser().getId());
			model.addAttribute("my_repairApplyList", instrumentService.getInstRepairList(repair));
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("my_repairApplyList", null);
		}
		return new ModelAndView("normalUser/instrumentRepairApply");
	}
	/**
	 * 在普通用户进行维修申请时，快速填写当前登录用户的信息
	 */
	@RequestMapping(value="/backend/normaluser/writeSelfInfo.html",produces={"text/html;charset=UTF-8"},method=RequestMethod.POST)
	@ResponseBody
	public Object writeSelfInfo(@RequestParam(value="u_id",required=false) String _id){
		
		User user = new User();
		if(StringUtils.isNullOrEmpty(_id)){
			user.setId(this.getCurrentUser().getId());
		}else{
			user.setId(Integer.valueOf(_id));
		}
		System.out.println("正在读取编号为u_id = "+user.getId()+"用户的数据！");
		try {
			return JSONObject.fromObject(userService.getUserById(user)).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
	}
	/**
	 * 保存普通使用者维修申请
	 */
	@RequestMapping(value="/backend/normaluser/instrumentRepairSave.html",
			produces={"text/html;charset=UTF-8"},method=RequestMethod.POST)
	@ResponseBody
	public Object instrumentRepairSave(@RequestParam("instRepair") String instRepair){
		System.out.println("正在保存本次维修申请！instRepair : "+instRepair);
		String json = null;
		if(StringUtils.isNullOrEmpty(instRepair)){
			json = "nodata";
		}else{
			try {
				
				JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"}));
				InstRepair instRepair2 = (InstRepair)JSONObject.toBean(JSONObject.fromObject(instRepair),InstRepair.class);
				instRepair2.setCreatedBy(this.getCurrentUser().getId());
				instRepair2.setCreationDate(new Date());
				instRepair2.setStatus(Constants.INSTRUMENT_STATUS_8);  //将该条记录设置成  申请维修  状态
				Affiche aff = new Affiche();
				aff.setCode(Constants.INSTRUMENT_STATUS_8);
				aff.setTypeId(Constants.AFFICHE_TYPE_2);
				aff.setTitle("申请仪器维修");
				aff.setContent(instRepair2.getApplyPerson()+"于 "+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())+"申请维修仪器.");
				aff.setPublishDate(new Date());
				aff.setPublisher(instRepair2.getApplyPerson());
				
				//获取已申请的维修记录、便于实时刷新页面的已申请的维修记录表格
				//所有有日期的属性，都要按照此日期格式进行json转换（对象转json）
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm"));
				JSONArray jo = JSONArray.fromObject(instrumentService.cl_addInstRepair(instRepair2,aff),jsonConfig);
				json = jo.toString();
			} catch (Exception e) {
				e.printStackTrace();
				json = "failed";
			}
		}
		return json;
	}
	/**
	 * 异步上传图片到服务器上（多张）
	 */
	@RequestMapping(value="/backend/normaluser/uploadImgToServer",produces={"text/html;charset=UTF-8"},method=RequestMethod.POST)
	@ResponseBody
	public Object uploadImgToServer(HttpServletRequest request,HttpSession session,
				@RequestParam(value="rep_repairImgPath_file",required=false)MultipartFile repairImgFile){
		List<String> imgPathList= this.getCurrentUser().getRepairImgList();
		if(imgPathList.size() >= Constants.MAXNUMBER_REPAIR_IMG){
			return Constants.FILEUPLOAD_ERROR_4;
		}
        //根据服务器的操作系统，自动获取物理路径，自动适应各个操作系统的路径
        String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");  
        System.out.println("磁盘物理路径为 ======== " + path);
        
        if(repairImgFile != null){
        	String oldFileName = repairImgFile.getOriginalFilename();//获取原文件名
            String suffix=FilenameUtils.getExtension(oldFileName);//取文件后缀
            System.out.println("上传的文件的后缀名为======== " + suffix);
            if(repairImgFile.getSize() >  Constants.MAXSIZE_REPAIR_IMG){//上传大小不得超过5M
            	return Constants.FILEUPLOAD_ERROR_3;
            }else if(suffix.equals("jpg") || suffix.equals("png") 
            		|| suffix.equals("jpeg") || suffix.equals("pneg")){
            	//给文件重命名：系统毫秒数+100W以内的随机数
            	String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_repairImg."+suffix;
            	
                File targetFile = new File(path,fileName);
                if(!targetFile.exists()){  
                    targetFile.mkdirs();  
                }
                
                //保存  
                try {  
                	repairImgFile.transferTo(targetFile);   //将文件存到服务器上
                	String url = request.getContextPath()+"/statics/uploadfiles/"+fileName;
                	System.out.println("上传的文件的URL为："+url);
                	imgPathList.add(url);  //将上传的文件的url存起来
                	return url;  
                } catch (Exception e) {  
                    e.printStackTrace();
                    return Constants.FILEUPLOAD_ERROR_1;
                }  
            }else{
            	return Constants.FILEUPLOAD_ERROR_2;
            }
        }
        return Constants.FILEUPLOAD_ERROR_1;
	}
	private boolean delFile(String path){
		File file = new File(path);
		if(file.exists())
			if(file.delete())
				return true;
		return false;
	}
	/**
	 * 删除服务器上的保存的图片
	 */
	@RequestMapping(value = "/backend/normaluser/delImgFromServer.html", 
			produces = {"text/html;charset=UTF-8"},method=RequestMethod.POST)
	@ResponseBody
	public String delPic(@RequestParam(value="imgPath",required=false) String imgPath,
						 @RequestParam(value="id",required=false) String id,
						 @RequestParam(value="f",required=false) String f,
						HttpServletRequest request,HttpSession session){
		String result= "failed" ;
		List<String> zzz = this.getCurrentUser().getRepairImgList();
		System.out.println(" size() ==== "+zzz.size());
		if(f.equals("all")){ //删除全部该用户此次上传的全部文件
			if(zzz.size() <= 0){
				result = "failed";
			}else{
				for(String s : this.getCurrentUser().getRepairImgList()){
					String[] paths1 = s.split("/");
					String path1 = request.getSession().getServletContext().getRealPath(paths1[1]+File.separator+paths1[2]+File.separator+paths1[3]);
					System.out.println("正在删除图片==== "+path1);
					if(!this.delFile(path1)){
						result = "failed";
						break;
					}else{
						result = "success";
					}
				}
				zzz.clear();
			}
		}else if(f.equals("only")){
			if(imgPath == null || imgPath.equals("")){
				result = "failed";
			}else{
				System.out.println("本次删除的图片为："+imgPath);
				//picpath：传过来的网络路径，需要解析成物理路径
				String[] paths = imgPath.split("/");
				String path = request.getSession().getServletContext().getRealPath(paths[1]+File.separator+paths[2]+File.separator+paths[3]);  
				if(!this.delFile(path)){
					result = "failed";
				}else{
					for(int i=0; i<zzz.size();i++){
						if(zzz.get(i).equals(imgPath))
							zzz.remove(i);
					}
					result = "success";
				}
			}
		}
		return result;
	}
	
	/**
	 * 进入仪器报废申请页面
	 */
	@RequestMapping(value="/backend/normaluser/instrumentScrapApply.html")
	public ModelAndView instrumentScrapApply(Model model,HttpSession session){
		System.out.println("进入仪器变动审核页面！");
		@SuppressWarnings("unchecked")
		Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		model.addAllAttributes(baseModel);
		return new ModelAndView("normalUser/instrumentScrapApply");
	}
	
	/**
	 * 判断当前输入的仪器编号是否存在，若存在，则返回当前仪器信息
	 */
	@RequestMapping(value="/backend/normaluser/assignIdIsExist.html",
			produces={"text/html;charset=UTF-8"},method=RequestMethod.POST)
	@ResponseBody
	public Object assignIdIsExist(@RequestParam(value="assignId",required=false) String assignId){
		String cjson = "noexist";
		if(assignId != null && !"".equals(assignId)){
			try {
				InstAssign assign = instrumentService.getInstAssignByAssId(assignId);
				if(assign != null){
					JSONObject jo = JSONObject.fromObject( assign);
					cjson = jo.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cjson;
	}
	
	/**
	 * 保存普通用户的仪器报废申请
	 */
	@RequestMapping(value="/backend/normaluser/instrumentScrapSave.html",method=RequestMethod.POST)
	@ResponseBody
	public Object instrumentScrapSave(
			@RequestParam(value="assignId",required=false) String assignId,
			@RequestParam(value="scrapNumber",required=false) String scrapNumber,
			@RequestParam(value="scrapDetail",required=false) String scrapDetail){
		if(!StringUtils.isNullOrEmpty(assignId) && 
				!StringUtils.isNullOrEmpty(scrapNumber) &&
				!StringUtils.isNullOrEmpty(scrapDetail)){
			try {
				InstScrap instscrap  = new InstScrap();
				instscrap.setAssignId(assignId);
				instscrap.setScrapNumber(Integer.valueOf(scrapNumber));
				instscrap.setScrapDetail(scrapDetail);
				
				Affiche aff = new Affiche();
				aff.setCode(Constants.INSTRUMENT_STATUS_12);
				aff.setTypeId(Constants.AFFICHE_TYPE_3);
				aff.setTitle("申请仪器报废");
				aff.setContent(this.getCurrentUser().getUserName()+"于"+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())+"申请仪器报废.");
				aff.setPublishDate(new Date());
				aff.setPublisher(this.getCurrentUser().getUserName());
			
				instrumentService.cl_addInstScrap(instscrap,aff);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}else{
			return "notdata";
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 进入仪器变动申请审核页面
	 */
	@RequestMapping(value="/backend/manager/instrumentApplyCheck.html")
	public ModelAndView instrumentApplyCheck(Model model,HttpSession session){
		System.out.println("进入仪器变动审核页面！");
		@SuppressWarnings("unchecked")
		Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		model.addAllAttributes(baseModel);
		return new ModelAndView("manager/instrumentApplyCheck");

	}
	
	/**
	 * 管理员对仪器变动申请进行审核操作（三种不同的变动申请已合并）
	 * @param flag 取值如下：<br>
	 * 			   1 - 院系管理员同意调拨申请<br>
	 * 			   2 - 院系管理员拒绝调拨申请<br>
	 * 			   3 - 院系管理员同意维修申请<br>
	 * 			   4 - 院系管理员拒绝维修申请<br>
	 * 			   5 - 院系管理员同意报废申请<br>
	 * 			   6 - 院系管理员拒绝报废申请<br>
	 * 			   7 - 系统管理员同意调拨审核<br>
	 * 			   8 - 系统管理员拒绝调拨审核<br>
	 * 			   9 - 院系管理员确认已维修仪器<br>
	 * 			   10- <br>
	 * 			   11- 系统管理员同意报废审核<br>
	 * 			   12- 系统管理员拒绝报废审核<br>
	 *             13- 院系管理员签收仪器      
	 */
	@RequestMapping(value={"/backend/manager/instApplyHandle.html","/backend/admin/instApplyHandle.html"},
			method=RequestMethod.POST)
	@ResponseBody
	public String instApplyHandle(HttpSession session,@RequestParam(value="id",required=false) String id,
			@RequestParam(value="flag",required=false) String flag,
			@RequestParam(value="reason",required=false) String reason,
			@RequestParam(value="repairmanId",required=false) String repairmanId){
		System.out.println("正在处理仪器变动申请！");
		if(StringUtils.isNullOrEmpty(id) || StringUtils.isNullOrEmpty(flag)){
			return "nodata";
		}else{
			
			try {
				Integer h_id = Integer.valueOf(id);
				Integer h_flag = Integer.valueOf(flag);
				User user = new User();
				
				Affiche affiche = new Affiche();
				affiche.setPublishDate(new Date());
				affiche.setPublisher(this.getCurrentUser().getUserName());
				
				switch(h_flag){
					case 1:case 2:
						InstAssign instAssign = instrumentService.getInstAssignById(h_id);
						user.setId(instAssign.getCreatedBy());
						user = userService.getUserById(user);
						instAssign.setAssignManagerId(this.getCurrentUser().getId());
						instAssign.setAssignManager(this.getCurrentUser().getUserName());
						instAssign.setManagerTel(this.getCurrentUser().getPhone());
						instAssign.setCheckDate(new Date());
												
						instrumentService.cl_instApplyHandle(instAssign,null,null,user,h_flag,affiche);
						break;
					case 3:case 4:
						InstRepair instRepair = new InstRepair();
						instRepair.setId(h_id);
						instRepair = instrumentService.getInstRepairById(instRepair);
						instRepair.setCheckDate(new Date());
						user.setId(instRepair.getCreatedBy());
						user = userService.getUserById(user);
						
						if(h_flag == 3)
							if(!StringUtils.isNullOrEmpty(repairmanId))
								instRepair.setRepairmanId(Integer.valueOf(repairmanId));
							else return "nodata"; 
						
						instrumentService.cl_instApplyHandle(null, instRepair, null, user, h_flag, affiche);
						break;
					case 5:case 6:
						InstScrap instScrap = new InstScrap();
						instScrap.setId(h_id);
						instScrap = instrumentService.getInstScrapById(instScrap);
						instScrap.setDealer(this.getCurrentUser().getUserName());
						instScrap.setDealerTel(this.getCurrentUser().getPhone());
						instScrap.setDealDate(new Date());
												
						instrumentService.cl_instApplyHandle(null, null, instScrap, null, h_flag, affiche);
						break;
					case 7:case 8:
						InstAssign instAssign1 = instrumentService.getInstAssignById(h_id);
						if(h_flag == 8)
							if(reason == null || reason.equals("")){
								System.out.println("这里的提交的拒绝调拨的理由为空！");
								return "nodata";
							}
							else instAssign1.setReason(reason);
						
						user.setId(instAssign1.getCreatedBy());
						user = userService.getUserById(user);
						instAssign1.setCheckDate(new Date());
												
						instrumentService.cl_instApplyHandle(instAssign1, null, null, user, h_flag, affiche); 
						break;
					case 9:  //院系管理员确认已维修仪器
						InstRepair instRepair1 = new InstRepair();
						instRepair1.setId(h_id);
						instRepair1 = instrumentService.getInstRepairById(instRepair1);
						user.setId(instRepair1.getCreatedBy());
						user = userService.getUserById(user);
						instRepair1.setCheckDate(new Date());
						instrumentService.cl_instApplyHandle(null, instRepair1, null, user, h_flag, affiche);
						break;
					case 10:case 11:
						
						break;
					case 13:
						InstAssign instAssign2 = instrumentService.getInstAssignById(h_id);
						user.setId(instAssign2.getCreatedBy());
						user = userService.getUserById(user);
						instAssign2.setCheckDate(new Date());
						
						instrumentService.cl_instApplyHandle(instAssign2, null, null, user, h_flag, affiche);
						break;
					default: return "failed";
					
				}
				return "success";
				
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
	}
	
	/**
	 * 仪器调拨申请审核页面（分页操作）
	 */
	@RequestMapping(value="/backend/manager/instAssignApplyCheck.html")
	public ModelAndView instAssignApplyCheck(HttpSession session,Model model,
			@RequestParam(value="pageIndex",required=false)String pageIndex){
		InstAssign assign = new InstAssign();
		assign.setDept(this.getCurrentUser().getDept());
		assign.setPartitionNo(1);
		try {
			PageSupport page = new PageSupport();
			page.setTotalCount(instrumentService.getInstAssignCount(assign)); //设置总记录数
			
			if(page.getTotalCount() > 0){
				//设置当前页号
				if(!StringUtils.isNullOrEmpty(pageIndex)){
					page.setCurrentPageNo(Integer.valueOf(pageIndex));
				}else{
					page.setCurrentPageNo(1);
				}
				
				//控制首尾页
				if(page.getCurrentPageNo() <= 0){
					page.setCurrentPageNo(1);
				}
				if(page.getCurrentPageNo() > page.getTotalPageCount()){
					page.setCurrentPageNo(page.getTotalPageCount());
				}
				
				assign.setStartPageNo((page.getCurrentPageNo() - 1)*page.getPageSize());
				assign.setPageSize(page.getPageSize());
				page.setItems(instrumentService.getInstAssignList(assign));
			}
			else {
				page.setItems(null);
			}
			model.addAttribute("page",page);
			model.addAttribute("pageIndex",pageIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("manager/instAssignApplyCheck");
	}
	
	/**
	 * 仪器维修申请审核页面（分页操作）
	 */
	@RequestMapping(value="/backend/manager/instRepairApplyCheck.html")
	public ModelAndView instRepairApplyCheck(HttpSession session,Model model,
			@RequestParam(value="pageIndex",required=false)String pageIndex){
		InstRepair repair = new InstRepair();
		repair.setDept(this.getCurrentUser().getDept());
		try {
			PageSupport page = new PageSupport();
			page.setTotalCount(instrumentService.getInstRepairCount(repair)); //设置总记录数
			
			if(page.getTotalCount() > 0){
				//设置当前页号
				if(!StringUtils.isNullOrEmpty(pageIndex)){
					page.setCurrentPageNo(Integer.valueOf(pageIndex));
				}else{
					page.setCurrentPageNo(1);
				}
				
				//控制首尾页
				if(page.getCurrentPageNo() <= 0){
					page.setCurrentPageNo(1);
				}
				if(page.getCurrentPageNo() > page.getTotalPageCount()){
					page.setCurrentPageNo(page.getTotalPageCount());
				}
				
				repair.setStartPageNo((page.getCurrentPageNo() -1)*page.getPageSize());
				repair.setPageSize(page.getPageSize());
				List<InstRepair> rList = instrumentService.getInstRepairList(repair);
				page.setItems(rList);
				System.out.println("此次获取的数量为："+rList.size());
			}
			else {
				page.setItems(null);
			}
			model.addAttribute("page",page);
			model.addAttribute("pageIndex",pageIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("manager/instRepairApplyCheck");
	}
	
	/**
	 * 仪器报废申请审核页面（分页操作）
	 */
	@RequestMapping(value="/backend/manager/instScrapApplyCheck.html")
	public ModelAndView instScrapApplyCheck(HttpSession session,Model model,
			@RequestParam(value="pageIndex",required=false)String pageIndex){
		InstScrap scrap = new InstScrap();
		scrap.setDept(this.getCurrentUser().getDept());
		try {
			PageSupport page = new PageSupport();
			page.setTotalCount(instrumentService.getInstScrapCount(scrap)); //设置总记录数
			
			if(page.getTotalCount() > 0){
				//设置当前页号
				if(!StringUtils.isNullOrEmpty(pageIndex)){
					page.setCurrentPageNo(Integer.valueOf(pageIndex));
				}else{
					page.setCurrentPageNo(1);
				}
				
				//控制首尾页
				if(page.getCurrentPageNo() <= 0){
					page.setCurrentPageNo(1);
				}
				if(page.getCurrentPageNo() > page.getTotalPageCount()){
					page.setCurrentPageNo(page.getTotalPageCount());
				}
				
				scrap.setStartPageNo((page.getCurrentPageNo() -1)*page.getPageSize());
				scrap.setPageSize(page.getPageSize());
				page.setItems(instrumentService.getInstScrapList(scrap));
			}
			else {
				page.setItems(null);
			}
			model.addAttribute("page",page);
			model.addAttribute("pageIndex",pageIndex);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("manager/instScrapApplyCheck");
	}
	
	/**
	 * 院系管理员查看报修详情
	 */
	@RequestMapping(value="/backend/manager/showRepairDetail.html")
	public ModelAndView showRepairDetail(HttpServletResponse response,HttpSession session,
			Model model,@RequestParam(value="id",required=false)String instId){
		//Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(StringUtils.isNullOrEmpty(instId)){
			response.setStatus(404);
		}else{
			try {
				InstRepair repair = new InstRepair();
				repair.setId(Integer.valueOf(instId));
				model.addAttribute("repairInfo",instrumentService.getInstRepairById(repair));
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(404);
			}
		}
		//model.addAllAttributes(baseModel);
		return new ModelAndView("template");
	}

}
