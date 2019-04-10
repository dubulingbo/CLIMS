package cn.clims.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密类：该类采用MD5对字符创加密，保护敏感信息
 * @author DubLBo
 *
 */
public class MD5 {
	
	private static final String[] HEXArray = {"0","1","2","3",
											  "4","5","6","7",
										      "8","9","a","b",
										      "c","d","e","f" };
	//MD5加密的字符创，得到的是128位2进制编码
	public static String encrypt(String origin){
		byte[] result = null;
		try {
			result = MessageDigest.getInstance("MD5").digest(origin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
//		for(byte b : result){
//			System.out.println(Integer.toBinaryString(b&255));
//		}
		return byteToString(result);
	}
	
	/**
	 * 将byte数组转化为16进制的字符串
	 * @param result
	 * @return
	 */
	public static String byteToString(byte[] result) {
		String resultString = "";
		for(byte b : result){
            /* 解释：为什么采用b&255
             * b:它本来是一个byte类型的数据(1个字节) 255：是一个int类型的数据(4个字节)
             * byte类型的数据与int类型的数据进行运算，会自动类型提升为int类型 eg: b: 1001 1100(原始数据)
             * 运算时： b: 0000 0000 0000 0000 0000 0000 1001 1100 255: 0000
             * 0000 0000 0000 0000 0000 1111 1111 结果：0000 0000 0000 0000
             * 0000 0000 1001 1100 此时的temp是一个int类型的整数
             */
			int temp = b&255;
			resultString +=(HEXArray[temp/16]+HEXArray[temp%16]);
		}
//		System.out.println(resultString.length());
		return resultString;
	}
}
