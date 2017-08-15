<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|编辑题库</title>
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
                    <li><a href="list.do">题库管理</a></li>
                    <li><a href="#" class="active">编辑题库</a></li>
                </ol>
            </div>

            <input type="hidden" value="${param.type}" id="type">

            <form class="form-horizontal col-md-8" method="post" action="save.do" data-toggle="validator" role="form">
                <input id="id" type="hidden" name="id" value="${questionBank.id}">
                <div class="form-group">
                    <label class="control-label col-md-2">题库名称</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="name" value="${questionBank.name}" required
                               maxlength="30">
                        <span class="help-block with-errors"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">描述</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="description" value="${questionBank.description}"
                               maxlength="100">
                        <span class="help-block with-errors">对该题库的使用场景，题目信息和出题部门简要说明</span>
                    </div>
                </div>

                <div class="form-group" <c:if test="${param.type != 'detail'}">hidden</c:if>>
                    <label class="control-label col-md-2">创建时间</label>
                    <div class="col-md-10">
                        <p class="form-control-static"><fmt:formatDate value="${questionBank.createTime}"
                                                                       pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></p>
                        <span class="help-block with-errors"></span>
                    </div>
                </div>
                <div class="form-group" <c:if test="${param.type != 'detail'}">hidden</c:if>>
                    <label class="control-label col-md-2">创建用户</label>
                    <div class="col-md-10">
                        <p class="form-control-static">${username}</p>
                        <span class="help-block with-errors"></span>
                    </div>
                </div>

                <div class="button pull-right">
                    <button class="btn btn-primary btn-commit">保存</button>
                    <div class="space">

                    </div>
                    <button class="btn btn-warning btn-cancel">取消</button>
                </div>
            </form>


        </div>
    </div>
</div>

<script></script>
</body>
</html>
