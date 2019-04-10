package cn.clims;

import org.junit.Test;

import cn.clims.tools.MD5;

public class MD5Test {

	@Test
	public void test() {
		System.out.println(MD5.encrypt("123456"));
	}

}
