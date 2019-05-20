package cn.clims.controller;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.clims.pojo.User;
import cn.clims.tools.Constants;


public class BaseController {
	//抽取当前用户
	private User currentUser;
	
	
	public User getCurrentUser(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(false);
		if(null != session){
			currentUser = (User)session.getAttribute(Constants.CURRENT_USER);
			System.out.println("================from RequestContextHolder===当前用户的userCode = "
					+currentUser.getUserCode()+"\t userName = "+currentUser.getUserName());
		}else {
			currentUser = null;
		}
//		}else System.out.println("=================当前用户的userCode = "
//				+currentUser.getUserCode()+"\t userName = "+currentUser.getUserName());
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	/**
	 * 日期格式化
	 */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder){
		dataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport(){
			@Override
			public void setAsText(String value){
				try {
					setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					setValue(null);
				}
			}
			@Override
			public String getAsText() {
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)getValue());
			}
		});
	}
	
}
