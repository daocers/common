<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<!DOCTYPE html>--%>
<%--<html lang="en">--%>
<%--<head>--%>
<%--<meta charset="UTF-8">--%>
<%--<title>菜单</title>--%>
<%--<style>--%>
<%--.navbar-inverse{--%>
<%--border-radius: 0px;--%>
<%--}--%>

<%--</style>--%>
<style>
    .dropdown-menu li {
        height: 35px;
        line-height: 35px;
    }

    .dropdown-menu li a {
        line-height: 35px;
    }
</style>
<%--</head>--%>
<%--<body>--%>
<%--<div class="menu-top">--%>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">布谷培训</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="#">我的布谷</a></li>
                <c:forEach items="${sessionScope.roleList}" var="role">
                    <li><a roleid="${role.id}" class="role" href="javascript:changeRole(${role.id});">${role.name}</a>
                    </li>
                </c:forEach>
                <%--<li><a href="/role/index/student.do">我是考生</a></li>--%>
                <%--<li><a href="/role/index/teacher.do">我是教师</a></li>--%>
                <%--<li><a href="/role/index/admin.do">我是管理员</a></li>--%>
                <!--<li><a href="#">培训学习</a></li>-->
                <!--<li><a href="#">统计分析</a></li>-->
                <!--<li><a href="#">考试管理</a></li>-->


            </ul>

            <ul class="nav navbar-nav navbar-right">
                <form class="navbar-form navbar-left form-inline" role="search">
                    <div class="input-group">
                        <input class="form-control">
                        <div class="input-group-btn">
                            <button class="btn btn-default">搜索</button>
                        </div>
                    </div>
                </form>
                <%--<li class="head" style="padding: 0px;">--%>
                <%--<a class="navbar-brand" href="#" style="padding: 9px">--%>
                <%--<img src="/assets/img/head-off.png">--%>
                <%--</a>--%>
                <%--</li>--%>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false" style="height: 50px; padding: 9px;"> <span class="person-center"></span>
                        <img src="/assets/img/head-off.png">&nbsp;&nbsp;${sessionScope.username}&nbsp; <span
                                class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">账户设置</a></li>
                        <li><a href="#">我的布谷</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="/signOut.do">安全退出</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">One more separated link</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<script>
    $(function () {
        var currentRoleId = $("#currentRoleId").val();
        $("a.role[roleid='" + currentRoleId + "']").parent().addClass("active");
    })

    function changeRole(id) {
        var currentRoleId = $("#currentRoleId").val();
        if (id == currentRoleId) {
            return false;
        } else {
            window.location.href = "/selectRole.do?roleId=" + id;
        }
    }
</script>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>