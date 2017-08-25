<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|编辑岗位</title>
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
                    <li><a href="list.do">岗位管理</a></li>
                    <li><a href="#" class="active">用户列表</a></li>
                </ol>
            </div>

            <form class="form-horizontal col-md-8" method="post" action="save.do" data-toggle="validator" role="form">
                <input id="id" type="hidden" name="id" value="${station.id}">
                <div class="form-group">
                    <label class="control-label col-md-2">岗位名称</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="name" value="${station.name}" required>
                        <span class="help-block with-errors"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">岗位编码</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="code" value="${station.code}">
                        <span class="help-block with-errors">岗位编号，建议使用英文，数字，拼音等形式</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">岗位描述</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="description" value="${station.description}">
                        <span class="help-block with-errors">对该岗位的人员分配，职责规划等信息做简要记录</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">所属机构</label>
                    <div class="col-md-10">
                        <select class="form-control" name="branchId" required>
                            <option value="">请选择</option>
                            <c:forEach var="branch" items="${branchList}">
                                <option value="${branch.id}"
                                        <c:if test="${branch.id == station.branchId}">selected</c:if> >${branch.name}</option>
                            </c:forEach>
                        </select>
                        <span class="help-block with-errors">该岗位是某个机构及其上级机构特有，可以不选，表示可以被所有机构使用</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2">所属部门</label>
                    <div class="col-md-10">
                        <select class="form-control" name="departmentId" required>
                            <option value="">请选择</option>
                            <c:forEach items="${departmentList}" var="dept">
                                <option value="${dept.id}"
                                        <c:if test="${dept.id == station.departmentId}">selected</c:if> >${dept.name}</option>
                            </c:forEach>
                        </select>
                        <span class="help-block with-errors">该岗位归属的部门信息，可以不选，表示可以被所有部门使用</span>
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
<script>$(".btn-commit").onclick(function () {
    $("form select[required]").each(function () {
        var val = $(this).val();
        console.log("val: ", val);
        if (!val || val == '') {
            zeroModal.error("");
        }
    })
})</script>
</body>
</html>