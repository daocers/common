<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>设置用户</title>
    <%@ include file="../template/header.jsp" %>

    <link rel="stylesheet" href="../assets/css/ztree/zTreeStyle.css">
    <script type="text/javascript" src="../assets/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="../assets/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="../assets/js/jquery.ztree.exedit.js"></script>
    <SCRIPT type="text/javascript">
        <!--
        var setting = {
            check:{
                enable: true,
                autoCheckTrigger: true,
            },
            edit: {
                enable: false,
                editNameSelectAll: true,
//                showRemoveBtn: showRemoveBtn,
//                showRenameBtn: showRenameBtn,
                drag: {
//                    autoExpandTrigger: true,
//                    prev: dropPrev,
//                    inner: dropInner,
//                    next: dropNext
                },
                showRemoveBtn: false,
                showRenameBtn: false,
                removeTitle: "删除节点",
                renameTitle: "修改名称",
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
//                beforeDrag: beforeDrag,
//                beforeDrop: beforeDrop,
//                beforeDragOpen: beforeDragOpen,
//                onDrag: onDrag,
//                onDrop: onDrop,
                onClick: onClick,
                onCheck: onCheck,
//                onExpand: onExpand,
//                beforeEditName: beforeEditName,
//                beforeRemove: beforeRemove,
//                beforeRename: beforeRename,
//                onRemove: onRemove,
//                onRename: onRename,
//                onNodeCreated: onNodeCreated
            },
            view: {
                showLine: true,
//                addHoverDom: addHoverDom,
//                removeHoverDom: removeHoverDom,
                selectedMulti: false
            }
        };

        var zNodes = eval(${data});

        function setTrigger() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr("checked");
        }


        /**
         * 节点被点击时候触发操作
         * @param e
         * @param treeId
         * @param treeNode
         */
        function onClick(e, treeId, treeNode) {
            if(!clickable){
                return false;
            }
            zeroModal.loading(3);
            var branchId = treeNode.id;
            var buffer = '';
            $.ajax({
                url: "/user/listAll.do",
                data: {branchId: branchId},

                success: function (data) {
                    var users = JSON.parse(data);
                    $.each(users, function (idx, obj) {
                        console.log(obj)
                        var username = obj.username;
                        var name = '';
                        if(obj.profile){
                            name =  obj.profile.name;
                        }
                        buffer = buffer + "<tr rowid=" + obj.id +"><td>" + username + "</td><td>" + name +"</td><td><a href='javascript:add(" +obj.id + ");'>选择</a></td></tr>";
//                        buffer = buffer + "<tr><td>" + username + "</td><td>" +
//                            name +"</td><td><input type='checkbox' value='" + obj.id + "'></td></tr>";
                    });
                    $("#user-container").find("tbody").html(buffer);
                    zeroModal.closeAll();
                },
                error: function (data) {
                    buffer = buffer + "<tr><td colspan='3'>查询失败！</td></tr>";
                    $("#user-container").find("tbody").html(buffer);
                    zeroModal.closeAll();
                }
            });
        }

        function onCheck(event, treeId, treeNode) {
            var id = treeNode.id;
            var name = treeNode.name;
            var checked = treeNode.checked;
            if(checked){
                if($("#branch-box tbody ").find("tr[rowid='" + id +"']").length > 0){
                    console.log("已经添加过了");
                    return false;
                }
                $("#branch-box tbody").append("<tr rowid='" + id + "'><td>" + name
                    +"</td><td><a href='javascript:delBranch(" + id + ");'>删除</a> </td></tr>")
            }
        }
        $(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            $("#callbackTrigger").bind("change", {}, setTrigger);
        });
        //-->
    </SCRIPT>
</head>
<body>
<div class="container">
    <div class="row nav-path">
        <ol class="breadcrumb">
            <li><a href="#">首页</a> </li>
            <li><a href="#" class="active">权限管理</a> </li>
        </ol>
    </div>


    <div class="progress">
        <div class="progress-bar" style="width: 30%; background-color: #7bc0ff">
            <span style="height: 30px;">1 设置信息</span>
        </div>
        <div class="progress-bar  progress-bar-striped" style="width:35%; background-color: #389fff">
            <span>2 选择用户</span>
        </div>
        <div class="progress-bar" style="width: 35%; background-color: #3076ff">
            <span>3 生成试卷</span>
        </div>
    </div>

    <input type="hidden" name="id" value="${scene.id}">
    <input type="hidden" id="choiceInfo" value="${scene.choiceInfo}">
    <div class="form-inline form-group">
        <div class="input-group">
            <label class="input-group-addon">考试人员控制方式</label>
            <div>
                <select class="form-control" id="userType">
                    <option value="2">我的机构</option>
                    <option value="0">指定机构</option>
                    <option value="1">指定用户</option>
                    <option value="3">设置授权码</option>
                </select>
            </div>
        </div>
    </div>
    <span class="help-block with-errors">如果考试人员较多，建议直接选择参考机构；如果少量人员考试，直接点击机构信息然后选择用户</span>
    <span class="help-block with-errors">【我的机构】 我所在机构的用户均可参加，不包括下属机构</span>
    <span class="help-block with-errors">【指定机构】 指定机构下的用户可参加，不包括下属机构</span>
    <span class="help-block with-errors">【指定用户】 指定用户可参加，单击机构名称可查询用户信息，适用于少量用户考试</span>
    <span class="help-block with-errors">【设置授权码】 用户录入授权码参与考试，适用于集中性的考试</span>

    <div class="row form-inline authBox" style="display: none">
        <div class="form-group">
            <label class="control-label ">授权码</label>
            <input class="form-control" minlength="6" maxlength="6" name="authCode" id="authCode" placeholder="请输入6位数字或字母组合">
            <button class="btn btn-info" type="button">自动生成</button>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4" id="tree" style="display: none;">
            <div class="zTreeDemoBackground left">
                <ul id="treeDemo" class="ztree"></ul>
            </div>
        </div>
        <div class="col-md-4 choice-user" style="display: none;">
            <table class="table table-bordered" id="user-container">
                <thead>
                <tr>
                    <th class="col-md-1">用户名</th>
                    <th class="col-md-1">姓名</th>
                    <th class="col-md-1"><input class="selectAll" type="checkbox"></th>
                </tr>
                </thead>
                <tbody>
                </tbody>

            </table>
        </div>

        <div class="col-md-4  choice-user" style="display: none; overflow-y: auto; height: 300px;">
            <table class="table table-bordered" id="choice-box">
                <thead>
                <tr>
                    <th>用户名</th>
                    <th>姓名</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>

        <div class="col-md-4 col-md-offset-4 choice-branch" style="overflow-y: auto; height: 300px; display: none">
            <table class="table table-bordered" id="branch-box">
                <thead>
                <tr>
                    <th>机构</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
    </div>


    <div class="button" style="margin-bottom: 50px; margin-top: 30px;">
        <button class="btn btn-warning btn-cancel" onclick="history.back();">取消</button>

        <div class="space">

        </div>
        <button class="btn btn-primary btn-commit">下一步</button>

    </div>
</div>
<script>
    var clickable = false;
    $(function () {
        $("#userType").on("change", function () {
            console.log("type");
            var type = $(this).val();
            if(type == 0){//按照机构选择
                clickable = false;
                $(".choice-user").hide();
                $(".choice-branch").show();
                $("#tree").show();
                $(".authBox").hide();
            }else if(type == 1){//按照用户选择
                clickable = true;
                setting.check.enable = false;
                $(".choice-user").show();
                $(".choice-branch").hide();
                $("#tree").show();
                $(".authBox").hide();
            }else if(type == 2){//我的机构
                clickable = false;
                setting.check.enable = false;
                $("#tree").hide();
                $(".choice-branch").hide();
                $(".choice-user").hide();
                $(".authBox").hide();
            }else if(type == 3){
                clickable = false;
                setting.check.enable = false;
                $("#tree").hide();
                $(".choice-branch").hide();
                $(".choice-user").hide();
                $(".authBox").show();
            }
            else{
                clickable = false;
                console.log("无效数据");
            }
        })
    })
    function delBranch(id) {
        $("#branch-box").find("tr[rowid='" + id +"']").remove();
    }function del(id) {
        $("#choice-box").find("tr[rowid='" + id +"']").remove();
    }

    function add(id) {
        if($("#choice-box").find("tr[rowid='" + id +"']").length > 0){
            console.log("已经添加了该用户")
            return false;
        }
        var $tr = $("#user-container tr[rowid='" + id +"']");
        var username = $tr.find("td:eq(0)").html();
        var name = $tr.find("td:eq(1)").html();
        $("#choice-box").find("tbody").append("<tr userId='" + id + "'><td>"
            + username + "</td><td>" + name + "</td><td><a href='javaScript:del(" + id + ");'>删除</a> </td></tr>");
    }
    $(".commit").on("click", function () {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        var data = zTree.transformToArray(zTree.getNodes());
        console.log("data: ", data);

        $.ajax({
            url: 'update.do',
            type: "post",
            data: {"info" : JSON.stringify(data)},
            success: function (data) {
                if(data == "0"){
                    console.log("提交成功");
                    window.location.href = "list.do";
                }else{
                    console.log("提交失败");
                }
            },
            error: function () {
                console.log("提交失败");
            }
        })
    });

    $(".cancel").on("click", function () {
        window.history.back();
    });


    /**
     * 提交信息
     */
    $(".btn-commit").click(function () {
        zeroModal.loading(4);
        var type= $("#userType").val();
        var ids = new Array();
        if(type == 0){//处理机构
            $("#branch-box tbody").find("tr").each(function (idx, obj) {
                var id = $(obj).attr("rowId");
                ids.push(id);
            })
        }else if(type == 1){//处理用户
            $("#choice-box tbody").find("tr").each(function (idx, obj) {
                var id = $(obj).attr("rowId");
                ids.push(id);
            })
        }else if(type == 2){//我的机构

        }else if(type == 3){//设置授权码
            choiceInfo = $("#authCode").val();
            if(!choiceInfo || choiceInfo == ''){
                zeroModal.closeAll();
                swal("请输入6位授权码");
                return false;
            }
        }else{
            swal("", "非法操作", "error");
            zeroModal.closeAll();
            return false;
        }
        var choiceInfo = $("#choiceInfo").val();

        if(ids.length == 0){
            if(!choiceInfo || choiceInfo.length == 0){
                swal("", "没有选择用户或者指定机构", "warning");
                zeroModal.closeAll();
                return false;
            }else{
                choiceInfo = JSON.stringify(new Array());
            }
        }else{
            choiceInfo = JSON.stringify(ids);
        }


        console.log("choiceINfo: ", choiceInfo + "?");

        $.ajax({
            url: "saveUser.do",
            type: "post",
            data: {info: choiceInfo, type: type, sceneId: $("[name='id']").val()},
            success: function (data) {
                var res = JSON.parse(data);
                zeroModal.closeAll();
                if (res.code == "-1") {
                    swal("", "请选择本场考试人员", "warning");
                    return false;
                }
                if (res.code == "0") {
                    console.log("god")
                    window.location.href = "selectPolicy.do?id=" + $("[name='id']").val();
                    return false;
                }
            },
            error: function (data) {
                zeroModal.closeAll();
                swal("", "保存考试人员失败", "error");
                return false;
            }
        })
    });
</script>
</body>
</html>