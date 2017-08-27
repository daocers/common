<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|场次管理</title>
    <%@ include file="../template/header.jsp" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
</head>
<body>
<%--<%@ include file="../template/menu-top.jsp" %>--%>

<div class="container-fluid">
    <div class="row">
        <%--<div class="col-sm-0 col-md-2 sidebar menu-left">--%>
        <%--<%@ include file="../template/menu-left.jsp" %>--%>
        <%--</div>--%>
        <div class="col-md-12 main" id="main">
            <div>
                <div class="panel blank-panel">

                    <div class="panel-heading">
                        <%--<div class="panel-title m-b-md">--%>
                            <%--<h4>图标选项卡</h4>--%>
                        <%--</div>--%>
                        <div class="panel-options my-tab">

                            <ul class="nav nav-tabs">
                                <li class="active"><a data-toggle="tab" href="#0"><i class="fa fa-laptop"></i>待完善</a>
                                </li>
                                <li class=""><a data-toggle="tab" href="#1"><i class="fa fa-desktop"></i>就绪</a>
                                </li>
                                <li class=""><a data-toggle="tab" href="#2"><i class="fa fa-signal"></i>考试中</a>
                                </li>
                                <li class=""><a data-toggle="tab" href="#3"><i class="fa fa-bar-chart-o"></i>已封场</a>
                                </li>
                                <li class=""><a data-toggle="tab" href="#4"><i class="fa fa-bar-chart-o"></i>已取消</a>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <div class="panel-body">
                        <div class="tab-content">
                            <form class="form-inline">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-addon">考试名称</div>
                                    <input class="form-control" name="name" value="${param.name}">
                                </div>
                                <div class="input-group input-group-sm">
                                    <div class="input-group-addon">编码</div>
                                    <input class="form-control" name="code" value="${param.code}">
                                </div>
                                <div class="input-group input-group-sm">
                                    <div class="input-group-addon">授权码</div>
                                    <input class="form-control" name="authCode" value="${param.authCode}">
                                </div>
                                <div class="input-group input-group-sm">
                                    <button class="btn btn-sm btn-info" id="search">查询</button>
                                </div>
                            </form>

                            <div id="waiting" class="tab-pane active">
                                <jsp:include page="list_content.jsp"/>
                            </div>

                            <div id="0" class="tab-pane">
                                <strong>就绪</strong>

                                <p>为了增强跨浏览器表现的一致性，我们使用了 Normalize.css，这是由 Nicolas Gallagher 和 Jonathan Neal 维护的一个CSS 重置样式库。</p>
                            </div>
                            <div id="1" class="tab-pane">
                                <strong>考试中</strong>

                                <p>Bootstrap 需要为页面内容和栅格系统包裹一个 .container 容器。我们提供了两个作此用处的类。注意，由于 padding 等属性的原因，这两种 容器类不能互相嵌套。</p>
                            </div>
                            <div id="2" class="tab-pane">
                                <strong>已封场</strong>

                                <p>Bootstrap 提供了一套响应式、移动设备优先的流式栅格系统，随着屏幕或视口（viewport）尺寸的增加，系统会自动分为最多12列。它包含了易于使用的预定义类，还有强大的mixin 用于生成更具语义的布局。</p>
                            </div>
                            <div id="3" class="tab-pane">
                                <strong>已封场</strong>

                                <p>Bootstrap 提供了一套响应式、移动设备优先的流式栅格系统，随着屏幕或视口（viewport）尺寸的增加，系统会自动分为最多12列。它包含了易于使用的预定义类，还有强大的mixin 用于生成更具语义的布局。</p>
                            </div>
                            <div id="4" class="tab-pane">
                                <strong>已取消</strong>

                                <p>Bootstrap 提供了一套响应式、移动设备优先的流式栅格系统，随着屏幕或视口（viewport）尺寸的增加，系统会自动分为最多12列。它包含了易于使用的预定义类，还有强大的mixin 用于生成更具语义的布局。</p>
                            </div>
                        </div>

                    </div>

                </div>
            </div>

           
        </div>
    </div>
</div>
</div>
<script>

    $(function () {
        $("#search").on("click", function () {
            var id = $(".nav-tabs li.active a[data-toggle='tab']").attr("href");
            if(id.length > 0){
                id = id.substring(1);
            }
            showTabs(id, '/scene/list/mine/content.do?status=' + id);
            return false;

        });
        function showTabs(tabsId,url) {
            var info = $("form").serialize();
            console.log(".nav-tabs a[href='#"+tabsId+"']")
            console.log("2", $(".nav-tabs a[href='#"+tabsId+"']"))
            $(".nav-tabs a[href='#"+tabsId+"']").tab('show');
            var $tabContent = $('#'+tabsId);
            if($tabContent.length < 100) {
                $tabContent.load(url + "&"  + info);
                //console.info(tabsId + ' load done!');
            }
        }

        $(".my-tab a[data-toggle='tab']").on("click", function (e) {
            console.log("dddfdf")
            var id = $(this).attr("href");
            if(id.length > 0){
                id = id.substring(1);
            }
            console.log("id", id);
            showTabs(id, '/scene/list/mine/content.do?status=' + id);
            e.preventDefault();
        })

        //依次为每个tab导航a标签添加单击事件
//        $('a[href="#waiting"]').click(function(e) {
//            showTabs('waiting','/scene/list/mine/content.do?status=0');
//            e.preventDefault();
//        });
//
//        $('a[href="#ready"]').click(function(e) {
//            console.log("ready")
//            showTabs('ready','/scene/list/mine/content.do?status=1');
//            e.preventDefault();
//        });
//        $('a[href="#examing"]').click(function(e) {
//            showTabs('ready','/scene/list/mine/content.do?status=2');
//            e.preventDefault();
//        });
//        $('a[href="#closed"]').click(function(e) {
//            showTabs('ready','/scene/list/mine/content.do?status=3');
//            e.preventDefault();
//        });
//        $('a[href="#canceled"]').click(function(e) {
//            showTabs('ready','/scene/list/mine/content.do?status=4');
//            e.preventDefault();
//        });
    })
</script>
</body>
</html>
