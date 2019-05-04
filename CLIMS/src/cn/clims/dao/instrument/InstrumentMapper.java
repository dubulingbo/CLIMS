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
	 * 获取仪器列表（可用于分页）
	 * @param instrument
	 * @return
	 * @throws Exception
	 */
	public List<InstStock> getInstrumentList(InstStock instStock)throws Exception;
	
	/**
	 * 获取仪器的总数量
	 * @return
	 * @throws Exception
	 */
	public int count(InstStock instStock)throws Exception;
	
	/**
	 * 更新仪器表中的字段
	 * @param instrument
	 * @return
	 * @throws Exception
	 */
	public int updateInstStock(InstStock instStock)throws Exception;
	
	/**
	 * 添加仪器信息
	 * @param instrument
	 * @return
	 * @throws Exception
	 */
	public int addInstrument(Instrument instrument)throws Exception;

	public int instrumentNoIsExist(@Param("instrumentNo") String instrumentNo)throws Exception;

	public InstStock getInstStockByInstId(@Param("instrumentId") Integer instrumentId)throws Exception;

	public int instrumentNameIsExist(Instrument instrument)throws Exception;
	
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

	
}
