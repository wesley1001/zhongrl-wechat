 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
 <!-- navbar -->
    <div class="navbar navbar-inverse">
        <div class="navbar-inner">
            <button type="button" class="btn btn-navbar visible-phone" id="menu-toggler">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            
            <a class="brand" href="#"><img src="${path}static/detail/img/logo.png" /></a>

            <ul class="nav pull-right">                
                <li class="hidden-phone">
                    <input class="search" type="text" />
                </li>
                <li class="notification-dropdown hidden-phone">
                    <a href="#" class="trigger">
                        <i class="icon-warning-sign"></i>
                        <span class="count"><!-- 8 --></span>
                    </a>
                    <div class="pop-dialog">
                        <div class="pointer right">
                            <div class="arrow"></div>
                            <div class="arrow_border"></div>
                        </div>
                        <div class="body">
                            <a href="#" class="close-icon"><i class="icon-remove-sign"></i></a>
                            <div class="notifications">
                                <h3>建设中...<!-- You have 6 new notifications --></h3>
                                <a href="#" class="item">
                                    <i class="icon-signin"></i> 建设中...<!-- New user registration -->
                                    <span class="time"><i class="icon-time"></i> <!-- 13 min. --></span>
                                </a>
                                <a href="#" class="item">
                                    <i class="icon-signin"></i> 建设中...<!-- New user registration -->
                                    <span class="time"><i class="icon-time"></i> <!-- 18 min. --></span>
                                </a>
                                <a href="#" class="item">
                                    <i class="icon-envelope-alt"></i> 建设中...<!-- New message from Alejandra -->
                                    <span class="time"><i class="icon-time"></i> <!-- 28 min. --></span>
                                </a>
                                <a href="#" class="item">
                                    <i class="icon-signin"></i> 建设中...<!-- New user registration -->
                                    <span class="time"><i class="icon-time"></i> <!-- 49 min. --></span>
                                </a>
                                <a href="#" class="item">
                                    <i class="icon-download-alt"></i> 建设中...<!-- New order placed -->
                                    <span class="time"><i class="icon-time"></i> <!-- 1 day. --></span>
                                </a>
                                <div class="footer">
                                    <a href="#" class="logout">建设中...<!-- View all notifications --></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
                <li class="notification-dropdown hidden-phone">
                    <a href="#" class="trigger">
                        <i class="icon-envelope-alt"></i>
                    </a>
                    <div class="pop-dialog">
                        <div class="pointer right">
                            <div class="arrow"></div>
                            <div class="arrow_border"></div>
                        </div>
                        <div class="body">
                            <a href="#" class="close-icon"><i class="icon-remove-sign"></i></a>
                            <div class="messages">
                                <a href="#" class="item">
                                    <img src="${path}static/detail/img/contact-img.png" class="display" />
                                    <div class="name">建设中...</div>
                                    <div class="msg">
                                    			建设中...
                                        <!-- There are many variations of available, but the majority have suffered alterations. -->
                                    </div>
                                    <span class="time"><i class="icon-time"></i> 建设中...</span>
                                </a>
                                <a href="#" class="item">
                                    <img src="${path}static/detail/img/contact-img2.png" class="display" />
                                    <div class="name">建设中...<!-- Alejandra Galván --></div>
                                    <div class="msg">建设中...
                                        <!-- There are many variations of available, have suffered alterations. -->
                                    </div>
                                    <span class="time"><i class="icon-time"></i>建设中...</span>
                                </a>
                                <a href="#" class="item last">
                                    <img src="${path}static/detail/img/contact-img.png" class="display" />
                                    <div class="name"><!-- Alejandra Galván -->建设中...</div>
                                    <div class="msg">建设中...
                                        <!-- There are many variations of available, but the majority have suffered alterations. -->
                                    </div>
                                    <span class="time"><i class="icon-time"></i> 建设中...<!-- 48 min. --></span>
                                </a>
                                <div class="footer">
                                    <a href="#" class="logout">建设中...<!-- View all messages --></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle hidden-phone" data-toggle="dropdown">
                        ${username}
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#">建设中...</a></li>
                    </ul>
                </li>
                <li class="settings hidden-phone">
                    <a href="#" role="button">
                        <i class="icon-cog"></i>
                    </a>
                </li>
                <li class="settings hidden-phone">
                    <a href="${path}/logout.do" role="button">
                        <i class="icon-share-alt"></i>
                    </a>
                </li>
            </ul>            
        </div>
    </div>
    <!-- end navbar -->