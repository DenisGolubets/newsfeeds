<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/favicon.png" type="image/png">
    <title>Home</title>

</head>
<body>
<div id="header">
    <jsp:include page="/jsp/headerMenue.jsp"/>
</div>
<c:forEach items="${newsList}" var="news">
    <table border="0">
        <tbody>
        <tr>
            <c:if test="${!empty news.getPicturePath()}">
                <td>
                    <img src="${pageContext.request.contextPath}${news.getPicturePath()}" width="100" height="100"/>
                </td>
            </c:if>
            <td>
                <table>
                    <tbody>
                    <tr>
                        <td><h1>${news.getTitle()}</h1></td>
                    </tr>
                    <tr>
                        <td>${news.getNewsDate()}</td>
                    </tr>
                    <tr>
                        <td>${news.getMessage()}</td>
                    </tr>
                    </tbody>
                </table>
            </td>
        </tr>
        </tbody>
    </table>
</c:forEach>

<p>
    <c:forEach begin="1" end="${pages}" varStatus="loop">
        <a href="${loop.count}">${loop.count}</a>
    </c:forEach>
</p>
</body>
</html>
