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
        <div class="container-fluid">
            <div id="pad-wrapper" class="new-user" >
                <div class="row-fluid header" >
                    <h3  style="padding-left: 320px;">微信关键词配置</h3>
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
		                            <div class="span12 field-box">
	                                    <label>关键词:<font style="color: red;">*</font></label>
	                                    <input class="span8" type="text" id="keyName" name="keyName"/>
	                                    <font style="color: red;">*</font>
                                	</div>
                               <form method="POST" id="formData" action-target="http://file.api.weixin.qq.com/cgi-bin/media/upload" target="result-iframe" form-method="POST" form-type="args" return-type="json" enctype="multipart/form-data">
		                           <div class="span12 field-box">
		                            <label>推送消息类型:</label>
		                            <div class="ui-select span5" style="width: 300px;">
		                                 <select id="message_type" name="type" style="width: 300px;">
		                                     <option value="text" selected="selected">文本</option>
		                                      <option value="image">图片</option>
		                                     <option value="news">图文</option>
		                                 </select>
		                              </div>
		                           </div>
                                <div class="span12 field-box" data-id="image">
                                   	<label>token:<font style="color: red;">*</font></label>
                                    <input class="span8" type="text" id="Title" name="Title" readonly="readonly"/>
                                </div>
                                <div class="span12 field-box" data-id="image">
                                   	<label>上传图片:<font style="color: red;">*</font></label>
                                   	<input type="file" id="media" name="media" method="POST" data-type="file" required="true">
                                </div>
                                </form>
                                 <div class="span12 field-box" data-id="image">
                                   	<label>图片名字:<font style="color: red;">*</font></label>
                                    <input class="span8" type="text" id="imageName" name="imageName"/>
                                </div>
                                <div class="span12 field-box" data-id="text">
                                    <div id="text" data-id='text'  style="padding-top: 30px;padding-left:100px;"></div>
                                </div>
                                <div class="span12 field-box" data-id="news">
                                    <label>标题:<font style="color: red;">*</font></label>
                                    <input class="span8" type="text" id="Title" name="Title"/>
                                </div>
                                <div class="span12 field-box" data-id="news">
                                    <label>描述:<font style="color: red;">*</font></label>
                                    <input class="span8" type="text" id="Description" name="Description"/>
                                </div>
                                <div class="span12 field-box" data-id="news">
                                    <label>图片地址:<font style="color: red;">*</font></label>
                                    <input class="span8" type="text" id="PicUrl" name="PicUrl"/>
                                </div>
                                <div class="span12 field-box" data-id="news">
                                    <label>图文地址:<font style="color: red;">*</font></label>
                                    <input class="span8" type="text" id="Url" name="Url"/>
                                </div>
                                <div class="span12 field-box" >
                                    <span style="color: red;" id="error_message"></span>
                                </div>
                                <div class="span11 field-box actions" style="padding-right: 400px;">
                                    <input type="button"  class="btn-glow primary" value="保存" id="saveConfig"/>
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
        	
            // toggle form between inline and normal inputs
            var $buttons = $(".toggle-inputs button");
            var $form = $("form.new_user_form");
			$("#message_type").change(function(){
				var message_type = $("#message_type").val();
				if(message_type == "text"){
					$(document).find("div[data-id='news']").css("display","none");
					$(document).find("div[data-id='image']").css("display","none");
					$(document).find("div[data-id='text']").css("display","");
				}if(message_type == "news"){
					$(document).find("div[data-id='text']").css("display","none");
					$(document).find("div[data-id='image']").css("display","none");
					$(document).find("div[data-id='news']").css("display","");
				}if(message_type == "image"){
					$(document).find("div[data-id='text']").css("display","none");
					$(document).find("div[data-id='news']").css("display","none");
					$(document).find("div[data-id='image']").css("display","");
				}
			});
			$("#message_type").change();
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
            	var message_type = $("#message_type").val();
            	var wechatName = $("#wechat_name").val();
            	var keyName = $("#keyName").val();
            	if(keyName==null || keyName==""){
            		$("#error_message").text('');
            		$("#error_message").text("关键词不能为空！");
            		return false;
            	}
            	var param = {};
            	if(message_type == "text"){
            		
            		var text = $('#text').code();
            		if(text==null || text==""){
            			$("#error_message").text("");
                		$("#error_message").text("文本消息不能为空！");
                		alert("文本消息不能为空！");
                		return false;
            		}
            		param = {
            				'wechatName' : wechatName,
            				'type':message_type,
            				'text':text,
            				'keyName':keyName
            		}
            	}if(message_type == "news"){

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
            				'type':message_type,
            				'keyName':keyName,
            				'Title':Title,
            				'Description':Description,
            				'PicUrl':PicUrl,
            				'Url':Url,
            		}
            	}if(message_type == "image"){
            		  var $form = $('#formData'); 
            	        url = $form.attr('action'); 
            	        var i = $.post(url, $form.serialize(), function(data)
            	        {
            	          var imageName = $("#imageName").val();
            	          if(data.media == null || data.media == ""){
            	        	  alert("上传失败");
            	        	  return false;
            	          }
            	          param = {
            	        		  'imageName':imageName, 
            	        		  'media':data.media,
            	        		  'type':data.type,
            	        		  'keyName':keyName,
            	        		  'wechatName' : wechatName,
            	        		  'times':data.created_at // 微信返回的时间戳
            	          }
            	          
            	        });
            	}
            	if(param == null ){
            		alert("参数不正确");
            		return false;
            	}
				$.ajax({
           			type : "post",
           			dataType : 'json',
           			async: false,
           			url : '${path}wechat/saveKeyWord.do',
           			data : param,
           			success : function(data){	
           				
           			}
           		});
				window.location.href = "${path}wechat/keyWords.do";
            });
        });
    </script>
</body>
</html>