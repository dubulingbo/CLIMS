package cn.clims.service.instrument;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.clims.dao.affiche.AfficheMapper;
import cn.clims.dao.dept.DeptMapper;
import cn.clims.dao.instrument.InstrumentMapper;
import cn.clims.pojo.Affiche;
import cn.clims.pojo.InstAssign;
import cn.clims.pojo.InstRepair;
import cn.clims.pojo.InstScrap;
import cn.clims.pojo.InstStock;
import cn.clims.pojo.Instrument;
import cn.clims.pojo.User;
import cn.clims.service.mail.MailService;
import cn.clims.tools.Constants;
import cn.clims.tools.InstrumentNoGenerator;


@Service("instrumentService")
public class InstrumentServiceImpl implements InstrumentService {
	
	@Resource
	private InstrumentMapper instrumentMapper;
	
	@Resource 
	private MailService mailService;
	
	@Resource
	private AfficheMapper afficheMapper;
	
	@Resource
	private DeptMapper deptMapper;

	@Override
	public List<InstStock> getInstStockList(InstStock instStock) throws Exception {
		return instrumentMapper.getInstStockList(instStock);
	}

	@Override
	public int getInstStockCount(InstStock instStock) throws Exception {
		return instrumentMapper.getInstStockCount(instStock);
	}

	@Override
	public int updateInstStock(InstStock instStock) throws Exception {
		return instrumentMapper.updateInstStock(instStock);
	}

	@Override
	public void cl_addInstStock(InstStock instStock) throws Exception {
		/**
		 * 1.添加仪器基本信息表
		 * 2.添加库存信息表
		 */
		instrumentMapper.addInstrument(instStock);
		instrumentMapper.addInstStock(instStock);
		
	}

	@Override
	public int instrumentNoIsExist(String instrumentNo) throws Exception {
		return instrumentMapper.instrumentNoIsExist(instrumentNo);
	}

	@Override
	public InstStock getInstStockByInstId(Integer instrumentId) throws Exception {
		return instrumentMapper.getInstStockByInstId(instrumentId);
	}

	@Override
	public int instrumentIsExist(Instrument instrument) throws Exception {
		return instrumentMapper.instrumentIsExist(instrument);
	}

	@Override
	public void cl_addInstAssign(InstAssign instAssign,Affiche affiche)throws Exception {
		//1.添加到系统公告
		//2.同时需要锁定库存表的里的相应申请的仪器数量 
		//3.添加调拨数据表
		InstStock inst = instrumentMapper.getInstStockByInstId(instAssign.getInstrumentId());
		if(inst.getStockNumber()-instAssign.getAssignNumber() >= 0){
			inst.setStockNumber(inst.getStockNumber()-instAssign.getAssignNumber());
			afficheMapper.addAffiche(affiche);
			instrumentMapper.updateInstStock(inst);
			instrumentMapper.addInstAssign(instAssign);
		}else{
			throw new Exception("要调拨的数量大于库存数量！");
		}
	}

	@Override
	public List<InstAssign> getInstAssignList(InstAssign assign) throws Exception {
		return instrumentMapper.getInstAssignList(assign);
	}
	
	@Override
	public List<InstRepair> getInstRepairList(InstRepair repair) throws Exception {
		return instrumentMapper.getInstRepairList(repair);
	}

	@Override
	public List<InstScrap> getInstScrapList(InstScrap scrap) throws Exception {
		return instrumentMapper.getInstScrapList(scrap);
	}

	@Override
	public InstAssign getInstAssignById(Integer h_id) throws Exception {
		return instrumentMapper.getInstAssignById(h_id);
	}

	@Override
	public int getInstAssignCount(InstAssign assign) throws Exception {
		return instrumentMapper.getInstAssignCount(assign);
	}

	@Override
	public int getInstRepairCount(InstRepair repair) throws Exception {
		return instrumentMapper.getInstRepairCount(repair);
	}

	@Override
	public int getInstScrapCount(InstScrap scrap) throws Exception {
		return instrumentMapper.getInstScrapCount(scrap);
	}
	
	private String getCurrentDate(){
		String cDate = "";
		cDate = new SimpleDateFormat("yyyy-M-d").format(new Date()).toString();
		return cDate;
	}

	@Override
	public void cl_instApplyHandle(InstAssign instAssign, InstRepair instRepair, InstScrap instScrap, User user,
			Integer h_flag,Affiche affiche) throws Exception {
		switch(h_flag){
			case 1:  //院系管理员同意调拨操作
				//添加公告表
				affiche.setCode(Constants.INSTRUMENT_STATUS_2);
				affiche.setTypeId(Constants.AFFICHE_TYPE_1);
				affiche.setTitle("已审核仪器调拨");
				affiche.setContent(instAssign.getAssignManager()+"于"+getCurrentDate()+"已审核 "+user.getUserName()+" 申请的仪器调拨.");
				afficheMapper.addAffiche(affiche);
				//修改仪器调拨表
				instAssign.setStatus(Constants.INSTRUMENT_STATUS_2); //设置为 审核通过 状态
				instAssign.setOpacifyToAdmin(Constants.OPACIFY_TO_ADMIN_2); //设置 对管理员可见  --1:不可见（默认），2:可见
				instrumentMapper.updateInstAssign(instAssign);
				
				
				//向用户发邮件
				mailService.sendMsg(user.getEmail(),
								"你申请的仪器【"+instAssign.getInstrumentName()+
								"】已由院系管理员【"+instAssign.getAssignManager()+
								"】审核通过！请随时关注设备申请状态，谢谢！（本通知由系统自动发出，请勿回复）");
				break;
			case 2:  //院系管理员拒绝调拨操作
				InstStock st = instrumentMapper.getInstStockByInstId(instAssign.getInstrumentId());
				st.setStockNumber(st.getStockNumber()+instAssign.getAssignNumber());
				if(st.getStockNumber() <= 0 || st.getStockNumber() > st.getNumber()){
					throw new Exception("仪器【"+st.getInstrumentName()+"】库存数量有误！");
				}else{
					//修改库存表数量
					instrumentMapper.updateInstStock(st);
					//添加公告表
					affiche.setCode(Constants.INSTRUMENT_STATUS_3);
					affiche.setTypeId(Constants.AFFICHE_TYPE_1);
					affiche.setTitle("仪器调拨审核未通过");
					affiche.setContent(instAssign.getAssignManager()+"于"+getCurrentDate()+"已拒绝 "+user.getUserName()+" 申请的仪器调拨.");
					afficheMapper.addAffiche(affiche);
					//更新仪器调拨表中的状态
					instAssign.setStatus(Constants.INSTRUMENT_STATUS_3);  //设置为 拒绝调拨 状态
					instAssign.setOpacifyToAdmin(Constants.OPACIFY_TO_ADMIN_1); //设置 对管理员可见  --1:不可见（默认），2:可见
					instrumentMapper.updateInstAssign(instAssign);
					//向用户发邮件
					mailService.sendMsg(user.getEmail(),
							"你申请的仪器【"+instAssign.getInstrumentName()+
							"】审核未通过！具体详情请关注本网站最新公告或联系所在院系的仪器管理员，谢谢！（本通知由系统自动发出，请勿回复）");
				}
				break;
			case 3:  //院系管理员同意维修操作
				//1.添加公告
				affiche.setCode(Constants.INSTRUMENT_STATUS_9);
				affiche.setTypeId(Constants.AFFICHE_TYPE_2);
				affiche.setTitle("院系管理员已通过仪器维修");
				affiche.setContent(user.getUserName()+"申请的仪器维修已受理，正在委派维修员前去维修.");
				afficheMapper.addAffiche(affiche);
				//2.更新维修表
				instRepair.setStatus(Constants.INSTRUMENT_STATUS_9);
				instrumentMapper.updateInstRepair(instRepair);
				//3.给用户发邮件
//				mailService.sendMsg(user.getEmail(), "你申请维修的仪器【"+instRepair.getInstrumentName() +
//						"】已成功受理！我们将委派维修员前去修理，请保持手机畅通，方便我们联系您。谢谢！（本通知由系统自动发出，请勿回复）");
				break;
			case 4:  //院系管理员拒绝维修操作
				//1.添加公告
				affiche.setCode(Constants.INSTRUMENT_STATUS_10);
				affiche.setTypeId(Constants.AFFICHE_TYPE_2);
				affiche.setTitle("仪器维修审核未通过");
				affiche.setContent(user.getUserName()+"申请维修仪器【"+instRepair.getInstrumentName()+"】未通过审核.");
				afficheMapper.addAffiche(affiche);

				//2.更新维修表
				instRepair.setStatus(Constants.INSTRUMENT_STATUS_10);
				instrumentMapper.updateInstRepair(instRepair);
				//3.给用户发邮件
//				mailService.sendMsg(user.getEmail(), "你申请维修的仪器【"+instRepair.getInstrumentName() +
//						"】审核未通过！请仔细检查申请资料的完整性，谢谢！（本通知由系统自动发出，请勿回复）");
				break;
			case 5:  //院系管理员同意报废操作
				InstAssign assign = instrumentMapper.getInstAssignByAssId(instScrap.getAssignId());
				int assignN = assign.getAssignNumber() - instScrap.getScrapNumber();
				if(assignN >= 0){
					throw new Exception("要报废的数量大于调拨库存的数量！");
				}else{
					//1.添加公告
					affiche.setCode(Constants.INSTRUMENT_STATUS_13);
					affiche.setTypeId(Constants.AFFICHE_TYPE_3);
					affiche.setTitle("仪器报废成功");
					affiche.setContent("仪器【"+instScrap.getInstrumentName()+"】已于"+this.getCurrentDate()+"报废.");
					afficheMapper.addAffiche(affiche);
					
					//2.跟新调拨库存数量
					assign.setAssignNumber(assignN);
					instrumentMapper.updateInstAssign(assign);
					
					//3.更新维修表
					instScrap.setStatus(Constants.INSTRUMENT_STATUS_13);
					instrumentMapper.updateInstScrap(instScrap);
				}
				
				break;
			case 6:  //院系管理员拒绝报废操作
				//1.添加公告
				affiche.setCode(Constants.INSTRUMENT_STATUS_14);
				affiche.setTypeId(Constants.AFFICHE_TYPE_3);
				affiche.setTitle("仪器报废未成功");
				affiche.setContent(instScrap.getCreatedByName()+"申请的仪器【"+instScrap.getInstrumentName()+"】未达报废标准，申请被拒绝.");
				afficheMapper.addAffiche(affiche);
				
				//2.更新维修表
				instScrap.setStatus(Constants.INSTRUMENT_STATUS_14);
				instrumentMapper.updateInstScrap(instScrap);
				break;
			case 7:  //系统管理员允许仪器调拨申请
				//1.添加公告
				affiche.setCode(Constants.INSTRUMENT_STATUS_4);
				affiche.setTypeId(Constants.AFFICHE_TYPE_1);
				affiche.setTitle("派遣仪器");
				affiche.setContent(user.getUserName()+"申请的仪器<a href='#' class='viewInstrument' id='"
						+instAssign.getInstrumentId()+"'>【"+instAssign.getInstrumentName()+"】</a>已于"
						+getCurrentDate()+"开始派遣.");
				afficheMapper.addAffiche(affiche);
				
				//2.修改仪器调拨表
				instAssign.setStatus(Constants.INSTRUMENT_STATUS_4); //设置为 派遣中 状态
				instrumentMapper.updateInstAssign(instAssign);
				
				
				//3.向用户发邮件
				mailService.sendMsg(user.getEmail(),
						"你申请的仪器【"+instAssign.getInstrumentName()+
						"】正在派遣！请及时关注仪器申请状态，联系院系管理员（<b>"+instAssign.getAssignManager()+
						" "+instAssign.getManagerTel()+"</b>）获取。谢谢！（本通知由系统自动发出，请勿回复）");
				break;
			case 8:  //系统管理员不允许仪器调拨申请
				InstStock st1 = instrumentMapper.getInstStockByInstId(instAssign.getInstrumentId());
				st1.setStockNumber(st1.getStockNumber()+instAssign.getAssignNumber());
				if(st1.getStockNumber() <= 0 || st1.getStockNumber() > st1.getNumber()){  //不能大于仪器总数，且必须大于零
					throw new Exception("仪器【"+st1.getInstrumentName()+"】库存数量有误！");
				}else{
					//1.修改库存表
					instrumentMapper.updateInstStock(st1);
					//2.添加公告
					affiche.setCode(Constants.INSTRUMENT_STATUS_5);
					affiche.setTypeId(Constants.AFFICHE_TYPE_1);
					affiche.setTitle("仪器调拨失败");
					affiche.setContent(user.getUserName()+"申请的仪器【"+instAssign.getInstrumentName()+"】调拨失败 !");
					afficheMapper.addAffiche(affiche);
					
					//3.给用户和审核员留言（暂时不做）
					
					
					//4.修改仪器调拨表
					instAssign.setStatus(Constants.INSTRUMENT_STATUS_5); //设置为 调拨失败 状态
					instrumentMapper.updateInstAssign(instAssign);
					
					
					//5.向用户发邮件
					mailService.sendMsg(user.getEmail(),
									"你申请的仪器【"+instAssign.getInstrumentName()+
									"】调拨失败！有关原因请登录网站查看我的留言。给您带来不便，尽情谅解！（本通知由系统自动发出，请勿回复）");
				}
				break;
			case 9:  //院系管理员确认已维修仪器
				//1.添加公告
				affiche.setCode(Constants.INSTRUMENT_STATUS_11);
				affiche.setTypeId(Constants.AFFICHE_TYPE_2);
				affiche.setTitle("仪器已维修");
				affiche.setContent(user.getUserName()+"申请的仪器【"+instRepair.getInstrumentName()+"】于 "+getCurrentDate()+" 已维修完毕 .");
				afficheMapper.addAffiche(affiche);
				//2.更新维修表
				instRepair.setStatus(Constants.INSTRUMENT_STATUS_11);
				instrumentMapper.updateInstRepair(instRepair);
				break;
			case 13:  //院系管理员签收仪器
				//1.添加公告表
				affiche.setCode(Constants.INSTRUMENT_STATUS_6);
				affiche.setTypeId(Constants.AFFICHE_TYPE_1);
				affiche.setTitle("仪器已签收");
				affiche.setContent(user.getUserName()+"申请的仪器【"+instAssign.getInstrumentName()+
								"】已由院系管理员【"+instAssign.getAssignManager()+"】签收.");
				afficheMapper.addAffiche(affiche);
				//2.修改仪器调拨表   （修改仪器调拨状态、添加仪器调拨表库存记录（p2区））
				instAssign.setStatus(Constants.INSTRUMENT_STATUS_6); //设置为 已调拨 状态
				instrumentMapper.updateInstAssign(instAssign);
				
				instAssign.setStatus(Constants.INSTRUMENT_STATUS_7);  //调整  仪器调拨库存区的仪器状态 为   使用中
				if(instrumentMapper.assignNoIsExist(instAssign) == 0){ //不存在，插入仪器调拨库存记录
					instAssign.setAssignNo(InstrumentNoGenerator.generateAssignNo(deptMapper.
							getDnoByDname(instAssign.getDept()), instAssign.getInstrumentId()));  //生成仪器调拨库存编号
					instrumentMapper.addInstAssign_all(instAssign);
				}else{  //存在，增加 assignNumber 的数量
					Integer num = instAssign.getAssignNumber();
					if(num > 0){
						instAssign.setPartitionNo(2);
						instAssign = instrumentMapper.getInstAssignByInstId(instAssign);
						instAssign.setAssignNumber(instAssign.getAssignNumber() + num);
						instrumentMapper.addInstAssign_all(instAssign);
					}else{
						throw new Exception("仪器调拨表里的调拨数量有无 instrumentId = "+instAssign.getId());
					}
				}
				
				
				
				
				
				//3.向用户发邮件
				mailService.sendMsg(user.getEmail(),
								"你申请的仪器【"+instAssign.getInstrumentName()+
								"】已由院系管理员【"+instAssign.getAssignManager()+" : "+instAssign.getManagerTel()+
								"】签收。请尽联系管理员快取件，谢谢！（本通知由系统自动发出，请勿回复）");
				break;
		}
		
	}

	@Override
	public List<InstRepair> cl_addInstRepair(InstRepair instRepair, Affiche affiche) throws Exception {
		//1.添加到系统公告
		afficheMapper.addAffiche(affiche);
		//2.添加仪器维修表
		instrumentMapper.addInstRepair(instRepair);
		//获取当前用户全部的维修记录
		InstRepair repair = new InstRepair();
		repair.setCreatedBy(instRepair.getCreatedBy());
		return instrumentMapper.getInstRepairList(repair);
	}



	@Override
	public InstRepair getInstRepairById(InstRepair instRepair) throws Exception {
		return instrumentMapper.getInstRepairById(instRepair);
	}

	

	@Override
	public void cl_addInstScrap(InstScrap instScrap, Affiche affiche) throws Exception {
		InstAssign assign = instrumentMapper.getInstAssignByAssId(instScrap.getAssignId());
		instScrap.setInstrumentId(assign.getInstrumentId());
		instScrap.setDept(assign.getDept());
		instScrap.setCreatedBy(assign.getCreatedBy());
		instScrap.setCreationDate(new Date());
		instScrap.setAddress(assign.getLocName());
		instScrap.setStatus(Constants.INSTRUMENT_STATUS_12);
		afficheMapper.addAffiche(affiche);
		instrumentMapper.addInstScrap(instScrap);
	}

	@Override
	public InstAssign getInstAssignByAssId(String assignId)throws Exception {
		return instrumentMapper.getInstAssignByAssId(assignId);
	}

	@Override
	public InstScrap getInstScrapById(InstScrap instScrap) throws Exception {
		return instrumentMapper.getInstScrapById(instScrap);
	}

	

}
