<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|编辑文字录入</title>
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
            <%--<h1 class="page-header">Dashboard</h1>--%>
            <div class="page-header nav-path">
                <ol class="breadcrumb">
                    <li><a href="/index.do">首页</a></li>
                    <li><a href="list.do">文字录入管理</a></li>
                    <li><a href="#" class="active">编辑文字录入</a></li>
                </ol>
            </div>


            <form class="form-horizontal col-md-8" method="post" action="save.do" data-toggle="validator" role="form">
                <input id="id" type="hidden" name="id" value="${typeInQuestion.id}">

                <div class="form-group">
                    <label class="control-label col-md-2">凭条名称</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="name" value="${typeInQuestion.name}" required>
                        <span class="help-block with-errors">输入凭条名称，便于记忆</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">凭条内容</label>
                    <div class="col-md-10">
                        <table class="table table-bordered  table-editable">
                            <thead>
                            <tr>
                                <td class="col-md-2">序号</td>
                                <td class="col-md-5">数字</td>
                            </tr>
                            </thead>
                        </table>
                        <div style="max-height: 300px; height: 300px; overflow-x: scroll; border: 1px solid gainsboro">
                            <table class="table table-bordered  table-editable">
                                <tbody>

                                </tbody>
                            </table>
                        </div>

                        <input class="form-control" type="hidden" name="content" value="${typeInQuestion.content}"
                               required>
                    </div>
                </div>

                <div class="button pull-right">
                    <button class="btn btn-primary btn-commit">保存</button>
                    <div class="space">

                    </div>
                    <button class="btn btn-warning btn-cancel">取消</button>
                </div>
            </form>


        </div>
    </div>
</div>
<script>

    $(function () {
        var numbers = eval(${typeInQuestion.content});
        var buffer = "";
        if (numbers && numbers.length > 0) {
            $.each(numbers, function (idx, num) {
                buffer += "<tr><td class='col-md-2'>" + (idx + 1) + "</td><td class='cell-edit col-md-5'><input class='form-control form-control-intable' value=' " + fmoney(num) + "'</td></tr>"
            });
        } else {
            for (var i = 0; i < 100; i++) {
                buffer += "<tr><td class='col-md-2'>" + (i + 1) + "</td><td class='cell-edit col-md-5'><input class='form-control form-control-intable' value=''></td></tr>"
            }
        }


        $("table tbody").html(buffer);

        $(".btn-commit").on("click", function () {
            var array = new Array();
            $("table tbody tr input").each(function (idx, obj) {
                console.log("idx: ", idx);
                console.log("obj: ", obj);

                var num = $(obj).val();
                if (num == '') {
                    $(obj).parent().addClass("has-error");
                    console.log("return false");
                    return false;
                }
                num = num.replace(new RegExp(/(,)/g), "");
                array.push(num);
            });
            if (array.length != 100) {
                swal("", "请录入数字", "info");
                return false;
            }
            $("[name='content']").val(JSON.stringify(array));
            console.log(JSON.stringify(array));
        })

        $("table tbody input").on("blur", function () {
            var val = $(this).val();
            if (val) {
                if (val.indexOf(",") > -1) {
                    val = val.replace(new RegExp(/(,)/g), "");
                }
                var reg = val.match(/\d+\.?\d{0,2}/);
                var txt = '';
                if (reg != null) {
                    txt = reg[0];
                }
                $(this).val(fmoney(txt));
                $(this).parent().removeClass("has-error");
            } else {
                $(this).parent().addClass("has-error");
            }

        }).on("focus", function () {
            var val = $(this).val();
            if (val) {
                if (val.indexOf(",") > -1) {
                    val = val.replace(new RegExp(/(,)/g), "");
                }
                $(this).val(val).select();
            }
        });
    })

    function fmoney(s, n) {
        n = n > 0 && n <= 20 ? n : 2;
        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
        var l = s.split(".")[0].split("").reverse(),
            r = s.split(".")[1];
        t = "";
        for (i = 0; i < l.length; i++) {
            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
        }
        return t.split("").reverse().join("") + "." + r;
    }
</script>
</body>
</html>