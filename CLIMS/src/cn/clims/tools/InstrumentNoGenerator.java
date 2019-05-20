package cn.clims.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 生成8位的仪器编号
 * 出厂年份+三位随机数字+(创建者ID+类别号)%36
 * @author DubLBo
 *
 */
public class InstrumentNoGenerator {
	private static final char[] IDARRAY = {'0','1','2','3','4',
				                           '5','6','7','8','9',
				                           'A','B','C','D','E',
				                           'F','G','H','I','J',
				                           'K','L','M','N','O',
				                           'P','Q','R','S','T',
				                           'U','V','W','X','Y','Z'};
	//生成3位随机数字
	private static String generate3Number(){
		String code="";
		int num[]=new int[3];
		int c=0;
		for(int i = 0; i < 3; i++){
			num[i] = new Random().nextInt(10);
			c = num[i];
			for(int j = 0; j < i; j++){
				if(num[j] == c) {
					i--;
					break;
				}
			}
		}
		if(num.length>0) {
			for(int i = 0; i < num.length; i++)
				code+=num[i];
		}
		return code;
	}
	
	//出厂年份+三位随机数字+(创建者ID+类别号)%36
	public static String generateStockNo(String year,Integer createdBy,Integer classNo)throws Exception{
		String res = year;
		res += generate3Number();
		res += IDARRAY[(createdBy+classNo)%36];
		return res;
	}
	
	/**
	 * 学院编号（deptNo） - 签收日期（年月日yyyyMMdd） - MD5码最后7位    - 仪器库存Id(instrumentId)
	 */
	public static String generateAssignNo(Integer deptNo, Integer instStockId){
		StringBuilder sb = new StringBuilder();
		sb.append(deptNo);
		sb.append("-");
		Date cur = new Date();
		sb.append(new SimpleDateFormat("yyyyMMdd").format(cur));
		sb.append("-");
		String md5 = MD5.encrypt(String.valueOf(cur.getTime())).toUpperCase();
		sb.append(md5.substring(md5.length()-7));
		sb.append("-");
		sb.append(instStockId);
		return sb.toString();
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(InstrumentNoGenerator.generateStockNo("1997",1,8));
			System.out.println(InstrumentNoGenerator.generateAssignNo(1, 1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
