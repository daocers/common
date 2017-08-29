<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|题库管理</title>
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
                    <li><a href="#" class="active">题库管理</a></li>
                </ol>
            </div>

            <a href="/questionBank/edit.do" class="btn btn-primary btn-sm pull-right" style="margin-bottom: 10px;">新增题库</a>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th class="col-md-1"><input type="checkbox" class="selectAll"></th>
                    <%--<th>创建用户</th>--%>
                    <th class="col-md-2">题库名称</th>
                    <th class="col-md-3">描述</th>
                    <th class="col-md-2">创建时间</th>
                    <th class="col-md-1">状态</th>
                    <%--<th>更新时间</th>--%>
                    <%--<th>更新用户</th>--%>
                    <th class="col-md-3">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pi.data}" var="questionbank" varStatus="line">
                    <tr>
                        <td><input type="checkbox" objId="${questionbank.id}"></td>
                            <%--<td>${questionbank.createUserId}</td>--%>
                        <td>${questionbank.name}</td>
                        <td>${questionbank.description}</td>
                        <td><fmt:formatDate value="${questionbank.createTime}"
                                            pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                        <td>${questionbank.status == 1 ? "启用":"禁用"}</td>
                            <%--<td><fmt:formatDate value="${questionbank.updateTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate> </td>--%>
                            <%--<td>${questionbank.updateUserId}</td>--%>
                        <td>
                            <a href="edit.do?id=${questionbank.id}&type=detail" class="opr">详情</a>
                            <a href="edit.do?id=${questionbank.id}" class="opr">修改</a>
                            <a href="javascript:del(${questionbank.id})" class="opr">删除</a>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>

            <div class="after-table">
                <div class="pull-left form-inline">
                    <select class="form-control show-count">
                        <option value="10" <c:if test="${ pi.showCount == 10 }">selected</c:if>>10</option>
                        <option value="25" <c:if test="${ pi.showCount == 25}">selected</c:if>>25</option>
                        <option value="50" <c:if test="${ pi.showCount == 50}">selected</c:if>>50</option>
                    </select>
                    <div>条/页</div>

                </div>
                <div class="pull-right">
                    <jsp:include page="../template/page-nav.jsp"/>
                </div>
            </div>


        </div>
    </div>
</div>
<script></script>
</body>
</html>