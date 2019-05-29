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
	 */
	public List<InstStock> getInstStockList(InstStock instStock)throws Exception;
	
	/**
	 * 根据条件获取库存列表总数量
	 */
	public int getInstStockCount(InstStock instStock)throws Exception;
	
	/**
	 * 更新库存表
	 */
	public int updateInstStock(InstStock instStock)throws Exception;
	
	/**
	 * 添加库存表 （添加仪器表clims_instrument && 添加库存表clims_instStock）
	 */
	public int addInstrument(Instrument instrument)throws Exception;
	public int addInstStock(InstStock instStock)throws Exception;

	/**
	 * 判断仪器库存编号是否存在（库存表）
	 */
	public int instrumentNoIsExist(@Param("instrumentNo") String instrumentNo)throws Exception;
	
	/**
	 * 通过仪器库存编号获取库存记录（instrumentId）
	 */
	public InstStock getInstStockByInstId(@Param("instrumentId") Integer instrumentId)throws Exception;

	/**
	 * 判断仪器是否存在  （instrumentName && instrumentType)
	 */
	public int instrumentIsExist(Instrument instrument)throws Exception;
	
	/**
	 * 添加调拨表信息
	 */
	public int addInstAssign(InstAssign instAssign)throws Exception;

	/**
	 * 按条件按查找调拨表的p1分区里的数据列表（可用于分页）
	 */
	public List<InstAssign> getInstAssignList_p1(InstAssign assign)throws Exception;
	
	/**
	 * 按条件按查找调拨表的p2分区里的数据列表（可用于分页）
	 */
	public List<InstAssign> getInstAssignList_p2(InstAssign assign)throws Exception;
	
	/**
	 * 按条件按查找调拨表的p1分区里的数据列表的总数量
	 */
	public int getInstAssignCount_p1(InstAssign assign)throws Exception;
	
	/**
	 * 按条件按查找调拨表的p2分区里的数据列表的总数量
	 */
	public int getInstAssignCount_p2(InstAssign assign)throws Exception;
	
	/**
	 * 按条件查找维修表中的数据列表的总数量
	 */
	public int getInstRepairCount(InstRepair repair)throws Exception;
	
	/**
	 * 按条件查找报废表中的数据列表的总数量
	 */
	public int getInstScrapCount(InstScrap scrap)throws Exception;
	
	/**
	 * 按条件查找维修表中的数据列表（可用于分页）
	 */
	public List<InstRepair> getInstRepairList(InstRepair repair)throws Exception;

	/**
	 * 按条件查找报废表中的数据列表（可用于分页）
	 */
	public List<InstScrap> getInstScrapList(InstScrap scrap)throws Exception;
	
	/**
	 * 更新调拨表
	 */
	public int updateInstAssign(InstAssign instAssign)throws Exception;

	/**
	 * 通过主键获取调拨表的记录
	 */
	public InstAssign getInstAssignById(@Param("id") Integer h_id)throws Exception;
	
	/**
	 * 添加维修表记录
	 */
	public int addInstRepair(InstRepair instRepair)throws Exception;
	
	/**
	 * 通过主键获取维修表的记录
	 */
	public InstRepair getInstRepairById(InstRepair instRepair)throws Exception;

	/**
	 * 更新维修表
	 */
	public int updateInstRepair(InstRepair instRepair)throws Exception;

	/**
	 * 调拨编号是否存在（根据仪器存储编号instrumentId---p2）
	 */
	public int assignIdIsExist(Integer instrumentId)throws Exception;
	
	/**
	 * 通过仪器库存id（instrumentId）获取调拨表中p1分区的记录
	 */
	public InstAssign getInstAssignByInstId_p1(Integer instrumentId)throws Exception;
	
	/**
	 * 通过仪器库存id（instrumentId）获取调拨表中p2分区的记录
	 */
	public InstAssign getInstAssignByInstId_p2(Integer instrumentId)throws Exception;
	
	/**
	 * 根据调拨库存编号查找调拨表中的数据
	 */
	public InstAssign getInstAssignByAssignNo(String assignNo)throws Exception;

	/**
	 * 添加报废表数据
	 */
	public int addInstScrap(InstScrap instScrap)throws Exception;

	/**
	 * 通过主键查找报废表中的记录
	 */
	public InstScrap getInstScrapById(InstScrap instScrap)throws Exception;

	/**
	 * 更新报废表
	 */
	public int updateInstScrap(InstScrap instScrap)throws Exception;
}
