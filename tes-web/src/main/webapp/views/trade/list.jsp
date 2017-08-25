<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../template/header.jsp" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>布谷培训|交易管理</title>
</head>
<body>
<div class="container">
    <div class="row nav-path">
        <ol class="breadcrumb">
            <li><a href="/index.do">首页</a></li>
            <li><a href="#" class="active">交易管理</a></li>
        </ol>
    </div>
    <div class="row info-search">
        <div class="pull-right form-inline">
            <input type="text" class="form-control" placeholder="输入关键词，例如名称、品牌、序号、供应商等">
            <!--<span class="input-group-btn">-->
            <button class="btn btn-info" type="button">搜索</button>
        </div>
    </div>

    <div class="row pre-table">
        <div class="pull-right">
            <jsp:include page="../template/page-nav.jsp"/>
        </div>
    </div>
    <div class="row table-responsive">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th><input type="checkbox" class="selectAll"></th>
                <th>branchId</th>
                <th>code</th>
                <th>createTime</th>
                <th>createUserId</th>
                <th>departmentId</th>
                <th>name</th>
                <th>stationId</th>
                <th>status</th>
                <th>updateTime</th>
                <th>updateUserId</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pi.data}" var="trade" varStatus="line">
                <tr>
                    <td><input type="checkbox" objId="${trade.id}"></td>
                    <td>${trade.branchId}</td>
                    <td>${trade.code}</td>
                    <td>${trade.createTime}</td>
                    <td>${trade.createUserId}</td>
                    <td>${trade.departmentId}</td>
                    <td>${trade.name}</td>
                    <td>${trade.stationId}</td>
                    <td>${trade.status}</td>
                    <td>${trade.updateTime}</td>
                    <td>${trade.updateUserId}</td>
                    <td>
                        <a href="edit.do?id=${trade.id}&type=detail" class="opr">详情</a>
                        <a href="edit.do?id=${trade.id}" class="opr">修改</a>
                        <a href="javascript:del(${trade.id})" class="opr">删除</a>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>

    <div class="row after-table">
        <div class="pull-left form-inline">
            <select class="form-control show-count">
                <option value="10" <c:if test="${ pi.showCount == 10 }">selected</c:if>>10</option>
                <option value="25" <c:if test="${ pi.showCount == 25}">selected</c:if>>25</option>
                <option value="50" <c:if test="${ pi.showCount == 50}">selected</c:if>>50</option>
            </select>
            <div>条/页</div>

        </div>
        <div class="pull-right">

        </div>
    </div>
</div>
<script type="javascript">

</script>
</body>
</html>