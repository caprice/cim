package com.farsunset.framework.web.tag;

import  com.farsunset.framework.web.DataMap;;

public class Functions {

	
	public static String chatAt(String source,int index)
	{
		if(source==null || index >source.length() -1 ||  index<0)
		{
			return "";
		}
		return String.valueOf(source.charAt(index));
	}
	
	public static String powers(String power)
	{
		 StringBuffer buffer = new StringBuffer();
		 if(power!=null)
		 {
			 for(int i=0;i<power.length();i++)
			 {
				 if(power.charAt(i) == '1')
				 {
					 
					 buffer.append(DataMap.getPowerMap().get(String.valueOf(i)));
					 if(i!=power.length()-1)
					 {
						 buffer.append(" | ");
						 
					 }
				 }
			 }
		 }
		 
		 if(buffer.length() == 0)
		 {
			 buffer.append("无");
		 }
		 
		 return buffer.toString();
	}
}
