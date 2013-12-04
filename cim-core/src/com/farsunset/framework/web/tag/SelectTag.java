package com.farsunset.framework.web.tag;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Random;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.google.gson.Gson;

public class SelectTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int width = 150;;
	private int height = 40;
	private String borderColor = "#68B8E0";
	private LinkedHashMap<String,String> dataMap;
	private String  dataJson;
	private String name ="";
	private String inputId ="";
	private String listId ="";
	private String position ="";
	private String onChange="";
	private String defaultKey;
	private Long tagItemList;
	private String itemSelected;
	private String showSelectList;
	public int doStartTag(){   
		return EVAL_BODY_INCLUDE;   
	} 
	public int doEndTag() {
		itemSelected=inputId + "itemSelected";
		showSelectList=inputId + "showSelectList";
		tagItemList=System.currentTimeMillis()+Math.abs(new Random().nextLong());
		JspWriter out = pageContext.getOut();
		try {
			out.println("<div  style=\"float: left;"+position+"\" onblur=\"$('#TagitemList').fadeOut('slow')\">");
				out.println("<input type=\"hidden\" name=\""+name+"\" id=\""+inputId+"\">");
				out.println("<div class=\"selectTag\" onclick=\""+showSelectList+"()\" style=\""+width+"px;height: "+height+"px;\"><span id=\""+inputId+"TextView\"></span><s></s></div>");
				out.println("<div class=\"selectItemPanel\" id=\""+tagItemList+"\" style=\"width:"+width+"px; \">");
					out.println("<ul style=\"width:"+width+"px;padding: 0;margin-top: 0px;\" id=\""+listId+"\">");
					if(dataJson!= null)
					{
						Gson gson = new Gson();
						dataMap = gson.fromJson(dataJson, LinkedHashMap.class );
					}
					if(dataMap!= null)
					{
						for(String key:dataMap.keySet())
						{   
							out.println("<li onclick=\""+itemSelected+"('"+key+"',$(this).text());\" class=\"selectItem\" style=\"line-height: "+height+"px;width:"+(width-30)+"px;\">"+dataMap.get(key)+"</li>");
						}
						
					}
					out.println("</ul>");
				out.println("</div>");
				out.println("<script>");
					out.println("function "+itemSelected+"(key,text){$('#"+tagItemList+"').fadeOut();if($('#"+inputId+"').val()==key){ return ;}$('#"+inputId+"TextView').text(text);$('#"+inputId+"').val(key);"+onChange+";}");
					out.println("function "+showSelectList+"(){ $('#"+tagItemList+"').is(':visible')==true?$('#"+tagItemList+"').fadeOut():$('#"+tagItemList+"').fadeIn();}");
					out.println("function "+inputId+"InitValue(key,value){ $('#"+inputId+"TextView').text(text);$('#"+inputId+"').val(key);}");
				out.println("</script>");
				if(defaultKey!=null&&dataMap.containsKey(defaultKey))
				{
					out.println("<script>");
						out.println("$('#"+inputId+"TextView').text('"+dataMap.get(defaultKey)+"');");
						out.println("$('#"+inputId+"').val('"+defaultKey+"');");
						out.println(onChange+";");
					out.println("</script>");
				}
		  out.println("</div>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return Tag.EVAL_PAGE;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	 
	public void setDataMap(LinkedHashMap<String, String> dataMap) {
		this.dataMap = dataMap;
	}
	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}
	public void setDefaultKey(String defaultKey) {
		this.defaultKey = defaultKey;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setId(String id) {
		this.id = id;
	}
	 
	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public void setInputId(String inputId) {
		this.inputId = inputId;
	}
	
	
	
	
}
