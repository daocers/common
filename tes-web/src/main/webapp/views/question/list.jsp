<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|试题管理</title>
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
        <div class="col-sm-0 col-md-2 sidebar menu-left">
            <%@ include file="../template/menu-left.jsp" %>
        </div>
        <div class="col-sm-12 col-sm-offset-0 col-md-10 col-md-offset-2 main" id="main">
            <%--<h1 class="page-header">Dashboard</h1>--%>
            <div class="page-header nav-path">
                <ol class="breadcrumb">
                    <li><a href="/index.do">首页</a></li>
                    <li><a href="#" class="active">试题管理</a></li>
                </ol>
            </div>

            <form class="form-inline" action="list.do">
                <div class="input-group input-group-sm">
                    <div class="input-group-addon">
                        题库
                    </div>
                    <select class="form-control" name="EQ_questionBankId">
                        <option value="">全部</option>
                        <c:forEach items="${questionBankList}" var="questionBank">
                            <option value="${questionBank.id}"
                                    <c:if test="${questionBank.id == param.EQ_questionBankId}">selected</c:if> >
                                    ${questionBank.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="input-group input-group-sm">
                    <div class="input-group-addon">
                        题型
                    </div>
                    <select class="form-control" name="EQ_metaInfoId">
                        <option value="">全部</option>
                        <c:forEach items="${metaInfoList}" var="metaInfo">
                            <option value="${metaInfo.id}"
                                    <c:if test="${metaInfo.id == param.EQ_metaInfoId}">selected</c:if> >${metaInfo.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="input-group input-group-sm">
                    <div class="input-group-addon">
                        题目
                    </div>
                    <input type="text" name="LK_title" class="form-control" value="${param.LK_title}">
                </div>

                <div class="input-group input-group-sm">
                    <button class="btn btn-info btn-sm">查询</button>
                </div>
            </form>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th><input type="checkbox" class="selectAll"></th>
                    <th class="col-md-2">题目</th>
                    <th class="col-md-3">题干</th>
                    <th class="col-md-1">答案</th>
                    <th class="col-md-2">额外信息</th>
                    <th class="col-md-1">题型</th>
                    <th class="col-md-1">题库</th>
                    <th class="col-md-2">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pi.data}" var="question" varStatus="line">
                    <tr>
                        <td><input type="checkbox" objId="${question.id}"></td>
                        <td>${question.title}</td>
                        <td>${question.content}</td>
                        <td>${question.answer}</td>
                        <td>${question.extraInfo}</td>
                        <td>${metaInfoMap.get(question.metaInfoId)}</td>
                        <td>${bankMap.get(question.questionBankId)}</td>
                        <td>
                            <a href="edit.do?id=${question.id}&type=detail" class="opr">详情</a>
                            <a href="edit.do?id=${question.id}" class="opr">修改</a>
                            <a href="javascript:del(${question.id})" class="opr">删除</a>
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