<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../template/header.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <title>品类编辑</title>
    <style>
        .inline {
            margin-right: 50px;
            display: inline-block;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row nav-path">
        <ol class="breadcrumb">
            <li><a href="#">首页</a></li>
            <li><a href="#">品类管理</a></li>
            <li><a href="#" class="active">品类编辑</a></li>
        </ol>
    </div>
    <div class="row" style="border: 1px dashed lightgreen;">
        <div class="col-md-4">
            当前考试：<span class="label label-info">第三次模拟考试</span>
        </div>
        <%--<div class="col-md-3">--%>
        <%--当前试题<select class="form-control" style="display: inline; width: auto"><option>单选第一题</option></select>--%>
        <%--</div>--%>
        <div class="col-md-3 pull-right">
            剩余做答时间: <label id="timer" style="color: red;"></label>
        </div>
        <%--<label>考试名称</label><input style="display: inline-block;" value="三综合考试第一场" disabled>--%>
        <%--<div class="pull-right" style="font-size: 15px;">--%>
        <%--剩余做答时间: <label id="timer"></label>--%>
        <%--</div>--%>

    </div>
    <input type="hidden" value="${type}" id="type">
    <div class="row">
        <div class="col-md-9">
            <form class="form-horizontal" method="post" action="save.do" data-toggle="validator" role="form">
                <input id="id" type="hidden" name="id" value="${question.id}">

                <div class="form-group">
                    <label class="control-label col-md-2">题目</label>
                    <div class="col-md-10">
                        <p class="form-control">${question.title}</p>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">题干</label>
                    <div class="col-md-10">
                        <textarea class="form-control">${question.content}</textarea>
                        <%--<input class="form-control" type="" name="content" value="${question.content}" required>--%>
                        <%--<span class="help-block with-errors">试题选项等题干信息</span>--%>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">附加信息</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="extraInfo" value="${question.extraInfo}" required>
                        <span class="help-block with-errors">本试题附加的一些其他信息</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2">答案</label>
                    <div class="col-md-10">
                        <label class="checkbox inline">
                            <input type="checkbox" name="answer" value="A"> A
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" name="answer" value="B"> B
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" name="answer" value="C"> C
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" name="answer" value="D"> D
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" name="answer" value="E"> E
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2">答案</label>
                    <div class="col-md-10">
                        <label class="radio inline">
                            <input type="radio" name="answer" value="A"> A
                        </label>
                        <label class="radio inline">
                            <input type="radio" name="answer" value="B"> B
                        </label>
                        <label class="radio inline">
                            <input type="radio" name="answer" value="C"> C
                        </label>
                        <label class="radio inline">
                            <input type="radio" name="answer" value="D"> D
                        </label>
                        <label class="radio inline">
                            <input type="radio" name="answer" value="E"> E
                        </label>
                    </div>
                </div>

                <div class="button ">
                    <button class="btn btn-primary btn-commit pull-left">上一题</button>
                    <div class="space">

                    </div>
                    <button class="btn btn-warning btn-cancel pull-right">下一题</button>
                </div>
            </form>
        </div>
        <div class="col-md-3">
            <label class="control-label">答题信息</label>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>题目序号</th>
                    <th>做答信息</th>
                    <th>正确答案</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>1</td>
                    <td>A</td>
                    <td>A</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>B</td>
                    <td>A</td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>C</td>
                    <td>A</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script>
    //    $("#timer").syotimer({
    //        dayVisible: false,
    //        year: 2016,
    //        month: 8,
    //        day: 8,
    //        hour: 1,
    //        minute: 25,
    //        second: 0,
    //        afterDeadline: function(timerBlock){
    //            timerBlock.bodyBlock.html('<p style="font-size: 1.2em;">The countdown is finished!</p>');
    //            alert("时间到");
    //        }
    //    })

    //    $("#timer").countdown("2017/08/07 09:20:30", function(event) {
    //        console.log(event)
    //        var type = event.type;
    ////
    ////        if(type == 'update'){
    //            $(this).html(event.strftime('%H小时 %M分钟 %S秒'));
    ////        }
    //        if(type == "stoped"){
    //            alert("暂停了")
    //        }
    //        if(type == "finish"){
    //            alert("时间到")
    //        }
    //    });
    $(function () {
        $("#timer").countdown("2017/01/13 13:56:30")
            .on("update.countdown", function (event) {
                $(this).html(event.strftime('%H小时 %M分钟 %S秒'));

                console.log($("#timer").text());
            }).on("finish.countdown", function (event) {
                $(this).html(event.strftime('%H小时 %M分钟 %S秒'));

                console.log("时间到");
        });
    })


    //    $("[type='checkbox'], [type='radio']").iCheck({
    //        checkboxClass: 'icheckbox_square-blue',
    //        radioClass: 'iradio_square-blue',
    //        increaseArea: '20%' // optional
    //    });
</script>
</body>
</html>
