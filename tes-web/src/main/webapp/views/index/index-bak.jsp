<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>菜单</title>

    <link href="../assets/css/bootstrap.css" rel="stylesheet" >
    <script src="../assets/js/jquery-2.2.0.min.js"></script>
    <script src="../assets/js/bootstrap.js"></script>
    <script src="../assets/js/bugu.extend.js"></script>

    <link href="../assets/css/bootstrap-switch.css" rel="stylesheet">
    <script src="../assets/js/bootstrap-switch.js"></script>

    <link href="../assets/css/jquery.filer.css" type="text/css" rel="stylesheet"/>
    <link href="../assets/css/jquery.filer-dragdropbox-theme.css" type="text/css" rel="stylesheet"/>
    <script src="../assets/js/jquery.filer.js"></script>

    <link href="../assets/css/kacha-table-editable.css" rel="stylesheet"/>
    <script src="../assets/js/bugu.editable-table.js"></script>

    <script src="../assets/js/eModal.js"></script>

    <%--<script src="../assets/js/bootstrap-datetimepicker.min.js"></script>--%>
    <%--<script src="../assets/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>--%>
    <%--<link href="../assets/css/bootstrap-datetimepicker.min.css" rel="stylesheet" >--%>

    <%--<link href="../assets/css/kacha.css" rel="stylesheet">--%>


    <%--表单校验--%>
    <script src="../assets/js/validator.js"></script>

    <%--b表单优化插件--%>
    <link rel="stylesheet" href="../assets/css/jquery-filestyle.css">
    <script src="../assets/js/jquery-filestyle.min.js"></script>

    <%--定时器--%>
    <%--<link rel="stylesheet" href="../assets/css/jquery.syotimer.css">--%>
    <%--<script src="../assets/js/jquery.syotimer.js"></script>--%>

    <%--弹出框--%>
    <link rel="stylesheet" href="../assets/css/sweetalert2.css">
    <script src="../assets/js/sweetalert2.js"></script>

    <%--另外一个定时器--%>
    <%--<script src="../assets/js/jquery.countdown.js"></script>--%>


    <%--美化checkbox和radio--%>
    <link href="../assets/css/square/blue.css" rel="stylesheet">
    <script src="../assets/js/icheck.min.js"></script>


    <%--模态对话框--%>
    <link href="../assets/css/zeroModal.css" rel="stylesheet">
    <script src="../assets/js/zeroModal.min.js"></script>


    <%--日期选择框--%>
    <link href="../assets/css/flatpickr.material_blue.min.css" rel="stylesheet">
    <script src="../assets/js/flatpickr.min.js"></script>
    <script src="../assets/js/flatpickr.l10n.zh.js"></script>

    <script src="../assets/js/timer.jquery.min.js"></script>


    <%--自定义的js和css，因为依赖其他js，故放在最后--%>
    <link href="../assets/css/my.css" rel="stylesheet">
    <script src="../assets/js/page-nav.js"></script>
    <script src="../assets/js/my.js"></script>
    <script src="../assets/js/common.js"></script>

    <script>
        /**
         * 修改swal默认设置
         */
        swal.setDefaults({
            confirmButtonText: '确定',
            cancelButtonText: "取消"
        });
    </script>
    <style>
        .navbar-inverse{
            border-radius: 0px;
        }
        .menu-left{
            width: 240px;
            padding-left: 0px;
            margin-top: -20px;
        }
        .menu-left * {
            background-color: ghostwhite;
            margin: 0;
            padding: 0;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
        }
        .menu-left ul {
            list-style-type: none;
        }
        /** =======================
         * Contenedor Principal
         ===========================*/
        .accordion {
            border: 1px solid gainsboro;
            /*width: 100%;*/
            max-width: 240px;
            margin: 0 20px 20px 0px;
            background: ghostwhite;
        }
        .accordion .link {
            cursor: pointer;
            display: block;
            padding: 15px 15px 15px 42px;
            color: darkgray;
            /*background-color: #8ed7ff;*/
            font-size: 15px;
            font-weight: 400;
            border-bottom: 1px solid #CCC;
            position: relative;
            -webkit-transition: all 0.4s ease;
            -o-transition: all 0.4s ease;
            transition: all 0.4s ease;
        }

        .accordion .link:hover{
            color: darkblue;
            border-left: 3px solid dodgerblue;
            background-color: white;
        }
        .accordion li:last-child .link {
            border-bottom: 0;
        }
        .accordion li i {
            position: absolute;
            top: 16px;
            left: 12px;
            font-size: 18px;
            /*color: #595959;*/
            color: cornflowerblue;
            -webkit-transition: all 0.4s ease;
            -o-transition: all 0.4s ease;
            transition: all 0.4s ease;
        }
        .accordion li i.fa-chevron-down {
            right: 12px;
            left: auto;
            font-size: 16px;
        }
        .accordion li.open .link {
            color: darkblue;
            /*background-color: #dbf1ff;*/
            border-left: 3px solid dodgerblue;
        }
        .accordion li.open i {
            color: #b63b4d;
        }
        .accordion li.open i.fa-chevron-down {
            -webkit-transform: rotate(180deg);
            -ms-transform: rotate(180deg);
            -o-transform: rotate(180deg);
            transform: rotate(180deg);
        }

        /**
 * Submenu
 -----------------------------*/
        .submenu {
            display: none;
            /*background: #dbf1ff;*/
            /*background: #8ed7ff;*/
            font-size: 14px;
        }
        .submenu li {
            /*padding-left: 20px;*/
            border-bottom: 1px solid whitesmoke;
        }

        .submenu li:last-child{
            border-bottom-color: #ccc;
        }
        .submenu a {
            display: block;
            text-decoration: none;
            color: silver;
            padding: 12px;
            padding-left: 60px;
            -webkit-transition: all 0.25s ease;
            -o-transition: all 0.25s ease;
            transition: all 0.25s ease;
        }
        .submenu a:hover {
            background: white;
            color: grey;
        }

        .submenu a.active{
            background: white;
            border-right: 2px solid powderblue;;
        }
    </style>
</head>
<body>
<div class="hide" id="err">
    ${err}
</div>
<div class="hide" id="msg">
    ${msg}
</div>

<div class="menu-top">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">布谷培训</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li><a href="#">我的布谷</a></li>
                    <li><a href="#">我是考生</a></li>
                    <li><a href="#">我是教师</a></li>
                    <!--<li><a href="#">培训学习</a></li>-->
                    <!--<li><a href="#">统计分析</a></li>-->
                    <!--<li><a href="#">考试管理</a></li>-->


                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <form class="navbar-form navbar-left form-inline" role="search">
                        <div class="input-group">
                            <input class="form-control">
                            <div class="input-group-btn">
                                <button class="btn btn-default">搜索</button>
                            </div>
                        </div>
                    </form>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">                <span class="person-center"></span>
                            个人中心 <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">账户设置</a></li>
                            <li><a href="#">我的布谷</a></li>
                            <li><a href="#">Something else here</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">安全退出</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">One more separated link</a></li>
                        </ul>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</div>

<div class="row">
    <div style="display: inline-block; float: left">
        <div class="menu-left" style="margin-left: 15px">
            <ul id="accordion" class="accordion">
                <li>
                    <div class="link"><i class="fa fa-paint-brush"></i>系统管理<i class="fa fa-chevron-down"></i></div>
                    <ul class="submenu">
                        <li><a href="/user/list.do">用户管理</a></li>
                        <li><a href="#">角色管理</a></li>
                        <li><a href="#">权限管理</a></li>
                    </ul>
                </li>
                <li>
                    <div class="link"><i class="fa fa-code"></i>考试管理<i class="fa fa-chevron-down"></i></div>
                    <ul class="submenu">
                        <li><a href="#">我参加的</a></li>
                        <li><a href="#">我创建的</a></li>
                        <li><a href="#">我要考试</a></li>
                    </ul>
                </li>
                <li>
                    <div class="link"><i class="fa fa-mobile"></i>试题管理<i class="fa fa-chevron-down"></i></div>
                    <ul class="submenu">
                        <li><a href="#">题型设置</a></li>
                        <li><a href="#">题目管理</a></li>
                        <li><a href="#">试卷管理</a></li>
                        <li><a href="#">Otros dispositivos</a></li>
                    </ul>
                </li>
                <li><div class="link"><i class="fa fa-globe"></i>考试<i class="fa fa-chevron-down"></i></div>
                    <ul class="submenu">
                        <li><a href="#">Google</a></li>
                        <li><a href="#">Bing</a></li>
                        <li><a href="#">Yahoo</a></li>
                        <li><a href="#">Otros buscadores</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>


    <div class="col-md-10" style="width: 780px; margin-left: 5px;   ">
    <%--<div class="" style="width: 720px;">--%>
        <div id="main" class="row" style="margin-right: 0px">

        </div>
    </div>
</div>

<script>
    var Accordion = function (e, multiple) {
        this.e = e || {};
        this.multiple = multiple || false;
        var links = this.e.find(".link");
        links.on("click", {e: this.e, multiple: this.multiple}, this.dropdown)
    }

    Accordion.prototype.dropdown = function(e) {
//            $(".accordion li.open").removeClass("open");
        var $e = e.data.e;
        $this = $(this),
            $next = $this.next();

        $next.slideToggle();
        $this.parent().toggleClass('open');

        if (!e.data.multiple) {
            $e.find('.submenu').not($next).slideUp().parent().removeClass('open');
        };
    }

    $(function () {
        var accordion = new Accordion($('#accordion'), false);


        $(".menu-left .submenu li a").on("click", function () {
            $(".menu-left a").removeClass("active");
            $(this).addClass("active");
            var url = $(this).attr("href");
            console.log("url: ", url);
            if(!url || url == "#" || url == ''){
                return false;
            }
            $("#main").load(url);
            return false;
        })

    })
</script>
</body>
</html>