<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|注册</title>
    <%@ include file="../template/header.jsp" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">


</head>

<body>

<%@ include file="../template/menu-top.jsp" %>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-0 col-md-2 sidebar menu-left">
            <%@ include file="../template/menu-left.jsp" %>
        </div>
        <div class="col-sm-12 col-sm-offset-0 col-md-10 col-md-offset-2 main" id="main">
            <%--<h1 class="page-header">Dashboard</h1>--%>
            <div class="page-header nav-path">
                <ol class="breadcrumb">
                    <li><a href="/index.do">首页</a></li>
                    <li><a href="#" class="active">注册</a></li>
                </ol>
            </div>
            <div class="container-fluid">
                <form class="form-horizontal col-md-8" method="post" action="register.do" data-toggle="validator" role="form">

                    <div class="form-group">
                        <label class="control-label col-md-2">姓名</label>
                        <div class="col-md-10">
                            <input class="form-control" type="text" name="profile.name" value="${user.profile.name}"
                                   required minlength="2" maxlength="10">
                            <span class="help-block with-errors">用户姓名</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-2">用户名</label>
                        <div class="col-md-10">
                            <input class="form-control" type="text" name="username" value="${user.username}"
                                   maxlength="16"
                                   minlength="3" required>
                            <span class="help-block with-errors">输入用户名，使用员工号</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-2">用户密码</label>
                        <div class="col-md-10">
                            <input class="form-control" type="password" name="password" id="password"
                                   value="${user.password}"
                                   required maxlength="16" minlength="6">
                            <span class="help-block with-errors">用户密码，6-16位字母、数字、符号组合</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-2">密码确认</label>
                        <div class="col-md-10">
                            <input class="form-control" type="password" minlength="6" maxlength="16"
                                   required data-match="#password" data-error="两次密码输入不一致">
                            <span class="help-block with-errors">再次输入密码</span>
                        </div>
                    </div>

                    <div class="button pull-right">
                        <button class="btn btn-primary btn-commit">注册</button>
                        <div class="space">

                        </div>
                        <button class="btn btn-warning btn-cancel">取消</button>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>
<script></script>
</body>
</html>
