<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|导入模板</title>
    <%@ include file="../template/header.jsp" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <style>
        .prop-box > * {
            display: inline-block;
        }

        ul.list-group {
            width: auto;
        }
    </style>
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
                    <li><a href="list.do">试题管理</a></li>
                    <li><a href="#" class="active">导入模板</a></li>
                </ol>
            </div>

            <form class="form-horizontal" method="post" action="import.do" enctype="multipart/form-data"
                  data-toggle="validator" role="form">
                <div class="form-group">
                    <label class="control-label col-md-1">题型</label>
                    <div class="col-md-10">
                        <select class="form-control" name="metaInfoId" required>
                            <option value="">请选择</option>
                            <c:forEach var="metaInfo" items="${metaInfoList}">
                                <option value="${metaInfo.id}"
                                        <c:if test="${metaInfoId == metaInfo.id}">selected</c:if>>${metaInfo.name}</option>
                            </c:forEach>
                        </select>
                        <span class="help-block with-errors">选择需要导入的题型</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-1">题库</label>
                    <div class="col-md-10">
                        <select class="form-control" name="questionBankId" required>
                            <option value="">请选择</option>
                            <c:forEach var="bank" items="${questionBankList}">
                                <option value="${bank.id}"
                                        <c:if test="${bank.id == questionBankId}">selected</c:if> >${bank.name}</option>
                            </c:forEach>
                        </select>
                        <span class="help-block with-errors">选择导入的题库</span>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-offset-1" style="padding-left: 15px;">
                        <input type="file" name="file" class="jfilestyle" data-input="true" data-buttonText="选择文件">
                        <button class="btn btn-success btn-commit">上传</button>
                        ？ 没有模板文件<a href="javascript:download();">点击下载</a>模板
                    </div>

                </div>

            </form>


        </div>
    </div>
</div>
<script>
    $(function () {
        $(".btn-commit").on("click", function () {
            zeroModal.loading(3);
            $("form").submit();
            return false;
        })
    })

    /**
     * 下载提醒对应的模板
     */
    function download() {
        var id = $("select").val();
        if (!id) {
            swal("", "请选择题型", "warning");
            return false;
        } else {
            window.location.href = "/questionMetaInfo/downModel.do?metaInfoId=" + id;
        }
    }
</script>
</body>
</html>