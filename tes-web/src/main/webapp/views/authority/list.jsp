<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|权限管理</title>
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
                    <li><a href="#" class="active">权限管理</a></li>
                </ol>
            </div>
            <form class="form form-inline search-form" action="list.do" method="post">
                <input type="hidden" name="showCount" value="${param.showCount}">
                <div class="input-group input-group-sm">
                    <div class="input-group-addon">
                        名称
                    </div>
                    <input type="name" value="${param.name}" class="form-control">
                </div>
                <%--<div class="input-group input-group-sm">--%>
                <%--<div class="input-group-addon">类型</div>--%>
                <%--<select class="form-control" name="type">--%>
                <%--<option value="-1">全部</option>--%>
                <%--<option value="0"--%>
                <%--<c:if test="${param.type == 0}">selected</c:if>>操作权限--%>
                <%--</option>--%>
                <%--<option value="2"--%>
                <%--<c:if test="${param.type == 2}">selected</c:if>>菜单权限--%>
                <%--</option>--%>
                <%--</select>--%>
                <%--</div>--%>
                <div class="input-group input-group-sm">
                    <div class="input-group-addon">Controller</div>
                    <select class="form-control" name="controller" value="${param.controller}">
                        <option></option>
                        <c:forEach var="con" items="${controllerList}">
                            <c:if test="${'' != con}">
                                <option value="${con}"
                                        <c:if test="${param.controller == con}">selected</c:if> >${con}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="input-group  input-group-sm">
                    <button class="btn btn-sm btn-info">提交</button>
                </div>
            </form>

            <button class="btn btn-sm btn-danger" id="initMenu" type="button">初始化菜单</button>

            <div class="table-responsive">
                <table class="table table-bordered editable-table">
                    <thead>
                    <tr>
                        <th><input type="checkbox" class="selectAll"></th>
                        <th class="col-md-2">名称</th>
                        <th>controller</th>
                        <th>action</th>
                        <th>参数</th>
                        <th>描述</th>
                        <%--<th>状态</th>--%>
                        <%--<th>上级权限</th>--%>
                        <%--<th>类型</th>--%>
                        <th>url</th>
                        <th>请求方法</th>
                        <th>菜单容器</th>
                        <th>页面</th>
                        <th>接口</th>
                        <%--<th class="col-md-2">操作</th>--%>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pi.data}" var="authority" varStatus="line">
                        <tr>
                            <td><input type="checkbox" objId="${authority.id}"></td>
                            <td>${authority.name}</td>
                            <td>${authority.controller}</td>
                            <td>${authority.action}</td>
                            <td>${authority.parameter}</td>
                            <td>${authority.description}</td>

                                <%--<td>${authority.status}</td>--%>
                                <%--<td>${authority.superiorId}</td>--%>
                                <%--<td>${authority.type}</td>--%>
                            <td>${authority.url}</td>
                            <td>${authority.acceptMethod}</td>
                            <th>${authority.box ? "是": "否"}</th>
                            <th>${authority.view ? "是": "否"}</th>
                            <th>${authority.api ? "是": "否"}</th>
                                <%--<th>${authority.box}</th>--%>
                                <%--<th>${authority.view}</th>--%>
                                <%--<th>${authority.api}</th>--%>
                                <%--<td>--%>
                                <%--<a href="edit.do?id=${authority.id}&type=detail" class="opr">详情</a>--%>
                                <%--<a href="edit.do?id=${authority.id}" class="opr">修改</a>--%>
                                <%--<a href="javascript:del(${authority.id})" class="opr">删除</a>--%>
                                <%--</td>--%>
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


<script type="javascript">

    $("#initMenu").on("click", function () {
        console.log("click")
        swal({
            type: "question",
            text: "耗时较长，确定要执行初始化菜单操作吗？",
            showCancelButton: true
        }).then(function (isConfirm) {
            if (isConfirm == true) {
                $.ajax({
                    type: 'post',
                    url: 'initMenu.do',
                    success: function () {

                    },
                    error: function () {

                    }

                })
            } else {
                return false;
            }
        })
        return false;

    })

    $(function () {

    })

</script>
</body>
</html>
