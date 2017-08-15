<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>场次信息预览</title>
    <%@ include file="../template/header.jsp" %>
    <style type="text/css">
        /*.preview tbody.table tr td{*/
        /*widtd: 60px;*/
        /*min-widtd: 60px;*/
        /*}*/
        /*.preview tbody.table tr td{*/
        /*widtd: 120px;*/
        /*min-widtd: 120px;*/
        /*}*/
    </style>
</head>
<body>
<div class="container">
    <div class="row nav-path">
        <ol class="breadcrumb">
            <li><a href="/index.do">首页</a></li>
            <li><a href="list.do">场次管理</a></li>
            <li><a href="#" class="active">信息预览</a></li>
        </ol>
    </div>
    <input type="hidden" value="${type}" id="type">
    <div class="row">
        <div class="col-md-8">
            <form action="confirm.do" method="post">
                <input type="hidden" id="id" name="id" value="${scene.id}">
                <table class="table table-responsive table-bordered preview">
                    <thead>基本信息</thead>
                    <tbody>
                    <tr>
                        <td class="col-md-2">名称</td>
                        <td class="col-md-4">${scene.name}</td>
                        <td class="col-md-2">编号</td>
                        <td class="col-md-4">${scene.code}</td>
                    </tr>
                    <tr>
                        <td>开始时间</td>
                        <td><fmt:formatDate value="${scene.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>结束时间</td>
                        <td><fmt:formatDate value="${scene.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                    <tr>
                        <td>顺延时间（分）</td>
                        <td>${scene.delay}</td>
                        <td>考试时长（分）</td>
                        <td>${scene.duration}</td>
                    </tr>
                    <tr>
                        <td>授权码</td>
                        <td>${scene.authCode}</td>
                        <td>是否允许换卷</td>
                        <td>${scene.changePaper == 0 ? "是" : "否"}</td>
                    </tr>
                    </tbody>
                </table>

                <table class="table table-responsive table-bordered preview">
                    <thead>
                    试卷策略信息
                    </thead>
                    <tbody>
                    <tr>
                        <td class="col-md-2">策略名称</td>
                        <td colspan="3">${scene.paperPolicyId}</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">题量</td>
                        <td class="col-md-4">${paperPolicy.count}</td>
                        <td class="col-md-2">总分</td>
                        <td class="col-md-4">${paperPolicy.score}</td>
                    </tr>
                    <tr>
                        <td class="col-md-2">策略明细</td>
                        <td colspan="3">${policyInfo}</td>
                    </tr>
                    </tbody>

                </table>

                <table class="table table-responsive table-bordered preview">
                    <thead>参考人员</thead>
                    <tbody>
                    <tr>
                        <td colspan="4">${scene.joinUser == null ? "尚未选择参考人员" : scene.joinUser}</td>
                    </tr>
                    </tbody>

                </table>

                <div class="row">
                    <button type="button" class="btn btn-warning">取消</button>
                    <button class="btn btn-success" type="button" id="confirm">确定,开场</button>
                </div>
            </form>

        </div>

    </div>
</div>
<script>
    $("#confirm").on("click", function () {
        zeroModal.loading(4);
        $.ajax({
            url:"confirm.do",
            type: "post",
            data: {id: $("#id").val()},
            success: function (data) {
                var res = JSON.parse(data);
                if(res.code == 0){
                    swal("", "开场成功", "success");
                }else {
                    swal("", res.msg, "warning");
                }
                zeroModal.closeAll();
                window.location.href='list.do';
            },
            error: function (data) {
                swal("", "开场失败", "error");
                zeroModal.closeAll();
                window.location.href='list.do';
            }
        })
    })
</script>
</body>
</html>
