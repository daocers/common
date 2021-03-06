<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我的考试|待考</title>
    <%@ include file="../template/header.jsp" %>
</head>
<body>
<div class="container">
    <div class="row nav-path">
        <ol class="breadcrumb">
            <li><a href="/index.do">首页</a></li>
            <li><a href="#" class="active">我参加的考试</a></li>
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
                <th>考试名称</th>
                <th>考试编码</th>
                <th>开始时间</th>
                <th>交卷时间</th>
                <th>考试时长</th>
                <th>试题分布</th>
                <th>成绩</th>
                <th>排名</th>
            </tr>
            </thead>
            <tbody>
            <%--<c:forEach items="${pi.data}" var="page" varStatus="line">--%>
                <%--<tr>--%>
                    <%--<td><input type="checkbox" objId="${page.id}"></td>--%>
                    <%--<td>${page.code}</td>--%>
                    <%--<td>${page.status}</td>--%>
                    <%--<td>${page.url}</td>--%>
                    <%--<td>--%>
                        <%--<a href="edit.do?id=${page.id}&type=detail" class="opr">详情</a>--%>
                        <%--<a href="edit.do?id=${page.id}" class="opr">修改</a>--%>
                        <%--<a href="javascript:del(${page.id})" class="opr">删除</a>--%>
                    <%--</td>--%>
                <%--</tr>--%>
            <%--</c:forEach>--%>

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