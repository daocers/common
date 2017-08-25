<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|我的考试</title>
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
                    <li><a href="#">首页</a></li>
                    <li><a href="#" class="active">我的考试</a></li>
                </ol>
            </div>

            <form class="form-inline" action="list.do">
                <div class="input-group input-group-sm">
                    <div class="input-group-addon">考试名称</div>
                    <input class="form-control" name="name" value="${param.name}">
                </div>
                <div class="input-group input-group-sm">
                    <div class="input-group-addon">编码</div>
                    <input class="form-control" name="code" value="${param.code}">
                </div>
                <div class="input-group input-group-sm">
                    <div class="input-group-addon">授权码</div>
                    <input class="form-control" name="authCode" value="${param.authCode}">
                </div>
                <div class="input-group input-group-sm">
                    <button class="btn btn-sm btn-info">查询</button>
                </div>
            </form>

            <div class="table-responsive">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <%--<th><input type="checkbox" class="selectAll"></th>--%>
                        <th>考试名称</th>
                        <th>场次编码</th>
                        <th>识别码</th>
                        <th>开场时间</th>
                        <th>顺延时间</th>
                        <th>考试时长</th>
                        <th>结束时间</th>
                        <th>机构</th>
                        <th>部门</th>
                        <th>状态</th>
                        <th>得分</th>
                        <th>试卷状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pi.data}" var="paper" varStatus="line">
                        <c:set var="scene" value="${sceneList.get(line.index)}"/>
                        <tr>
                                <%--<td><input type="checkbox" objId="${scene.id}"></td>--%>
                            <td><a href="edit.do?type=detail&id=${scene.id}">${scene.name}</a></td>
                            <td>${scene.code}</td>
                            <td>${scene.authCode}</td>
                            <td><fmt:formatDate value="${scene.beginTime}"
                                                pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                            <td>${scene.delay}</td>
                            <td>${scene.duration}</td>
                            <td><fmt:formatDate value="${scene.endTime}"
                                                pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                            <td>${scene.branch == null ? "" : scene.branch.name}</td>
                            <td>${scene.department == null ? "" : scene.department.name}</td>
                            <td>
                                <c:if test="${scene.status == 0}">
                                    已开场
                                </c:if>
                                <c:if test="${scene.status == 1}">
                                    已封场
                                </c:if>
                                <c:if test="${scene.status == 2}">
                                    已作废
                                </c:if>
                                <c:if test="${scene.status == 3}">
                                    已取消
                                </c:if>
                            </td>
                            <td>${paper.mark}</td>
                            <td>${paper.status}</td>
                            <td>
                                <a href="index.do?id=${scene.id}&type=detail" class="opr">详情</a>
                                <a href="index.do?id=${scene.id}" class="opr">修改</a>
                                <a href="javascript:del(${scene.id})" class="opr">删除</a>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>
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
</div>
<script></script>
</body>
</html>
