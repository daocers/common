<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../template/header.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <title>布谷培训|编辑答题记录</title>
</head>
<body>
<div class="container">
    <div class="row nav-path">
        <ol class="breadcrumb">
            <li><a href="/index.do">首页</a></li>
            <li><a href="list.do">答题记录管理</a></li>
            <li><a href="#" class="active">编辑答题记录</a></li>
        </ol>
    </div>
    <input type="hidden" value="${param.type}" id="type">
    <div class="row">
        <div class="col-md-8">
            <form class="form-horizontal" method="post" action="save.do" data-toggle="validator" role="form">
                <input id="id" type="hidden" name="id" value="${record.id}">

                <div class="form-group">
                    <label class="control-label col-md-2">answer</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="answer" value="${record.answer}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">paperId</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="paperId" value="${record.paperId}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">questionId</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="questionId" value="${record.questionId}" required>
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
