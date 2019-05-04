package cn.clims.service.instrument;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.clims.dao.affiche.AfficheMapper;
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


@Service("instrumentService")
public class InstrumentServiceImpl implements InstrumentService {
	
	@Resource
	private InstrumentMapper instrumentMapper;
	
	@Resource 
	private MailService mailService;
	
	@Resource
	private AfficheMapper afficheMapper;

	@Override
	public List<InstStock> getInstrumentList(InstStock instStock) throws Exception {
		// TODO Auto-generated method stub
		return instrumentMapper.getInstrumentList(instStock);
	}

	@Override
	public int count(InstStock instStock) throws Exception {
		// TODO Auto-generated method stub
		return instrumentMapper.count(instStock);
	}

	@Override
	public int updateInstStock(InstStock instStock) throws Exception {
		// TODO Auto-generated method stub
		return instrumentMapper.updateInstStock(instStock);
	}

	@Override
	public int addInstrument(Instrument instrument) throws Exception {
		// TODO Auto-generated method stub
		return instrumentMapper.addInstrument(instrument);
		
	}

	@Override
	public int instrumentNoIsExist(String instrumentNo) throws Exception {
		// TODO Auto-generated method stub
		return instrumentMapper.instrumentNoIsExist(instrumentNo);
	}

	@Override
	public InstStock getInstStockByInstId(Integer instrumentId) throws Exception {
		// TODO Auto-generated method stub
		return instrumentMapper.getInstStockByInstId(instrumentId);
	}

	@Override
	public int instrumentNameIsExist(Instrument instrument) throws Exception {
		// TODO Auto-generated method stub
		return instrumentMapper.instrumentNameIsExist(instrument);
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
		// TODO Auto-generated method stub
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
			case 1:  //同意调拨操作
				//添加公告表
				affiche.setCode(Constants.AFFICHE_TYPE_2);
				affiche.setTitle("已审核仪器调拨");
				affiche.setContent(instAssign.getAssignManager()+"于"+getCurrentDate()+"已审核 "+user.getUserName()+" 申请的仪器调拨.");
				afficheMapper.addAffiche(affiche);
				//修改仪器调拨表
				instAssign.setStatus(Constants.INSTRUMENT_STATUS_2); //设置为 审核中 状态
				instrumentMapper.updateInstAssign(instAssign);
				
				
				//向用户发邮件
				mailService.sendMsg(user.getEmail(),
								"你申请的设备【"+instAssign.getInstrumentName()+
								"】已由院系管理员【"+instAssign.getAssignManager()+
								"】审核通过！请随时关注设备申请状态，谢谢！（本通知由系统自动发出，请勿回复）");
				break;
			case 2:  //拒绝调拨操作
				instAssign.setStatus(Constants.INSTRUMENT_STATUS_10);  //设置为 拒绝调拨 状态
				
				InstStock st = instrumentMapper.getInstStockByInstId(instAssign.getInstrumentId());
				st.setStockNumber(st.getStockNumber()+instAssign.getAssignNumber());
				if(st.getStockNumber() <= 0){
					throw new Exception("仪器【"+st.getInstrumentName()+"】库存数量有误！");
				}else{
					//修改库存表数量
					instrumentMapper.updateInstStock(st);
					//添加公告表
					affiche.setCode(Constants.AFFICHE_TYPE_5);
					affiche.setTitle("拒绝仪器调拨");
					affiche.setContent(instAssign.getAssignManager()+"于"+getCurrentDate()+"已拒绝 "+user.getUserName()+" 申请的仪器调拨.");
					afficheMapper.addAffiche(affiche);
					//更新仪器调拨表中的状态
					instrumentMapper.updateInstAssign(instAssign);
					//向用户发邮件
					mailService.sendMsg(user.getEmail(),
							"你申请的设备【"+instAssign.getInstrumentName()+
							"】审核未通过！具体详情请关注本网站最新公告或联系所在院系的仪器管理员，谢谢！（本通知由系统自动发出，请勿回复）");
				}
				break;
			case 3:  //同意维修操作
				
				break;
			case 4:  //拒绝维修操作
				
				break;
			case 5:  //同意报废操作
				
				break;
			case 6:  //拒绝报废操作
				
				break;
		}
		
	}

	

}
