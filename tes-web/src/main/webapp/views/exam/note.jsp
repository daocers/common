<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|考试须知</title>
    <%@ include file="../template/header.jsp" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
</head>
<body>
<%@ include file="../template/menu-top.jsp" %>

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
                    <li><a href="#" class="active">考试须知</a></li>
                </ol>
            </div>


                <form action="exam.do" method="post">
                    <input type="hidden" name="id" value="${scene.id}" id="sceneId"/>
                    <div class="row">
                        <div class="col-md-9">
                            <h3>考试须知：</h3>
                            <h4>一、本场考试为：三综合第一场</h4>
                            <h4>二、本场考试有题型 单选：10， 多选：10，判断：10，交易：5</h4>
                            <h4>三、本场考试允许更换试卷一次，更换后第一份试卷作废，所答成绩作废</h4>
                            <h4>四、考试规则，请遵守考试纪律</h4>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-3 pull-left">
                            <div class="input-group col-md-3">
                                <label class="input-group-addon">我有疑问</label>
                                <div class="input-group-btn">
                                    <button class="btn btn-danger" onclick="history.back();">退出咨询教师</button>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3 col-md-offset-1">
                            <div class="input-group col-md-3">
                                <label class="input-group-addon">我已明确</label>
                                <div class="input-group-btn">
                                    <button class="btn btn-success">确定开始考试</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
        </div>
    </div>
</div>

<script></script>
</body>
</html>
