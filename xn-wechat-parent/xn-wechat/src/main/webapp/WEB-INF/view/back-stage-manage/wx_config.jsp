<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
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
                    <h3  style="padding-left: 320px;">微信公众号配置</h3>
                </div>
                <div class="row-fluid form-wrapper">
                    <!-- left column -->
                    <div class="span9 with-sidebar">
                        <div class="container">
                          
                                <div class="span12 field-box">
                                    <label>公众号:<font style="color: red;">*</font></label>
                                    <input class="span8" type="text" id="wechatName" name="wechatName"/>
                                </div>
                                <div class="span12 field-box">
                                    <label>APPID:<font style="color: red;">*</font></label>
                                    <input class="span8" type="text" id="appId" name="appId"/>
                                </div>
                                <div class="span12 field-box">
                                    <label>密钥:<font style="color: red;">*</font></label>
                                    <input class="span8" type="text" id="appSecret" name="appSecret"/>
                                </div>
                                <div class="span12 field-box">
                                    <label>类型:<font style="color: red;">*</font></label>
                                    <input class="span8" type="text" id="wechatType" name="wechatType"/><br/>
                                    <font style="color: red;padding-left: 140px;" >* 请填写公众号名称每一个汉字或者字母拼音首字母并大写</font>
                                </div>
                                <div class="span11 field-box actions" style="padding-right: 400px;">
                                    <input type="button"  class="btn-glow primary" value="保存" id="saveConfig"/>
                                    <span>  </span>
                                    <input type="reset"  value="返回" class="reset" />
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
            $("#saveConfig").click(function(){
            	var wechatName = $("#wechatName").val();
            	if(wechatName==null || wechatName==""){
            		alert("公众号不能为空！");
            		return false;
            	}
            	var appId = $("#appId").val();
				if(appId==null || appId==""){
					alert("appId不能为空！");
            		return false;
            	}
            	var appSecret = $("#appSecret").val();
				if(appSecret==null || appSecret==""){
					alert("appSecret不能为空！");
            		return false;
            	}
            	var wechatType = $("#wechatType").val();
				if(wechatType==null || wechatType==""){
					alert("wechatType不能为空！");
            		return false;
            	}
				var param = {
						'wechatName' : wechatName,
						'appId' : appId,
						'appSecret' : appSecret,
						'wechatType' : wechatType
				};
				$.ajax({
           			type : "post",
           			dataType : 'json',
           			url : '${path}wechat/saveWechatConfig.do',
           			data : param,
           			success : function(data){
           			}
           		});
				window.location.href = "${path}wechat/configs-list.do";
            });
        });
    </script>
</body>
</html>