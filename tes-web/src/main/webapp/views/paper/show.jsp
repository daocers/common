<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|试卷预览</title>
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
                    <li><a href="list.do">试卷管理</a></li>
                    <li><a href="#" class="active">试卷预览</a></li>
                </ol>
            </div>

            <form action="pageInfo.do" class="form-inline">
                <div class="input-group input-group-sm">
                    <div class="input-group-addon">
                        用户名
                    </div>
                    <input type="text" name="username" value="${param.username}" class="form-control">
                </div>
                <div class="input-group input-group-sm">
                    <div class="input-group-addon">
                        得分
                    </div>
                    <input style="width: 60px;" type="number" name="GTE_mark" value="${param.GTE_mark}"
                           class="form-control" min="0">
                    <div class="input-group-addon">
                        -
                    </div>
                    <input style="width: 60px;" type="number" name="LT_mark" value="${param.LT_mark}"
                           class="form-control">
                </div>
                <div class="input-group input-group-sm">
                    <div class="input-group-addon">
                        场次名称
                    </div>
                    <input type="text" name="sceneName" value="${param.sceneName}" class="form-control">
                </div>
                <button class="btn btn-info btn-sm">查询</button>
            </form>

            <table class="table table-bordered">
                <thead>
                <tr>
                    <th><input type="checkbox" class="selectAll"></th>
                    <th>我的答案</th>
                    <th>结果</th>
                    <th>得分</th>
                    <th>答题时剩余时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pi.data}" var="answer" varStatus="line">
                    <tr>
                        <td><input type="checkbox" objId="${answer.id}"></td>
                        <td>${answer.answer}</td>
                        <td>${answer.result}</td>
                        <td>${answer.score}</td>
                        <td>${answer.timeLeft}</td>
                        <td>
                            <a href="javascript:showQuestion(${answer.questionId}" class="opr">查看题目</a>
                                <%--<a href="edit.do?id=${paper.id}&type=detail" class="opr">详情</a>--%>
                            <a href="paperInfo.do?paperId=${paper.id}" class="opr">作答信息</a>
                            <a href="edit.do?id=${paper.id}" class="opr">修改</a>
                            <a href="javascript:del(${paper.id})" class="opr">删除</a>
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


<%--此处必须单独写在此处，解决无法生效的问题--%>
<script></script>
</body>
</html>