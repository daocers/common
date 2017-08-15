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
            <li><a href="#">首页</a> </li>
            <li><a href="#">品类管理</a> </li>
            <li><a href="#" class="active">品类编辑</a> </li>
        </ol>
    </div>
    <input type="hidden" value="${param.type}" id="type">
    <div class="row">
        <div class="col-md-8">
            <form class="form-horizontal" method="post" action="save.do" data-toggle="validator" role="form">
                <input id="id" type="hidden" name="id" value="${systemparam.id}">

				                    <div class="form-group">
                        <label class="control-label col-md-2">code</label>
                        <div class="col-md-10">
                            <input class="form-control" type="text" name="code" value="${systemparam.code}" required>
                            <span class="help-block with-errors">提示信息</span>
                        </div>
                    </div>
				                    <div class="form-group">
                        <label class="control-label col-md-2">description</label>
                        <div class="col-md-10">
                            <input class="form-control" type="text" name="description" value="${systemparam.description}" required>
                            <span class="help-block with-errors">提示信息</span>
                        </div>
                    </div>
				                    <div class="form-group">
                        <label class="control-label col-md-2">name</label>
                        <div class="col-md-10">
                            <input class="form-control" type="text" name="name" value="${systemparam.name}" required>
                            <span class="help-block with-errors">提示信息</span>
                        </div>
                    </div>
				                    <div class="form-group">
                        <label class="control-label col-md-2">value</label>
                        <div class="col-md-10">
                            <input class="form-control" type="text" name="value" value="${systemparam.value}" required>
                            <span class="help-block with-errors">提示信息</span>
                        </div>
                    </div>
				                    <div class="form-group">
                        <label class="control-label col-md-2">valueType</label>
                        <div class="col-md-10">
                            <input class="form-control" type="text" name="valueType" value="${systemparam.valueType}" required>
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
