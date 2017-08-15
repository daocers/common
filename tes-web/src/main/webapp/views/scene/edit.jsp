<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>设置场次参数</title>
    <%@ include file="../template/header.jsp" %>
    <style>
        .switch-btn {
            position: relative;
            display: block;
            vertical-align: top;
            width: 80px;
            height: 30px;
            border-radius: 5px;
            cursor: pointer;
        }

        .checked-switch {
            position: absolute;
            top: 0;
            left: 0;
            opacity: 0;
        }

        .text-switch {
            background-color: #ed5b49;
            border: 1px solid #d2402e;
            border-radius: inherit;
            color: #fff;
            display: block;
            font-size: 15px;
            height: inherit;
            position: relative;
            text-transform: uppercase;
        }

        .text-switch:before,
        .text-switch:after {
            position: absolute;
            top: 50%;
            margin-top: -.5em;
            line-height: 1;
            -webkit-transition: inherit;
            -moz-transition: inherit;
            -o-transition: inherit;
            transition: inherit;
        }

        .text-switch:before {
            content: attr(data-no);
            right: 11px;
        }

        .text-switch:after {
            content: attr(data-yes);
            left: 11px;
            color: #FFFFFF;
            opacity: 0;
        }

        .checked-switch:checked ~ .text-switch {
            background-color: #285eaf;
            border: 1px solid #1d53af;
        }

        .checked-switch:checked ~ .text-switch:before {
            opacity: 0;
        }

        .checked-switch:checked ~ .text-switch:after {
            opacity: 1;
        }

        .toggle-btn {
            background: linear-gradient(#eee, #fafafa);
            border-radius: 5px;
            height: 28px;
            left: 1px;
            position: absolute;
            top: 1px;
            width: 28px;
        }

        .toggle-btn::before {
            color: #aaaaaa;
            content: "|||";
            display: inline-block;
            font-size: 12px;
            letter-spacing: -2px;
            padding: 4px 0;
            vertical-align: middle;
        }

        .checked-switch:checked ~ .toggle-btn {
            left: 51px;
        }

        .text-switch, .toggle-btn {
            transition: All 0.3s ease;
            -webkit-transition: All 0.3s ease;
            -moz-transition: All 0.3s ease;
            -o-transition: All 0.3s ease;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row nav-path">
        <ol class="breadcrumb">
            <li><a href="#">首页</a></li>
            <li><a href="#">考试管理</a></li>
            <li><a href="#" class="active">考试信息录入</a></li>
        </ol>
    </div>

    <div class="progress">
        <div class="progress-bar progress-bar-striped" style="width: 30%; background-color: #7bc0ff">
            <span style="height: 30px;">1 设置信息</span>
        </div>
        <div class="progress-bar" style="width:35%; background-color: #389fff">
            <span>2 选择用户</span>
        </div>
        <div class="progress-bar" style="width: 35%; background-color: #3076ff">
            <span>3 生成试卷</span>
        </div>
    </div>

    <input type="hidden" value="${param.type}" id="type">
    <div class="row">
        <div class="col-md-8">
            <form class="form-horizontal" method="post" action="save.do" data-toggle="validator" role="form" id="sceneForm">
                <input id="id" type="hidden" name="id" value="${scene.id}">
                <div class="form-group">
                    <label class="control-label col-md-2">场次名称</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="name" value="${scene.name}" required>
                        <span class="help-block with-errors"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">场次授权码</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="authCode" value="${scene.authCode}" required>
                        <span class="help-block with-errors">本场考试的唯一标识，建议使用字母数字组合</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">考试开始时间</label>
                    <div class="col-md-10">
                        <input class="form-control time" type="text" name="beginTime" data-min-date="today"
                               value="<fmt:formatDate value="${scene.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate> ">
                        <span class="help-block with-errors"></span>
                    </div>
                </div>


                <div class="form-group">
                    <label class="control-label col-md-2">考试顺延时间</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="delay" value="${scene.delay}" required>
                        <span class="help-block with-errors">考生顺延时间内开始考试，作答时间不变。超过顺延时间，作答时间需要减去超出部分。</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">作答时间</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="duration" value="${scene.duration}" required>
                        <span class="help-block with-errors">最大答题时间，超过该时间将自动提交试卷。</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">结束时间</label>
                    <div class="col-md-10">
                        <input class="form-control time" type="text" name="endTime"
                               value="<fmt:formatDate value="${scene.endTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate> "
                               disabled>
                        <span class="help-block with-errors">超过该时间点后，所有的本场考试的试卷将会被自动提交。(根据开始时间，作答时长，顺延时间计算，不能手动输入)</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2">是否允许更换试卷</label>
                    <div class="col-md-3">
                        <div class="switch" style="height:30px;">
                            <input class="change-paper form-control" data-on-color="info" data-off-color="warning"
                                   type="checkbox" name="changePaper"
                                   data-on-text="是" data-off-text="否"
                                   <c:if test="${scene.changePaper == 0}">checked</c:if>
                                   value="${scene.changePaper}" onclick="this.checked?0:1"
                                   style="height: 30px;">
                        </div>

                    </div>

                </div>

                <div class="form-group">
                    <label class="control-label col-md-2">试卷生成方式</label>
                    <div class="col-md-10">
                        <select class="form-control" name="paperType">
                            <option value="1">按策略随机</option>
                            <option value="2">按策略统一</option>
                            <%--<option value="3">导入试题</option>--%>
                            <%--<option value="4">系统随机</option>--%>
                            <%--<option value="5">系统统一</option>--%>
                        </select>
                        <span class="help-block with-errors">随机方式每张试卷不相同，统一方式每张试卷相同</span>
                    </div>
                </div>

                <div class="button pull-right">
                    <button class="btn btn-warning btn-cancel" onclick="history.back();">取消</button>

                    <div class="space">

                    </div>
                    <button class="btn btn-primary btn-commit">继续</button>

                </div>
            </form>
        </div>

    </div>
</div>
<script>

    /**
     * 计算结束时间
     */
    $("[name='beginTime'], [name='delay'], [name='duration']").bind("change", function () {
        console.log("changed...")
        var beginTime = $("[name='beginTime']").val();
        var delay = $("[name='delay']").val().trim();
        var duration = $("[name='duration']").val().trim();
        var date = new Date(beginTime);
        var time = date.getTime() + delay * 60 * 1000 + duration * 60 * 1000;
        var endDate = new Date();
        endDate.setTime(time);
        console.log("time: ", time);
        if (beginTime && delay && duration) {
            $("[name='endTime']").setTime(endDate);
        }
    })

    $(function () {
        $(".change-paper").bootstrapSwitch({
            onSwitchChange: function (event, state) {
                if (state == true) {
                    $(this).val("0");
                } else {
                    $(this).val("1");
                }
            }
        });
    })

    $(".btn-commit").on("click", function () {
        $.ajax({
            url: 'save.do',
            type: 'post',
            data: $("#sceneForm").serialize(),
            success: function (data) {
                var res = JSON.parse(data);
                if(res.code == 0){
                    var id = res.data;
                    window.location.href = 'setUser.do?sceneId=' + id;
                }else{
                    swal("", res.msg, "error");
                    return false;
                }
            },
            error: function (data) {
                swal("", "保存失败", "error");
                return false;
            }
        });
        return false;
    })


</script>
</body>
</html>
