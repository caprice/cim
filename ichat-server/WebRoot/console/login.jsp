<%@ page language="java"   pageEncoding="utf-8"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
Object message = session.getAttribute("message");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>广东国税-掌上家园-管理中心</title>
	<link style="text/css" rel="stylesheet" href="<%=basePath %>/resource/css/base-ui.css"/>
	<link charset="utf-8" rel="stylesheet" href="<%=basePath%>/resource/bootstrap/css/bootstrap.min.css" />
	<link style="text/css" rel="stylesheet" href="<%=basePath %>/resource/css/login.css"/>
	<script type="text/javascript" src="<%=basePath%>/resource/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/resource/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/resource/js/framework.js"></script>
	<script  >
	 function doLogin()
	 {
		    var account = $('#account').val();
		    var password = $('#password').val();
		    if($.trim(account)=='')
		    {
		       showETip("请填登陆账号!");
		       return;
		    }
		     if($.trim(password)=='')
		    {
		       showETip("请填密码!");
		       return;
		    }
		    
		    document.getElementById('loginForm').submit();
	  }
	
	
	</script>
</head>
<body>
<div class="header">
	<div class="logo-box">
		<h2>广东国家税务局</h2>
		<h1>掌上家园管理中心</h1>
	</div>
	 
</div>
<div class="wrapper">
	<div class="content">
	    <div class="content">
		<div class="msg-box"><img src="<%=basePath%>/resource/img/login_bg.png" alt=""><i></i></div>
		 <div class="" style="padding: 10px 8px;width: 410px;position: absolute;top: -61px;right: 33px;height: 438px;background-image: url(<%=basePath%>/resource/img/login-box.png);">
			    <div class="" style="padding: 10px;padding-top: 20px;">
			      <%
			      if(message !=null)
			       {
			      %>
			      <div class="alert alert-danger"><%=message %>
			       <a class="close" data-dismiss="alert" href="#" aria-hidden="true">&times;</a>
			      </div>
			      <%} %>
			     <form action="<%=basePath%>/admin/user_login.action" method="post" id="loginForm" style="margin-top: 50px;">
				  <div class="form-group">
		             <label>账 号：</label> <input class="form-control" type="text" style="width: 330px;display: inline;height: 50px;" name="account" id="account">
		             </div>
		             <div class="form-group" style="margin-top: 40px;">
		             <label>密 码：</label> <input class="form-control" type="password" style="width: 330px;display: inline;height: 50px;" name="password" id="password">
		             </div>
		             
		              <div class="form-group" style="margin-top: 50px;">
		                 <center>
		                 <button type="button" style="width: 200px;font-weight: bold;" class="btn btn-success btn-lg" onclick="doLogin()">登 录</button>
		                 </center>
		             </div>
				 </form>
			</div>
		</div>
		</div>
	</div>
	</div>
</div>
<div class="footer">
	<p class="c">Copyright © 1998 - 2013  All Rights Reserved.</p>
	<p>广东国家税务局   版权所有</p>
</div>

</body></html>