package com.farsunset.framework.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.farsunset.framework.tools.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

public class SuperAction extends ActionSupport implements ServletRequestAware,ServletResponseAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public HttpServletRequest request;
	public HttpServletResponse response;
	public HttpSession session;
	public Page page ;
 
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		request = arg0;
		session = request.getSession();
		
		
		 page =  new Page();
		 String pageIndex = request.getParameter("currentPage");
		 String pageSize = request.getParameter("pageSize");
		 int currentPage = 1;
		 if(!StringUtil.isEmpty(pageIndex))
		 {
			 currentPage = Integer.parseInt(pageIndex);
		 }
		 if(!StringUtil.isEmpty(pageSize))
		 {
			 page.size = Integer.parseInt(pageSize);
		 }
		 page.setCurrentPage(currentPage);
 
	}
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		// TODO Auto-generated method stub
		response = arg0;
	}
	
 
}