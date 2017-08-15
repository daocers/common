<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|首页</title>
    <%@ include file="../template/header.jsp" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">

    <style>
        .menu-item:hover {
            cursor: pointer;
        }

        .menu-item {
            display: inline-block;
            margin: 20px;
            padding: 15px;
            border: 1px solid white;
            border-radius: 5px;
            width: 280px;
            background-color: ghostwhite;
            /*background-color: #ade4ff;*/
        }

        .menu-item:hover {
            background-color: #a5e0ff;
            /*background-color: darkgray;*/
        }

        .menu-item .top .left {
            display: inline-block;
            width: 130px;
            vertical-align: bottom;
            font-size: 24px;
            padding-left: 10px;
            line-height: 64px;
        }

        .menu-item .top .right {
            display: inline-block;
            width: 64px;
            height: 64px;
        }

        .menu-item .bottom {
            height: 35px;
            font-size: 16px;
            margin-top: 10px;
            padding-left: 10px;
        }
    </style>
</head>

<body>

<%@ include file="../template/menu-top.jsp" %>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-0 col-md-2 sidebar menu-left">
            <%@ include file="../template/menu-left.jsp" %>
        </div>
        <div class="col-sm-12 col-sm-offset-0 col-md-10 col-md-offset-2 main" id="main">
            <%--<h3 class="page-header">常用功能</h3>--%>
            <div class="row nav-path">
                <ol class="breadcrumb">
                    <li><a href="#" class="active">常用功能</a></li>
                </ol>
            </div>

            <div class="teacher">
                <a href="/scene/index.do">
                    <div class="menu-item">
                        <div class="top">
                            <div class="left">
                                开场设置
                            </div>
                            <div class="right">
                                <img src="/assets/img/setting.png" class="right">
                            </div>
                        </div>
                        <div class="bottom">
                            设置场次参数，组织考试
                        </div>
                    </div>
                </a>

                <a href="/scene/list.do?type=my">
                    <div class="menu-item">
                        <div class="top">
                            <div class="left">
                                场次管理
                            </div>
                            <div class="right">
                                <img src="/assets/img/exam.png" class="right">
                            </div>
                        </div>
                        <div class="bottom">
                            场次信息查看
                        </div>
                    </div>
                </a>

                <a href="/paper/list.do">
                    <div class="menu-item col-md-3">
                        <div class="top">
                            <div class="left">
                                试卷管理
                            </div>
                            <div class="right">
                                <img src="/assets/img/paper.png" class="right">
                            </div>
                        </div>

                        <div class="bottom">
                            查看考生试卷
                        </div>
                    </div>
                </a>
            </div>

        </div>
    </div>
</div>

<script></script>
</body>
</html>
