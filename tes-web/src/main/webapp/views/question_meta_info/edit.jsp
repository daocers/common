<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|编辑题型</title>
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
                    <li><a href="list.do">题型管理</a></li>
                    <li><a href="#" class="active">编辑题型</a></li>
                </ol>
            </div>

            <form class="form-horizontal col-md-8" method="post" action="save.do" data-toggle="validator" role="form">
                <input id="id" type="hidden" name="id" value="${questionmetainfo.id}">
                <div class="form-group">
                    <label class="control-label col-md-2">题型名称</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="name" value="${questionmetainfo.name}" required>
                        <span class="help-block with-errors"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">题型编码</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="code" value="${questionmetainfo.code}" required>
                        <span class="help-block with-errors">题型编码，建议使用英文，拼音或者首字母简称等</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">题型描述</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="description"
                               value="${questionmetainfo.description}" required>
                        <span class="help-block with-errors">简要描述该题型，作答方式，得分规则等</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2">状态</label>
                    <div class="col-md-10">
                        <select name="status" class="form-control" required>
                            <option value="0">启用</option>
                            <option value="1">禁用</option>
                        </select>
                        <span class="help-block with-errors">设置禁用/启用</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2">选择属性</label>
                    <div class="col-md-10">
                        <div class="prop-container">
                            <c:forEach items="${propertyList}" var="property">
                                <ul class="list-group col-md-3">
                                    <li class="list-group-item list-group-item-success">
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" name="propertyId" value="${property.id}"
                                                <c:if test="${propertyIdList.contains( property.id)}"> checked</c:if> >
                                                    ${property.name}
                                            </label>
                                        </div>
                                    </li>
                                    <c:forEach items="${property.propertyItemList}" var="item">
                                        <li class="list-group-item list-group-item-info">${item.code}
                                            - ${item.name}</li>
                                    </c:forEach>
                                </ul>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="button pull-right">
                    <button class="btn btn-primary btn-commit">保存</button>
                    <div class="space">

                    </div>
                    <button class="btn btn-warning btn-cancel">取消</button>
                </div>
            </form>


        </div>
    </div>
</div>

<script>
    $(function () {
        $(".btn-commit").bind("click", function () {
            var checked = $("input[name='propertyId']:checked");
            if (checked.length == 0) {
                swal("", "没有选择该题型的属性，是否继续？", "warning").then(function (isConfirmed) {
                    if (isConfirmed) {
                        $("form").submit();
                        $(this).attr("disabled", true);
                    }
                });
            } else {
                $(this).attr("disabled", true);
                $("form").submit();
            }
            return false;
        })

    })
</script>
</body>
</html>
