<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|批量添加机构</title>
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
                    <li><a href="list.do">机构管理</a></li>
                    <li><a href="#" class="active">批量添加</a></li>
                </ol>
            </div>
                <form class="form-horizontal" method="post" action="batchAdd.do"  enctype="multipart/form-data">
                    <input type="file" name="file" class="jfilestyle" data-input="true" data-buttonText="导入文件">
                    <button class="btn btn-success">上传</button>
                    ？   没有模板文件<a href="downModel.do">点击下载</a>模板
                </form>
        </div>
    </div>
</div>


<script></script>
</body>
</html>
















<%--<!DOCTYPE html>--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ include file="../template/navi.jsp" %>--%>
<%--<%@ include file="../template/header.jsp" %>--%>
<%--<html lang="en">--%>
<%--<head>--%>
    <%--<meta charset="UTF-8">--%>
    <%--<title>管理</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<div class="container">--%>
    <%--<div class="row nav-path">--%>
        <%--<ol class="breadcrumb">--%>
            <%--<li><a href="#">首页</a> </li>--%>
            <%--<li><a href="#" class="active">商品管理</a> </li>--%>
        <%--</ol>--%>
    <%--</div>--%>

    <%--<div class="row">--%>
        <%--<form class="form-horizontal" method="post" action="batchAdd.do"  enctype="multipart/form-data">--%>
            <%--<input type="file" name="file" class="jfilestyle" data-input="true" data-buttonText="导入文件">--%>
            <%--<button class="btn btn-success">上传</button>--%>
            <%--？   没有模板文件<a href="downModel.do">点击下载</a>模板--%>
        <%--</form>--%>
    <%--</div>--%>
<%--</div>--%>
<%--<script type="javascript">--%>

<%--</script>--%>
<%--</body>--%>
<%--</html>--%>