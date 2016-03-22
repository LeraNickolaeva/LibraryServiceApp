<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
    <h1>Author: ${bookVO.author.name} ${bookVO.author.surname}</h1>
    <h1>Title: ${bookVO.book.name}</h1>
    <h1>Description: ${bookVO.book.description}</h1>
    <h1>Count of views: ${bookVO.book.countOfViews}</h1>
    <table border="1">
        <tr>
            <th>User</th>
            <th>Review</th>
        </tr>
        <c:forEach var="comment" items="${bookVO.listOfComments}">
            <tr>
                <td>${comment.user}</td>
                <td>${comment.review}</td>
            </tr>
        </c:forEach>
    </table>
    <a href="${bookVO.readPath.path}">Read online</a>
    <form action="download" method="get" enctype="multipart/form-data">
        <input type="hidden" name="command" value="download_book">
        <input type="hidden" name="bookPath" value="${bookVO.downloadPath.path}">
        <input type="submit" value="download">
    </form>
</body>
</html>