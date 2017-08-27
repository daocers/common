<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<div class="table-responsive">
    <table class="table table-bordered">
        <thead>
        <tr>
            <%--<th><input type="checkbox" class="selectAll"></th>--%>
            <th>考试名称</th>
            <th>场次编码</th>
            <th>识别码</th>
            <th>开场时间</th>
            <th>顺延时间</th>
            <th>考试时长</th>
            <th>结束时间</th>
            <th>允许换卷</th>
            <th>创建时间</th>
            <th>开场用户</th>
            <th>机构</th>
            <th>部门</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pi.data}" var="scene" varStatus="line">
            <tr>
                    <%--<td><input type="checkbox" objId="${scene.id}"></td>--%>
                <td><a href="edit.do?type=detail&id=${scene.id}">${scene.name}</a></td>
                <td>${scene.code}</td>
                <td>${scene.authCode}</td>
                <td><fmt:formatDate value="${scene.beginTime}"
                                    pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                <td>${scene.delay}</td>
                <td>${scene.duration}</td>
                <td><fmt:formatDate value="${scene.endTime}"
                                    pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                <td>${scene.changePaper == 0 ? "是":"否"}</td>
                <td><fmt:formatDate value="${scene.createTime}"
                                    pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                <td>${scene.createUser == null ? "" : scene.createUser.username}</td>
                <td>${scene.branch == null ? "" : scene.branch.name}</td>
                <td>${scene.department == null ? "" : scene.department.name}</td>
                <td>
                        ${statusMap.get(scene.status)}
                        <%--<c:if conf.dev.test="${scene.status == 0}">--%>
                        <%--信息待完善--%>
                        <%--</c:if>--%>
                        <%--<c:if conf.dev.test="${scene.status == 1}">--%>
                        <%--创建成功--%>
                        <%--</c:if>--%>
                        <%--<c:if conf.dev.test="${scene.status == 2}">--%>
                        <%--已开场--%>
                        <%--</c:if>--%>
                        <%--<c:if conf.dev.test="${scene.status == 3}">--%>
                        <%--已封场--%>
                        <%--</c:if>--%>
                        <%--<c:if conf.dev.test="${scene.status == 4}">--%>
                        <%--已取消--%>
                        <%--</c:if>--%>
                </td>
                <td>
                    <a href="/scene/index.do?id=${scene.id}&type=detail" class="opr">详情</a>
                    <c:if test="${scene.status <= 1}">
                        <a href="/scene/index.do?id=${scene.id}" class="opr">修改</a>
                    </c:if>
                        <%--<a href="javascript:del(${scene.id})" class="opr">删除</a>--%>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>
<div class="after-table">
    <div class="pull-left form-inline">
        <select class="form-control show-count">
            <option value="10"
                    <c:if test="${ pi.showCount == 10 }">selected</c:if> >10
            </option>
            <option value="25" <c:if test="${ pi.showCount == 25}">selected</c:if>>25</option>
            <option value="50" <c:if test="${ pi.showCount == 50}">selected</c:if>>50</option>
        </select>
        <div>条/页</div>

    </div>
    <div class="pull-right">
        <jsp:include page="../template/page-nav.jsp"/>
    </div>
</div>