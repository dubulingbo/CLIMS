package cn.clims.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class CharacterEncodingFilter implements Filter{

	private static final String CHAR_ENCODING = "UTF-8";
	private Logger logger = Logger.getLogger(CharacterEncodingFilter.class);

	public void destroy() {
		logger.info(" ==  CharacterEncodingFilter destroy ==");
	}
	/**
	 * 过滤器的功能实现
	 */
	//过滤器请求、响应对象 是父类对象  而非前缀Http的Web子类
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//设定字符集
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		//抽取常量
		req.setCharacterEncoding(CHAR_ENCODING);
		resp.setCharacterEncoding(CHAR_ENCODING);
		//设置一些页面信息
		resp.setHeader( "Pragma", "no-cache" );   
        resp.setDateHeader("Expires", 0);   
        resp.addHeader( "Cache-Control", "no-cache" );//浏览器和缓存服务器都不应该缓存页面信息
        resp.addHeader( "Cache-Control", "no-store" );//请求和响应的信息都不应该被存储在对方的磁盘系统中；    
        resp.addHeader( "Cache-Control", "must-revalidate" );//于客户机的每次请求，代理服务器必须向服务器验证缓存是否过时；
        
//		logger.info("=="+req.getRequestURI()+"==");
		//完成功能后记得 放行
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		logger.info("== CharacterEncodingFilter init ==");
	}

}
