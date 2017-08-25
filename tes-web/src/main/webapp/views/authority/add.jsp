<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|添加权限</title>
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
        <%--<div class="col-sm-0 col-md-2 sidebar menu-left">--%>
            <%--<%@ include file="../template/menu-left.jsp" %>--%>
        <%--</div>--%>
        <div class="col-md-12 main" id="main">
            <%--<h1 class="page-header">Dashboard</h1>--%>
            <div class="page-header nav-path">
                <ol class="breadcrumb">
                    <li><a href="/index.do">首页</a></li>
                    <li><a href="list.do">权限管理</a></li>
                    <li><a href="#" class="active">添加权限</a></li>
                </ol>
            </div>

            <form class="form-horizontal col-md-8" method="post" action="add.do" data-toggle="validator" role="form">
                <input id="id" type="hidden" name="id" value="${authority.id}">


                <div class="form-group">
                    <label class="control-label col-md-2">名称</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="name" value="${authority.name}" required
                               maxlength="15">
                        <span class="help-block with-errors">权限名称</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2">描述</label>
                    <div class="col-md-10">
                    <textarea class="form-control" name="description" rows="5" maxlength="150" required>
                        ${authority.description}
                    </textarea>
                        <span class="help-block with-errors">简要描述该权限作用，使用场景，使用用户</span>
                    </div>
                </div>
                <div class="button pull-right">
                    <button class="btn btn-info">保存</button>
                    <div class="space">

                    </div>
                    <button class="btn btn-warning">取消</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script></script>
</body>
</html>
