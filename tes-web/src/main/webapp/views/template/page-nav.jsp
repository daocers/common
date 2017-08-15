<%--
  Created by IntelliJ IDEA.
  User: daocers
  Date: 2016/7/14
  Time: 11:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<%--<div>--%>
<%--<label class="control-label">总共${pi.count}条 ${pi.curPage}/${pi.pageSize}页</label>--%>
<%--</div>--%>
<nav>
    <ul class="pagination">
        <li><a class="info"> 共${pi.count}条记录, ${pi.curPage}/${pi.pageSize}页</a></li>

        <c:if test="${pi.curPage == 1}">
            <li class="disabled"><a href="#">首页</a></li>
            <li class="disabled"><a class=""><span>上一页</span></a>
            </li>
        </c:if>
        <c:if test="${pi.curPage  > 1}">
            <li><a href="javascript:toPage(1);">首页</a></li>
            <li><a href="javascript:toPage(${pi.curPage} - 1);"><span>上一页</span></a>
            </li>
        </c:if>
        <c:if test="${pi.pageSize > 5}">
            <c:if test="${pi.curPage > 3}">
                <c:if test="${pi.pageSize > (pi.curPage + 2)}">
                    <c:set var="end" value="${pi.curPage + 2}"></c:set>
                    <c:set var="begin" value="${pi.curPage - 2}"/>
                </c:if>
                <c:if test="${pi.pageSize <= (pi.curPage + 2)}">
                    <c:set var="end" value="${pi.pageSize}"/>
                    <c:set var="begin" value="${pi.pageSize - 2}"/>
                </c:if>
                <c:forEach var="page" begin="${begin}" end="${end}">
                    <c:if test="${page == pi.curPage}">
                        <li class="active"><a>${page}</a></li>
                    </c:if>
                    <c:if test="${page != pi.curPage}">
                        <li><a href="javascript:toPage(${page});">${page}</a></li>
                    </c:if>
                </c:forEach>
            </c:if>
            <c:if test="${pi.curPage <= 3}">
                <c:forEach var="page" begin="1" end="5">
                    <c:if test="${page == pi.curPage}">
                        <li class="active"><a>${page}</a></li>
                    </c:if>
                    <c:if test="${page != pi.curPage}">
                        <li><a href="javascript:toPage(${page});">${page}</a></li>
                    </c:if>
                </c:forEach>
            </c:if>
        </c:if>
        <c:if test="${pi.pageSize <= 5}">
            <c:forEach var="page" begin="1" end="${pi.pageSize}">
                <c:if test="${page == pi.curPage}">
                    <li class="active"><a>${page}</a></li>
                </c:if>
                <c:if test="${page != pi.curPage}">
                    <li><a href="javascript:toPage(${page});">${page}</a></li>
                </c:if>
            </c:forEach>
        </c:if>
        <c:if test="${pi.curPage == pi.pageSize}">
            <li class="disabled"><a><span>下一页</span></a></li>
            <li class="disabled"><a><span>尾页</span></a></li>

        </c:if>
        <c:if test="${pi.curPage < pi.pageSize}">
            <li><a href="javascript:toPage(${pi.curPage} + 1);"><span>下一页</span></a></li>
            <li><a href="javascript:toPage(${pi.pageSize});"><span>尾页</span></a></li>
        </c:if>
    </ul>
</nav>
