package cn.clims.interceptor;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.clims.pojo.User;
import cn.clims.tools.Constants;
import cn.clims.tools.RedisAPI;

public class SysInterceptor extends HandlerInterceptorAdapter {
	
	private Logger logger = Logger.getLogger(SysInterceptor.class);
	
	@Resource 
	private RedisAPI redisAPI;
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String urlPath = request.getRequestURI();
//		String projectRootPath = request.getContextPath();
		
		
		logger.info("进入自定义拦截页面======url : "+urlPath);
		
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		@SuppressWarnings("unchecked")
		Map<String,Object> baselModel = (Map<String, Object>) session.getAttribute(Constants.SESSION_BASE_MODEL);
		if(user == null||baselModel == null){  //session过期了
			response.sendRedirect("/");	
			return false;
		}else{
			String keyString = "Role"+user.getUserRole()+"UrlList";
			String urlsString = "url:"+redisAPI.get(keyString);
			//访问主菜单上的网址；
			if(urlsString != null && !urlsString.equals("") && urlsString.indexOf(urlPath) > 0){
				return true;
			}else{
				response.sendRedirect("/401.html");
				return false;
			}
		}
	}
}
