<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|下载试题模板</title>
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
                    <li><a href="list.do">题型管理</a></li>
                    <li><a href="#" class="active">下载题型模板</a></li>
                </ol>
            </div>

            <form class="form-horizontal" method="post" action="downModel.do" data-toggle="validator" role="form">
                <div class="form-group">
                    <label class="control-label col-md-1">题型</label>
                    <div class="col-md-10">
                        <select class="form-control" name="metaInfoId" required>
                            <option value="">请选择</option>
                            <c:forEach var="metaInfo" items="${metaInfoList}">
                                <option value="${metaInfo.id}">${metaInfo.name}</option>
                            </c:forEach>
                        </select>
                        <span class="help-block with-errors">选择需要下载的试题模板的题型</span>
                    </div>

                </div>


                <div class="form-group container">
                    <button class="btn btn-info">下载</button>

                </div>

            </form>


        </div>
    </div>
</div>
<script></script>
</body>
</html>
</html>