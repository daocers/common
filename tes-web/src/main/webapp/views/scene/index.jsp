<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|开场</title>
    <%@ include file="../template/header.jsp" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">

    <%--设置开关键--%>
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

        #rootwizard > ul > li > a{
            cursor: default;
            text-decoration: none;
        }
    </style>
    <%--设置向导样式--%>
    <style>
        .bwizard-steps {
            display: inline-block;
            margin: 0;
            padding: 0;
            background: #fff
        }

        .bwizard-steps .active {
            color: #fff;
            background: #007ACC
        }

        .bwizard-steps .active:after {
            border-left-color: #007ACC
        }

        .bwizard-steps .active a {
            color: #fff;
            cursor: default
        }

        .bwizard-steps .label {
            position: relative;
            top: -1px;
            margin: 0 5px 0 0;
            padding: 1px 5px 2px
        }

        .bwizard-steps .active .label {
            background-color: #333;
        }

        .bwizard-steps li {
            display: inline-block;
            position: relative;
            margin-right: 5px;
            padding: 12px 17px 10px 30px;
            *display: inline;
            *padding-left: 17px;
            background: #efefef;
            line-height: 18px;
            list-style: none;
            zoom: 1;
        }

        .bwizard-steps li:first-child {
            padding-left: 12px;
            -moz-border-radius: 4px 0 0 4px;
            -webkit-border-radius: 4px 0 0 4px;
            border-radius: 4px 0 0 4px;
        }

        .bwizard-steps li:first-child:before {
            border: none
        }

        .bwizard-steps li:last-child {
            margin-right: 0;
            -moz-border-radius: 0 4px 4px 0;
            -webkit-border-radius: 0 4px 4px 0;
            border-radius: 0 4px 4px 0;
        }

        .bwizard-steps li:last-child:after {
            border: none
        }

        .bwizard-steps li:before {
            position: absolute;
            left: 0;
            top: 0;
            height: 0;
            width: 0;
            border-bottom: 20px inset transparent;
            border-left: 20px solid #fff;
            border-top: 20px inset transparent;
            content: ""
        }

        .bwizard-steps li:after {
            position: absolute;
            right: -20px;
            top: 0;
            height: 0;
            width: 0;
            border-bottom: 20px inset transparent;
            border-left: 20px solid #efefef;
            border-top: 20px inset transparent;
            content: "";
            z-index: 2;
        }

        .bwizard-steps a {
            color: #333
        }

        .bwizard-steps a:hover {
            text-decoration: none
        }

        .bwizard-steps.clickable li:not(.active) {
            cursor: pointer
        }

        .bwizard-steps.clickable li:hover:not(.active) {
            background: #ccc
        }

        .bwizard-steps.clickable li:hover:not(.active):after {
            border-left-color: #ccc
        }

        .bwizard-steps.clickable li:hover:not(.active) a {
            color: #08c
        }

        @media (max-width: 720px) {
            /* badges only on small screens */
            .bwizard-steps li:after,
            .bwizard-steps li:before {
                border: none
            }

            .bwizard-steps li,
            .bwizard-steps li.active,
            .bwizard-steps li:first-child,
            .bwizard-steps li:last-child {
                margin-right: 0;
                padding: 0;
                background-color: transparent
            }
        }
    </style>

    <script src="../../assets/js/jquery.bootstrap.wizard.js"></script>

    <script>
        $(function () {
            $('#rootwizard').bootstrapWizard({
                'tabClass': 'bwizard-steps',
                onTabShow: function (tab, navigator, index) {
                    console.log("index: ", index);
                },
                onTabClick: function (tab, navigator, index) {
                    return false;
                }
            });
        })
    </script>
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
                    <li><a href="list.do?type=my">场次管理</a></li>
                    <li><a href="#" class="active">开场</a></li>
                </ol>
            </div>


            <input id="id" type="hidden" name="id" value="${scene.id}">
            <input type="hidden" value="${type}" id="type">
            <input type="hidden" id="choiceInfo" value="${scene.choiceInfo}">

            <div id="rootwizard">
                <ul>
                    <li><a href="#tab1" data-toggle="tab"><span class="label">1</span> 设置参数</a></li>
                    <li><a href="#tab2" data-toggle="tab"><span class="label">2</span> 考生选择</a></li>
                    <li><a href="#tab3" data-toggle="tab"><span class="label">3</span> 生成试卷</a></li>
                    <li><a href="#tab4" data-toggle="tab"><span class="label">4</span> 场次预览</a></li>
                    <li><a href="#tab5" data-toggle="tab"><span class="label">5</span> 开场完成</a></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane col-md-8" id="tab1" style="margin-left: -15px;">
                        <form class="form-horizontal" method="post" action="save.do" data-toggle="validator" role="form"
                              id="sceneForm">
                            <div class="form-group">
                                <label class="control-label col-md-2">场次名称</label>
                                <div class="col-md-10">
                                    <input class="form-control" type="text" name="name" value="${scene.name}" required>
                                    <span class="help-block with-errors"></span>
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
                                    <input class="form-control" type="text" name="delay" value="${scene.delay}"
                                           required>
                                    <span class="help-block with-errors">考生顺延时间内开始考试，作答时间不变。超过顺延时间，作答时间需要减去超出部分。</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">作答时间</label>
                                <div class="col-md-10">
                                    <input class="form-control" type="text" name="duration" value="${scene.duration}"
                                           required>
                                    <span class="help-block with-errors">最大答题时间，超过该时间将自动提交试卷。</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">结束时间</label>
                                <div class="col-md-10">
                                    <input class="form-control time" type="text" name="endTime"
                                           value="<fmt:formatDate value="${scene.endTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>"
                                           disabled>
                                    <span class="help-block with-errors">超过该时间点后，所有的本场考试的试卷将会被自动提交。(根据开始时间，作答时长，顺延时间计算，不能手动输入)</span>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">是否允许更换试卷</label>
                                <div class="col-md-3">
                                    <div class="switch" style="height:30px;">
                                        <input class="change-paper form-control" data-on-color="info"
                                               data-off-color="warning"
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
                                        <option value="1">随机试卷</option>
                                        <option value="2">统一试卷</option>
                                        <%--<option value="1">按策略随机</option>--%>
                                        <%--<option value="2">按策略统一</option>--%>
                                        <%--<option value="3">导入试题</option>--%>
                                        <%--<option value="4">系统随机</option>--%>
                                        <%--<option value="5">系统统一</option>--%>
                                    </select>
                                    <span class="help-block with-errors">随机方式每张试卷不相同，统一方式每张试卷相同</span>
                                </div>
                            </div>

                            <div class="button">
                                <button class="btn btn-warning btn-cancel" onclick="history.back();">取消</button>

                                <div class="space">

                                </div>
                                <button class="btn btn-primary btn-commit" id="saveBase">下一步</button>

                            </div>
                        </form>
                    </div>
                    <div class="tab-pane" id="tab2">

                        <div class="form-inline form-group">
                            <div class="input-group">
                                <label class="input-group-addon">考试人员控制方式</label>
                                <div>
                                    <select class="form-control" id="userType" name="userType">
                                        <option value="3" <c:if test="${scene.userType == 3}">selected</c:if>>设置授权码
                                        </option>
                                        <option value="2"
                                                <c:if test="${scene.userType == 2}">selected</c:if> disabled>我的机构
                                        </option>
                                        <option value="0"
                                                <c:if test="${scene.userType == 0}">selected</c:if> disabled>指定机构
                                        </option>
                                        <option value="1"
                                                <c:if test="${scene.userType == 1}">selected</c:if> disabled>指定用户
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <span class="help-block with-errors hidden">如果考试人员较多，建议直接选择参考机构；如果少量人员考试，直接点击机构信息然后选择用户</span>
                        <span class="help-block with-errors">【设置授权码】 用户录入授权码参与考试，适用于集中性的考试</span>
                        <span class="help-block with-errors hidden">【我的机构】 我所在机构的用户均可参加，不包括下属机构</span>
                        <span class="help-block with-errors hidden">【指定机构】 指定机构下的用户可参加，不包括下属机构</span>
                        <span class="help-block with-errors hidden">【指定用户】 指定用户可参加，单击机构名称可查询用户信息，适用于少量用户考试</span>

                        <div class="form-inline authBox">
                            <div class="input-group">
                                <label class="control-label input-group-addon">授权码</label>
                                <input class="form-control" minlength="6" maxlength="6" name="authCode" id="authCode"
                                       placeholder="请输入6位数字或字母组合" value="${scene.authCode}">
                                <div class="input-group-btn">
                                    <button class="btn btn-info" type="button">自动生成</button>

                                </div>
                            </div>
                        </div>

                        <div class="button" style="margin-bottom: 50px; margin-top: 30px;">
                            <button class="btn btn-warning btn-cancel" onclick="history.back();">取消</button>

                            <div class="space">

                            </div>
                            <button class="btn btn-primary btn-commit" id="saveAuthCode">下一步</button>

                        </div>
                    </div>

                    <div class="tab-pane" id="tab3">
                        <div class="">
                            <div class="col-md-8">
                                <form class="form-horizontal" method="post" action="savePolicy.do"
                                      data-toggle="validator"
                                      role="form" id="policyForm">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label class="input-group-addon"
                                                   style="margin-right: 15px;">选择题库</label>
                                            <select class="form-control" name="bankId" required>
                                                <option value="">--请选择--</option>
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
                                            <button class="btn btn-default" type="button">查询</button>
                                            <span style="margin-left: 30px; padding-bottom: 5px; margin-bottom: 5px;">没有合适策略？<a
                                                    href="/paperPolicy/edit.do">去添加</a> </span>
                                        </div>

                                    </div>

                                    <div class="" style="min-height: 300px; margin-left: -15px;">
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
                                            <c:forEach var="policy" items="${paperPolicyList}">
                                                <tr>
                                                    <td><input name="paperPolicyId" type="radio" value="${policy.id}"
                                                               <c:if test="${scene.paperPolicyId == policy.id}">checked</c:if>
                                                    ></td>
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

                                    <div class="button">
                                        <button class="btn btn-warning btn-cancel" onclick="history.back();">取消</button>
                                        <div class="space">

                                        </div>
                                        <button class="btn btn-primary btn-commit" id="savePaperPolicy">下一步</button>
                                    </div>
                                    <div class="row">

                                    </div>
                                </form>
                            </div>

                            <%--此处应修改为策略和题库信息的对比，符合或者不符合需要给出信息--%>
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
                                <div class="input-group">
                                    <div class="input-group-btn">
                                        <button class="btn btn-success" id="checkPaperPolicy">校验策略可用性</button>

                                    </div>
                                    <input type="text" disabled id="available" value="" class="form-control">
                                </div>
                                <div>
                                    <button class="btn btn-danger" id="updateBank">刷新题库</button>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="tab-pane" id="tab4">
                        <form action="confirm.do" method="post">
                            <div id="preview-box">
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
                                        <td><fmt:formatDate value="${scene.beginTime}"
                                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td>结束时间</td>
                                        <td><fmt:formatDate value="${scene.endTime}"
                                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
                                        <td colspan="3">${paperPolicy.name}</td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-2">题量</td>
                                        <td class="col-md-4">${paperPolicy.count}</td>
                                        <td class="col-md-2">总分</td>
                                        <td class="col-md-4">${paperPolicy.score}</td>
                                    </tr>
                                    <tr>
                                        <td class="col-md-2">策略明细</td>
                                        <td colspan="3">${paperPolicy.content}</td>
                                    </tr>
                                    </tbody>

                                </table>
                            </div>

                            <div class="button">
                                <button type="button" class="btn btn-warning" onclick="history.back();">取消</button>
                                <div class="space">

                                </div>
                                <button class="btn btn-success" type="button" id="confirm">确定,开场</button>
                            </div>
                        </form>
                    </div>
                    <div class="tab-pane" id="tab5">
                        <div class="col-md-8">
                            <div class="center-block" style="display: none;" id="success-box">
                                <img src="/assets/img/success.png" height="150px" width="150px" class="img-circle">
                                <h3>恭喜，开场成功！</h3>
                                <a href="list.do?type=my" class="btn btn-success">确定</a>
                            </div>
                            <div class="center-block" style="display: none;" id="error-box">
                                <img src="/assets/img/error.png" height="150px" width="150px" class="img-cricle">
                                <h3>抱歉，开场失败！</h3>
                                <h4 id="errMsg"></h4>
                                <a href="#" class="btn btn-info">场次管理</a>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

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

    /**
     * 初始化开关键
     * */
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

        /**
         * 处理试卷策略信息
         * */
        $("table").on("click", "input[name='paperPolicyId'][type='radio']", function () {
            if ($(this).is(":checked")) {
                $("#policy").val($(this).parentsUntil("tr").next().text());
                var paperPolicyId = $(this).val();
                $("#content").val($(this).parentsUntil("tr").parent().find("td:eq(5)").text());
                $("#available").val("");
//                $.ajax({
//                    url: "/paperPolicy/getPolicyInfo.do",
//                    data: {id: paperPolicyId},
//                    success: function (data) {
//                        $("#content").val(data);
//                    },
//                    error: function (data) {
//                        swal("", "获取已选策略信息失败", "error");
//                        return false;
//                    }
//                });
//                $("#content").val($(this).parentsUntil("tr").parent().find("td:eq(5)").text());
            }
        });
    })

    /**
     * 保存基本信息
     * */
    $("#saveBase").on("click", function () {
        $.ajax({
            url: 'save.do',
            type: 'post',
            data: $("#sceneForm").serialize() + "&id=" + $("#id").val(),
            success: function (data) {
                var res = JSON.parse(data);
                if (res.code == 0) {
                    var id = res.data;
                    $("#id").val(id);
                    $("#rootwizard").bootstrapWizard('next');
                } else {
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
    });

    /**
     * 保存场次授权码
     * */
    $("#saveAuthCode").on("click", function () {
        $.ajax({
            url: "saveAuthCode.do",
            type: 'post',
            data: {id: $("#id").val(), authCode: $("#authCode").val()},

            success: function (data) {
                var res = JSON.parse(data);
                if (res.code == 0) {
                    $("#rootwizard").bootstrapWizard('next');
                } else {
                    swal("", res.err, "error");
                }
            },
            error: function (data) {
                swal("", "保存失败", "error");
            }
        });
        return false;
    });

    /**
     * 保存试卷策略
     * */
    $("#savePaperPolicy").on("click", function () {
        var bankId = $("[name='bankId']").val();
        if (!bankId) {
            swal("", "请选择题库", "error");
            return false;
        }
        var paperPolicyId = $("[name='paperPolicyId']:checked").val();
        if (!paperPolicyId) {
            swal("", "请选择试卷策略", "error");
            return false;
        }

        var id = $("#id").val();
        if (!id) {
            swal("", "请设置考试参数", "error");
            return false;
        }

        var id = $("#id").val();
        $.ajax({
            url: 'savePaperPolicy.do',
            type: 'post',
            data: $("#policyForm").serialize() + "&id=" + id,
            success: function (data) {
                var res = JSON.parse(data);
                if (res.code == 0) {
                    $("#preview-box").load("preview.do?id=" + id);
                    $("#rootwizard").bootstrapWizard('next');
                } else {
                    swal("", res.err, "error");
                }
            },
            error: function (data) {
                swal("", "保存失败", "error");
            }
        });
        return false;
    });


    /**
     * 检查试卷策略是否可用
     * */
    $("#checkPaperPolicy").on("click", function () {
        var bankId = $("[name='bankId']").val();
        console.log("bankId: ", bankId);
        if (!bankId) {
            swal("", "请选择题库", "error");
            return false;
        }
        var paperPolicyId = $("[name='paperPolicyId']:checked").val();
        if (!paperPolicyId) {
            swal("", "请选择试卷策略", "error");
            return false;
        }

        var id = $("#id").val();
        if (!id) {
            swal("", "请设置考试参数", "error");
            return false;
        }

        $.ajax({
            url: "checkPaperPolicy.do",
            data: {bankId: bankId, paperPolicyId: paperPolicyId},
            success: function (data) {
                var res = JSON.parse(data);
                if (res.code == 0) {
                    if (res.data == true) {
                        $("#available").val("试题充足，可用");
                    } else {
                        $("#available").val("试题不足，不可用")
                    }
                }
            },
            error: function (data) {
                swal("", "请求失败", "error");
            }
        })
        return false;
    });

    /**
     * 更新题库信息，主要是更新试题在redis上的缓存，用于校验试题策略可用性
     * */
    $("#updateBank").on("click", function () {
        swal({
            title: "耗时请求，谨慎操作",
            title: "建议在试卷策略不可用的时候尝试，请勿多次请求",
            type: "warning",
            showCancelButton: true,

        }).then(function (isConfirm) {
            if (isConfirm) {
                zeroModal.loading(3);
                $.ajax({
                    url: "/question/updateCache.do",
                    type: 'post',
                    success: function (data) {
                        zeroModal.closeAll();
                        var res = JSON.parse(data);
                        console.log("res: ", res);
                        if (res.code == 0) {
                        } else {
                            swal("", "刷新题库失败", "error");
                        }
                    },
                    error: function (data) {
                        zeroModal.closeAll();
                        swal("", "刷新题库失败", "error");
                    }
                })
            } else {
                return false;
            }
        })
    });

    $("#confirm").on("click", function () {
        var id = $("#id").val();
        $.ajax({
            url: "confirm.do",
            type: 'post',
            data: {id: id},
            success: function (data) {
                var res = JSON.parse(data);
                if (res.code == 0) {
                    $("#success-box").show();
                    $("#error-box").hide();
                } else {
                    var err = res.err;
                    $("#success-box").hide();
                    $("#error-box").show();
                    $("#errmsg").text(err);
                }
                $("#rootwizard").bootstrapWizard('next');
            },
            error: function (data) {
                swal("", "开场失败", "error");
                $("#success-box").hide();
                $("#error-box").hide();
            }
        });
        return false;
    })


</script>
</body>
</html>
