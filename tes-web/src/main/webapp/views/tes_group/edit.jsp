<%@ taglib prefix="jap" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <input type="hidden" value="${type}" id="type">
    <div class="row">
        <div class="col-md-8">
            <form class="form-horizontal" method="post" action="save.do" data-toggle="validator" role="form">
                <input id="id" type="hidden" name="id" value="${group.id}">

                <div class="form-group">
                    <label class="control-label col-md-2">分组名称</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="name" value="${group.name}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">分组描述</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="description" value="${group.description}"
                               required>
                        <span class="help-block with-errors"></span>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-offset-2 col-md-6">
                        <jsp:include page="../template/_branch_tree.jsp"></jsp:include>
                    </div>
                </div>


                <%--<div class="form-group">--%>
                    <%--<label class="control-label col-md-2">branchId</label>--%>
                    <%--<div class="col-md-10">--%>
                        <%--<input class="form-control" type="text" name="branchId" value="${group.branchId}" required>--%>
                        <%--<span class="help-block with-errors">提示信息</span>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="control-label col-md-2">departmentId</label>--%>
                    <%--<div class="col-md-10">--%>
                        <%--<input class="form-control" type="text" name="departmentId" value="${group.departmentId}"--%>
                               <%--required>--%>
                        <%--<span class="help-block with-errors">提示信息</span>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="control-label col-md-2">stationId</label>--%>
                    <%--<div class="col-md-10">--%>
                        <%--<input class="form-control" type="text" name="stationId" value="${group.stationId}" required>--%>
                        <%--<span class="help-block with-errors">提示信息</span>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="control-label col-md-2">createTime</label>--%>
                    <%--<div class="col-md-10">--%>
                        <%--<input class="form-control" type="text" name="createTime" value="${group.createTime}" required>--%>
                        <%--<span class="help-block with-errors">提示信息</span>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="control-label col-md-2">createUserId</label>--%>
                    <%--<div class="col-md-10">--%>
                        <%--<input class="form-control" type="text" name="createUserId" value="${group.createUserId}"--%>
                               <%--required>--%>
                        <%--<span class="help-block with-errors">提示信息</span>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="control-label col-md-2">status</label>--%>
                    <%--<div class="col-md-10">--%>
                        <%--<input class="form-control" type="text" name="status" value="${group.status}" required>--%>
                        <%--<span class="help-block with-errors">提示信息</span>--%>
                    <%--</div>--%>
                <%--</div>--%>

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
