<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../template/header.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <title>布谷培训|编辑交易</title>
</head>
<body>
<div class="container">
    <div class="row nav-path">
        <ol class="breadcrumb">
            <li><a href="/index.do">首页</a></li>
            <li><a href="list.do">交易管理</a></li>
            <li><a href="#" class="active">品类编辑</a></li>
        </ol>
    </div>
    <input type="hidden" value="${param.type}" id="type">
    <div class="row">
        <div class="col-md-8">
            <form class="form-horizontal" method="post" action="save.do" data-toggle="validator" role="form">
                <input id="id" type="hidden" name="id" value="${trade.id}">

                <div class="form-group">
                    <label class="control-label col-md-2">branchId</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="branchId" value="${trade.branchId}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">code</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="code" value="${trade.code}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">createTime</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="createTime" value="${trade.createTime}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">createUserId</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="createUserId" value="${trade.createUserId}"
                               required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">departmentId</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="departmentId" value="${trade.departmentId}"
                               required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">name</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="name" value="${trade.name}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">stationId</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="stationId" value="${trade.stationId}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">status</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="status" value="${trade.status}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">updateTime</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="updateTime" value="${trade.updateTime}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">updateUserId</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="updateUserId" value="${trade.updateUserId}"
                               required>
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
