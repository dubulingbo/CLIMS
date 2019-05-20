package cn.clims.dao.instrument;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.clims.pojo.InstAssign;
import cn.clims.pojo.InstRepair;
import cn.clims.pojo.InstScrap;
import cn.clims.pojo.InstStock;
import cn.clims.pojo.Instrument;

public interface InstrumentMapper {
	/**
	 * 根据条件获取库存列表（可用于分页）
	 * @param instStock
	 * @return
	 * @throws Exception
	 */
	public List<InstStock> getInstStockList(InstStock instStock)throws Exception;
	
	/**
	 * 根据条件获取库存列表总数量
	 * @return int
	 * @throws Exception
	 */
	public int getInstStockCount(InstStock instStock)throws Exception;
	
	/**
	 * 更新库存表
	 * @param instStock
	 * @return int
	 * @throws Exception
	 */
	public int updateInstStock(InstStock instStock)throws Exception;
	
	/**
	 * 添加库存表 （添加仪器表clims_instrument && 添加库存表clims_instStock）
	 * @param instStock
	 * @return int
	 * @throws Exception
	 */
	public int addInstrument(Instrument instrument)throws Exception;
	public int addInstStock(InstStock instStock)throws Exception;

	
	public int instrumentNoIsExist(@Param("instrumentNo") String instrumentNo)throws Exception;

	public InstStock getInstStockByInstId(@Param("instrumentId") Integer instrumentId)throws Exception;

	public int instrumentIsExist(Instrument instrument)throws Exception;
	
	/**
	 * 往调拨数据表中添加一条数据
	 * @param instAssign
	 * @return
	 * @throws Exception
	 */
	public int addInstAssign(InstAssign instAssign)throws Exception;

	public List<InstAssign> getInstAssignList(InstAssign assign)throws Exception;
	
	//getInstAssignCount
	public int getInstAssignCount(InstAssign assign)throws Exception;
	//getInstRepairCount
	public int getInstRepairCount(InstRepair repair)throws Exception;
	//getInstScrapCount
	public int getInstScrapCount(InstScrap scrap)throws Exception;
	
	public List<InstRepair> getInstRepairList(InstRepair repair)throws Exception;

	public List<InstScrap> getInstScrapList(InstScrap scrap)throws Exception;
	
	public int updateInstAssign(InstAssign instAssign)throws Exception;
//	public int updateInstRepair(InstRepair instRepair)throws Exception;
//	public int updateInstScrap(InstScrap instScrap)throws Exception;
//	public int instRepairApplyRefuse(@Param("id") Integer h_id)throws Exception;
//	public int instScrapApplyAgree(@Param("id") Integer h_id)throws Exception;
//	public int instScrapApplyRefuse(@Param("id") Integer h_id)throws Exception;

	public InstAssign getInstAssignById(@Param("id") Integer h_id)throws Exception;

	public int addInstRepair(InstRepair instRepair)throws Exception;

	public InstRepair getInstRepairById(InstRepair instRepair)throws Exception;

	public int updateInstRepair(InstRepair instRepair)throws Exception;

	/**
	 * 调拨编号是否存在（根据仪器存储编号instrumentId）
	 * @param instAssign
	 * @return
	 * @throws Exception
	 */
	public int assignNoIsExist(InstAssign instAssign)throws Exception;

	public InstAssign getInstAssignByInstId(InstAssign instAssign)throws Exception;

	public int addInstAssign_all(InstAssign instAssign)throws Exception;
	
	public InstAssign assignIdIsExist(@Param(value = "assignId") String assignId)throws Exception;

	public InstAssign getInstAssignByAssId(String assignId)throws Exception;

	public int addInstScrap(InstScrap instScrap)throws Exception;

	public InstScrap getInstScrapById(InstScrap instScrap)throws Exception;

	public int updateInstScrap(InstScrap instScrap)throws Exception;
}
