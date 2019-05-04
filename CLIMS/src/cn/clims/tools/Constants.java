package cn.clims.tools;

/**
 * 该类用来定义本系统的一些常量
 * @author DubLBo
 *
 */
public class Constants {
	public final static String SYS_MESSAGE = "message";
	public final static int PAGE_SIZE = 5;
	public final static String FILEUPLOAD_ERROR_1 = " * 设备信息不完整！";
	public final static String FILEUPLOAD_ERROR_2 = " * 上传失败！";
	public final static String FILEUPLOAD_ERROR_3 = " * 上传文件格式不正确！";
	public final static String FILEUPLOAD_ERROR_4 = " * 上传文件过大！";
	
	/**
	instrument_status:
		1 -- 申请调拨
		2 -- 调拨中
		3 -- 已调拨
		4 -- 申请维修 
		5 -- 维修中
		6 -- 未使用：在库存放着且在保修期内
		7 -- 申请报废
		8 -- 已报废 
		9 -- 使用中
		10 -- 拒绝调拨
	 */
	public final static Integer INSTRUMENT_STATUS_1 = 1;
	public final static Integer INSTRUMENT_STATUS_2 = 2;
	public final static Integer INSTRUMENT_STATUS_3 = 3;
	public final static Integer INSTRUMENT_STATUS_4 = 4;
	public final static Integer INSTRUMENT_STATUS_5 = 5;
	public final static Integer INSTRUMENT_STATUS_6 = 6;
	public final static Integer INSTRUMENT_STATUS_7 = 7;
	public final static Integer INSTRUMENT_STATUS_8 = 8;
	public final static Integer INSTRUMENT_STATUS_9 = 9;
	public final static Integer INSTRUMENT_STATUS_10 = 10;
	
	//当前登录用户session key
	public static final String SESSION_USER = "user";
	//当前登录用户的基础业务数据(菜单列表)
	public static final String SESSION_BASE_MODEL = "baseModel";
	
	/**
	 * 公告表公告类型的值
	 * 1 : 申请仪器调拨
	 * 2 : 已审核仪器调拨
	 * 3 : 已调拨仪器
	 * 4 : 未调拨仪器
	 * 5 : 拒绝仪器调拨
	 */
	public final static Integer AFFICHE_TYPE_1 = 1;
	public final static Integer AFFICHE_TYPE_2 = 2;
	public final static Integer AFFICHE_TYPE_3 = 3;
	public final static Integer AFFICHE_TYPE_4 = 4;
	public final static Integer AFFICHE_TYPE_5 = 5;
}
