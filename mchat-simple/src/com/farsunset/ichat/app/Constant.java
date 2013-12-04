package com.farsunset.ichat.app;

import android.os.Environment;


  


/**
 * 
 * @version 1.0
 */
public interface Constant { 
	
		
        public static final String SERVER_URL = "http://192.168.1.11:8080/ichat";
	    
	  //  public static final String SERVER_URL = "http://124.237.121.75:8989/ichat";


		
		public static final String SENDER_SPLIT_CHAR = "[|]";
		
		public static final String USER_CION_IMAGE_URL = SERVER_URL+"/userIcon";
		public static final String GROUP_CION_IMAGE_URL = SERVER_URL+"/groupIcon";
		public static final  String CACHE_DIR=Environment.getExternalStorageDirectory().getPath()+"/com.farsunset.ichat.cache";
	 
		
		public int MESSAGE_PAGE_SIZE = 20;
		
		
		public static final String SYSTEM = "system";

	 
      public static interface ReturnCode{
			
    	  //账号已经存在
    	  public static final String CODE_101 = "101";
		}
		

		public static interface MessageType{
			
			
			//用户之间的普通消息
			public static final String TYPE_0 = "0";
			
			public static final String TYPE_1 = "1";
			
			//系统向用户发送的普通消息
			public static final String TYPE_2 = "2";
			
			//群里用户发送的  消息
			public static final String TYPE_3 = "3";
			
			//系统定制消息---好友验证请求
			String TYPE_100 = "100";
			
			//系统定制消息---同意好友请求
			String TYPE_101 = "101";
			
			//系统定制消息---进群请求
			String TYPE_102 = "102";
			
			//系统定制消息---同意进群请求
			String TYPE_103 = "103";
			
		 
			//系统定制消息---好友下线消息
			String TYPE_900 = "900";
			
			//系统定制消息---好友上线消息
			String TYPE_901 = "901";
			
			//下线类型
			String TYPE_999 = "999";
		}
		
		
         public static interface MessageStatus{
			
        	//消息未读
 			public static final String STATUS_0 = "0";
 			//消息已经读取
			public static final String STATUS_1 = "1";
			
			
			//消息已经解码
			public static final String STATUS_999 = "999";
			
		 
		 
			
		}
		
}
