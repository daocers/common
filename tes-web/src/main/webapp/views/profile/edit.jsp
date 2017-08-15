<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../template/header.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <title>品类编辑</title>
</head>
<body>
<div class="container">
    <div class="row nav-path">
        <ol class="breadcrumb">
            <li><a href="#">首页</a></li>
            <li><a href="#">品类管理</a></li>
            <li><a href="#" class="active">品类编辑</a></li>
        </ol>
    </div>
    <input type="hidden" value="${param.type}" id="type">
    <div class="row">
        <div class="col-md-8">
            <form class="form-horizontal" method="post" action="save.do" data-toggle="validator" role="form">
                <input id="id" type="hidden" name="id" value="${profile.id}">

                <div class="form-group">
                    <label class="control-label col-md-2">examStatus</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="examStatus" value="${profile.examStatus}"
                               required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">examStatusUpdate</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="examStatusUpdate"
                               value="${profile.examStatusUpdate}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">idNo</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="idNo" value="${profile.idNo}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">name</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="name" value="${profile.name}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">userId</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="userId" value="${profile.userId}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">registTime</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="registTime" value="${profile.registTime}"
                               required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">type</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="type" value="${profile.type}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">level</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="level" value="${profile.level}" required>
                        <span class="help-block with-errors">提示信息</span>
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
<script>

</script>
</body>
</html>
