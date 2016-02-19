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
                    <h3  style="padding-left: 320px;">微信生成带参数二维码</h3>
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
                           <div class="field-box" >
                              <label> 渠道:</label>
                              <input type="text" name="channel" id="channel" class="span8" style="width: 455px;"/>
	                        </div>
	                         <div class="field-box">
                               <div id="html_image" class="name" style="color: red;"></div>
	                        </div>
                            <div class="span11 field-box actions" style="padding-right: 800px;">
                                    <input type="button"  class="btn-glow primary" id="qr_button" value="生成二维码" />
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
           		var channel = $("#channel").val();
           		if(channel == "" || channel == null){
           			alert("请填写渠道");
           			return false;
           		}
           		var wechat_name = $("#wechat_name").val();
           		//初始化微信接口
           		$.ajax({
           			type : "post",
           			dataType : 'json',
           			url : '${path}/weChatQrCode/createQrCode.do',
           			data : {
           				"channel":channel,
           				"wechatName":wechat_name
           			},
           			success : function(data){
           				$("#html_image").html("");
           				var html = "";
           				if(data.code == 0){
           					html = "<img src='"+data.data.qrImage+"' width='200' height='200'><br/>";
           					html = html + "<span style='font-size:18px;'>生成的"+channel+"二维码成功</span><br/>";
           					html = html + '<a href="'+data.data.qrImage+'" id=pic1 onclick="savepic();return false;" style="cursor:hand">点击下载二维码,只支持IE内核浏览器，其他浏览器可点击鼠标右键选择图片另存为选项</a>'
           					$("#html_image").html(html);
           				}else{
           					html = html + "<span style='font-size:18px;'>生成的"+channel+"二维码失败</span>";
           					$("#html_image").html(html);
           				}
           			}
           		});
            });

            
        });
        function savepic() { 
        	if (document.all.a1 == null) { 
        		objIframe = document.createElement("IFRAME"); 
        		document.body.insertBefore(objIframe); 
        		objIframe.outerHTML = "<iframe name=a1 style='width:0px;hieght:0px;display:none;' src=" + imageName.href + "></iframe>"; 
        		re = setTimeout("savepic()", 1) 
        	} else { 
        		clearTimeout(re) 
        		pic = window.open(imageName.href, "a1") 
        		pic.document.execCommand("SaveAs") 
        		document.all.a1.removeNode(true) 
        	} 
        } 
    </script>
</body>
</html>