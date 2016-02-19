<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% 
	String webpath = request.getContextPath(); 
	String path =  request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + webpath +"/";
	request.setAttribute("path", path);
%>
<html>
<head>
	<title>渠道统计</title>
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
        	 <div class="row-fluid head">
                        <div class="span12" style="padding-top: 40px;padding-left: 80px;">
                            <a href="${path}wechat/replyMessage.do"  class="btn-flat success" >新增</a>
                        </div>
                    </div>
            <div id="pad-wrapper">
                <div class="table-wrapper products-table section">
                    <div class="row-fluid head">
                        <div class="span12">
                            <h4>渠道统计</h4>
                        </div>
                    </div>
                    <form action="${path}wechat/replyMessages.do" id="formPage" method="post">
	                    <input type='hidden' id='current_page' name="page" value="${page}"/>
						<input type='hidden' id='show_per_page' value="${pageSize}"/>
						<input type='hidden' id='totalPage' value="${totalPage}"/>
	                    <div class="row-fluid">
	                        <table class="table table-hover">
	                            <thead>
	                                <tr >
	                                    <th class="span3" align="center">
	                                        <span class="line"></span>公众号名称
	                                    </th>
	                                    <th class="span3" align="center">
	                                        <span class="line"></span>消息类型
	                                    </th>
	                                    <th class="span3" align="center">
	                                        <span class="line"></span>渠道
	                                    </th>
	                                    <th class="span3" align="center">
	                                        <span class="line"></span>标题
	                                    </th>
	                                    <th class="span3" align="center">
	                                        <span class="line"></span>描述
	                                    </th>
	                                    <th class="span3" align="center">
	                                        <span class="line"></span>图片
	                                    </th>
	                                    <th class="span3" align="center">
	                                        <span class="line"></span>图文地址
	                                    </th>
	                                    <th class="span3" align="center">
	                                        <span class="line"></span>文本
	                                    </th>
	                                    <th class="span3" align="center">
	                                        <span class="line"></span>创建时间
	                                    </th>
	                                    <th class="span3" align="center">
	                                        <span class="line"></span>操作
	                                    </th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                            <c:forEach items="${requestScope.replyMessages}" var="item">
	                                <tr class="first">
	                                    <td class="description">
	                                       ${item.wechatName}
	                                    </td>
	                                    <td class="description">
	                                        ${item.messageType}
	                                    </td>
	                                     <td class="description">
	                                        ${item.channel}
	                                    </td>
	                                    <td class="description">
	                                    	${fn:substring(item.title,0,10)}
	                                    </td>
	                                     <td class="description">
	                                     	${fn:substring(item.description,0,15)}
	                                    </td>
	                                     <td class="description">
	                                     	<c:if test="${not empty item.picUrl}">
	                                     		<img alt="" src="${item.picUrl}" style="width: 120px;px;height: 80px;"/>
	                                     	</c:if>
	                                    </td>
	                                     <td class="description" >
	                                     	<a href="${item.url}" style="width: 80px;" target="_Blank">${fn:substring(item.url,0,10)}</a>
	                                    </td>
	                                     <td class="description">
	                                     	${fn:substring(item.text,0,10)}
	                                    </td>
	                                     <td class="description">
	                                        ${item.createTime}
	                                    </td>
	                                    <td>
	                                       <a href="${path}wechat/editReplyMessage.do?id=${item.id}" class="btn-flat primary">修改</a>
	                                       <a href="javascript:void(0);" onclick="deleteMessage('${item.id}')" class="btn-flat danger">删除</a>
	                                    </td>
	                                </tr>
	                            </c:forEach>
	                            </tbody>
	                        </table>
	                        <div id="pagination" class="pagination pull-left" style="float: right;padding-right: 50px;"></div>
	                      </div>
                       </form> 
                    </div>
                </div>
          </div>
</div>

<script type="text/javascript" src="${path}static/detail/js/page.js"></script>
    <script type="text/javascript">
        $(function () {
            // toggle form between inline and normal inputs
            var $buttons = $(".toggle-inputs button");
            var $form = $("form.new_user_form");

            $buttons.click(function(){
                var mode = $(this).data("input");
                $buttons.removeClass("active");
                $(this).addClass("active");

                if (mode === "inline") {
                    $form.addClass("inline-input");
                } else {
                    $form.removeClass("inline-input");
                }
            });
        });
        function deleteMessage(id){
        	$.ajax({
       			type : "post",
       			dataType : 'json',
       		 	async: false,
       			url : '${path}wechat/deleteReplyMessage.do',
       			data : {"id" : id},
       			success : function(data){
       			}
       		});
        	window.location.href = "${path}wechat/replyMessages.do";
        }
    </script>
</body>
</html>