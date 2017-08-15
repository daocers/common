<%--
  Created by IntelliJ IDEA.
  User: daocers
  Date: 2016/8/27
  Time: 23:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>导航栏</title>
</head>
<body>
<div class="menu-top">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">布谷培训</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li><a href="#">我的布谷</a></li>
                    <li><a href="#">我是考生</a></li>
                    <li><a href="#">我是教师</a></li>
                    <!--<li><a href="#">培训学习</a></li>-->
                    <!--<li><a href="#">统计分析</a></li>-->
                    <!--<li><a href="#">考试管理</a></li>-->


                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <form class="navbar-form navbar-left form-inline" role="search">
                        <div class="input-group">
                            <input class="form-control">
                            <div class="input-group-btn">
                                <button class="btn btn-default">搜索</button>
                            </div>
                        </div>
                    </form>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">                <span class="person-center"></span>
                            个人中心 <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">账户设置</a></li>
                            <li><a href="#">我的布谷</a></li>
                            <li><a href="#">Something else here</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">安全退出</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">One more separated link</a></li>
                        </ul>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</div>
</body>
</html>
