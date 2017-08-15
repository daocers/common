<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|编辑用户</title>
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
                    <li><a href="#" class="active">编辑用户</a></li>
                </ol>
            </div>

            <div class="container-fluid">
                <form class="form-horizontal col-md-8" method="post" action="save.do" data-toggle="validator"
                      role="form">
                    <input id="id" type="hidden" name="id" value="${user.id}">

                    <div class="form-group">
                        <label class="control-label col-md-2">用户名</label>
                        <div class="col-md-10">
                            <input class="form-control" type="text" name="username" value="${user.username}"
                                   maxlength="16"
                                   minlength="3" required
                                   <c:if test="${user.id != null}">readonly</c:if> >
                            <span class="help-block">输入用户名，使用员工号</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-2">姓名</label>
                        <div class="col-md-10">
                            <input class="form-control" type="text" name="profile.name" value="${user.profile.name}"
                                   required minlength="2" maxlength="10">
                            <span class="help-block with-errors">用户姓名</span>
                        </div>
                    </div>
                    <div class="form-group hidden">
                        <label class="control-label col-md-2">profile id</label>
                        <div class="col-md-10">
                            <input class="form-control" type="text" name="profile.id" value="${user.profile.id}"
                                   required readonly>
                            <span class="help-block with-errors">提示信息</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-2">身份证号码</label>
                        <div class="col-md-10">
                            <input class="form-control" type="text" name="profile.idNo" value="${user.profile.idNo}">
                            <span class="help-block with-errors"></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-2">机构</label>
                        <div class="col-md-10">
                            <select class="form-control" name="branchId" required
                                    style="display: inline-block;">
                                <option value="${user.branchId}" selected>${branchName}</option>
                            </select>
                            <c:if test="${param.type != 'detail'}"> <a href="javascript:showModel();">修改机构</a>
                            </c:if>
                            <span class="help-block">用户所在机构信息</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-2">部门</label>
                        <div class="col-md-10">
                            <select class="form-control" name="departmentId" required style="display: inline-block;">
                                <option value="">请选择</option>
                                <c:forEach items="${departmentList}" var="department">
                                    <option value="${department.id}" <c:if
                                            test="${user.departmentId == department.id}"> selected</c:if> >${department.name}</option>
                                </c:forEach>
                            </select>
                            <c:if test="${param.type != 'detail'}"> ？缺少部门 去<a href="../department/edit.do">添加</a>
                            </c:if>
                            <span class="help-block">用户所在部门</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-2">岗位信息</label>
                        <div class="col-md-10">
                            <select class="form-control" name="stationId" required style="display: inline-block;">
                                <option value="">请选择</option>
                                <c:forEach items="${stationList}" var="station">
                                    <option value="${station.id}" <c:if
                                            test="${user.stationId == station.id}"> selected</c:if> >${station.name}</option>
                                </c:forEach>
                            </select>
                            <c:if test="${param.type != 'detail'}">？缺少岗位 去<a href="../station/edit.do">添加</a></c:if>
                            <span class="help-block">用户当前岗位</span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-2">考试状态</label>
                        <div class="col-md-10">
                            <select class="form-control" name="profile.examStatus">
                                <option value="0"
                                        <c:if test="${user.profile.examStatus == 0}"> selected </c:if>
                                >正常
                                </option>
                                <option value="1"  <c:if test="${user.profile.examStatus == 1}"> selected </c:if>>缺考
                                </option>
                                <option value="2"  <c:if test="${user.profile.examStatus == 2}"> selected </c:if>>作弊
                                </option>
                            </select>
                            <span class="help-block with-errors">作弊状态下，柜员将在指定时间内不能使用本系统进行练习和考试</span>
                        </div>
                    </div>
                    <div class="form-group <c:if test="${user.id == null}">hidden</c:if>">
                        <label class="control-label col-md-2">考试状态更新时间</label>
                        <div class="col-md-10">
                            <input class="form-control time" type="text" name="profile.examStatusUpdate"
                                   value="<fmt:formatDate value="${user.profile.examStatusUpdate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                   disabled
                            >
                            <span class="help-block with-errors"></span>
                        </div>
                    </div>

                    <div class="form-group hidden">
                        <label class="control-label col-md-2">注册时间</label>
                        <div class="col-md-10">
                            <input class="form-control time" type="text" name="profile.registTime"
                                   value="${user.profile.registTime}"
                                   required>
                            <span class="help-block with-errors">提示信息</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-2">柜员类型</label>
                        <div class="col-md-10">
                            <input class="form-control" type="text" name="profile.type" value="${user.profile.type}"
                                   required>
                            <span class="help-block with-errors"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-2">柜员等级</label>
                        <div class="col-md-10">
                            <input class="form-control" type="text" name="profile.level" value="${user.profile.level}"
                                   required>
                            <span class="help-block with-errors"></span>
                        </div>
                    </div>

                    <div class="button pull-right">
                        <button class="btn btn-primary btn-commit">保存</button>
                        <div class="space">

                        </div>
                        <button class="btn btn-warning btn-cancel" onclick="history.back();">取消</button>
                    </div>
                </form>

            </div>
        </div>
    </div>

    <div class="modal fade container" id="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span
                            class="sr-only">Close</span></button>
                    <h4 class="modal-title">选择用户所属机构</h4>
                </div>
                <div class="modal-body">
                    <div class="row" style="margin-left: 10px;">
                        <div class="zTree">
                            <ul id="tree" class="ztree" style="width: auto; border-right-width: 0px;"></ul>
                        </div>

                        <div class="form-inline">
                            <div class="input-group input-group-sm">
                                <div class="input-group-addon">
                                    已选机构
                                </div>
                                <input class="form-control" type="text" readonly id="branchInfo" branchid value="未选择">
                            </div>
                            <button class="btn btn-info btn-sm" type="button" id="confirm">确定</button>
                            <button class="btn btn-warning btn-sm" type="button" id="cancel">取消</button>
                        </div>
                        <div class="form-horizontal">
                            <div class="form-group-sm">
                                <%--<label class="control-label col-md-2">已选机构</label>--%>
                                <div class="col-md-3">
                                </div>
                            </div>
                            <div class="form-group-sm">

                            </div>
                        </div>


                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
</div>
<SCRIPT type="text/javascript">
    var setting = {
//        edit: {
//            enable: true,
//            editNameSelectAll: true,
//            showRemoveBtn: showRemoveBtn,
//            showRenameBtn: showRenameBtn,
//            drag: {
//                autoExpandTrigger: true,
//                prev: dropPrev,
//                inner: dropInner,
//                next: dropNext
//            },
//            showRemoveBtn: true,
//            showRenameBtn: true,
//            removeTitle: "删除节点",
//            renameTitle: "修改名称",
//        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
//            beforeDrag: beforeDrag,
//            beforeDrop: beforeDrop,
//            beforeDragOpen: beforeDragOpen,
//            onDrag: onDrag,
//            onDrop: onDrop,
            onClick: onClick,
            onExpand: onExpand,
//            beforeEditName: beforeEditName,
//            beforeRemove: beforeRemove,
//            beforeRename: beforeRename,
//            onRemove: onRemove,
//            onRename: onRename,
            onNodeCreated: onNodeCreated
        },
        view: {
            showLine: true,
//            addHoverDom: addHoverDom,
//            removeHoverDom: removeHoverDom,
//            selectedMulti: false
        }
    };

    //    function dropPrev(treeId, nodes, targetNode) {
    //        var pNode = targetNode.getParentNode();
    //        if (pNode && pNode.dropInner === false) {
    //            return false;
    //        } else {
    //            for (var i = 0, l = curDragNodes.length; i < l; i++) {
    //                var curPNode = curDragNodes[i].getParentNode();
    //                if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
    //                    return false;
    //                }
    //            }
    //        }
    //        return true;
    //    }
    function onNodeCreated(event, treeId, treeNode) {
        console.log("treeNode", treeNode);
        if (treeNode.name.indexOf("新节点") > -1) {
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            console.log("新节点创建了");
            treeObj.editName(treeNode);
        }

    }
    //    function dropInner(treeId, nodes, targetNode) {
    //        if (targetNode && targetNode.dropInner === false) {
    //            return false;
    //        } else {
    //            for (var i = 0, l = curDragNodes.length; i < l; i++) {
    //                if (!targetNode && curDragNodes[i].dropRoot === false) {
    //                    return false;
    //                } else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
    //                    return false;
    //                }
    //            }
    //        }
    //        return true;
    //    }
    //    function dropNext(treeId, nodes, targetNode) {
    //        var pNode = targetNode.getParentNode();
    //        if (pNode && pNode.dropInner === false) {
    //            return false;
    //        } else {
    //            for (var i = 0, l = curDragNodes.length; i < l; i++) {
    //                var curPNode = curDragNodes[i].getParentNode();
    //                if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
    //                    return false;
    //                }
    //            }
    //        }
    //        return true;
    //    }

    //    var log, className = "dark", curDragNodes;
    //    function beforeDrag(treeId, treeNodes) {
    //        className = (className === "dark" ? "" : "dark");
    //        showLog("[ " + getTime() + " beforeDrag ]&nbsp;&nbsp;&nbsp;&nbsp; drag: " + treeNodes.length + " nodes.");
    //        for (var i = 0, l = treeNodes.length; i < l; i++) {
    //            if (treeNodes[i].drag === false) {
    //                curDragNodes = null;
    //                return false;
    //            } else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
    //                curDragNodes = null;
    //                return false;
    //            }
    //        }
    //        curDragNodes = treeNodes;
    //        return true;
    //    }
    //    function beforeDragOpen(treeId, treeNode) {
    //        autoExpandNode = treeNode;
    //        return true;
    //    }
    //    function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
    //        className = (className === "dark" ? "" : "dark");
    //        showLog("[ " + getTime() + " beforeDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:" + moveType);
    //        showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is " + (isCopy == null ? "cancel" : isCopy ? "copy" : "move"));
    //        return true;
    //    }
    //    function onDrag(event, treeId, treeNodes) {
    //        className = (className === "dark" ? "" : "dark");
    //        showLog("[ " + getTime() + " onDrag ]&nbsp;&nbsp;&nbsp;&nbsp; drag: " + treeNodes.length + " nodes.");
    //    }
    //    function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
    //        className = (className === "dark" ? "" : "dark");
    //        showLog("[ " + getTime() + " onDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:" + moveType);
    //        showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is " + (isCopy == null ? "cancel" : isCopy ? "copy" : "move"))
    //    }
    var autoExpandNode;

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

    //    function showRemoveBtn(treeId, treeNode) {
    //        return !treeNode.isFirstNode;
    //    }
    //    function showRenameBtn(treeId, treeNode) {
    //        return !treeNode.isLastNode;
    //    }

    function setTrigger() {
        var zTree = $.fn.zTree.getZTreeObj("tree");
        zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr("checked");
    }

    //    function beforeEditName(treeId, treeNode) {
    //        className = (className === "dark" ? "" : "dark");
    //        showLog("[ " + getTime() + " beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    //        var zTree = $.fn.zTree.getZTreeObj("tree");
    //        zTree.selectNode(treeNode);
    ////            return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
    //    }

    /**
     * 用于捕获节点被删除之前的事件毁掉函数，并且根据返回值确定是否允许删除
     * @param treeId
     * @param treeNode
     */
    //    function beforeRemove(treeId, treeNode) {
    //        className = (className === "dark" ? "" : "dark");
    //        showLog("[ " + getTime() + " beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    //        var zTree = $.fn.zTree.getZTreeObj("tree");
    //        zTree.selectNode(treeNode);
    //        return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
    //    }
    //    /**
    //     * 捕获删除节点之后的事件回调函数
    //     * @param e
    //     * @param treeId
    //     * @param treeNode
    //     */
    //    function onRemove(e, treeId, treeNode) {
    //        showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
    //    }

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
    //    function beforeRename(treeId, treeNode, newName, isCancel) {
    //        className = (className === "dark" ? "" : "dark");
    ////            showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
    //        if (newName.length == 0) {
    //            alert("节点名称不能为空.");
    //            var zTree = $.fn.zTree.getZTreeObj("tree");
    //            setTimeout(function () {
    //                zTree.editName(treeNode)
    //            }, 10);
    //            return false;
    //        }
    //        return true;
    //    }
    /**
     * 用于捕获节点编辑名称结束之后的事件回调函数。
     * @param e
     * @param treeId
     * @param treeNode
     * @param isCancel
     */
    //    function onRename(e, treeId, treeNode, isCancel) {
    //        var parent = treeNode.getParentNode();
    //        if (parent) {
    //            $("#level").val(parseInt(parent.level) + 1);
    //        } else {
    //            $("#level").val(0);
    //        }
    ////            showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
    //    }

    /**
     * 节点被点击时候触发操作
     * @param e
     * @param treeId
     * @param treeNode
     */
    function onClick(e, treeId, treeNode) {
        var branchId = treeNode.id;
        var branchName = treeNode.name;
        console.log(treeNode);
        console.log(branchId);
        console.log(branchName);
        $("#branchInfo").attr("branchid", branchId);
        $("#branchInfo").val(branchName);
    }
    ;

    //    var newCount = 1;
    //    function addHoverDom(treeId, treeNode) {
    //        var sObj = $("#" + treeNode.tId + "_span");
    //        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
    //        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
    //            + "' title='添加新节点' onfocus='this.blur();'></span>";
    //        sObj.after(addStr);
    //        var btn = $("#addBtn_" + treeNode.tId);
    //        if (btn) btn.bind("click", function () {
    //            var zTree = $.fn.zTree.getZTreeObj("tree");
    //            zTree.addNodes(treeNode, {id: (100 + newCount), pId: treeNode.id, name: "添加新节点" + (newCount++)});
    //            return false;
    //        });
    //    }
    //    ;
    //    function removeHoverDom(treeId, treeNode) {
    //        $("#addBtn_" + treeNode.tId).unbind().remove();
    //    }
    //    ;
</SCRIPT>
<script>
    $(function () {
//        $("#confirm")


        $("#modal").modal('hide');

        $("#confirm").on("click", function () {
            var branchId = $("#branchInfo").attr("branchid");
            var name = $("#branchInfo").val();
            $("[name='branchId']").html("<option value='" + branchId + "' selected>" + name + "</option>")
            $("#modal").modal("hide");
        })

        $("#cancel").on("click", function () {
            $("#modal").modal("hide");
        })


    });
    var zNodes = undefined;
    function showModel() {
        if (zNodes != undefined) {
            $.fn.zTree.init($("#tree"), setting, zNodes);
            $("#callbackTrigger").bind("change", {}, setTrigger);
            $("#modal").modal("show");
        } else {
            $.ajax({
                url: "/branch/getBranchInfo.do",
                success: function (data) {
                    zNodes = eval(data);
                    $.fn.zTree.init($("#tree"), setting, zNodes);
                    $("#callbackTrigger").bind("change", {}, setTrigger);
                    $("#modal").modal("show");
                },
                error: function (data) {
                    swal("", "查找机构信息失败", "warning");
                }
            });
        }

    }
</script>
</body>
</html>
