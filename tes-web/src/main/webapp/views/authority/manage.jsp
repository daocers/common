<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|权限维护</title>
    <%@ include file="../template/header.jsp" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="../assets/css/ztree/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="../assets/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="../assets/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="../assets/js/jquery.ztree.exedit.js"></script>
    <style>
        .form-control-intable {
            height: 36px;
        }
    </style>

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
                    <li><a href="#" class="active">权限维护</a></li>
                </ol>
            </div>

            <div class="" style="width:780px; vertical-align: top; display: inline-block">
                <div class="col-md-5" style="margin-left: -15px;">
                    <div class="zTreeDemoBackground left">
                        <ul id="treeDemo" class="ztree" style="height: 460px;"></ul>
                    </div>

                    <button class="btn btn-info commit">确定</button>
                    <button class="btn btn-warning cancel">取消</button>
                </div>

                <div class="col-md-5 col-md-offset-1" id="info-box">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                权限详情
                            </h3>
                        </div>
                        <table class="table table-bordered">
                            <input type="hidden" id="authId"/>
                            <tbody>
                            <tr>
                                <td class="col-md-1">名称</td>

                                <td class="editable"><input id="name" type="text"
                                                            class="form-control form-control-intable" name="name"
                                                            placeholder="请输入新的名称..."></td>
                            </tr>
                            <tr>
                                <td class="col-md-1">URL</td>
                                <td id="url"></td>
                            </tr>
                            <tr>
                                <td class="col-md-1">编码</td>
                                <td id="code"></td>
                            </tr>
                            <tr>
                                <td class="col-md-1">Controller</td>
                                <td id="controller"></td>
                            </tr>
                            <tr>
                                <td class="col-md-1">Method</td>
                                <td id="action"></td>
                            </tr>
                            <tr>
                                <td class="col-md-1">请求方式</td>
                                <td id="acceptMethod"></td>
                            </tr>
                            <tr>
                                <td class="col-md-1">参数</td>
                                <td id="param"></td>
                            </tr>
                            <tr>
                                <td class="col-md-1"> 权限</td>
                                <td id="needAuth"></td>
                            </tr>
                            <tr>
                                <td class="col-md-1">page</td>
                                <td id="isPage"></td>
                            </tr>
                            <tr>
                                <td>API</td>
                                <td id="isApi"></td>
                            </tr>
                            <tr>
                                <td>描述</td>
                                <td class="editable"><input class="form-control form-control-intable" id="description">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="panel panel-info">
                        <button class="btn btn-info pull-right" type="button" id="commitChange">提交修改</button>
                    </div>
                </div>


            </div>
            <script>
                function getInfo(id) {
                    zeroModal.loading(3);
                    $.ajax({
                        url: "edit.do",
                        data: {id: id},
                        success: function (data) {
                            var obj = JSON.parse(data);
                            if (obj.code == 0) {
                                obj = obj.data;
                                $("#authId").val(obj.id);
                                $("#name").val(obj.name);
                                $("#url").text(obj.url);
                                $("#code").text(obj.code);
                                $("#param").text(obj.param);
                                $("#controller").text(obj.controller);
                                $("#action").text(obj.action);
                                $("#acceptMethod").text(obj.acceptMethod);
                                $("#description").val(obj.description);
                                $("#isApi").text(obj.isApi == 0);
                                $("#isPage").text(obj.isApi == 1);
                                $("#needAuth").text("暂无");
                                zeroModal.closeAll();
                                $("#info-box").show();
                            } else {
                                zeroModal.closeAll();
                                swal("", obj.msg, "error");
                                $("#info-box").hide();
                            }
                        }
                    });
                }
                $(".commit").on("click", function () {
                    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                    var data = zTree.transformToArray(zTree.getNodes());
                    console.log("data: ", data);

                    $.ajax({
                        url: 'update.do',
                        type: "post",
                        data: {"info": JSON.stringify(data)},
                        success: function (data) {
                            if (data == "0") {
                                console.log("提交成功");
                                window.location.href = "list.do";
                            } else {
                                console.log("提交失败");
                            }
                        },
                        error: function () {
                            console.log("提交失败");
                        }
                    })
                });

                $("#commitChange").on("click", function () {
                    zeroModal.loading(3);
                    var id = $("#authId").val();
                    var name = $("#name").val();
                    var des = $("#description").val();

                    $.ajax({
                        url: "save.do",
                        type: 'post',
                        data: {id: id, name: name, description: des},
                        success: function (data) {
                            var res = JSON.parse(data);
                            if (res.code == 0) {
                                var tree = $.fn.zTree.getZTreeObj("treeDemo");
                                var node = tree.getNodeByParam("id", id, null);
                                if (node) {
                                    node.name = name;
                                    tree.updateNode(node);
                                }
                                zeroModal.closeAll();
                                $("#info-box").hide();
                            } else {
                                swal("", res.err, "error");
                                zeroModal.closeAll();
                            }
                        },
                        error: function (data) {
                            swal("", "保存失败", "error");
                            zeroModal.closeAll();
                        }
                    })

                });

                $(".cancel").on("click", function () {
                    window.history.back();
                })
            </script>
        </div>
    </div>
</div>


<SCRIPT type="text/javascript">
    <!--
    var setting = {
        edit: {
            enable: true,
            editNameSelectAll: true,
            showRemoveBtn: showRemoveBtn,
            showRenameBtn: showRenameBtn,
            drag: {
                autoExpandTrigger: true,
                prev: dropPrev,
                inner: dropInner,
                next: dropNext
            },
            showRemoveBtn: true,
            showRenameBtn: true,
            removeTitle: "删除节点",
            renameTitle: "修改名称",
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeDrag: beforeDrag,
            beforeDrop: beforeDrop,
            beforeDragOpen: beforeDragOpen,
            onDrag: onDrag,
            onDrop: onDrop,
            onDblClick: onDoubleClick,
            onExpand: onExpand,
            beforeEditName: beforeEditName,
            beforeRemove: beforeRemove,
            beforeRename: beforeRename,
            onRemove: onRemove,
            onRename: onRename,
            onNodeCreated: onNodeCreated
        },
        view: {
            showLine: true,
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom,
            selectedMulti: false
        }
    };

    var zNodes = eval(${zNode});


    console.log("ori: ", zNodes);
    function dropPrev(treeId, nodes, targetNode) {
        var pNode = targetNode.getParentNode();
        if (pNode && pNode.dropInner === false) {
            return false;
        } else {
            for (var i = 0, l = curDragNodes.length; i < l; i++) {
                var curPNode = curDragNodes[i].getParentNode();
                if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                    return false;
                }
            }
        }
        return true;
    }
    function onNodeCreated(event, treeId, treeNode) {
        console.log("treeNode", treeNode.name);
        var name = treeNode.name;
        if (name) {
            if (name.indexOf("新节点") > -1) {
                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                console.log("新节点创建了");
                treeObj.editName(treeNode);
            }
        }
    }
    function dropInner(treeId, nodes, targetNode) {
        if (targetNode && targetNode.dropInner === false) {
            return false;
        } else {
            for (var i = 0, l = curDragNodes.length; i < l; i++) {
                if (!targetNode && curDragNodes[i].dropRoot === false) {
                    return false;
                } else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
                    return false;
                }
            }
        }
        return true;
    }
    function dropNext(treeId, nodes, targetNode) {
        var pNode = targetNode.getParentNode();
        if (pNode && pNode.dropInner === false) {
            return false;
        } else {
            for (var i = 0, l = curDragNodes.length; i < l; i++) {
                var curPNode = curDragNodes[i].getParentNode();
                if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                    return false;
                }
            }
        }
        return true;
    }

    var log, className = "dark", curDragNodes, autoExpandNode;
    function beforeDrag(treeId, treeNodes) {
        className = (className === "dark" ? "" : "dark");
        showLog("[ " + getTime() + " beforeDrag ]&nbsp;&nbsp;&nbsp;&nbsp; drag: " + treeNodes.length + " nodes.");
        for (var i = 0, l = treeNodes.length; i < l; i++) {
            if (treeNodes[i].drag === false) {
                curDragNodes = null;
                return false;
            } else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
                curDragNodes = null;
                return false;
            }
        }
        curDragNodes = treeNodes;
        return true;
    }
    function beforeDragOpen(treeId, treeNode) {
        autoExpandNode = treeNode;
        return true;
    }
    function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
        className = (className === "dark" ? "" : "dark");
        showLog("[ " + getTime() + " beforeDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:" + moveType);
        showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is " + (isCopy == null ? "cancel" : isCopy ? "copy" : "move"));
        return true;
    }
    function onDrag(event, treeId, treeNodes) {
        className = (className === "dark" ? "" : "dark");
        showLog("[ " + getTime() + " onDrag ]&nbsp;&nbsp;&nbsp;&nbsp; drag: " + treeNodes.length + " nodes.");
    }
    function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
        className = (className === "dark" ? "" : "dark");
        showLog("[ " + getTime() + " onDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:" + moveType);
        showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is " + (isCopy == null ? "cancel" : isCopy ? "copy" : "move"))
    }
    function onExpand(event, treeId, treeNode) {
        if (treeNode === autoExpandNode) {
            className = (className === "dark" ? "" : "dark");
            showLog("[ " + getTime() + " onExpand ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name);
        }
    }

    function showLog(str) {
        if (!log) log = $("#log");
        log.append("<li class='" + className + "'>" + str + "</li>");
        if (log.children("li").length > 8) {
            log.get(0).removeChild(log.children("li")[0]);
        }
    }
    function getTime() {
        var now = new Date(),
            h = now.getHours(),
            m = now.getMinutes(),
            s = now.getSeconds(),
            ms = now.getMilliseconds();
        return (h + ":" + m + ":" + s + " " + ms);
    }

    function showRemoveBtn(treeId, treeNode) {
        return !treeNode.isFirstNode;
    }
    function showRenameBtn(treeId, treeNode) {
        return !treeNode.isLastNode;
    }

    function setTrigger() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr("checked");
    }

    function beforeEditName(treeId, treeNode) {
        className = (className === "dark" ? "" : "dark");
        showLog("[ " + getTime() + " beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.selectNode(treeNode);
//            return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
    }
    function beforeRemove(treeId, treeNode) {
        className = (className === "dark" ? "" : "dark");
        showLog("[ " + getTime() + " beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.selectNode(treeNode);
        return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
    }
    function onRemove(e, treeId, treeNode) {
        showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    }
    function beforeRename(treeId, treeNode, newName, isCancel) {
        className = (className === "dark" ? "" : "dark");
//            showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
        if (newName.length == 0) {
            alert("节点名称不能为空.");
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            setTimeout(function () {
                zTree.editName(treeNode)
            }, 10);
            return false;
        }
        return true;
    }
    function onRename(e, treeId, treeNode, isCancel) {
//            showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
    }
    var newCount = 1;
    function addHoverDom(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='添加新节点' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_" + treeNode.tId);
        if (btn) btn.bind("click", function () {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.addNodes(treeNode, {id: (100 + newCount), pId: treeNode.id, name: "添加新节点" + (newCount++)});
            return false;
        });
    }
    ;
    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_" + treeNode.tId).unbind().remove();
    }
    ;

    /**
     * 节点被点击时候触发操作
     * @param e
     * @param treeId
     * @param treeNode
     */
    function onDoubleClick(e, treeId, treeNode) {
        var id = treeNode.id;
        getInfo(id);
        return false;
    }

    $(document).ready(function () {
        $("#info-box").hide();
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        $("#callbackTrigger").bind("change", {}, setTrigger);
    });
    //-->
</SCRIPT>
</body>
</html>