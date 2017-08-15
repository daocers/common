<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../template/header.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <title>品类编辑</title>
    <style>
        .branchItem {
            display: inline-block;
            width: auto;
        }

    </style>
</head>
<body>
<div class="container">
    <div class="row nav-path">
        <ol class="breadcrumb">
            <li><a href="/index.do">首页</a></li>
            <li><a href="list.do">机构管理</a></li>
            <li><a href="#" class="active">修改机构</a></li>
        </ol>
    </div>
    <input type="hidden" value="${param.type}" id="type">
    <div class="row">
        <div class="col-md-8">
            <form class="form-horizontal" method="post" action="save.do" data-toggle="validator" role="form">
                <input id="id" type="hidden" name="id" value="${branch.id}">
                <div class="form-group">
                    <label class="control-label col-md-2">机构名称</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="name" value="${branch.name}" required>
                        <span class="help-block">分行、支行、网点或者分理处的具体名称</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">机构编码</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="code" value="${branch.code}" required>
                        <span class="help-block with-errors">如果已经赋值，请谨慎修改</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">机构地址</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="address" value="${branch.address}"
                               maxlength="100">
                        <span class="help-block with-errors"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">机构级别</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="level" value="${branch.level}" required>
                        <span class="help-block"></span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2">上级机构</label>
                    <div class="col-md-4">
                        <input type="hidden" name="superiorId">
                        <input type="text" class="form-control" name="superiorName" value="${branch.superiorName}"
                               readonly>
                        <span class="help-block"></span>
                    </div>
                    <button class="btn btn-warning" id="changeSuper">修改</button>
                </div>
                <div class="form-group" style="margin-top: 0px;">
                    <label class="col-md-2"></label>
                    <div class="hidden" id="branchBox" style="margin-left: 50px;">
                        <select class="form-control level branchItem">
                            <option value="">请选择</option>
                            <c:forEach var="branch" items="${branchList}">
                                <option value="${branch.id}" level="${branch.level}">${branch.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>


                <div class="form-group">
                    <label class="control-label col-md-2">创建时间</label>
                    <div class="col-md-10">
                        <input class="form-control time" type="text" name="createTime"
                               value="<fmt:formatDate value="${branch.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               required>
                        <span class="help-block">机构信息入库时间</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2">更新时间</label>
                    <div class="col-md-10">
                        <input class="form-control time" type="text" name="updateTime"
                               value="<fmt:formatDate value="${branch.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               required disabled>
                        <span class="help-block">机构信息最后更新时间</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">状态</label>
                    <div class="col-md-10">
                        <select class="form-control" name="status">
                            <option value="">请选择</option>
                            <option value="0"
                                    <c:if test="${branch.status == 0}">selected</c:if> >正常
                            </option>
                            <option value="1"
                                    <c:if test="${branch.status == 1}">selected</c:if> >禁用
                            </option>
                        </select>
                        <%--<input class="form-control" type="text" name="status" value="${branch.status}" required>--%>
                        <span class="help-block"></span>
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
<script type="text/javascript">
    $(function () {
        $("#changeSuper").on("click", function () {
            $("#branchBox").removeClass("hidden");
            return false;
        })
        $(document).on("change", "#branchBox > select", function () {
            var $this = $(this);
            console.log("goods");
            var id = $(this).val();
            var level = $(this).attr("level");
            console.log("level: ", level);
            console.log("id: ", id);
            if (id != undefined && id != '') {
                $("[name='superiorId']").val(id);
                $("[name='superiorName']").val($this.find('option:selected').text());
                $("[name='level']").val(parseInt($this.find('option:selected').attr("level")) + 1);
                zeroModal.loading(3);
                $.ajax({
                    url: 'listAll.do',
                    data: {superiorId: id},
                    success: function (data) {
                        zeroModal.closeAll();
                        var branchList = JSON.parse(data);
                        if (branchList.length > 0) {
                            var html = '<select class="form-control level branchItem">' +
                                '<option value="">请选择</option>';
                            $.each(branchList, function (i, val) {
                                html += '<option level="' + val.level + '" value="' + val.id + '">' + val.name + "</option>";
                            })
                            html += '</select>';
                            $this.nextAll().remove();
                            $this.after(html);
                        }

                    }
                })
            }
        });
    })
</script>
</body>
</html>
