<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/favicon.png" type="image/png">
    <title>Add news</title>
</head>
<!-- -*- HTML -*- -->

<body>
<div id="header">
    <jsp:include page="/jsp/headerMenue.jsp"/>

</div>
<div id="main">
    <div id="content">
        <form:form method="POST" commandName="news" action="addnews" enctype="multipart/form-data">
            <table>
                <tr>
                    <td>Title</td>
                    <td>
                        <form:input path="title"/>
                    </td>
                </tr>
                <tr>
                    <td>Message</td>
                    <td>
                        <form:textarea rows="5" cols="45" path="message"/>
                        </textarea>
                    </td>
                </tr>
                <tr>
                    <td>Pictures</td>
                    <td><input type="file" name="file"></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Submit"/></td>
                </tr>
            </table>
        </form:form>

    </div>
</div>
</body>
</html>