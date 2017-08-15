<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tes" uri="/WEB-INF/tes.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|用户管理</title>
    <%@ include file="../template/header.jsp" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <style type="text/css">
        .inline {
            display: inline;
            margin-right: 30px;
        }

        .form-group > .radio.inline > label {
            padding-left: 15px;
        }
    </style>
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
                    <li><a href="#" class="active">用户管理</a></li>
                </ol>
            </div>

            <div class="">
                <form class="form-inline" action="list.do">
                    <input type="hidden" name="showCount" value="${param.showCount}">
                    <div class="input-group input-group-sm">
                        <div class="input-group-addon">用户名</div>
                        <input type="text" class="form-control" name="username" value="${param.username}">
                    </div>
                    <div class="input-group input-group-sm">
                        <div class="input-group-addon">姓名</div>
                        <input type="text" class="form-control" name="name" value="${param.name}">
                    </div>
                    <div class="input-group-sm">
                        <button class="btn btn-info btn-sm">查询</button>
                    </div>
                </form>
                <div class=" pre-table">

                    <div class="pull-left form-inline">
                        <button class="btn btn-info" id="import">导入数据</button>
                        <button class="btn btn-primary" id="download">下载模板</button>
                        <div style="display: inline-block; width: 80px;"></div>
                        <a href="edit.do" class="btn btn-success">添加用户</a>
                    </div>

                </div>


                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th><input type="checkbox" class="selectAll"></th>
                        <th>用户名</th>
                        <th>姓名</th>
                        <th>角色</th>
                        <th>机构</th>
                        <th>部门</th>
                        <th>岗位</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pi.data}" var="user" varStatus="line">
                        <tr rowid="${user.id}">
                            <td><input type="checkbox" objId="${user.id}"></td>
                            <td>${user.username}</td>
                            <td>${user.name}</td>
                            <td>${checkedRole.get(line.index)}</td>
                            <td>${branchMap.get(user.branchId)}</td>
                            <td>${departmentMap.get(user.departmentId)}</td>
                            <td>${stationMap.get(user.stationId)}</td>
                            <td>${user.status == 0 ? "启用" : "禁用"}</td>
                            <td>
                                <a href="edit.do?id=${user.id}&type=detail" class="opr btn btn-info btn-sm">详情</a>
                                <a href="edit.do?id=${user.id}" class="opr">修改</a>
                                <tes:hasRole name="admin">
                                </tes:hasRole>
                                <tes:hasPermission name="user_delete">
                                    <a href="javascript:del(${user.id})" class="opr">删除</a>
                                </tes:hasPermission>
                                <a href="javascript:resetPassword(${user.id})" class="opr">重置密码</a>

                                <a href="javascript:setRole(${user.id})" class="opr">配置角色</a>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>

                <div class="row"></div>

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

            <div class="modal fade container" id="modal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span
                                    aria-hidden="true">&times;</span><span
                                    class="sr-only">Close</span></button>
                            <h4 class="modal-title">选择文件</h4>
                        </div>
                        <div class="modal-body">
                            <div class="row" style="margin-left: 10px;">
                                <form id="upload" class="form-horizontal" method="post" action="batchAdd.do"
                                      enctype="multipart/form-data">
                                    <input type="file" name="file" class="jfilestyle" data-input="true"
                                           data-buttonText="导入文件">
                                    <button class="btn btn-success" onclick="javascript:upload()">上传</button>
                                    ？ 没有模板文件<a href="javascript:downloadModel()">点击下载</a>模板
                                </form>
                            </div>
                        </div>
                        <%--<div class="modal-footer">--%>
                        <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                        <%--<button type="button" class="btn btn-primary">Save changes</button>--%>
                        <%--</div>--%>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>

            <div class="modal fade container" id="setRole">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span
                                    aria-hidden="true">&times;</span><span
                                    class="sr-only">Close</span></button>
                            <h4 class="modal-title">用户角色管理</h4>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" id="userId">
                            <div class="row" style="margin-left: 0px; margin-bottom: 30px;" id="role-box">
                                <c:forEach var="role" items="${roleList}">
                                    <label class="checkbox inline">
                                        <input type="checkbox" name="roleId" id="${role.id}"
                                               value="${role.id}">&nbsp;&nbsp;${role.name}
                                    </label>
                                </c:forEach>
                            </div>
                            <button class="btn btn-info" type="button" id="confirmRole">确定</button>
                            <div style="display: inline-block; margin-left: 50px;">

                            </div>
                            <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>
            <script>
                var roleIdNameMap = eval(${roleInfoMap});
                $(function () {
                    $("#import").on("click", function () {
                        $("#modal").modal('show');
                    });

                    $("#download").on("click", function () {
                        window.location.href = "download.do";
                    });

                    /**
                     * *勾选题型之后的事件
                     */
                    $('[name="roleId"]').on("ifUnchecked ifChecked", function (event) {
                        console.log(event)
                        var id = $(this).val();
                        var type = event.type;
                        if (type == "ifChecked") {
                        } else {
                        }

                    });


                    $("#role-box [type='checkbox']").iCheck({
                        checkboxClass: 'icheckbox_square-blue',
                        radioClass: 'iradio_square-blue',
                        increaseArea: '20%' // optional
                    });
                    
                    $("#confirmRole").on("click", function () {
                        var roles = "";
                        var arr = new Array();
                        $("[name='roleId']:checked").each(function () {
                            var roleId = $(this).val();
                            arr.push(roleId);
                            roles += roleIdNameMap[roleId] + " ";
                        });
                        var userId = $("#userId").val();
                        $.ajax({
                            url: "setRole.do",
                            data: {id: userId, roleId: JSON.stringify(arr)},
                            type: "post",
                            success: function (data) {
                                var res = JSON.parse(data);
                                if(res.code == 0){
                                    $("table tr[rowid='" + userId + "']").find("td:eq(3)").html(roles);
                                    $("#setRole").modal("hide");

                                }else{
                                    swal("", "设置角色失败", "error");
                                }
                            },
                            error: function (data){
                                swal("", "请求失败", "error");
                            }
                        })
                        return false;
                    })
                })

                function setRole(id) {
                    $.ajax({
                        url: "getRole.do",
                        data: {id: id},
                        type: "get",
                        success: function (data) {
                            var res = JSON.parse(data);
                            if(res.code == 0){
                                var hadRoles = res.has;
                                $.each(hadRoles, function (idx, item) {
                                    $("#" + item).iCheck("check");
                                });
                                $("#userId").val(id);
                                $("#setRole").modal("show");
                            }else{
                                swal("", res.err, "error");
                            }
                        },
                        error: function (data) {
                            swal("", "请求失败", "error");
                        }
                    });
                    return false;
                }
                
                

                function resetPassword(id) {
                    swal({
                        title: "确定要重置密码？",
                        text: "密码将会重置为‘888888’，重置完毕请立即更新密码",
                        type: "warning",
                        showCancelButton: true,
                    }).then(function (isConfirm) {
                        if (isConfirm === true) {
                            $.ajax({
                                type: 'post',
                                data: {userId: id},
                                url: 'resetPassword.do',
                                success: function (data) {
                                    var res = JSON.parse(data);
                                    if (res.code == 0) {
                                        swal("", "重置密码成功", "success");
                                    } else {
                                        swal("", "重置密码失败", "error");
                                    }
                                    return false;
                                },
                                error: function () {
                                    swal("", "请求失败", "error");
                                    return false;
                                }
                            })
                        }
                        return false;
                    });
                    return false;
                }

                function downloadModel() {
                    window.location.href = 'download.do';
                    $("#modal").modal("hide");
                }

                function upload() {
                    zeroModal.loading(3);
                    $("#upload").submit();
                }






            </script>
        </div>
    </div>
</div>

<script></script>
</body>
</html>
