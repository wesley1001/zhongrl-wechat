<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!-- sidebar -->
    <div id="sidebar-nav">
        <ul id="dashboard-menu">
            <!-- <li class="active">
                <div class="pointer">
                    <div class="arrow"></div>
                    <div class="arrow_border"></div>
                </div>
                <a href="index.html">
                    <i class="icon-home"></i>
                    <span>主页</span>
                </a>
            </li>  -->           
            <%-- <li>
                <a class="dropdown-toggle" href="#">
                    <i class="icon-signal"></i>
                    <span>公众号信息</span>
                    <i class="icon-chevron-down"></i>
                </a>
                <ul class="submenu">
                    <li><a href="${path}wechat/configs-list.do">公众号配置</a></li>
                    <li><a href="new-user.html">公众接口</a></li>
                    <li><a href="user-profile.html">公众号文档</a></li>
                </ul>
            </li> --%>
            <li>
                <a class="dropdown-toggle" href="#">
                    <i class="icon-group"></i>
                    <span>二维码管理</span>
                    <i class="icon-chevron-down"></i>
                </a>
                <ul class="submenu">
                    <li><a href="${path}weChatQrCode/createQrPage.do">渠道二维码</a></li>
                    <li><a href="${path}weChatQrCode/statisticalScanCode.do">渠道统计</a></li>
                </ul>
            </li>
            <li>
                <a class="dropdown-toggle" href="#">
                    <i class="icon-group"></i>
                    <span>微信菜单</span>
                    <i class="icon-chevron-down"></i>
                </a>
                <ul class="submenu">
                    <li><a href="${path}weChatMenu/wechatMenuPage.do">菜单配置</a></li>
                </ul>
            </li>
          <!--   <li>
                <a class="dropdown-toggle" href="#">
                    <i class="icon-edit"></i>
                    <span>微信服务器</span>
                    <i class="icon-chevron-down"></i>
                </a>
                <ul class="submenu">
                    <li><a href="form-showcase.html">服务器回调地址</a></li>
                </ul>
            </li> -->
            <li>
                <a class="dropdown-toggle" href="#">
                    <i class="icon-picture"></i>
                    <span>自定义回复</span>
               		<i class="icon-chevron-down"></i>
                </a>
                <ul class="submenu">
                    <li><a href="${path}wechat/replyMessages.do">回复消息</a></li>
                    <li><a href="${path}wechat/keyWords.do">关键词</a></li>
                    <!--  <li><a href="form-showcase.html">回复音频</a></li>
                    <li><a href="form-showcase.html">回复视频</a></li>
                    <li><a href="form-showcase.html">回复音乐</a></li> 
                    <li><a href="form-showcase.html">回复回复图文</a></li>-->
                </ul>
            </li>
            <!-- <li>
                <a class="dropdown-toggle" href="#">
                    <i class="icon-calendar-empty"></i>
                    <span>微信分享</span>
                </a>
            </li> -->
           <!--  <li>
                <a class="dropdown-toggle" href="#">
                    <i class="icon-th-large"></i>
                    <span>报表统计</span>
                </a>
            </li> -->
            <!-- <li>
                <a class="dropdown-toggle ui-elements" href="#">
                    <i class="icon-code-fork"></i>
                    <span>UI Elements</span>
                    <i class="icon-chevron-down"></i>
                </a>
                <ul class="submenu">
                    <li><a href="ui-elements.html">UI Elements</a></li>
                    <li><a href="icons.html">Icons</a></li>
                </ul>
            </li>
            <li>
                <a href="personal-info.html">
                    <i class="icon-cog"></i>
                    <span>My Info</span>
                </a>
            </li>
            <li>
                <a class="dropdown-toggle" href="#">
                    <i class="icon-share-alt"></i>
                    <span>Extras</span>
                    <i class="icon-chevron-down"></i>
                </a>
                <ul class="submenu">
                    <li><a href="code-editor.html">Code editor</a></li>
                    <li><a href="grids.html">Grids</a></li>
                    <li><a href="signin.html">Sign in</a></li>
                    <li><a href="signup.html">Sign up</a></li>
                </ul>
            </li> -->
        </ul>
    </div>
    <!-- end sidebar -->