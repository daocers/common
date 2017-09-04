<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|用户列表</title>
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
</head>
<body>
<%--<%@ include file="../template/menu-top.jsp" %>--%>

<div class="zTree left">
    <ul id="tree" class="ztree"></ul>
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
            onClick: onClick,
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

    var zNodes = eval(${data});

    console.log("znode: ", zNodes)

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
        console.log("treeNode", treeNode);
        if (treeNode.name.indexOf("新节点") > -1) {
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            console.log("新节点创建了");
            treeObj.editName(treeNode);
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
        var zTree = $.fn.zTree.getZTreeObj("tree");
        zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr("checked");
    }

    function beforeEditName(treeId, treeNode) {
        className = (className === "dark" ? "" : "dark");
        showLog("[ " + getTime() + " beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
        var zTree = $.fn.zTree.getZTreeObj("tree");
        zTree.selectNode(treeNode);
//            return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
    }

    /**
     * 用于捕获节点被删除之前的事件毁掉函数，并且根据返回值确定是否允许删除
     * @param treeId
     * @param treeNode
     */
    function beforeRemove(treeId, treeNode) {
        className = (className === "dark" ? "" : "dark");
        showLog("[ " + getTime() + " beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
        var zTree = $.fn.zTree.getZTreeObj("tree");
        zTree.selectNode(treeNode);
        return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
    }
    /**
     * 捕获删除节点之后的事件回调函数
     * @param e
     * @param treeId
     * @param treeNode
     */
    function onRemove(e, treeId, treeNode) {
        showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    }

    /**
     * 用于捕获节点编辑名称结束（Input 失去焦点 或 按下 Enter 键）之后，更新节点名称数据之前的事件回调函数，并且根据返回值确定是否允许更改名称的操作
     节点进入编辑名称状态后，按 ESC 键可以放弃当前修改，恢复原名称，取消编辑名称状态
     从 v3.5.13 开始，取消编辑状态也会触发此回调，根据 isCancel 参数判断
     * @param treeId
     * @param treeNode
     * @param newName
     * @param isCancel
     * @returns {boolean}
     */
    function beforeRename(treeId, treeNode, newName, isCancel) {
        className = (className === "dark" ? "" : "dark");
//            showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
        if (newName.length == 0) {
            alert("节点名称不能为空.");
            var zTree = $.fn.zTree.getZTreeObj("tree");
            setTimeout(function () {
                zTree.editName(treeNode)
            }, 10);
            return false;
        }
        return true;
    }
    /**
     * 用于捕获节点编辑名称结束之后的事件回调函数。
     * @param e
     * @param treeId
     * @param treeNode
     * @param isCancel
     */
    function onRename(e, treeId, treeNode, isCancel) {
        var parent = treeNode.getParentNode();
        if (parent) {
            $("#level").val(parseInt(parent.level) + 1);
        } else {
            $("#level").val(0);
        }
//            showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
    }

    /**
     * 节点被点击时候触发操作
     * @param e
     * @param treeId
     * @param treeNode
     */
    function onClick(e, treeId, treeNode) {
//        zeroModal.loading(3);
//        console.log(treeNode)
//        var id = treeNode.cId;
//        $("input[name='superiorId']").val(treeNode.pId);
//        $("#id").val(treeNode.id);
//        if(!id){//新建的
//            $("input[name='id']").val("");
//            $("input[name='name']").val(treeNode.name);
//            $("input[name='address']").val("");
//            $("input[name='code']").val("");
//        }else{//修改原有的
//            $.ajax({
//                url: 'edit.do',
//                type: "get",
//                data: {id: id},
//                success: function (data) {
//                    console.log("data: ", data);
//                    var res = JSON.parse(data);
//                    if(res.code == 0){
//                        var info = res.data;
//                        $("input[name='id']").val(info.id);
//                        $("input[name='name']").val(info.name);
//                        $("input[name='address']").val(info.address);
//                        $("input[name='code']").val(info.code);
//                        $("select[name='status']").val(info.status);
//                        zeroModal.closeAll();
//                    }else{
//                        zeroModal.closeAll();
//                        swal("", "获取机构详情失败", "error");
//                    }
//                    return false;
//                },
//                error: function (data) {
//                    zeroModal.closeAll();
//                    swal("", "服务错误", data.msg);
//                    return false;
//                }
//            });
//        }

    };

    var newCount = 1;
    function addHoverDom(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='添加新节点' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_" + treeNode.tId);
        if (btn) btn.bind("click", function () {
            var zTree = $.fn.zTree.getZTreeObj("tree");
            zTree.addNodes(treeNode, {id: (100 + newCount), pId: treeNode.id, name: "添加新节点" + (newCount++)});
            return false;
        });
    };
    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_" + treeNode.tId).unbind().remove();
    };

    $(document).ready(function () {
        $.fn.zTree.init($("#tree"), setting, zNodes);
        $("#callbackTrigger").bind("change", {}, setTrigger);
    });
    //-->
</SCRIPT>
<script type="text/javascript">
    $(function () {
        $("#submit").on("click", function () {
            zeroModal.loading(3);
            var zTree = $.fn.zTree.getZTreeObj("tree");
            var nodes = zTree.transformToArray(zTree.getNodes());
            var arr = new Array();
            $.each(nodes, function (index, node) {
                var id = node.id;
                var name = node.name;
                var level = node.level;
                var pid = node.pId;
                arr.push({id: id, pId: pid, level: level, name: name});
            })
            $.ajax({
                url: "update.do",
                type: 'post',
                data: {info: JSON.stringify(arr)},
                success: function (data) {
                    var res = JSON.parse(data);
                    if (res.code == 0) {
                        swal("", "更新成功", "success");
                    } else {
                        swal("", res.msg, "error");
                    }
                    zeroModal.closeAll();
                },
                error: function () {
                    swal("", "请求失败", "error");
                    zeroModal.closeAll();
                }
            })
//            console.log(nodes);
        });

//        $("#form-box").on("valid.bs.validator", function (event) {
//            console.log("event: ", event.result);
//            console.log("校验功过")
//            zeroModal.loading(3);
//            $.ajax({
//                url: 'save.do',
//                type: 'post',
//                data: $("form").serialize(),
//                success: function (data) {
//                    var res = JSON.parse(data);
//                    if(res.code == 0){
//                        var zTree = $.fn.zTree.getZTreeObj("tree");
//                        var node = zTree.getNodeByParam("id", $("#id").val());
//                        if(node){
//                            node.id = res.data;
//                            node.cId = res.data;
//                        }
//                        swal("", "保存成功", "info");
//                        $("input").val("");
//                        $(this).hide();
//                    }
//                    zeroModal.closeAll();
//                },
//                error: function (data) {
//                    var res = JSON.parse(data);
//                    zeroModal.closeAll();
//                    swal("", res.msg, "error");
//                }
//            });
//            return false;
//        })
//        $(".btn-commit").on("click", function () {
//            $(this).attr("disabled", true);
//
//        });
    });
</script>
</body>
</html>
