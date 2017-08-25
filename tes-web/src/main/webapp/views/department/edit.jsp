<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|用户列表</title>
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
                    <li><a href="list.do">部门管理</a></li>
                    <li><a href="#" class="active">编辑部门信息</a></li>
                </ol>
            </div>


                <form class="form-horizontal col-md-8" method="post" action="save.do" data-toggle="validator" role="form">
                    <input id="id" type="hidden" name="id" value="${department.id}">


                    <%--<div class="form-group">--%>
                    <%--<label class="control-label col-md-2">创建时间</label>--%>
                    <%--<div class="col-md-10">--%>
                    <%--<input class="form-control" type="text" name="createTime" value="${department.createTime}">--%>
                    <%--<span class="help-block with-errors">提示信息</span>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label class="control-label col-md-2">部门名称</label>
                        <div class="col-md-10">
                            <input class="form-control" type="text" name="name" value="${department.name}" required>
                            <span class="help-block with-errors"></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-2">部门编码</label>
                        <div class="col-md-10">
                            <input class="form-control" type="text" name="code" value="${department.code}" >
                            <span class="help-block with-errors">部门代号，建议英文、拼音或者首字母简称等，用于直观区分部门，可为空</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-2">上级部门</label>
                        <div class="col-md-10">
                            <select class="form-control" name="superiorId">
                                <option value="0">请选择</option>
                                <c:forEach items="${departmentList}" var="dept">
                                    <option value="${dept.id}" <c:if test="${department.superiorId == dept.id}">selected</c:if> >${dept.name}</option>
                                </c:forEach>
                            </select>
                            <%--<input class="form-control" type="text" name="superiorId" value="${department.superiorId}">--%>
                            <span class="help-block with-errors">当前部门所属的上级部门，可为空，默认为顶级部门</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-2">状态</label>
                        <div class="col-md-10">
                            <select class="form-control" name="status" required>
                                <option value="0" <c:if test="${department.status == 0}">selected</c:if> >启用</option>
                                <option value="1" <c:if test="${department.status == 1}">selected</c:if> >禁用</option>
                            </select>
                            <span class="help-block with-errors">可用/禁用等</span>
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                    <%--<label class="control-label col-md-2">更新时间</label>--%>
                    <%--<div class="col-md-10">--%>
                    <%--<input class="form-control" type="text" name="updateTime" value="${department.updateTime}">--%>
                    <%--<span class="help-block with-errors"></span>--%>
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

<script></script>
</body>
</html>
