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
	<title>微信关键词配置</title>
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
      <form action="${path}wechat/keyWords.do" id="formPage" method="post">
        <div class="container-fluid">
        	 	<div class="row-fluid head">
                        <div class="span12" style="padding-top: 40px;padding-left: 80px;">
                            <a href="${path}wechat/addKeyWord.do"  class="btn-flat success" >新增</a>
                        </div>
                        <label>公众号:</label>
                   		<div class="ui-select span5" style="width: 300px;">
                        <select id="wechat_name" name="wechatName" style="width: 300px;" onchange="wechatNameSubmit();">
                        	<c:forEach items="${requestScope.wechatConfigs}" var="item">
                        	 	<option value="${item.wechatType}" <c:if test="${replyMessage.wechatName eq item.wechatType}">selected="selected"</c:if>>${item.wechatName}</option>
                        	</c:forEach>
                        </select>
                     </div>
                 </div>
               
            <div id="pad-wrapper">
                <div class="table-wrapper products-table section">
                    <div class="row-fluid head">
                        <div class="span12">
                            <h4>渠道统计</h4>
                        </div>
                    </div>
                   
	                    <input type='hidden' id='current_page' name="page" value="${page}"/>
						<input type='hidden' id='show_per_page' value="${pageSize}"/>
						<input type='hidden' id='totalPage' value="${totalPage}"/>
	                    <div class="row-fluid">
	                        <table class="table table-hover">
	                            <thead>
	                                <tr >
	                                	<th class="span3" align="center">
	                                        <span class="line"></span>公众号
	                                    </th>
	                                	<th class="span3" align="center">
	                                        <span class="line"></span>关键词
	                                    </th>
	                                    <th class="span3" align="center">
	                                        <span class="line"></span>消息类型
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
	                            <c:forEach items="${requestScope.keyWords}" var="item">
	                                <tr class="first">
	                                	<td class="description">
	                                       ${item.wechatName}
	                                    </td>
	                                    <td class="description">
	                                       ${item.keyName}
	                                    </td>
	                                    <td class="description">
	                                        ${item.type}
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
	                                       <a href="${path}wechat/editKeyWord.do?id=${item.id}" class="btn-flat primary">修改</a>
	                                       <a href="javascript:void(0);" onclick="deleteKeyWord('${item.id}')" class="btn-flat danger">删除</a>
	                                    </td>
	                                </tr>
	                            </c:forEach>
	                            </tbody>
	                        </table>
	                        <div id="pagination" class="pagination pull-left" style="float: right;padding-right: 50px;"></div>
	                      </div>
                       
                    </div>
                </div>
          </div>
          </form> 
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
        function deleteKeyWord(id){
        	$.ajax({
       			type : "post",
       			dataType : 'json',
       		 	async: false,
       			url : '${path}wechat/deleteKeyWord.do',
       			data : {"id" : id},
       			success : function(data){
       			}
       		});
        	window.location.href = "${path}wechat/keyWords.do";
        }
        
        function wechatNameSubmit(){
        	$("#formPage").submit();
        }
    </script>
</body>
</html>