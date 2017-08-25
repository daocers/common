<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|编辑试题策略</title>
    <%@ include file="../template/header.jsp" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <style type="text/css">
        .opr-td {
            vertical-align: middle;
            padding-left: 10px;
            padding-top: 10px;
        }
    </style>
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
                    <li><a href="list.do">试题策略管理</a></li>
                    <li><a href="#" class="active">编辑试题策略</a></li>
                </ol>
            </div>


            <form class="form-horizontal col-md-9" method="post" action="save.do" data-toggle="validator" role="form">
                <input id="id" type="hidden" name="id" value="${questionPolicy.id}">

                <div class="form-group">
                    <label class="control-label col-md-2">策略名字</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="name" value="${questionPolicy.name}" required>
                        <span class="help-block with-errors">策略名称，便于记忆</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2">试题策略编号</label>
                    <div class="col-md-10">
                        <input class="form-control" readonly type="text" name="code" value="${questionPolicy.code}"
                               required>
                        <span class="help-block with-errors">便于识别和记忆的一个代号，系统自动生成</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2">题型</label>
                    <div class="col-md-10">
                        <select class="form-control" name="questionMetaInfoId" id="questionMetaInfoId" required>
                            <option value="">请选择</option>
                            <c:forEach items="${questionMetaInfoList}" var="meta">
                                <option value="${meta.id}"
                                        <c:if test="${meta.id == questionPolicy.questionMetaInfoId}">selected</c:if> >
                                        ${meta.name}</option>
                            </c:forEach>
                        </select>
                        <span class="help-block with-errors">选择试题策略</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2">试题策略</label>
                    <div class="col-md-10">
                        <table class="table table-bordered  table-editable">
                            <thead>
                            <tr>
                                <c:if test="${propertyList == null || propertyList.size() == 0}">
                                    <th>属性信息</th>
                                </c:if>
                                <c:if test="${propertyList.size() > 0}">
                                    <c:forEach items="${propertyList}" var="property">
                                        <th>${property.name}</th>
                                    </c:forEach>
                                    <th class="cell-edit">选择数量</th>
                                    <th class="cell-edit">题库存量</th>
                                    <th class="cell-edit">操作</th>
                                </c:if>

                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${propertyList == null || propertyList.size() == 0}">
                                <tr>
                                    <td style="height: 30px;vertical-align: middle;">&nbsp;&nbsp;请选择题型</td>
                                </tr>
                            </c:if>
                            <c:if test="${propertyList.size() > 0}">
                                <c:if test="${contentList != null && contentList.size() > 0}">
                                    <c:forEach items="${contentList}" var="line">
                                        <tr>
                                            <c:forEach items="${propertyList}" var="property" varStatus="idx">
                                                <td>
                                                        <%--${idx.count}--%>
                                                    <select class="form-control form-control-intable">
                                                        <option value="">请选择</option>
                                                        <c:forEach var="item" items="${property.propertyItemList}"
                                                                   varStatus="idx1">
                                                            <option value="${item.id}"
                                                                    <c:if test="${line.get(idx.count - 1) == item.id}"> selected</c:if>
                                                            >${item.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                            </c:forEach>
                                            <td width="80px;"><input class="form-control form-control-intable want"
                                                                     value="${line.get(line.size() - 1) == null ? 0: line.get(line.size() - 1)}"
                                                                     type="number"
                                                                     min="0"></td>
                                            <td width="90px;"><input class="form-control form-control-intable exist"
                                                                     type="number" min="0" readonly></td>
                                            <td class='opr-td'><a href='' class='del-row'>删除</a></td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </c:if>

                            </tbody>
                        </table>
                        <button id="addLine" class="btn btn-info">+</button>
                        <span style="margin-bottom: 0px;">点击再添加一行</span>
                        <input class="form-control" type="hidden" name="content" value="${questionPolicy.content}"
                               required>
                        <span class="help-block with-errors">勾选所需要的属性组合，然后输入数量</span>
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
    $("body").on("click", ".del-row", function () {
        console.log($("tbody").find("tr").length);
        if ($("tbody").find("tr").length == 1) {
            swal("", "只剩一条基础信息，请勿删除！", "info");
            return false;
        }
        $(this).parentsUntil("tr").parent().remove();
        return false;
    });

    var changeMetaInfo = false;

    $("#questionMetaInfoId").on("click", function () {
        if (!$("#id").val()) {
            console.log("true");
            return true;
        }
        if (!changeMetaInfo) {
            swal({
                text: "不建议修改题型，是否继续:",
                type: "warning",
                showCancelButton: true,

            }).then(function (isConfirm) {
                if (isConfirm) {
                    changeMetaInfo = true;
//                $this.trigger("click");
                } else {
                    return false;
                }
            })
        }

    });


    $(function () {
        $("tr").find("select:first").trigger("change");


    })
    /*
     * 根据输入情况，改变表格中信息
     * */
    $(document).on('input propertychange', "input.want", function () {
        var want = parseInt($(this).val());
        var exist = parseInt($(this).parents("td").next().find(".exist").val());
        if (want - exist > 0) {
            $(this).addClass("input-warning");
        } else {
            $(this).removeClass("input-warning");
        }
    })

    /**
     * 根据表格选择属性的变化，动态更新表格中题库中数量 栏位信息
     */
    $(document).on("change", "tbody select", function () {
        var $tar = $(this).parents("tr").find("input:last");
        var propId = new Array();
        $(this).parents("tr").find("select").each(function (idx, e) {
            if ($(e).val()) {
                propId.push($(e).val());
            }
        });
        var metaId = $("[name='questionMetaInfoId']").val();
        if (!metaId) {
            return false;
        }
        $.ajax({
            url: "../question/getCount.do",
            type: "get",
            data: {"propId": JSON.stringify(propId), "metaId": metaId},
            success: function (data) {
                if (data != "-1") {
                    console.log("count:", data);
                    $tar.val(data);
                } else {
                    swal({
                        title: "",
                        text: "获取选中类型的题库数量失败",
                        type: "warning"
                    });
                }
            },
            error: function () {
                swal({
                    title: "",
                    text: "获取选中类型的题库数量失败",
                    type: "error"
                })
            }
        })
    })

    /**
     * 提交之前处理属性信息
     * */
    $(".btn-commit").on("click", function () {
        var len = $(".table-editable thead").find("th").length;
        var data = new Array();

        $(".table-editable").find("tbody tr").each(function (idx, e) {
            var line = new Array();
            $(e).find("td").each(function (idx1, e1) {
                if (idx1 == len - 1) {

                } else if (idx1 == len - 2) {
                    data.push(line);
                } else if (idx1 == len - 3) {
                    line.push($(e1).find("input").val());
                } else {
                    console.log("children: ", $(e).children())
                    line.push($(e1).find("select").val());
                }
            });

        });
        var res = JSON.stringify(data);
        console.log(res, res.length)
        if (res.length < 13) {
            swal("无效数据", "请完成表格中数据的录入", "error");
            return false;
        }
        $("[name='content']").val(res);
        $("form").submit();
        return false;
    });

    var lineHtml;
    $("#addLine").click(function () {
        if ($(".table-editable").find("tbody tr:last td").length == 1) {
            return false;
        }
        lineHtml = $(".table-editable").find("tbody tr:last").html().replace("input-warning", "");
        if (!lineHtml || lineHtml == '') {
            swal("", "操作失误，请刷新页面重试!", "info");
            return false;
        }
        $(".table-editable").find("tbody").append("<tr>" + lineHtml + "</tr>");
        $(".table-editable").find("tbody tr:last select").each(function (idx, e) {
            $(e).val("");
        })
        $(".table-editable").find("tbody tr:last input").each(function (idx, e) {
            $(e).val("0");
        })
        return false;
    })

    /**
     *  处理题型变化造成的表格数据变化
     * */
    var oriMetaId = $("#questionMetaInfoId").val();

    $("#questionMetaInfoId").on("change", function () {
        zeroModal.loading(3);
        var metaId = $(this).val();
        console.log(metaId)
        if (!metaId) {
            console.log("没有选值");
            zeroModal.closeAll();
            return false;
        }
        var valueFlag = false;
        $(".table-editable").find("input").each(function (idx, e) {
            var num = $(e).val();
            console.log("num: ", num);
            if (num != null && num > 0) {
                valueFlag = true;
                return false;
            }
        });

        if (valueFlag) {
            console.log("已经有录入")
            swal({
                title: "确认修改？",
                text: "下文表格中已经录入数据，点击确定将清空而不提交数据！",
                type: "warning",
                showCancelButton: true,
                confirmButtonText: "确定",
                cancelButtonText: "取消",
            }).then(function (isConfirm) {
                console.log("设置按钮不可用")
                if (isConfirm) {
                    oriMetaId = metaId;
                    console.log("设置oriMetaId: ", oriMetaId);
                    refreshTableByMetaInfo(metaId);
                    return false;
                } else {
                    oriMetaId = oriMetaId;
                    console.log("oriMetaId: ", oriMetaId);
                    $("#questionMetaInfoId").val(oriMetaId);
                    return false;
                }
            });
        } else {
            console.log("没有录入")
            refreshTableByMetaInfo(metaId);
        }
    });

    /**
     * 根据题型数据刷新表格
     * @returns {boolean}
     */
    function refreshTableByMetaInfo(metaId) {
        $(".table-editable > input").attr("disabled");
        $(".table-editable > select").attr("disabled");
        $.ajax({
            url: "getProperties.do",
            type: "get",
            data: {id: metaId},
            success: function (data) {
                if (data == "-1") {
                    swal(
                        "请求失败",
                        "服务异常，请联系管理员",
                        "error"
                    );
                    zeroModal.closeAll();
                } else {
                    var builder = "";
                    var json = eval(data);
                    console.log("json: ", json)

                    builder += "<thead><tr>";
                    $.each(json, function (idx, e) {
                        builder += ("<th>" + e.name + "</th>");
                    });
                    builder += "<th width='80px'>数量</th>"
                    builder += "<th width='90px'>现有数量</th>"
                    builder += "<th width='90px'>操作</th>"
                    builder += "</tr></thead>"

                    builder += "<tbody><tr>"
                    var tmp = "";
                    $.each(json, function (idx, e) {
                        tmp = "<td><select class='form-control form-control-intable' name='" + e.id + "'> ";
                        builder += tmp;
                        builder += "<option value=''>请选择</option>"
                        $.each(e.propertyItemList, function (idx1, e1) {
                            tmp = "<option value='" + e1.id + "'>" + e1.name + "</option>";
                            builder += tmp;
                        });
                        tmp = "</select></td>"
                    });
                    builder += tmp;
                    builder += '<td width="80px;"><input class="form-control form-control-intable want" type="number" min="0" value="0"></td>';
                    builder += '<td width="90px;"><input class="form-control form-control-intable exist" readonly type="number" value="0"></td>';
                    builder += "<td class='opr-td'><a href='' class='del-row'>删除</a></td>";

                    builder += "</tr></tbody>";
                    lineHtml = builder.substring(builder.indexOf("<tbody>") + 7, builder.length - 8);
                    $(".table-editable").html(builder);
                    zeroModal.closeAll();
                }
            },
            error: function () {
                swal(
                    "请求失败",
                    "服务异常，请联系管理员",
                    "error"
                );
                zeroModal.closeAll();
            }
        });

        $(".table-editable > input").removeAttr("disabled");
        $(".table-editable > select").removeAttr("disabled");
    }
</script>
</body>
</html>
