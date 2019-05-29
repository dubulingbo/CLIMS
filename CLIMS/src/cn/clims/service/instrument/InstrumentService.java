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
	public int instrumentNoIsExist(String instrumentNo)throws Exception;
	public int instrumentIsExist(Instrument instrument)throws Exception;
	public List<InstStock> getInstStockList(InstStock instStock)throws Exception;
	public int getInstStockCount(InstStock instStock)throws Exception;
	public InstStock getInstStockByInstId(Integer instrumentId)throws Exception;
	public int updateInstStock(InstStock instStock)throws Exception;
	public void cl_addInstStock(InstStock instrument)throws Exception;
	
	
	
	
	
	
	public List<InstAssign> getInstAssignList_p1(InstAssign assign)throws Exception;
	public List<InstAssign> getInstAssignList_p2(InstAssign assign)throws Exception;
	public int getInstAssignCount_p1(InstAssign assign)throws Exception;
	public int getInstAssignCount_p2(InstAssign assign)throws Exception;
	public InstAssign getInstAssignById(Integer h_id)throws Exception;
	public InstAssign getInstAssignByAssignNo(String assignNo)throws Exception;
	public void cl_addInstAssign(InstAssign instAssign, Affiche affiche)throws Exception;
	
	
	
	
	
	
	
	public List<InstRepair> getInstRepairList(InstRepair repair)throws Exception;
	public int getInstRepairCount(InstRepair repair)throws Exception;
	public List<InstRepair> cl_addInstRepair(InstRepair instRepair, Affiche affiche)throws Exception;
	public InstRepair getInstRepairById(Integer id)throws Exception;
	
	
	
	
	
	
	
	public List<InstScrap> getInstScrapList(InstScrap scrap)throws Exception;
	public int getInstScrapCount(InstScrap scrap)throws Exception;
	public InstScrap getInstScrapById(Integer id)throws Exception;
	public void cl_addInstScrap(InstScrap instscrap, Affiche aff)throws Exception;
	
	
	
	
	
	
	public void cl_instApplyHandle(InstAssign instAssign, InstRepair instRepair, 
			InstScrap instScrap, User user, Integer h_flag, Affiche affiche)throws Exception;

}
