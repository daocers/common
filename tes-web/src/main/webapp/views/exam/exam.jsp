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
    <%--<div class="hidden" id="metaInfo">--%>
    <%--${metaInfo}--%>
    <%--</div>--%>
    <input type="hidden" value='${metaInfo}' id="metaInfo">
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
                    <button class="btn btn-warning" id="changePaper">确定</button>
                </div>
            </div>
            <div class="input-group" style="border: 1px solid #6bff22">
                <div class="input-group-addon">
                    当前做答：
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
                           style="width: 100px; color: #2b59ff;">
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
                                </ul>
                            </div>

                            <div class="panel-footer" id="answer" style="padding-left: 30px; min-height: 50px;">
                                <div class="form-group answer-box" id="single-box" style="display: none">
                                    <label class="radio inline">
                                        <input type="radio" name="answer" value="A">&nbsp;&nbsp;A
                                    </label>
                                    <label class="radio inline">
                                        <input type="radio" name="answer" value="B">&nbsp;&nbsp;B
                                    </label>
                                    <label class="radio inline">
                                        <input type="radio" name="answer" value="C">&nbsp;&nbsp;C
                                    </label>
                                    <label class="radio inline">
                                        <input type="radio" name="answer" value="D">&nbsp;&nbsp;D
                                    </label>
                                    <label class="radio inline">
                                        <input type="radio" name="answer" value="E">&nbsp;&nbsp;E
                                    </label>
                                    <label class="radio inline">
                                        <input type="radio" name="answer" value="F">&nbsp;&nbsp;F
                                    </label>
                                </div>
                                <div class="form-group answer-box" id="multi-box" style="display: none">
                                    <label class="checkbox inline">
                                        <input type="checkbox" name="answer" value="A">&nbsp;&nbsp;A
                                    </label>
                                    <label class="checkbox inline">
                                        <input type="checkbox" name="answer" value="B">&nbsp;&nbsp;B
                                    </label>
                                    <label class="checkbox inline">
                                        <input type="checkbox" name="answer" value="C">&nbsp;&nbsp;C
                                    </label>
                                    <label class="checkbox inline">
                                        <input type="checkbox" name="answer" value="D">&nbsp;&nbsp;D
                                    </label>
                                    <label class="checkbox inline">
                                        <input type="checkbox" name="answer" value="E">&nbsp;&nbsp;E
                                    </label>
                                    <label class="checkbox inline">
                                        <input type="checkbox" name="answer" value="F">&nbsp;&nbsp;F
                                    </label>
                                    <label class="checkbox inline">
                                        <input type="checkbox" name="answer" value="G">&nbsp;&nbsp;G
                                    </label>
                                </div>
                                <div class="form-group answer-box" id="judge-box" style="display: none">
                                    <label class="radio inline">
                                        <input type="radio" name="answer" value="T">&nbsp;&nbsp;正确
                                    </label>
                                    <label class="radio inline">
                                        <input type="radio" name="answer" value="F">&nbsp;&nbsp;错误
                                    </label>
                                </div>
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
<script>
    var answerArr = "";
    var metaInfo = eval(${metaInfo});
    console.log("metaInfo: ", metaInfo)
    var index = 0;
    var questionIds = new Array();


    $(function () {
        questionIds = JSON.parse($("#questionIds").val());
        //初始化答题数据
        var quebody = "";
        var selectBox = "";
        $.each(questionIds, function (idx, obj) {
            quebody += "<tr queid='" + obj + "'><td><a href='javascript:jumpTo(" + obj + ")'>" + "第" + (idx + 1) + "题" + "</a> </td><td></td><td class='hide'></td></tr>"
            selectBox += "<option value='" + idx + "'>第" + (idx + 1) + "题</option>"
        });
        $("#myAnswer tbody").html(quebody);
        $("#current").html(selectBox);

        /**
         * 初始化题目
         */
        getQuestionInfo();

//        所有数据初始化完成后，初始化定时器
        var info = $("#timer").val();
        $("#timer").timer({
            duration: '65s',
            countdown: true,
            format: '%H:%M:%S',
            callback: function () {
                swal("", "时间到", "info");
            }
        });

        /**
         * 选中执行事件
         */
        $("[name='answer']").on("ifChecked", function () {
            var answer = $(this).val();
        })

        $("[name='answer']").on("ifUnchecked", function () {
            var answer = $(this).val();
        })

    });

    /**
     * 获取作答信息
     */
    function getAnswer() {
        var res = "";
        var checkedInput = $("[name='answer']:checked");
        $.each(checkedInput, function (idx, obj) {
            res += $(obj).val();
        });
        return res;
    }

    /**
     * 前一题
     */
    $("#prevBtn").on("click", function () {
        if (index == 0) {
            swal("", "已经是第一题了", "info");
        }
        zeroModal.loading(4);
        var flag = getQuestionInfo();
        if(flag){
            index--;
        }
        zeroModal.closeAll();
    });

    /**
     *  后一题
     * */
    $("#nextBtn").on("click", function () {
        if (index == questionIds.length - 1) {
            swal("", "已经是最后一题了,请提交试卷。", "info");
            return false;
        }
        zeroModal.loading(4);
        var flag = getQuestionInfo();
        console.log("flag: ", flag);
        if(flag){
            index++;
        }
        zeroModal.closeAll();
    });
    /**
     * 获取试题信息
     * 并提交当前答案
     */
    function getQuestionInfo() {
        console.log("index: ", index);
        if (index < 0 || index > questionIds.length - 1) {
            swal("", "数据异常，请联系管理员", "error");
            return false;
        }
        var param = {};
        param.paperId = $("#paperId").val();
        param.current = questionIds[index];
        param.currentIndex = index;
        if(index == 0){
            param.questionId = questionIds[index + 1];
            param.time = -1;
            param.answer = -1;
        }else if(index == questionIds.length - 1){
            var time= getSeconds();
            var ans = getAnswer();
            param.time = time;
            param.answer = ans;
            param.questionId = -1;
        }else{
            var time= getSeconds();
            var ans = getAnswer();
            param.time = time;
            param.answer = ans;
            param.questionId = questionIds[index + 1];
        }

        console.log("answer: ", param.answer);
        if(param.answer != -1){
            $("table tbody tr:eq(" + index + ")").find("td:eq(1)").val(param.answer);
        }


        var reqFlag = true;
        $.ajax({
            url: "getQuestion.do",
            type: "post",
            data: param,
            success: function (data) {
                console.log(data);
                var res = JSON.parse(data);
                if (res.code == 0) {
                    var question = res.data;
                    var title = question.title;
                    var content = JSON.parse(question.content);
                    var remark = question.remark;
                    var metaInfoId = question.metaInfoId;


                    var answerCount = 0;
                    var buffer = '';
                    $.each(content, function (idx, obj) {
                        answerCount++;
                        buffer += '<li class="list-group-item">' + obj + '</li>';
                    });

                    if (metaInfo[metaInfoId] == "single") {
                        $("#single-box").show();
                        $("#multi-box").hide();
                        $("#judge-box").hide();
                    } else if (metaInfo[metaInfoId] == "multi") {
                        $("#single-box").hide();
                        $("#multi-box").show();
                        $("#judge-box").hide();
                    } else if (metaInfo[metaInfoId] == "judge") {
                        $("#single-box").hide();
                        $("#multi-box").hide();
                        $("#judge-box").show();
                    }
                    $("[name='answer']").iCheck("uncheck")
                    $(".answer-box").find("label:gt(" + (answerCount - 1) + ")").hide();
                    $(".answer-box").find("label:lt(" + answerCount + ")").show();
                    $("#title").html(title);
                    $("#content").html(buffer);
                    if (remark) {
                        $("#remark").html("备注：" + remark);
                    }
                    zeroModal.closeAll();
                    reqFlag = true;
                } else {
                    swal("", res.msg, "warning");
                    reqFlag = false;
                }
            },
            error: function (data) {
                swal("", "获取试题异常，请联系管理员", "error");
                reqFlag = false;
            }
        });
        return reqFlag;
    }

    function getSeconds() {
        return $("#timer").data("seconds");
    }

    $("#changePaper").bind("click", function () {
        swal("", "更换试卷后当前试卷作废，剩余作答时间不变。且仅有一次更换机会，确认要更换试卷吗？", "warning");
    });


    //勾选插件
    $("[type='checkbox'], [type='radio']").iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%' // optional
    });

    $("#commitPaper").on("click", commitPaper);

    function commitPaper() {
        swal({})
        var seconds = getSeconds();
        if (!seconds) {
            console.log("没有获取到时间信息");
        }
    }
</script>
</body>
</html>
