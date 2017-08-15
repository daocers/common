<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>布谷考培|试卷策略展示</title>
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
                    <li><a href="list.do">试卷策略管理</a></li>
                    <li><a href="#" class="active">试卷策略展示</a></li>
                </ol>
            </div>


            <form class="form-horizontal" method="post" action="save.do" data-toggle="validator" role="form">
                <input id="id" type="hidden" name="id" value="${paperpolicy.id}">

                <div class="form-group">
                    <label class="control-label col-md-2">机构</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="branchId" value="${paperpolicy.branchId}"
                               required>
                        <span class="help-block with-errors">试题策略的归属机构</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">试卷策略编码</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="code" value="${paperpolicy.code}" required>
                        <span class="help-block with-errors">策略编码，便于识别和记忆</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">创建时间</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="createTime" value="${paperpolicy.createTime}"
                               required>
                        <span class="help-block with-errors"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">创建人</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="createUserId" value="${paperpolicy.createUserId}"
                               required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">所属部门</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="departmentId" value="${paperpolicy.departmentId}"
                               required>
                        <span class="help-block with-errors">创建人所在的部门</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">策略名称</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="name" value="${paperpolicy.name}" required>
                        <span class="help-block with-errors"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">所属岗位</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="stationId" value="${paperpolicy.stationId}"
                               required>
                        <span class="help-block with-errors">策略试用岗位</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">状态</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="status" value="${paperpolicy.status}" required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">updateTime</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="updateTime" value="${paperpolicy.updateTime}"
                               required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-2">updateUserId</label>
                    <div class="col-md-10">
                        <input class="form-control" type="text" name="updateUserId" value="${paperpolicy.updateUserId}"
                               required>
                        <span class="help-block with-errors">提示信息</span>
                    </div>
                </div>
                <input type="hidden" value="${type}" id="type">

                <div class="form-group">
                    <label class="control-label col-md-2">试题策略</label>
                    <div class="col-md-10 table-responsive">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>题型</th>
                                <th>试题策略</th>
                                <th>题量</th>
                                <th>分值</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td><select>
                                    <option>请选择</option>
                                </select></td>
                                <td><select>
                                    <option>请选择</option>
                                </select></td>
                                <td>10</td>
                                <td>1</td>
                            </tr>
                            </tbody>
                        </table>
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
<script></script>
</body>
</html>
