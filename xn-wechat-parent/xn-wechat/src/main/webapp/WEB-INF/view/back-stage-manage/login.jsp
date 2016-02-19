<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% 
	String webpath = request.getContextPath(); 
	String path =  request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + webpath +"/";
	request.setAttribute("path", path);
%>
<!DOCTYPE html>
<html class="login-bg">
<head>
	<title>登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="common.jsp" %>
	<!-- this page specific styles -->
<link rel="stylesheet" href="${path}static/detail/css/compiled/signin.css" type="text/css" media="screen" />
	<style type="text/css">
		.back-image {background:no-repeat;}
	</style>
</head>
<body>


    <!-- background switcher -->
    <div class="bg-switch visible-desktop">
        <div class="bgs">
            <a href="#" data-img="landscape.jpg" class="bg active">
                <img src="${path}static/detail/img/bgs/landscape.jpg" />
            </a>
            <a href="#" data-img="blueish.jpg" class="bg">
                <img src="${path}static/detail/img/bgs/blueish.jpg" />
            </a>            
            <a href="#" data-img="7.jpg" class="bg">
                <img src="${path}static/detail/img/bgs/7.jpg" />
            </a>
            <a href="#" data-img="8.jpg" class="bg">
                <img src="${path}static/detail/img/bgs/8.jpg" />
            </a>
            <a href="#" data-img="9.jpg" class="bg">
                <img src="${path}static/detail/img/bgs/9.jpg" />
            </a>
            <a href="#" data-img="10.jpg" class="bg">
                <img src="${path}static/detail/img/bgs/10.jpg" />
            </a>
            <a href="#" data-img="11.jpg" class="bg">
                <img src="${path}static/detail/img/bgs/11.jpg" />
            </a>
        </div>
    </div>

<form action="${path}login.do" id="login_form" method="post">
    <div class="row-fluid login-wrapper">
        <a href="index.html">
            <img class="logo" src="${path}static/detail/img/logo-white.png" />
        </a>
	        <div class="span4 box">
	            <div class="content-wrap">
	                <h6>小牛钱罐子微信CMS</h6>
	                <input class="span12" id="username" name="username" type="text" placeholder="请输入账户" />
	                <input class="span12" id="password" name="password" type="password" placeholder="请输入密码" />
	                <a href="#" class="forgot">忘记密码?</a>
	                <div class="remember">
	                    <input id="remember-me" type="checkbox" />
	                    <label for="remember-me">记住密码</label>
	                     <span style="color:red;padding-left:10px;" id="login_msg">${error_msg}</span>
	                </div>
	                <a class="btn-glow primary login" href="#" onclick="login();">登录</a>
	            </div>
	        </div>
	
        <div class="span4 no-account">
            <p>@深圳市前海小牛网络科技有限公司</p>
        </div>
    </div>
</form>
	
    <!-- pre load bg imgs -->
    <script type="text/javascript">
        $(function () {
            // bg switcher
            var $btns = $(".bg-switch .bg");
            $btns.click(function (e) {
                e.preventDefault();
                $btns.removeClass("active");
                $(this).addClass("active");
                var bg = $(this).data("img");

                $("html").css("background-image", "url('${path}static/detail/img/bgs/" + bg + "')");
            });
            $("html").css("background-image", "url('${path}static/detail/img/bgs/10.jpg')");
        });
        
        function login(){
        	var username = $("#username").val();
        	if(username==undefined || username==null || username==""){
        		$("#login_msg").text("");
        		$("#login_msg").text("用户名不能为空!");
        		return false;
        	}
        	var password = $("#password").val();
			if(password==undefined || password==null || password==""){
				$("#login_msg").text("");
        		$("#login_msg").text("密码不能为空!");
        		return false;
        	}
			$("#login_form").submit();
        }
    </script>
</body>
</html>