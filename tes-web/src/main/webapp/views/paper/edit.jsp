<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|编辑试卷</title>
    <%@ include file="../template/header.jsp" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
</head>
<body>
<%--<%@ include file="../template/menu-top.jsp" %>--%>

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
                    <li><a href="list.do">试卷列表</a></li>
                    <li><a href="#" class="active">编辑试卷</a></li>
                </ol>
            </div>


            <form class="form-horizontal col-md-8" method="post" action="save.do" data-toggle="validator" role="form">
                <input id="id" type="hidden" name="id" value="${paper.id}">

                <div class="form-group">
                    <label class="control-label col-md-2">答题标志</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="answerFlag" value="${paper.answerFlag}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">开始时间</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="beginTime" value="${paper.beginTime}" required>
                        <span class="help-block with-errors">进入本场考试的时间</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">结束时间</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="endTime" value="${paper.endTime}" required>
                        <span class="help-block with-errors">最后答题时间</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">得分</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="mark" value="${paper.mark}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">场次序号</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="sceneId" value="${paper.sceneId}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">试卷状态</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="status" value="${paper.status}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">用户id</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="userId" value="${paper.userId}" required>
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
<script></script>
</body>
</html>