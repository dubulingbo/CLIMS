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
	
	//当前登录用户session key
	public static final String SESSION_USER = "userSession";
	//当前登录用户的基础业务数据(菜单列表)
	public static final String SESSION_BASE_MODEL = "baseModel";
}
