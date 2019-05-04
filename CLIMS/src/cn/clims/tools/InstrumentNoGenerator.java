package cn.clims.tools;

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
	public static String generate3Number(){
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
	public static String generate(String year,Integer createdBy,Integer classNo)throws Exception{
		String res = year;
		res += generate3Number();
		res += IDARRAY[(createdBy+classNo)%36];
		return res;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(InstrumentNoGenerator.generate("1997",1,8));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
