<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|录制交易</title>
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
        <div class="col-sm-0 col-md-2 sidebar menu-left">
            <%@ include file="../template/menu-left.jsp" %>
        </div>
        <div class="col-sm-12 col-sm-offset-0 col-md-10 col-md-offset-2 main" id="main">
            <%--<h1 class="page-header">Dashboard</h1>--%>
            <div class="page-header nav-path">
                <ol class="breadcrumb">
                    <li><a href="/index.do">首页</a></li>
                    <li><a href="list.do">交易管理</a></li>
                    <li><a href="#" class="active">交易录制</a></li>
                </ol>
            </div>


            <div class="menu-bar form-inline" style="margin-bottom: 15px">
                <div class="input-group input-group-sm">
                    <div class="input-group-addon">输入网址</div>
                    <input class="form-control col-md-3" name="url" style="width: 200px;">
                    <div class="input-group-btn">
                        <button class="btn btn-info" id="jump">确定</button>
                    </div>
                </div>

            </div>
            <div class="page-container">
                <iframe id="page" src="trade.html" seamless="seamless" style="border-right: none;min-width: 900px; width: auto;">

                </iframe>
            </div>
            <div class="">
                <button class="btn btn-info" id="get">获取交易信息</button>
                <button class="btn btn-success" id="getPage">保存当前交易页面</button>
            </div>
            <!--<div class="row">-->


            <div class="info-container col-md-7" style="margin-left: -15px;">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h3 class="panel-title">我录入的信息</h3>
                    </div>
                    <table class="table table-bordered" id="fieldInfo">
                        <thead>
                        <tr>
                            <th class="hidden">name</th>
                            <th class="col-md-3">名称</th>
                            <th class="col-md-6">我的录入</th>
                            <th class="col-md-2">参考栏位</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>


            </div>
            <div class="col-md-5">
                <form class="form form-horizontal" role="form">
                    <div class="form-group">
                        <label class="control-label col-md-3">交易名称</label>
                        <div class="col-md-9">
                            <input type="text" name="name" class="form-control" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3">交易码</label>
                        <div class="col-md-9">
                            <input type="text" name="code" class="form-control" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3">场景描述</label>
                        <div class="col-md-9">
                            <textarea name="description" class="form-control" rows="10" required></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3">题库</label>
                        <div class="col-md-9">
                            <select name="bankId" class="form-control" required>
                                <option>请选择</option>
                                <c:forEach items="${bankList}" var="bank">
                                    <option value="${bank.id}">${bank.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <button id="save" type="button" class="btn btn-info btn-commit col-md-offset-3">保存试题</button>
                    </div>
                </form>
            </div>
            <!--</div>-->

        </div>
    </div>
</div>


<%--此处必须单独写在此处，解决无法生效的问题--%>
<script src="/assets/js/menu.js"></script>
<script>

    $(function () {
        /**
         * iframe加载完毕后对高度和宽度进行处理
         * */
        $("#page").on("load", function () {
            console.log("iframe loaded。。。");
            $("#get").trigger("click");
            var iframe = $("iframe")[0].contentDocument;
            var $iframe = $(iframe);

            $iframe.on("dblclick", "input, select", function () {
                console.log("double click");
            });
//            console.log($iframe.height)
            var height = $("iframe")[0].contentWindow.document.documentElement.scrollHeight;
            console.log("height:", height)

            //另外一种获取height的方法
            height = $("#page").contents().find("html").height();
            console.log("height:", height)
            var width = $("#page").contents().find("html").width();
            console.log("width: ", width);
            $("#page").width(width);

            $("#page").height(height + 20);
        })
        $("#get").on("click", function () {
            var iframe = $("iframe")[0].contentDocument;
            var $iframe = $(iframe);
            var buffer = "";
            $iframe.find("input, select").each(function (idx, obj) {
                var label = $(obj).prev().text();
                var name = $(obj).attr("name");
                if (!name) {
                    name = "";
                }
                var val = $(obj).val();
                var type = obj.type;
                var text = "";
                if (type == "select-one") {
                    text = $(obj).find("option:selected").text();
                } else {
                    text = val;
                }
//                console.log("label: ", label);
//                console.log("idx: ", idx);
//                console.log("obj: ", $(obj).val());
                buffer += "<tr>";
                buffer += "<td class='hidden'>" + name + "</td>" + "<td class='hidden'>" + val + "</td>" + "<td>" + label + "</td><td>" + text +
                    "</td><td style='padding: 0px;'><input class='checkbox' style='height:' type='checkbox'></td>"
                buffer += "</tr>";
            });
            $("#fieldInfo").find("tbody").html(buffer);
        })

        $("#jump").on("click", function () {
            var url = $("[name='url']").val();
            console.log("url: ", url);
            $("#page").attr("src", url);
        })

        $("#getPage").on("click", function () {
            var html = $("#page").contents().find("html").html();
//            html = $("#page")[0].contentDocument;
//            console.log("html", html);
            html = "<html>" + html + "</html>";
            console.log("iframe", html);
            $.ajax({
                url: "savePage.do",
                type: 'post',
                data: {"info": html},
                success: function (data) {
                    var res = JSON.parse(data);
                    if (res.code == 0) {
                        console.log("save successed");
                    } else {
                        console.log("save error");
                    }
                },
                error: function (data) {
                    console.log("save error");
                }
            })
        })

        function getInfo() {
            var arr = new Array();
            $("#fieldInfo tbody tr").each(function (idx, obj) {
                var line = new Array();
                var $tds = $(obj).find("td");
                var name = $($tds[0]).html();
                if (!name) {
                    name = "";
                }
                var value = $($tds[1]).html();
                if (!value) {
                    value = "";
                }
                var label = $($tds[2]).html();
                if (!label) {
                    label = "";
                }

                var check = $($tds[4]).find("input")[0].checked;
                if (check) {
                    check = 0;
                } else {
                    check = 1;
                }

                line.push(name);
                line.push(value);
                line.push(label);
                line.push(check);
//                    $(obj).find("td").each(function (idx1, obj1) {
//                        var text = $(obj1).html();
//                        if (!text) {
//                            text = "";
//                        }
//                        if (text.indexOf('checkbox') > -1) {
//                            line.push($(obj1).find("input")[0].checked);
//                        } else {
//                            line.push(text)
//                        }
//                    })
                arr.push(line);
            })
            console.log("table info: ", JSON.stringify(arr));
            return arr;
        }

        $("#save").on("click", function () {
            var arr = getInfo();
            var code = $("[name='code']").val();
            var name = $("[name='name']").val();
            var description = $("[name='description']").val();
            var bankId = $("[name='bankId']").val();

            $.ajax({
                url: 'save.do',
                type: 'post',
                data: {
                    fieldInfo: JSON.stringify(arr), code: code,
                    pageUrl: $("#page").attr("src"), bankId: bankId,
                    name: name, description: description
                },
                success: function (data) {
                    var res = JSON.parse(data);
                    if (res.code == 0) {
                        swal('', "保存成功", "success");
                        window.location.reload();
                    } else {
                        swal("保存失败", res.err, "error");
                    }
                },
                error: function (data) {
                    swal("", "保存失败", "error");
                }
            });
            return false;
        })
    })

</script>
</body>
</html>


<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--<meta charset="utf-8">--%>
<%--<title>录制交易</title>--%>
<%--<%@ include file="../template/header.jsp" %>--%>
<%--<%@ include file="../template/menu-top.jsp" %>--%>
<%--</head>--%>
<%--<body>--%>

<%--<div class="container">--%>

<%--&lt;%&ndash;<%@ include file="../template/menu-left.jsp"%>&ndash;%&gt;--%>
<%--<div class="nav-path" style="margin-left: -30px;">--%>
<%--<ol class="breadcrumb">--%>
<%--<li><a href="#">首页</a></li>--%>
<%--<li><a href="#" class="active">交易录制</a></li>--%>
<%--</ol>--%>
<%--</div>--%>
<%--<div style="width:900px; vertical-align: top; display: inline-block">--%>
<%--<div class="row"></div>--%>

<%--</div>--%>
<%--<script></script>--%>

<%--</div>--%>
<%--</body>--%>
<%--</html>--%>
