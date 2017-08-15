<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../template/header.jsp" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>管理</title>
</head>
<body>
<div class="container">
    <div class="row nav-path">
        <ol class="breadcrumb">
            <li><a href="#">首页</a> </li>
            <li><a href="#" class="active">商品管理</a> </li>
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
                <th><input type="checkbox" class="selectAll"> </th>
				                    <th >examStatus</th>
				                    <th >examStatusUpdate</th>
				                    <th >idNo</th>
				                    <th >name</th>
				                    <th >userId</th>
				                    <th >registTime</th>
				                    <th >type</th>
				                    <th >level</th>
				                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pi.data}" var="profile" varStatus="line">
                <tr>
                    <td><input type="checkbox" objId="${profile.id}"></td>
					                        <td>${profile.examStatus}</td>
					                        <td>${profile.examStatusUpdate}</td>
					                        <td>${profile.idNo}</td>
					                        <td>${profile.name}</td>
					                        <td>${profile.userId}</td>
					                        <td>${profile.registTime}</td>
					                        <td>${profile.type}</td>
					                        <td>${profile.level}</td>
					                    <td>
                        <a href="edit.do?id=${profile.id}&type=detail" class="opr">详情</a>
                        <a href="edit.do?id=${profile.id}" class="opr">修改</a>
                        <a href="javascript:del(${profile.id})" class="opr">删除</a>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>

    <div class="row after-table">
        <div class="pull-left form-inline">
                        <select class="form-control show-count" >
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