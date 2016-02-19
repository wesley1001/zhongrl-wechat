<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% 
	String webpath = request.getContextPath(); 
	String path =  request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + webpath +"/";
	request.setAttribute("path", path);
%>
<html>
<head>
	<title>微信配置</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!-- this page specific styles -->
    <link rel="stylesheet" href="${path}/static/detail/css/compiled/new-user.css" type="text/css" media="screen" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="common.jsp"%>
	</head>
<body>
	<%@ include file="head.jsp" %>
    <!-- sidebar -->
    <%@ include file="left.jsp" %>
    <!-- end sidebar -->
	<!-- main container -->
    <div class="content">
        <div class="container-fluid">
            <div id="pad-wrapper" class="new-user" >
                <div class="row-fluid header" >
                    <h3  style="padding-left: 320px;">微信公众号菜单配置</h3>
                </div>
                <div class="row-fluid form-wrapper">
                    <!-- left column -->
                    <div class="span9 with-sidebar">
                        <div class="container">
                        	<div class="span12 field-box">
                            <label>公众号:</label>
                            <div class="ui-select span5" style="width: 300px;">
                                 <select id="wechat_name" style="width: 300px;">
                                     <c:forEach items="${requestScope.wechatConfigs}" var="item">
		                                <option value="${item.wechatType}">${item.wechatName}</option>
		                             </c:forEach>
                                 </select>
                              </div>
                           </div>
                           <div class="field-box">
                              <label>生菜单的json:</label>
                              <textarea class="span8" rows="20"  id="jsonMenu"></textarea>
	                        </div>
	                         <div class="field-box">
                               <div id="html_image" class="name" style="color: red;padding-left: 160px;" ></div>
	                        </div>
                            <div class="span11 field-box actions" style="padding-right: 400px;">
                                    <input type="button"  class="btn-glow primary" id="qr_button" value="生成菜单" />
                             </div>
                        </div>
                    </div>
 				</div>
			</div>
		</div>
</div>
    <script type="text/javascript">
        $(function () {

            // toggle form between inline and normal inputs
            var $buttons = $(".toggle-inputs button");
            var $form = $("form.new_user_form");

            $buttons.click(function () {
                var mode = $(this).data("input");
                $buttons.removeClass("active");
                $(this).addClass("active");

                if (mode === "inline") {
                    $form.addClass("inline-input");
                } else {
                    $form.removeClass("inline-input");
                }
            });
            $("#qr_button").on("click",function(){
        		var jsonMenu = $("#jsonMenu").val();
        		if(jsonMenu == "" || jsonMenu == null){
        			alert("请填菜单");
        			return false;
        		}
        		var wechat_name = $("#wechat_name").val();
        		//初始化微信接口
        		$.ajax({
        			type : "post",
        			dataType : 'json',
        			url : '${path}weChatMenu/createWeChatMenu.do',
        			data : {
        				"jsonMenu":jsonMenu,
        				"wechatName":wechat_name
        			},
        			success : function(data){
        				$("#html_image").html("");
        				var html = "";
        				if(data.code == 0){
        					html = html + "<span style='font-size:20px;'>"+data.msg+"</span>";
        					$("#html_image").html(html);
        				}else{
        					html = html + "<span style='font-size:20px;'>"+data.msg+"/span>";
        					$("#html_image").html(html);
        				}
        				$("#wechat_name").val(wechat_name);
        			}
        		});
        	});
        });
        
    </script>
</body>
</html>