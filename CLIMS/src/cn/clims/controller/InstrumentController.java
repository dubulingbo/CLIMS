package cn.clims.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

import com.mysql.jdbc.StringUtils;

import cn.clims.pojo.Affiche;
import cn.clims.pojo.Country;
import cn.clims.pojo.Dept;
import cn.clims.pojo.InstAssign;
import cn.clims.pojo.InstRepair;
import cn.clims.pojo.InstScrap;
import cn.clims.pojo.InstStock;
import cn.clims.pojo.Instrument;
import cn.clims.pojo.Species;
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
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class InstrumentController extends BaseController{
	private Logger logger = Logger.getLogger(InstrumentController.class);
	
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
	 * 进入仪器管理页面
	 * @param model
	 * @param session
	 * @param s_instrumentName
	 * @param s_instrumentNo
	 * @param s_classNo
	 * @param s_dept
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value="/backend/admin/instrumentManage.html")
	public ModelAndView instrumentManage(Model model,HttpSession session,
			@RequestParam(value="s_instrumentName",required=false) String s_instrumentName,
			@RequestParam(value="s_instrumentNo",required=false) String s_instrumentNo,
			@RequestParam(value="s_classNo",required=false) String s_classNo,
			@RequestParam(value="s_dept",required=false) String s_dept,
			@RequestParam(value="pageIndex",required=false) String pageIndex){
		logger.debug("进入仪器管理页面！");
		@SuppressWarnings("unchecked")
		Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null || this.getCurrentUser() == null){
			return new ModelAndView("redirect:/");
		} else {
			//获取speciesList and deptList and countryList
			List<Species> speciesList = null;
			List<Dept> deptList = null;
			List<Country> countryList = null;
			try {
				speciesList = speciesService.getSpeciesList();
				deptList = deptService.getDeptList();
				countryList = countryService.getCountryList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
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
			
			PageSupport page = this.getPageObject(instStock, pageIndex);
			
			model.addAllAttributes(baseModel);
			model.addAttribute("page", page);
			model.addAttribute("speciesList", speciesList);
			model.addAttribute("deptList", deptList);
			model.addAttribute("countryList", countryList);
			//用于回显
			model.addAttribute("s_instrumentName", s_instrumentName);
			model.addAttribute("s_instrumentNo", s_instrumentNo);
			model.addAttribute("s_classNo", s_classNo);//Integer.valueOf(s_classNo)
			model.addAttribute("s_dept", s_dept);
			
			return new ModelAndView("admin/instrumentManage");
		}
	}
	
	/**
	 * 添加仪器库存信息
	 * @param session
	 * @param addInstrument
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/backend/admin/addInstrument.html",method=RequestMethod.POST)
	public ModelAndView addInstrument(HttpSession session,@ModelAttribute("addInstrument") Instrument addInstrument){
		Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null || this.getCurrentUser() == null){
			return new ModelAndView("redirect:/");
		} else {
			logger.info("正在保存刚刚提交的仪器信息！");
			try{
				addInstrument.setCreationDate(new Date());
				addInstrument.setCreatedBy(this.getCurrentUser().getId());
				String year = new SimpleDateFormat("yyyy").format(addInstrument.getProductionDate());
				logger.info("该仪器的出厂年份为：" + year);
				String instrumentNo = "";
				do{
					instrumentNo = InstrumentNoGenerator.generate(
							year, addInstrument.getCreatedBy(), addInstrument.getClassNo());
				}while(instrumentService.instrumentNoIsExist(instrumentNo) != 0);
				
				addInstrument.setInstrumentNo(instrumentNo);
				
				logger.info("成功添加 "+instrumentService.addInstrument(addInstrument)+" 条仪器信息！");
			}catch(Exception e){
				e.printStackTrace();
				return new ModelAndView("redirect:/");
			}
			
			return new ModelAndView("redirect:/backend/admin/instrumentManage.html");
		}		
	}
	
	@RequestMapping(value="/backend/admin/instrumentIsExist.html",
			produces={"text/html;charset=UTF-8"},method=RequestMethod.POST)
	@ResponseBody
	public Object instrumentNameIsExist(@RequestParam(value="instrumentName",required=false) String instrumentName,
			@RequestParam(value="instrumentType",required=false) String instrumentType){
		if(StringUtils.isNullOrEmpty(instrumentName) || 
				StringUtils.isNullOrEmpty(instrumentType)){
			return "empty";
		}else{
			try {
				Instrument instrument = new Instrument();
				instrument.setInstrumentName(instrumentName);
				instrument.setInstrumentType(instrumentType);
				if(instrumentService.instrumentNameIsExist(instrument) == 0)
					return "noexist";
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
			
		}
	
		return "exist";
	}
	
	@RequestMapping(value="/backend/admin/viewInstrument.html",produces={"text/html;charset=UTF-8"},method=RequestMethod.POST)
	@ResponseBody
	public Object viewInstrument(@RequestParam(value="id",required=false) String id){
		logger.info("viewInstrument=========== id : " + id);
		String cjson = "";
		if(StringUtils.isNullOrEmpty(id)){
			cjson = "nodata";
		}else{
			try {
				Instrument instrument = instrumentService.getInstStockByInstId(Integer.valueOf(id));
				
				//所有有日期的属性，都要按照此日期格式进行json转换（对象转json）
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd"));
				JSONObject jo = JSONObject.fromObject(instrument,jsonConfig);
				cjson = jo.toString();
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return "failed";
			}
		}
		return cjson;
	}
	
	/**
	 * 为分页显示信息列表封装的函数  --获取分页对象
	 * @param model
	 * @param strings
	 */
	@SuppressWarnings("unused")
	private PageSupport getPageObject(InstStock instStock,String pageIndex){
		//page分页列表
		PageSupport page = new PageSupport();
		try {
			page.setTotalCount(instrumentService.count(instStock));
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
			List<InstStock> instStockList = null;
			try {
				instStockList = instrumentService.getInstrumentList(instStock);
				
			} catch (Exception e) {
				e.printStackTrace();
				instStockList = null;
				if(page == null){
					page = new PageSupport();
					page.setItems(null);
				}
			}
			page.setItems(instStockList);
		}else{
			page.setItems(null);
		}
		return page;
	}
	
	/**
	 * 进入仪器设备分类统计页面
	 * @param session
	 * @param addInstrument
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/backend/admin/instrumentClassify.html")
	public ModelAndView instrumentClassify(Model model,HttpSession session){
		Map<String,Object> baselModel = (Map<String, Object>) session.getAttribute(Constants.SESSION_BASE_MODEL);
		
			
			
		model.addAllAttributes(baselModel);
		return new ModelAndView("admin/instrumentClassify");	
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/backend/admin/instAssignManage.html")
	public ModelAndView instAssignManage(Model model,HttpSession session,
			@RequestParam(value="pageIndex",required=false) String pageIndex){
		Map<String,Object> baselModel = (Map<String, Object>) session.getAttribute(Constants.SESSION_BASE_MODEL);
		//系统管理员只能查看院系管理员已审核的仪器调拨申请
		InstAssign assign = new InstAssign();
		assign.setStatus(Constants.INSTRUMENT_STATUS_2);
		
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
	 * @param model
	 * @param session
	 * @param s_instrumentName
	 * @param s_instrumentNo
	 * @param s_classNo
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value="/backend/normaluser/instrumentAssignApply.html")
	public ModelAndView instrumentAssignApply(Model model,HttpSession session,
			@RequestParam(value="s_instrumentName",required=false) String s_instrumentName,
			@RequestParam(value="s_instrumentNo",required=false) String s_instrumentNo,
			@RequestParam(value="s_classNo",required=false) String s_classNo,
			@RequestParam(value="pageIndex",required=false) String pageIndex){
		logger.debug("进入仪器调拨申请页面！");
		logger.info("instrumentAssignApply=======s_instrumentName : "+s_instrumentName+
					"\ts_classNo : "+s_classNo + "\tpageIndex : "+pageIndex);
		@SuppressWarnings("unchecked")
		Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null || this.getCurrentUser() == null){
			return new ModelAndView("redirect:/");
		} else {
			//获取speciesList
			List<Species> speciesList = null;

			try {
				speciesList = speciesService.getSpeciesList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
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
			
			PageSupport page = this.getPageObject(instStock, pageIndex);
			
			model.addAllAttributes(baseModel);
			model.addAttribute("page", page);
			model.addAttribute("speciesList", speciesList);

			//用于回显
			model.addAttribute("s_instrumentName", s_instrumentName);
			model.addAttribute("s_instrumentNo", s_instrumentNo);
			model.addAttribute("s_classNo", s_classNo);
			
			return new ModelAndView("normalUser/instrumentAssignApply");
		}
	}
	
	@RequestMapping(value="backend/normaluser/instrumentAssignSave.html",method=RequestMethod.POST)
	@ResponseBody
	public Object instrumentAssignSave(@RequestParam String instAssign){
		logger.info("正在保存本次调拨申请！");
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
				Affiche aff = new Affiche();
				aff.setCode(Constants.AFFICHE_TYPE_1);
				aff.setTitle("申请仪器调拨");
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
	 * 进入仪器变动申请审核页面
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/backend/manager/instrumentApplyCheck.html")
	public ModelAndView instrumentApplyCheck(Model model,HttpSession session){
		logger.debug("进入仪器变动审核页面！");
		@SuppressWarnings("unchecked")
		Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null || this.getCurrentUser() == null){
			return new ModelAndView("redirect:/");
		} else {
			model.addAllAttributes(baseModel);
			return new ModelAndView("manager/instrumentApplyCheck");
		}
	}
	
	/**
	 * 院系管理员对仪器变动申请进行审核操作（三种不同的变动申请已合并）
	 * @param session
	 * @param id
	 * @param flag
	 * @return
	 */
	@RequestMapping(value="/backend/manager/instApplyHandle.html",
			method=RequestMethod.POST)
	@ResponseBody
	public String instApplyHandle(HttpSession session,
			@RequestParam(value="id",required=false) String id,
			@RequestParam(value="flag",required=false) String flag){
		logger.info("正在处理仪器变动申请！");
		if(StringUtils.isNullOrEmpty(id) || StringUtils.isNullOrEmpty(flag)){
			return "nodata";
		}else{
			try {
				Integer h_id = Integer.valueOf(id);
				Integer h_flag = Integer.valueOf(flag);
				User user = new User();
				Affiche aff = new Affiche();
				
				switch(h_flag){
					case 1:case 2:
						InstAssign instAssign = instrumentService.getInstAssignById(h_id);
						user.setId(instAssign.getCreatedBy());
						user = userService.getUserById(user);
						instAssign.setAssignManager(this.getCurrentUser().getUserName());
						instAssign.setManagerTel(this.getCurrentUser().getPhone());
						
						aff.setPublishDate(new Date());
						aff.setPublisher(this.getCurrentUser().getUserName());
						
						instrumentService.cl_instApplyHandle(instAssign,null,null,user,h_flag,aff);
						break;
					case 3:case 4:
						
						break;
					case 5:case 6:
						
						break;
					default: return "failed";
					
				}
				
				
				return "success";
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return "failed";
			}
		}
	}
	
	/**
	 * 仪器调拨申请审核页面（分页操作）
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value="/backend/manager/instAssignApplyCheck.html")
	public ModelAndView instAssignApplyCheck(HttpSession session,Model model,
			@RequestParam(value="pageIndex",required=false)String pageIndex){
		Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null || this.getCurrentUser() == null){
			return new ModelAndView("redirect:/");
		} else {
			if(this.getCurrentUser().getDept().equals("")){
				return new ModelAndView("redirect:/modifyInfo.html");
			}else{
				InstAssign assign = new InstAssign();
				assign.setDept(this.getCurrentUser().getDept());
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
						
						assign.setStartPageNo((page.getCurrentPageNo() -1)*page.getPageSize());
						assign.setPageSize(page.getPageSize());
						page.setItems(instrumentService.getInstAssignList(assign));
					}
					else {
						page.setItems(null);
					}
					model.addAttribute("page",page);
					model.addAttribute("pageIndex",pageIndex);
					return new ModelAndView("manager/instAssignApplyCheck");
				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/");
				}
			}
		}
	}
	
	/**
	 * 仪器维修申请审核页面（分页操作）
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value="/backend/manager/instRepairApplyCheck.html")
	public ModelAndView instRepairApplyCheck(HttpSession session,Model model,
			@RequestParam(value="pageIndex",required=false)String pageIndex){
		Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null || this.getCurrentUser() == null){
			return new ModelAndView("redirect:/");
		} else {
			if(this.getCurrentUser().getDept().equals("")){
				return new ModelAndView("redirect:/modifyInfo.html");
			}else{
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
						page.setItems(instrumentService.getInstRepairList(repair));
					}
					else {
						page.setItems(null);
					}
					model.addAttribute("page",page);
					model.addAttribute("pageIndex",pageIndex);
					return new ModelAndView("manager/instRepairApplyCheck");
				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/");
				}
			}
		}
	}
	
	/**
	 * 仪器报废申请审核页面（分页操作）
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value="/backend/manager/instScrapApplyCheck.html")
	public ModelAndView instScrapApplyCheck(HttpSession session,Model model,
			@RequestParam(value="pageIndex",required=false)String pageIndex){
		Map<String,Object> baseModel = (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(baseModel == null || this.getCurrentUser() == null){
			return new ModelAndView("redirect:/");
		} else {
			if(this.getCurrentUser().getDept().equals("")){
				return new ModelAndView("redirect:/modifyInfo.html");
			}else{
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
					return new ModelAndView("manager/instScrapApplyCheck");
				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/");
				}
			}
		}
	}
	
	
}
