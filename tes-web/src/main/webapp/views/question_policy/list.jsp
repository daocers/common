<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|试题策略管理</title>
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
                    <li><a href="#" class="active">试题策略管理</a></li>
                </ol>
            </div>

            <form class="form-inline" action="list.do">
                <div class="input-group input-group-sm">
                    <div class="input-group-addon">
                        题型
                    </div>
                    <select class="form-control" name="EQ_questionMetaInfoId">
                        <option value="">全部</option>
                        <c:forEach items="${questionMetaInfoList}" var="metaInfo">
                            <option value="${metaInfo.id}"
                                    <c:if test="${metaInfo.id == param.EQ_questionMetaInfoId}">selected</c:if> >${metaInfo.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="input-group input-group-sm">
                    <div class="input-group-addon">
                        名称
                    </div>
                    <input type="text" name="LK_name" value="${param.LK_name}" class="form-control">
                </div>
                <div class="input-group input-group-sm">
                    <div class="input-group-addon">
                        题量
                    </div>
                    <input type="text" name="EQ_count" value="${param.EQ_count}" class="form-control">
                </div>

                <button class="btn btn-default btn-sm" type="reset" value="reset">重置</button>
                <button class="btn btn-info btn-sm">查询</button>

                <a href="/questionPolicy/edit.do" class="btn btn-warning btn-sm">新增试题策略</a>

            </form>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th><input type="checkbox" class="selectAll"></th>
                    <th>策略名称</th>
                    <th>编号</th>
                    <th>题型</th>
                    <th>策略内容</th>
                    <th>题量</th>
                    <th>修改时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pi.data}" var="questionPolicy" varStatus="line">
                    <tr>
                        <td><input type="checkbox" objId="${questionPolicy.id}"></td>
                        <td>${questionPolicy.name}</td>
                        <td>${questionPolicy.code}</td>
                        <td>${questionMetaInfoMap.get(questionPolicy.questionMetaInfoId)}</td>
                        <td>${questionPolicy.content}</td>
                        <td>${questionPolicy.count}</td>
                        <td><fmt:formatDate value="${questionPolicy.updateTime}"
                                            pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                        <td>
                            <a href="edit.do?id=${questionPolicy.id}&type=detail" class="opr">详情</a>
                            <a href="edit.do?id=${questionPolicy.id}" class="opr">修改</a>
                            <a href="javascript:del(${questionPolicy.id})" class="opr">删除</a>
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