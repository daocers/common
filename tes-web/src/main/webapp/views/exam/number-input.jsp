<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>答题</title>
    <%@ include file="../template/header.jsp" %>
    <style>
        .inline {
            display: inline;
            margin-right: 30px;
        }

        .form-group > .radio.inline > label {
            padding-left: 15px;
        }

        .list-group {
            border: none;
        }

        .list-group-item {
            border: none;
        }

        .current{
            border: 2px solid yellowgreen;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row nav-path">
        <ol class="breadcrumb">
            <li><a href="#">首页</a></li>
            <li><a href="#">考试管理</a></li>
            <li><a href="#" class="active">答题</a></li>
        </ol>
    </div>
    <input type="hidden" value="${param.type}" id="type">
    <input type="hidden" value="${paper.id}" id="paperId">
    <input type="hidden" value="${paper.questionIds}" id="questionIds">
    <input type="hidden" value="${timeLeft}" id="timeLeft">
    <div class="row" style="margin-bottom: 15px;">
        <div class="form form-inline pull-left">
            <div class="input-group">
                <div class="input-group-addon">
                    考试名称：
                </div>
                <input class="form-control col-md-1" readonly type="text" value="${scene.name}">
            </div>
            <div class="input-group">
                <div class="input-group-addon">
                    更换另外一套试卷
                </div>
                <div class="input-group-btn">
                    <button class="btn btn-error" id="changePaper">确定</button>
                </div>
            </div>
            <div class="input-group" style="border: 1px solid #6bff22">
                <div class="input-group-addon">
                    当前试题：
                </div>
                <select class="form-control" id="current">

                </select>
            </div>
        </div>

        <div class="form form-inline pull-right">
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-addon">剩余时间：</div>
                    <input id="timer" type="text" class="timer form-control" readonly
                           style="width: 120px; color: #2b59ff;">
                    <button class="btn btn-primary btn-group-addon" id="commitPaper">提交试卷</button>
                </div>
            </div>

        </div>
    </div>
    <div class="row">
        <div class="col-md-8">
            <div class="form form-horizontal">
                <div class="form-group">
                    <div class="col-md-12">
                        <div class="panel panel-default" style="margin-left: -15px; margin-right: -25px;">
                            <div class="panel-heading">
                                <h3 class="panel-title" id="title" style="min-height: 25px;"></h3>
                            </div>
                            <div class="panel-body" style="min-height: 300px;">
                                <ul class="list-group" id="content">
                                    <button class="btn btn-warning">点击开始</button>
                                    <div class="input-group">
                                        <div class="input-group-addon">剩余时间：</div>
                                        <input id="num-used" type="text" class="timer form-control" readonly
                                               style="width: 120px; color: #2b59ff;" value="5m">
                                        <button class="btn btn-primary btn-group-addon" id="commitOpr">提交凭条</button>
                                    </div>
                                </ul>
                            </div>

                            <div class="panel-footer" id="answer" style="padding-left: 30px; min-height: 50px;">
                            </div>
                            <div class="panel-footer" id="opr-box" style="padding-left: 30px; padding-right: 30px;">
                                <div class="row">
                                    <button class="btn btn-default pull-left" id="prevBtn">上一题</button>
                                    <div class="space" style="display: inline-block; margin-right: 200px;">

                                    </div>
                                    <button class="btn btn-primary pull-right" id="nextBtn">下一题</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class=" pull-right">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">作答信息</h3>
                </div>
                <div style="max-height: 400px; overflow-y: auto;">
                    <table class="table table-bordered" id="myAnswer">
                        <thead>
                        <tr>
                            <th width="100px">题号</th>
                            <th width="90px">我的答案</th>
                            <th width="90px" class="hide">最佳答案</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <div class="panel-footer">
                    @点击题目，跳转到对应的作答页面
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    /*初始化计时器*/
    function initTimer() {
        var left = $("#num-used").val();
//        left = left + "s";
        console.log("leftTime: ", left)
        $("#num-used").timer({
            duration: left,
            countdown: false,
            format: '%H:%M:%S',
            callback: function () {
                swal("", "时间到", "info");
            }
        });

    }

</script>

</html>
