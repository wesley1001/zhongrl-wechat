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
	<title>扫码统计</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!-- this page specific styles -->
    <link rel="stylesheet" href="${path}/static/detail/css/compiled/new-user.css" type="text/css" media="screen" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${path}/static/detail/css/lib/jquery-ui-1.10.2.custom.css" rel="stylesheet" type="text/css" />
    <link href="${path}/static/detail/css/lib/font-awesome.css" type="text/css" rel="stylesheet" />
    <link href="${path}/static/detail/css/lib/morris.css" type="text/css" rel="stylesheet" />
    <!-- this page specific styles -->
    <link rel="stylesheet" href="${path}/static/detail/css/compiled/chart-showcase.css" type="text/css" media="screen" />
	<%@ include file="common.jsp"%>
	 <!-- knob -->
    <script src="${path}/static/detail/js/jquery.knob.js"></script>
    <!-- flot charts -->
    <script src="${path}/static/detail/js/jquery.flot.js"></script>
    <script src="${path}/static/detail/js/jquery.flot.stack.js"></script>
    <script src="${path}/static/detail/js/jquery.flot.resize.js"></script>
    <!-- morrisjs -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
    <script src="${path}/static/detail/js/morris.min.js"></script>
</head>
<body>
	<%@ include file="head.jsp" %>
    <!-- sidebar -->
    <%@ include file="left.jsp" %>
    <!-- end sidebar -->
	<!-- main container -->
    <div class="content">
        <div class="container-fluid">
        	<div class="row-fluid section" style="padding:40px;">
                    <h4 class="title">
                        	扫码统计
                    </h4>
                    <div class="span6 chart" style="padding:20px;">
                        <h5>曲线图</h5>
                        <div id="hero-bar" style="height: 250px;"></div>
                    </div>
                    <div class="span5 chart" style="padding:20px;">
                        <h5>百分比</h5>
                        <div id="hero-donut" style="height: 250px;"></div>    
                    </div>
               </div>
            <div id="pad-wrapper">
                <div class="table-wrapper products-table section">
                    <div class="row-fluid head">
                        <div class="span12">
                            <h4>消息列表</h4>
                        </div>
                    </div>
                    <form action="${path}weChatQrCode/statisticalScanCode.do" id="formPage" method="post">
	                    <input type='hidden' id='current_page' name="page" value="${page}"/>
						<input type='hidden' id='show_per_page' value="${pageSize}"/>
						<input type='hidden' id='totalPage' value="${totalPage}"/>
	                    <div class="row-fluid">
	                        <table class="table table-hover">
	                            <thead>
	                                <tr >
	                                    <th class="span3" align="center">
	                                        <span class="line"></span>渠道总数
	                                    </th>
	                                    <th class="span3" align="center">
	                                        <span class="line"></span>渠道名称
	                                    </th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                            <c:forEach items="${requestScope.biScanCodes}" var="item">
	                                <tr class="first">
	                                    <td class="description">
	                                       ${item.number}
	                                    </td>
	                                    <td class="description">
	                                        ${item.qrCodeName}
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

            // Morris Bar Chart
            Morris.Bar({
                element: 'hero-bar',
                data: ${data},
                xkey: 'device',
                ykeys: ['sells'],
                labels: ['扫码次数'],
                barRatio: 0.4,
                xLabelMargin: 10,
                hideHover: 'auto',
                barColors: ["#3d88ba"]
            });


            // Morris Donut Chart
            Morris.Donut({
                element: 'hero-donut',
                data: ${dataYuan},
                colors:  ${colors},
                formatter: function (y) { return y + "%" }
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