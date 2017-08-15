<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<body>
<table class="table table-responsive table-bordered preview">
    <thead>基本信息</thead>
    <tbody>
    <tr>
        <td class="col-md-2">名称</td>
        <td class="col-md-4">${scene.name}</td>
        <td class="col-md-2">编号</td>
        <td class="col-md-4">${scene.code}</td>
    </tr>
    <tr>
        <td>开始时间</td>
        <td><fmt:formatDate value="${scene.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        <td>结束时间</td>
        <td><fmt:formatDate value="${scene.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    </tr>
    <tr>
        <td>顺延时间（分）</td>
        <td>${scene.delay}</td>
        <td>考试时长（分）</td>
        <td>${scene.duration}</td>
    </tr>
    <tr>
        <td>授权码</td>
        <td>${scene.authCode}</td>
        <td>是否允许换卷</td>
        <td>${scene.changePaper == 0 ? "是" : "否"}</td>
    </tr>
    </tbody>
</table>

<table class="table table-responsive table-bordered preview">
    <thead>
    试卷策略信息
    </thead>
    <tbody>
    <tr>
        <td class="col-md-2">策略名称</td>
        <td colspan="3">${paperPolicy.name}</td>
    </tr>
    <tr>
        <td class="col-md-2">题量</td>
        <td class="col-md-4">${paperPolicy.count}</td>
        <td class="col-md-2">总分</td>
        <td class="col-md-4">${paperPolicy.score}</td>
    </tr>
    <tr>
        <td class="col-md-2">策略明细</td>
        <td colspan="3">${paperPolicy.content}</td>
    </tr>
    </tbody>

</table>
</body>
