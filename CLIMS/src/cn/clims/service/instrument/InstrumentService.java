package cn.clims.service.instrument;

import java.util.List;

import cn.clims.pojo.Affiche;
import cn.clims.pojo.InstAssign;
import cn.clims.pojo.InstRepair;
import cn.clims.pojo.InstScrap;
import cn.clims.pojo.InstStock;
import cn.clims.pojo.Instrument;
import cn.clims.pojo.User;

public interface InstrumentService {
	public List<InstStock> getInstrumentList(InstStock instStock)throws Exception;
	public int count(InstStock instStock)throws Exception;
	public int updateInstStock(InstStock instStock)throws Exception;
	public int addInstrument(Instrument instrument)throws Exception;
	public int instrumentNoIsExist(String instrumentNo)throws Exception;
	public InstStock getInstStockByInstId(Integer instrumentId)throws Exception;
	public int instrumentNameIsExist(Instrument instrument)throws Exception;
	public void cl_addInstAssign(InstAssign instAssign, Affiche affiche)throws Exception;
	public List<InstAssign> getInstAssignList(InstAssign assign)throws Exception;
	public InstAssign getInstAssignById(Integer h_id)throws Exception;
	public List<InstRepair> getInstRepairList(InstRepair repair)throws Exception;
	public List<InstScrap> getInstScrapList(InstScrap scrap)throws Exception;
	
	//getInstAssignCount
	public int getInstAssignCount(InstAssign assign)throws Exception;
	//getInstRepairCount
	public int getInstRepairCount(InstRepair repair)throws Exception;
	//getInstScrapCount
	public int getInstScrapCount(InstScrap scrap)throws Exception;
	
	public void cl_instApplyHandle(InstAssign instAssign, InstRepair instRepair, 
			InstScrap instScrap, User user, Integer h_flag, Affiche affiche)throws Exception;
}
