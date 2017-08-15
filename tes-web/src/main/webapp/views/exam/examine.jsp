<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>布谷培训|答题</title>
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
            <li><a href="/index.do">首页</a></li>
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
</body>
<script>
    var currentIndex = -1;
    //    var questionCount = 0;
    var questionIdList = eval(${questionIdList});
    /**
     * 保存题型id和对应的试题id信息
     * */
    var questionMetaAndIdListMap = eval(${paper.content});
    /**
     * 试题类型map
     * */
    var metaInfoMap = eval(${metaInfo});

    /**
    **保存已经作答的题目答案信息，key是试题id，value 为答案
    * */
    var answerMap = eval(${answerMap})

    /**
     * 试题id 对应的试题试题信息
     * */
    var questionMap = eval(${questionMap});

    var questionIdMap = eval(${paper.content});
    <%--提交试题--%>
    function commitQuestion() {
        var resFlag = true;
        var paperId = $("#paperId").val();
        var questionId = questionIdList[currentIndex];
        var answer = getAnswer();
        $("table tbody").find("tr[queid='" + questionId + "'] > td:eq(1)").text(answer);
        if (!answer || answer == '') {//没有答题
            return true;
        } else {
            $.ajax({
                url: 'commitQuestion.do',
                type: "post",
                data: {questionId: questionId, answer: answer, paperId: paperId, timeLeft: getTileLeft()},
                success: function (data) {
                    var res = JSON.parse(data);
                    if (res.code == 0) {
                        console.log("提交答案成功");
                        resFlag = true;
                    } else {
                        console.log("提交答案失败，请重试或者点击上一题下一题");
                        var msg = res.msg;
                        swal("提交答案失败", msg, "error");
                        resFlag = false;
                    }
                    return resFlag;
                },
                error: function (data) {
                    resFlag = false;
                    swal("", "服务异常", "error");
                    return resFlag;
                }
            })
        }

    }
    /*获取试题*/
    function getQuestion(id) {
        var ans = $("#myAnswer tbody tr[queid=" + id + "] td:eq(1)").text();
        console.log("getquetion ans: ", ans);
        var reqFlag = true;
        var question = questionMap[id];
        console.log("question: ", question);
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
        var code = metaInfoMap[metaInfoId].code;

        if (code == "single") {
            $("#single-box").show();
            $("#multi-box").hide();
            $("#judge-box").hide();
        } else if (code == "multi") {
            $("#single-box").hide();
            $("#multi-box").show();
            $("#judge-box").hide();
        } else if (code == "judge") {
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


        if(ans && ans.length > 0){
            $.each(ans.split(""), function (idx, obj) {
                $("#" + code + "-box").find("input[value='" + obj + "']").iCheck('check');
            })
        }

        $("#myAnswer tbody tr").removeClass("current");
        $("#myAnswer tbody tr[queid='" + id + "']").addClass("current");
//        $("#current option[queid='" + id + "']").attr("selected", true);
        $("#current").val(id);
        return reqFlag;

    }
    /*提交试卷*/
    function commitPaper() {
        var resFlag = true;
        var paperId = $("#paperId").val();
        var data = {};
        $("table tbody tr").each(function (idx, obj) {
            var questionId = parseInt($(obj).attr("queid"));
            var answer = $(obj).find("td:eq(1)").text();
            data[questionId] = answer;
        });
        $.ajax({
            url: "commitPaper.do",
            type: 'post',
            data: {answerInfo: JSON.stringify(data), paperId: paperId},
            success: function (data) {
                var res = JSON.parse(data);
                if (res.code == 0) {
                    resFlag = true;
                    console.log("试卷提交成功");
                    swal("", "试卷提交成功", "success").then(function (isconfirm) {
                        if (isconfirm) {
                            window.location.href = "/index.jsp";//页面跳转待定
                        }
                    })
                }
            },
            error: function (data) {
                swal("", "服务失败，请联系管理员", "error");
                resFlag = false;
            }
        })


    }
    /*下一题*/
    function next() {
        commitQuestion();
        if (currentIndex == questionIdList.length - 1) {
            swal("", "已经是最后一题", "warning");
            return false;
        }
        try {
            getQuestion(questionIdList[currentIndex + 1]);
            currentIndex++;
            return false;
        } catch (err) {
            console.log("失败", err);
            swal("", "提交试题失败", "error");//此处根据需要后续不做弹框处理，要做页面头部显示
            return false;
        }
    }

    /*前一题*/
    function prev() {
        commitQuestion();
        if (currentIndex == 0 || currentIndex == -1) {
            swal("", "已经是第一题", "warning");
            return false;
        }
        try {
            getQuestion(questionIdList[currentIndex - 1]);
            currentIndex--;
            return false;
        } catch (err) {
            console.log("失败", err);
            swal("", "提交试题失败", "error");//此处根据需要后续不做弹框处理，要做页面头部显示
            return false;
        }
    }
    /*初始化计时器*/
    function initTimer() {
        var left = $("#timeLeft").val();
//        left = left + "s";
        console.log("leftTime: ", left)
        $("#timer").timer({
            duration: left,
            countdown: true,
            format: '%H:%M:%S',
            callback: function () {
                swal("", "时间到", "info");
            }
        });

    }
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

    function getSeconds() {
        return $("#timer").data("seconds");
    }

    function getTileLeft() {
        return $("#timer").val();
    }

    function jumpTo(id) {
        commitQuestion();
        currentIndex = questionIdList.indexOf(id);
        getQuestion(id);


    }
    $(function () {

        $("#myAnswer a").on("click", function () {
            return false;
        })
//        questionIdList = JSON.parse($("#questionIds").val());
        //初始化答题数据
        var tableBox = "";
        var selectBox = "";
        $.each(questionMetaAndIdListMap, function (key, val) {
            var queType = metaInfoMap[key].name;
            $.each(val, function (idx, id) {
                var ans = answerMap[id];
                if(!ans){
                    ans = "";
                }
                tableBox += "<tr queid='" + id + "'><td><a href='javascript:jumpTo(" + id + ")'>" + queType + "第" + (idx + 1) + "题" + "</a> </td><td>" + ans + "</td><td class='hide'></td></tr>"
                selectBox += "<option queid='" + id + "' value='" + id + "'>" + queType + "第" + (idx + 1) + "题</option>"
            })

        });
        $("#myAnswer tbody").html(tableBox);
        $("#current").html(selectBox);

        /**
         * 初始化题目
         */
        var queFlag = getQuestion(questionIdList[0]);
        //第一次加载题目后将currentIndex++,也就是变成0
        if (queFlag) {
            currentIndex++;
        }
        initTimer();
        $("#prevBtn").on("click", prev);

        $("#nextBtn").on("click", next);
    });

    /**
     * 获取作答信息
     */


    $("#changePaper").bind("click", function () {
        swal("", "更换试卷后当前试卷作废，剩余作答时间不变。且仅有一次更换机会，确认要更换试卷吗？", "warning");
    });

    //勾选插件
    $("[type='checkbox'], [type='radio']").iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%' // optional
    });

    /**
     * 实时更新答案
     * */
    $("[name='answer']").on("ifChanged", function (event) {
        $("#myAnswer tr[queid='" + questionIdList[currentIndex] + "'] td:eq(1)").text(getAnswer());
    });

    $("#current").on("change", function () {
        var id = $(this).val();
        jumpTo(id);
    })

    $("#commitPaper").on("click", commitPaper);


    /**
     * 以下是websocket处理，用来处理强制交卷信息
     * */
    var ws;
    $(function () {
        ws = new WebSocket("ws://localhost:8080/ws/my.ws");
        console.log("初始化");
        ws.onopen = function () {
            console.log("open。。。")
        }

        ws.onmessage = function (event) {
            console.log("event", event);
            var data = event.data;
            console.log("收到服务器消息：", data);

            var res = "";
            try {
                res = JSON.parse(data);
            } catch (err) {
                console.log("解析消息失败: ", err);
            }
            if (res != '') {
                var type = res.type;
                if (type == "4") {
                    swal("", "教师强制提交试卷", "info");
                    zeroModal.loading(3);
                    commitPaper();
//                    $("#changePaper").trigger("click");
                }
            }
        }

        ws.onclose = function (event) {
            console.log("event:", event);
            console.log("close...")
        }
    })

</script>

</html>
