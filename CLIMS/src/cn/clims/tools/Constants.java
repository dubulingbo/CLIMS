package cn.clims.tools;

/**
 * 该类用来定义本系统的一些常量
 * @author DubLBo
 *
 */
public class Constants {
	public final static String SYS_MESSAGE = "message";
	public final static int PAGE_SIZE = 5;
	public final static Integer MAXSIZE_REPAIR_IMG = 5000000;  //维修图片单张最大5M
	public final static Integer MAXNUMBER_REPAIR_IMG = 2;  //维修图片最多能上传 2 张
	/**
	 * 1 - 上传失败
	 */
	public final static String FILEUPLOAD_ERROR_1 = "1";
	/**
	 * 2 - 上传文件格式不正确
	 */
	public final static String FILEUPLOAD_ERROR_2 = "2";
	/**
	 * 3 - 上传文件过大
	 */
	public final static String FILEUPLOAD_ERROR_3 = "3";
	/**
	 * 4 - 上传数量过多
	 */
	public final static String FILEUPLOAD_ERROR_4 = "4";
	/**
	 * 5 - 没有文件上传
	 */
	public final static String FILEUPLOAD_ERROR_5 = "5";
	//当前登录用户session key
	public static final String CURRENT_USER = "currentUser";
	//当前登录用户的基础业务数据(菜单列表，公告列表，资讯列表，即用户主页的数据)
	public static final String SESSION_BASE_MODEL = "baseModel";
	
	/**
	instrument_status:
		1 -- 申请调拨
		2 -- 审核通过
		3 -- 审核未通过
		4 -- 调拨中
		5 -- 调拨失败
		6 -- 已调拨
		7 -- 使用中
		8 -- 申请维修
		9 -- 维修中 
		10 -- 无需维修
		11 -- 已维修
		12 -- 申请报废
		13 -- 已报废
		14 -- 未报废
	 */
	/**
	 * 1 -- 申请调拨
	 */
	public final static Integer INSTRUMENT_STATUS_1 = 1;
	/**
	 * 2 -- 审核通过
	 */
	public final static Integer INSTRUMENT_STATUS_2 = 2;
	/**
	 * 3 -- 审核未通过
	 */
	public final static Integer INSTRUMENT_STATUS_3 = 3;
	/**
	 * 4 -- 调拨中
	 */
	public final static Integer INSTRUMENT_STATUS_4 = 4;
	/**
	 * 5 -- 调拨失败
	 */
	public final static Integer INSTRUMENT_STATUS_5 = 5;
	/**
	 * 6 -- 已调拨
	 */
	public final static Integer INSTRUMENT_STATUS_6 = 6;
	/**
	 * 7 -- 使用中
	 */
	public final static Integer INSTRUMENT_STATUS_7 = 7;
	/**
	 * 8 -- 申请维修
	 */
	public final static Integer INSTRUMENT_STATUS_8 = 8;
	/**
	 * 9 -- 维修中
	 */
	public final static Integer INSTRUMENT_STATUS_9 = 9;
	/**
	 * 10 -- 无需维修
	 */
	public final static Integer INSTRUMENT_STATUS_10 = 10;
	/**
	 * 11 -- 已维修
	 */
	public final static Integer INSTRUMENT_STATUS_11 = 11;
	/**
	 * 12 -- 申请报废
	 */
	public final static Integer INSTRUMENT_STATUS_12 = 12;
	/**
	 * 13 -- 已报废
	 */
	public final static Integer INSTRUMENT_STATUS_13 = 13;
	/**
	 * 14 -- 未报废
	 */
	public final static Integer INSTRUMENT_STATUS_14 = 14;
	
	
	/**
	 * 公告表公告类型的值
	 * 1 : 仪器调拨
	 * 2 : 仪器维修
	 * 3 : 仪器报废
	 */
	
	/**
	 * 1 : 仪器调拨
	 */
	public final static Integer AFFICHE_TYPE_1 = 1;
	/**
	 * 2 : 仪器维修
	 */
	public final static Integer AFFICHE_TYPE_2 = 2;
	/**
	 * 3 : 仪器报废
	 */
	public final static Integer AFFICHE_TYPE_3 = 3;
	
	/**
	 * 对于调拨，维修，报废申请，是否对系统管理员可见
	 * 1 -- 不可见
	 * 2 -- 可见
	 */
	public final static Integer OPACIFY_TO_ADMIN_1 = 1;
	public final static Integer OPACIFY_TO_ADMIN_2 = 2;
}
