package com.farsunset.framework.web.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

 
public class TimestampTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	private long timestamp;
	private String format;
	public int doStartTag(){   
		return EVAL_BODY_INCLUDE;   
	} 
	public int doEndTag() {
		
		JspWriter out = pageContext.getOut();
		Date date = new Date(timestamp);
		if(timestamp==0l)
		{
			try {
				out.print("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return Tag.EVAL_PAGE;
		}
		if(format == null )
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				out.print(dateFormat.format(date));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			try {
				out.print(dateFormat.format(date));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    return Tag.EVAL_PAGE;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	
	
	 
}
