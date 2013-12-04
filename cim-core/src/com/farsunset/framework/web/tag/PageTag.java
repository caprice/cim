package com.farsunset.framework.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.farsunset.framework.web.Page;

 
public class PageTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Page page;
	public int doStartTag(){   
		return EVAL_BODY_INCLUDE;   
	} 
	public int doEndTag() {
		
		JspWriter out = pageContext.getOut();
		try {
			
			out.println("<div style='float: left;line-height: 50px;'>共"+page.getCount()+"条记录,"+page.getCountPage()+"页</div>");
			if(page.getCountPage()==1)
			{
				return Tag.EVAL_PAGE;
			}
			out.println("<ul class=\"pagination\" style='margin: 10px 0;'>");
			   
				if(!page.isFristPage())
				{
					out.println("<li style='cursor: pointer;'><a onclick='gotoPage(1)'>&laquo;</a></li>");
					out.println("<li style='cursor: pointer;'><a onclick='gotoPage("+ (page.getCurrentPage()-1)+")'>上一页</a></li>");
				}
				else
				{
					out.println("<li  class=\"disabled\"><a>&laquo;</a></li>");
				}
				for(int i = page.getCurrentPage()-3;i<page.getCurrentPage()+3&&i<=page.getCountPage();i++)
				{
					if(i<1)
					{
						continue;
					}
					if(i==page.getCurrentPage())
					{
						out.println("<li class=\"active\"><a >"+i+"</a></li>");
					}else
					{
						out.println("<li style='cursor: pointer;'><a onclick='gotoPage("+i+")'>"+i+"</a></li>");
					}
					
				}
				if(!page.isLastPage())
				{
					out.println("<li style='cursor: pointer;'><a onclick='gotoPage("+ (page.getCurrentPage()+1)+")'>下一页</a></li>");
					out.println(" <li style='cursor: pointer;'><a onclick='gotoPage("+page.getCountPage()+")'>&raquo;</a></li>");
				}else
				{
					out.println(" <li class=\"disabled\"><a>&raquo;</a></li>");
					
				} 
				out.println(" <li><input type=\"text\" id='GoPageNumber' class=\"form-control\" style=\"width: 50px;display: inline;border-radius:0px;height:31px;margin-left: 20px;\"><a style='float: right;cursor: pointer;' onclick=\"gotoPage($('#GoPageNumber').val())\">GO</a></li>");
				out.println("</ul>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return Tag.EVAL_PAGE;
	}
	public void setPage(Page page) {
		this.page = page;
	}
}
