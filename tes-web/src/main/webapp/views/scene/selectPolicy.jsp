<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>生成试卷</title>
    <%@ include file="../template/header.jsp" %>
</head>
<body>
<div class="container">
    <div class="row nav-path">
        <ol class="breadcrumb">
            <li><a href="#">首页</a></li>
            <li><a href="#">考试管理</a></li>
            <li><a href="#" class="active">选择试卷策略</a></li>
        </ol>
    </div>
    <div class="progress">
        <div class="progress-bar" style="width: 30%; background-color: #7bc0ff">
            <span style="height: 30px;">1 设置信息</span>
        </div>
        <div class="progress-bar" style="width:35%; background-color: #389fff">
            <span>2 选择用户</span>
        </div>
        <div class="progress-bar progress-bar-striped" style="width: 35%; background-color: #3076ff">
            <span>3 生成试卷</span>
        </div>
    </div>
    <input type="hidden" value="${type}" id="type">
    <div class="row">
        <div class="col-md-8">
            <form class="form-horizontal" method="post" action="savePolicy.do" data-toggle="validator" role="form" id="sceneForm">
                <input id="id" type="hidden" name="id" value="${scene.id}">
                <div class="form-group">
                    <div class="input-group">
                        <label class="control-label input-group-addon" style="margin-right: 15px;">选择题库</label>
                        <select class="form-control" name="bankId" required>
                            <option>--请选择--</option>
                            <option value="0"
                                    <c:if test="scene.bankId == 0">selected</c:if>>不限
                            </option>
                            <c:forEach items="${bankList}" var="bank">
                                <option value="${bank.id}"
                                        <c:if test="${scene.bankId == bank.id}">selected</c:if>>${bank.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <label class="input-group-addon" style="margin-right: 15px;">策略类型</label>
                        <select class="form-control" id="privaryType" required>
                            <option value="0">我的策略</option>
                            <option value="1">公共策略</option>
                        </select>
                        <button class="btn btn-info btn-group-addon" type="button">查询</button>
                        <span style="margin-left: 30px; padding-bottom: 5px; margin-bottom: 5px;">没有合适策略？<a
                                href="/paperPolicy/edit.do">去添加</a> </span>
                    </div>

                </div>

                <div class="" style="min-height: 300px;">
                    <table class="table table-bordered data-table">
                        <thead>
                        <tr>
                            <th></th>
                            <th>名称</th>
                            <th>机构</th>
                            <th>部门</th>
                            <th>岗位</th>
                            <th>试题信息</th>
                            <th>题量</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="policy" items="${pageInfo.data}">
                            <tr>
                                <td><input name="paperPolicyId" type="radio" value="${policy.id}"></td>
                                <td>${policy.name}</td>
                                <td>${policy.branchId}</td>
                                <td>${policy.departmentId}</td>
                                <td>${policy.stationId}</td>
                                <td>${policy.content}</td>
                                <td>${policy.count}</td>
                            </tr>
                        </c:forEach>
                        <%--<tr>--%>
                            <%--<td colspan="7">请选择筛选条件！</td>--%>
                        <%--</tr>--%>
                        </tbody>
                    </table>
                </div>

                <div class="button pull-right">
                    <button class="btn btn-warning btn-cancel" onclick="history.back();">上一步</button>
                    <div class="space">

                    </div>
                    <button class="btn btn-primary btn-commit">下一步</button>
                </div>
                <div class="row">

                </div>
            </form>
        </div>

        <div class="col-md-4">
            <div class="input-group">
                <div class="input-group-addon">已选策略</div>
                <input id="paperPolicyId" value="${scene.paperPolicyId}" type="hidden"/>
                <input id="policy" class="form-control" readonly value="${policyName}" type="text">
            </div>
            <div class="form-group">
                <textarea id="content" class="form-control" rows="10" readonly
                          style="background-color: beige"> </textarea>
            </div>
        </div>

    </div>
</div>
<script>
    function search() {
//        var deptId = $("#departmentId").val();
//        var branchId = $("#branchId").val();
//        var stationId = $("#stationId").val();
        var privaryType = $("#privaryType").val();

//        if (!deptId && !branchId && !stationId) {
//            zeroModal.alert("请选择筛选条件");
//            return false;
//        }
        zeroModal.loading(3);
        $.ajax({
            url: '/paperPolicy/listAll.do',
//            data: {departmentId: deptId, branchId: branchId, stationId: stationId},
            data: {privaryType: privaryType},
            success: function (data) {
                if (data == '-1') {
                    zeroModal.closeAll();
                    zeroModal.error("获取试卷策略信息失败");
                } else {
                    console.log("data: ", data);

                    var paperPolicyList = JSON.parse(data);
                    var html = "";
                    $.each(paperPolicyList, function (index, val) {
                        console.log("index: ", index);
                        html += "<tr>";
                        html += '<td><input type="radio" name="paperPolicyId" value="' + val.id + '"></td>';
                        html += "<td>" + val.name + "</td>"
                        html += "<td>" + val.branchId + "</td>"
                        html += "<td>" + val.departmentId + "</td>"
                        html += "<td>" + val.stationId + "</td>"
                        html += "<td>" + val.content + "</td>"
                        html += "<td>" + val.count + "</td>"
                        html += "</tr>";
                    });
                    if (html == "") {
                        html = "<tr><td colspan='7'>查询无数据！</td></tr>";
                    }
                    console.log("html: ", html);
                    $(".data-table > tbody").html(html);
                    zeroModal.closeAll();
                }
            },
            error: function (data) {
                zeroModal.closeAll();
                zeroModal.error("获取试卷策略信息失败");
            }
        })
    }

    $(function () {
        $("table").on("click", "input[type='radio']", function () {
            if ($(this).is(":checked")) {
                $("#policy").val($(this).parentsUntil("tr").next().text());
                var paperPolicyId = $(this).val();
                $.ajax({
                    url: "/paperPolicy/getPolicyInfo.do",
                    data: {id: paperPolicyId},
                    success: function (data) {
                        $("#content").val(data);
                    },
                    error: function (data) {
                        swal("", "获取已选策略信息失败", "error");
                        return false;
                    }
                });
//                $("#content").val($(this).parentsUntil("tr").parent().find("td:eq(5)").text());
            }
        });

        /**
         * 如果已经选择试卷策略，获取信息
         * @type {*}
         */
        var paperPolicyId = $("#paperPolicyId").val();
        if (paperPolicyId) {
            $.ajax({
                url: "/paperPolicy/getPolicyInfo.do",
                data: {id: paperPolicyId},
                success: function (data) {
                    $("#content").val(data);
                },
                error: function (data) {
                    swal("", "获取已选策略信息失败", "error");
                    return false;
                }
            })
        }

    })

    $(".btn-commit").on("click", function () {
        $.ajax({
            url: 'savePolicy.do',
            type: 'post',
            data: $("#sceneForm").serialize(),
            success: function (data) {
                var res = JSON.parse(data);
                if(res.code == 0){
                    var id = res.data;
                    window.location.href = 'preview.do?id=' + id;
                }else{
                    swal("", res.msg, "error");
                }
            },
            error: function (data) {
                swal("", "保存失败", "error");
            }
        })
        return false;
    })
</script>
</body>
</html>
