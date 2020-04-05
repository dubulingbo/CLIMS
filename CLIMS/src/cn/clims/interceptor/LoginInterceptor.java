package cn.clims.interceptor;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.clims.pojo.User;
import cn.clims.tools.Constants;

public class LoginInterceptor extends HandlerInterceptorAdapter {
private Logger logger = Logger.getLogger(LoginInterceptor.class);
	
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String urlPath = request.getRequestURI();
//		String projectRootPath = request.getContextPath();
		
		
		logger.info("进入自定义登录管理拦截页面======url : "+urlPath);
		
		User user = (User) session.getAttribute(Constants.CURRENT_USER);
		
		if(user == null){  //session过期了
			response.setStatus(404);
		}
		else{
			response.setStatus(200);
		}
		return false;
	}
}
