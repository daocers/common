<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<!DOCTYPE html>--%>
<%--<html lang="en">--%>
<%--<head>--%>
<%--<meta charset="UTF-8">--%>
<%--<title>菜单</title>--%>

<%--</head>--%>
<%--<body>--%>
<%--<div class="menu-left" style="display: inline-block;">--%>
<style>

    .navbar-inverse {
        border-radius: 0px;
    }

    .menu-left {
        /*min-width: 200px;*/
        /*max-width: 260px;*/

        /*width: 260px;*/
        padding-left: 0px;
        padding-right: 0px;
        margin-top: -20px;
    }

    .menu-left * {
        background-color: #f7f7f7;
        margin: 0;
        padding: 0;
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
    }

    ul.accordion {
        list-style-type: none;
        overflow-y: hidden;;
    }

    /** =======================
     * Contenedor Principal
     ===========================*/
    .accordion {
        border: 1px solid gainsboro;
        /*width: 100%;*/
        /*max-width: 360px;*/
        margin: 0 0px 20px 0px;
        background: ghostwhite;
    }

    .accordion .link {
        cursor: pointer;
        display: block;
        padding: 15px 15px 15px 42px;
        color: darkgray;
        /*background-color: #8ed7ff;*/
        font-size: 15px;
        font-weight: 400;
        border-bottom: 1px solid #CCC;
        position: relative;
        -webkit-transition: all 0.4s ease;
        -o-transition: all 0.4s ease;
        transition: all 0.4s ease;
    }

    .accordion .link:hover {
        color: darkblue;
        border-left: 3px solid dodgerblue;
        background-color: white;
    }

    .accordion li:last-child .link {
        border-bottom: 0;
    }

    .accordion li i {
        position: absolute;
        top: 16px;
        left: 12px;
        font-size: 18px;
        /*color: #595959;*/
        color: cornflowerblue;
        -webkit-transition: all 0.4s ease;
        -o-transition: all 0.4s ease;
        transition: all 0.4s ease;
    }

    .accordion li i.fa-chevron-down {
        right: 12px;
        left: auto;
        font-size: 16px;
    }

    .accordion li.open .link {
        color: darkblue;
        /*background-color: #dbf1ff;*/
        border-left: 3px solid dodgerblue;
    }

    .accordion li.open i {
        color: #b63b4d;
    }

    .accordion li.open i.fa-chevron-down {
        -webkit-transform: rotate(180deg);
        -ms-transform: rotate(180deg);
        -o-transform: rotate(180deg);
        transform: rotate(180deg);
    }

    /**
* Submenu
-----------------------------*/
    .submenu {
        display: none;
        /*background: #dbf1ff;*/
        /*background: #8ed7ff;*/
        font-size: 14px;
    }

    .submenu li {
        /*padding-left: 20px;*/
        border-bottom: 1px solid #f5f1f5;
    }

    .submenu li:last-child {
        border-bottom-color: #ccc;
    }

    .submenu a {
        display: block;
        text-decoration: none;
        color: silver;
        padding: 12px;
        padding-left: 60px;
        -webkit-transition: all 0.25s ease;
        -o-transition: all 0.25s ease;
        transition: all 0.25s ease;
    }

    .submenu a:hover {
        background: white;
        color: grey;
    }

    .submenu a.active {
        background: white;
        border-right: 2px solid powderblue;;
    }

</style>
<input type="hidden" value="${sessionScope.currentRoleId}" id="currentRoleId"/>
<ul id="accordion" class="accordion">
    <c:forEach var="box" items="${boxList}" varStatus="index">
        <li>
            <div class="link">${box.name}<span class=" caret" style="float: right;"></span></div>
            <ul class="submenu">
                <c:forEach var="auth" items="${authInfoList.get(index.index)}">
                    <li><a href="${auth.url}.do">${auth.name}</a> </li>
                </c:forEach>
            </ul>
        </li>
    </c:forEach>



    <%--<li>--%>
        <%--<div class="link">权限管理<span class=" caret" style="float: right;"></span></div>--%>
        <%--<ul class="submenu">--%>
            <%--<li><a href="/authority/list.do">权限列表</a></li>--%>
            <%--<li><a href="/authority/manage.do">权限管理</a></li>--%>
            <%--<li><a href="/authority/init.do">权限初始化</a></li>--%>
        <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<div class="link">机构管理 <span class=" caret" style="float: right;"></span></div>--%>
        <%--<ul class="submenu">--%>
            <%--<li><a href="/branch/list.do">机构列表</a></li>--%>
            <%--<li><a href="/branch/manage.do">机构管理</a></li>--%>
        <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<div class="link"> 部门管理<span class=" caret" style="float: right;"></span></div>--%>
        <%--<ul class="submenu">--%>
            <%--<li><a href="/department/list.do"> 部门列表</a></li>--%>
            <%--<li><a href="/department/edit.do"> 添加部门</a></li>--%>
        <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<div class="link"> 考试管理<span class=" caret" style="float: right;"></span></div>--%>
        <%--<ul class="submenu">--%>
            <%--<li><a href="/exam/index.do">我要考试</a></li>--%>
            <%--<li><a href="/paper/list.do">试卷管理</a></li>--%>
        <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<div class="link">试卷策略管理<span class=" caret" style="float: right;"></span></div>--%>
        <%--<ul class="submenu">--%>
            <%--<li><a href="/paperPolicy/list.do"> 试卷策略管理</a></li>--%>
            <%--<li><a href="/paperPolicy/edit.do"> 新增</a></li>--%>
        <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<div class="link">试题属性管理 <span class=" caret" style="float: right;"></span></div>--%>
        <%--<ul class="submenu">--%>
            <%--<li><a href="/property/list.do">属性列表 </a></li>--%>
            <%--<li><a href="/property/edit.do"> 新增属性</a></li>--%>
        <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<div class="link">题库管理 <span class=" caret" style="float: right;"></span></div>--%>
        <%--<ul class="submenu">--%>
            <%--<li><a href="/questionBank/list.do">题库列表 </a></li>--%>
            <%--<li><a href="/questionBank/edit.do"> 新增题库</a></li>--%>
        <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<div class="link"> 试题管理<span class=" caret" style="float: right;"></span></div>--%>
        <%--<ul class="submenu">--%>
            <%--<li><a href="/question/list.do">试题列表 </a></li>--%>
            <%--<li><a href="/question/edit.do"> 新增试题</a></li>--%>
            <%--<li><a href="/question/batchAdd.do"> 批量添加</a></li>--%>
            <%--&lt;%&ndash;<li><a href="/question/downModel.do"> 模板下载</a></li>&ndash;%&gt;--%>
            <%--<li><a href="/tradeQuestion/edit.do"> 交易试题录制</a></li>--%>
            <%--<li><a href="/typeIn/edit.do"> 新增凭条</a></li>--%>
            <%--<li><a href="/typeIn/list.do"> 凭条列表</a></li>--%>

        <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<div class="link">题型管理 <span class=" caret" style="float: right;"></span></div>--%>
        <%--<ul class="submenu">--%>
            <%--<li><a href="/questionMetaInfo/list.do">题型列表</a></li>--%>
            <%--<li><a href="/questionMetaInfo/edit.do">题型添加 </a></li>--%>
        <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<div class="link">试题策略管理 <span class=" caret" style="float: right;"></span></div>--%>
        <%--<ul class="submenu">--%>
            <%--<li><a href="/questionPolicy/list.do"> 试题策略列表</a></li>--%>
            <%--<li><a href="/questionPolicy/edit.do"> 试题策略添加</a></li>--%>
        <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<div class="link">角色管理 <span class=" caret" style="float: right;"></span></div>--%>
        <%--<ul class="submenu">--%>
            <%--<li><a href="/role/list.do"> 角色列表</a></li>--%>
            <%--<li><a href="/role/edit.do">新增角色 </a></li>--%>
        <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<div class="link">场次管理 <span class=" caret" style="float: right;"></span></div>--%>
        <%--<ul class="submenu">--%>
            <%--<li><a href="/scene/index.do">开场 </a></li>--%>
            <%--<li><a href="/scene/list/join.do">我参加的</a></li>--%>
            <%--<li><a href="/scene/list/my.do">我创建的</a></li>--%>
            <%--<li><a href="/exam/index.do">我要考试</a></li>--%>
        <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<div class="link">岗位管理 <span class=" caret" style="float: right;"></span></div>--%>
        <%--<ul class="submenu">--%>
            <%--<li><a href="/station/list.do"> 岗位列表</a></li>--%>
            <%--<li><a href="/station/edit.do">新增岗位 </a></li>--%>
        <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<div class="link">交易管理 <span class=" caret" style="float: right;"></span></div>--%>
        <%--<ul class="submenu">--%>
            <%--<li><a href="/trade/list.do"> 交易列表</a></li>--%>
            <%--<li><a href="/trade/toRecord.do">录制交易 </a></li>--%>
        <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<div class="link">用户管理 <span class=" caret" style="float: right;"></span></div>--%>
        <%--<ul class="submenu">--%>
            <%--<li><a href="/user/list.do"> 用户列表</a></li>--%>
            <%--<li><a href="/user/edit.do">修改用户 </a></li>--%>
            <%--&lt;%&ndash;<li><a href="/user/resetPassword.do">重置密码 </a></li>&ndash;%&gt;--%>
            <%--<li><a href="/user/toChangePassword.do">修改密码 </a></li>--%>
            <%--<li><a href="/user/toRegister.do">注册 </a></li>--%>
            <%--<li><a href="/user/download.do">下载用户模板 </a></li>--%>
            <%--<li><a href="/user/batchAdd.do">批量添加 </a></li>--%>
        <%--</ul>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<div class="link">角色管理 <span class=" caret" style="float: right;"></span></div>--%>
        <%--<ul class="submenu">--%>
            <%--<li><a href="/role/list.do"> 角色列表</a></li>--%>
            <%--<li><a href="/role/edit.do">新增角色 </a></li>--%>
        <%--</ul>--%>
    <%--</li>--%>

</ul>

<div class="hidden" id="currentUrl">
    <%=session.getAttribute("currentUrl")%>
</div>


<%--以下的script标签不要修改位置，否则可能造成不生效--%>
<script src="/assets/js/menu.js"></script>

<script>
    $(function () {
        console.log("begin")
        var cuttentUrl = $("#currentUrl").text().trim();
        var $curLi = $("li a[href='" + cuttentUrl + "']");
        $curLi.addClass("active");
        $curLi.parents(".accordion > li").addClass("open");
        $curLi.parents("ul.submenu").attr("style", "display: block;");
    })
</script>