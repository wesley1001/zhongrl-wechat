<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>'
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
        	 <div class="row-fluid head">
                        <div class="span12" style="padding-top: 40px;padding-left: 80px;">
                            <a href="${path}wechat/wx-config.do"  class="btn-flat success" >新增</a>
                        </div>
                    </div>
            <div id="pad-wrapper">
                <div class="table-wrapper products-table section">
                    <div class="row-fluid head">
                        <div class="span12">
                            <h4>公众号列表</h4>
                        </div>
                    </div>
                    <form action="${path}wechat/configs-list.do" id="formPage" method="post">
                    <input type="hidden" name="page" id="page" />
                    <div class="row-fluid">
                        <table class="table table-hover">
                            <thead>
                                <tr >
                                    <th class="span3" align="center">
                                        <span class="line"></span>公众号名称
                                    </th>
                                    <th class="span3" align="center">
                                        <span class="line"></span>APPID
                                    </th>
                                    <th class="span3" align="center">
                                        <span class="line"></span>密钥
                                    </th>
                                    <th class="span3" align="center">
                                        <span class="line"></span>类型
                                    </th>
                                    <th class="span3" align="center">
                                        <span class="line"></span>操作
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.wechatConfigs}" var="item">
                                <tr class="first">
                                    <td class="description">
                                       ${item.wechatName}
                                    </td>
                                    <td class="description">
                                        ${item.appId}
                                    </td>
                                     <td class="description">
                                        ${item.appSecret}
                                    </td>
                                    <td class="description">
                                        ${item.wechatType}
                                    </td>
                                    <td>
                                       <a href="#" class="btn-flat primary">修改</a>
                                       <a href="#" class="btn-flat danger">删除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                       <!-- 分页 -->
                        <div id="pages" class='pagination pull-left'></div> 
                       </form> 
                    </div>
                </div>
</div>
<script type="text/javascript" src="${path}/static/detail/js/page.js" />
    <script type="text/javascript">
        $(function () {
        	initPage('${page}','${totalpage}','${pagesize}');
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
    </script>
</body>
</html>