<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>布谷培训|编辑权限</title>
</head>
<body>
<div class="container">
    <div class="row nav-path">
        <ol class="breadcrumb">
            <li><a href="/index.do">首页</a></li>
            <li><a href="list.do">权限管理</a></li>
            <li><a href="edit.do" class="active">编辑权限</a></li>
        </ol>
    </div>
    <input type="hidden" value="${param.type}" id="type">
    <%@ include file="../template/header.jsp" %>
    <%@ include file="../template/menu-top.jsp" %>
    <%@ include file="../template/menu-left.jsp" %>
    <div class="" style="width:780px; vertical-align: top; display: inline-block">
        <form class="form-horizontal" method="post" action="save.do" data-toggle="validator" role="form">
            <input id="id" type="hidden" name="id" value="${authority.id}">

            <div class="form-group">
                <label class="control-label col-md-2">动作</label>
                <div class="col-md-10">
                    <input class="form-control" type="text" name="action" value="${authority.action}">
                    <span class="help-block with-errors"></span>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">控制器</label>
                <div class="col-md-10">
                    <input class="form-control" type="text" name="controller" value="${authority.controller}" required>
                    <span class="help-block with-errors">权限所在的控制器</span>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">描述</label>
                <div class="col-md-10">
                    <input class="form-control" type="text" name="description" value="${authority.description}"
                           maxlength="150">
                    <span class="help-block with-errors">简要描述该权限作用，使用场景，使用用户</span>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">名称</label>
                <div class="col-md-10">
                    <input class="form-control" type="text" name="name" value="${authority.name}" required
                           maxlength="15">
                    <span class="help-block with-errors">权限名称</span>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">参数</label>
                <div class="col-md-10">
                    <input class="form-control" type="text" name="param" value="${authority.param}">
                    <span class="help-block with-errors">该权限必须使用的参数名，如果有多个，使用空格分开</span>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">状态</label>
                <div class="col-md-10">
                    <input class="form-control" type="text" name="status" value="${authority.status}">
                    <span class="help-block with-errors"></span>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">上级权限</label>
                <div class="col-md-10">
                    <input class="form-control" type="text" name="superiorId" value="${authority.superiorId}">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">权限类型</label>
                <div class="col-md-10">
                    <input class="form-control" type="text" name="type" value="${authority.type}">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">权限url</label>
                <div class="col-md-10">
                    <input class="form-control" type="text" name="url" value="${authority.url}">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2">请求方法</label>
                <div class="col-md-10">
                    <input class="form-control" type="text" name="acceptMethod" value="${authority.acceptMethod}">
                    <span class="help-block with-errors">该url可以接受的请求方法，如果为空，表示不限方法</span>
                </div>
            </div>

            <div class="button pull-right">
                <button class="btn btn-default">保存</button>
                <div class="space">

                </div>
                <button class="btn btn-default">取消</button>
            </div>
        </form>

    </div>
</div>
<script>

</script>
</body>
</html>
