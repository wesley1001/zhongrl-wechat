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
	<style type="text/css">
		@import url('http://cdn.gbtags.com/font-awesome/4.1.0/css/font-awesome.min.css');
		@import url('http://cdn.gbtags.com/summernote/0.5.2/summernote.css');
	</style>
	<script src="http://cdn.gbtags.com/jquery/1.11.1/jquery.min.js"></script>
	<script src="http://cdn.gbtags.com/twitter-bootstrap/3.2.0/js/bootstrap.js"></script>
	<script src="http://cdn.gbtags.com/summernote/0.5.2/summernote.min.js"></script>
	</head>
<body>
	<%@ include file="head.jsp" %>
    <!-- sidebar -->
    <%@ include file="left.jsp" %>
    <!-- end sidebar -->
	<!-- main container -->
    <div class="content">
        
        <input type="hidden" id="id" value="${replyMessage.id}"/>
        <div class="container-fluid">
            <div id="pad-wrapper" class="new-user" >
                <div class="row-fluid header" >
                    <h3  style="padding-left: 320px;">微信自定义回复消息配置</h3>
                </div>
                <div class="row-fluid form-wrapper">
                    <!-- left column -->
                    <div class="span9 with-sidebar">
                        <div class="container">
                          		<div class="span12 field-box">
		                            <label>公众号:</label>
		                            <div class="ui-select span5" style="width: 300px;">
		                                 <select id="wechat_name" style="width: 300px;" disabled="disabled">
		                                 	<c:forEach items="${requestScope.wechatConfigs}" var="item">
		                                 	 	<option value="${item.wechatType}" <c:if test="${replyMessage.wechatName eq item.wechatType}">selected="selected"</c:if>>${item.wechatName}</option>
		                                 	</c:forEach>
		                                 </select>
		                              </div>
		                           </div>
		                           <div class="span12 field-box">
		                            <label>消息类型:</label>
		                            <div class="ui-select span5" style="width: 300px;">
		                                 <select id="message_type" style="width: 300px;" disabled="disabled">
		                                     <option value="text" <c:if test="${replyMessage.messageType eq 'text'}">selected="selected"</c:if >>文本</option>
		                                     <option value="news" <c:if test="${replyMessage.messageType eq 'news'}">selected="selected"</c:if >>图文</option>
		                                 </select>
		                              </div>
		                           </div>
		                         <div class="span12 field-box">
                                    <label>渠道:<font style="color: red;">*</font></label>
                                    <input class="span8" type="text" id="channel" name="channel" value="${replyMessage.channel}"/>
                                    <font style="color: red;">*这里填写的渠道要跟生成渠道二维码参数一致</font>
                                </div>
                                <div class="span12 field-box" data-id="text">
                                    <label>文本消息:<font style="color: red;">*</font></label>
                                    <div id="text"  style="padding-top: 30px;padding-left:100px;"></div>
                                </div>
                                <div class="span12 field-box" data-id="news">
                                    <label>标题:<font style="color: red;">*</font></label>
                                    <input class="span8" type="text" id="Title" name="Title" value="${replyMessage.title}"/>
                                </div>
                                <div class="span12 field-box" data-id="news">
                                    <label>描述:<font style="color: red;">*</font></label>
                                    <input class="span8" type="text" id="Description" name="Description"  value="${replyMessage.description}"/>
                                </div>
                                <div class="span12 field-box" data-id="news">
                                    <label>图片地址:<font style="color: red;">*</font></label>
                                    <input class="span8" type="text" id="PicUrl" name="PicUrl"  value="${replyMessage.picUrl}"/>
                                </div>
                                <div class="span12 field-box" data-id="news">
                                    <label>图文地址:<font style="color: red;">*</font></label>
                                    <input class="span8" type="text" id="Url" name="Url" value="${replyMessage.url}"/>
                                </div>
                                <div class="span12 field-box" >
                                    <span style="color: red;" id="error_message"></span>
                                </div>
                                <div class="span11 field-box actions" style="padding-right: 400px;">
                                    <input type="button"  class="btn-glow primary" value="修改" id="saveConfig"/>
                                </div>
                           
                        </div>
                    </div>
 				</div>
			</div>
		</div>
</div>
    <script type="text/javascript">
        $(function () {
        	$('#text').summernote({
        		height: 300,
        		focus: true
        	});
        	var textCode = '${replyMessage.text}';
        	$('#text').code(textCode);
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
            $("#message_type").change(function(){
				var message_type = $("#message_type").val();
				if(message_type == "text"){
					$(document).find("div[data-id='news']").css("display","none");
					$(document).find("div[data-id='text']").css("display","");
				}else{
					$(document).find("div[data-id='text']").css("display","none");
					$(document).find("div[data-id='news']").css("display","");
				}
			});
			$("#message_type").change();
            $("#saveConfig").click(function(){
            	var message_type = $("#message_type").val();
            	var wechatName = $("#wechat_name").val();
            	var channel = $("#channel").val();
            	if(channel==null || channel==""){
            		$("#error_message").text('');
            		$("#error_message").text("渠道不能为空！");
            		return false;
            	}
            	var param = {};
            	var id = $("#id").val();
            	if(message_type == "text"){
            		var text = $("#text").code();
            		if(text==null || text==""){
            			$("#error_message").text("");
                		$("#error_message").text("文本消息不能为空！");
                		return false;
            		}
            		param = {
            				'wechatName' : wechatName,
            				'messageType':message_type,
            				'text':text,
            				'channel':channel,
            				'Id':id
            		}
            	}else{
            		
                	var Title = $("#Title").val();
    				if(Title==null || Title==""){
    					$("#error_message").text("");
                		$("#error_message").text("标题不能为空！");
                		return false;
                	}
                	var Description = $("#Description").val();
    				if(Description==null || Description==""){
    					$("#error_message").text("");
                		$("#error_message").text("描述不能为空！");
                		return false;
                	}
                	var PicUrl = $("#PicUrl").val();
    				if(PicUrl==null || PicUrl==""){
    					$("#error_message").text("");
                		$("#error_message").text("图片地址不能不能为空！");
                		return false;
                	}
    				var Url = $("#Url").val();
    				if(Url==null || Url==""){
    					$("#error_message").text("");
                		$("#error_message").text("图文地址不能不能为空！");
                		return false;
                	}
            	
            		param = {
            				'wechatName' : wechatName,
            				'messageType':message_type,
            				'channel':channel,
            				'Title':Title,
            				'Description':Description,
            				'PicUrl':PicUrl,
            				'Url':Url,
            				'Id':id
            		}
            	}
				$.ajax({
           			type : "post",
           			dataType : 'json',
           			async: false,
           			url : '${path}wechat/updateReplyMessage.do',
           			data : param,
           			success : function(data){
           				
           			}
           		});
				window.location.href = "${path}wechat/replyMessages.do";
            });
        });
    </script>
</body>
</html>