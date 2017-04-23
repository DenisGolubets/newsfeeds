<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page errorPage="error.jsp" %>
<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/favicon.png" type="image/png">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jq.js"></script>

    <script>
        $(document).ready(function () {
            $('input[type=file]').change(function () {
                var val = $(this).val().toLowerCase();
                var regex = new RegExp("(.*?)\.(jpg|bmp|png|gif)$");
                var size = document.getElementById("file").files[0].size;
                console.log(size);
                console.log(val);
                if (!(regex.test(val)) || size > 5242880) {
                    $(this).val('');
                    alert('Please select correct file format:jpg, bmp, png, gif and size below 5Mb!');
                }
            });
        });
    </script>
</head>

<body>
<form:form method="POST" commandName="news" action="addnews" enctype="multipart/form-data">
    <table>
        <tr>
            <td>Title</td>
            <td><form:input path="title"/></td>
            <td><form:errors path="title"/></td>
        </tr>
        <tr>
            <td>Message</td>
            <td>
                <form:textarea rows="5" cols="45" path="message"/>
                </textarea>
            </td>
            <td><form:errors path="message"/></td>
        </tr>
        <tr>
            <td>Pictures</td>
            <td><input id="file" type="file" name="file"></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>