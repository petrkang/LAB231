<%-- 
    Document   : student
    Created on : Jun 1, 2020, 7:23:22 AM
    Author     : Peter
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/fonts.css" rel="stylesheet" type="text/css" >
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="bootstrap/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>
        <script src="bootstrap/js/popper.min.js" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Page</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <c:url var="homeLink" value="MainController" >
                        <c:param name="action" value="Home" />
                    </c:url>
                    <a class="nav-item nav-link active text-light bg-primary" href="${homeLink}">Home <span class="sr-only">(current)</span></a>
                    <c:url var="logoutLink" value="MainController" >
                        <c:param name="action" value="Logout" />
                    </c:url>
                    <a class="nav-item nav-link text-danger" href="${logoutLink}">Logout</a>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="card">
                <div class="card-body">
                    <h1 class="my-3">Welcome student, ${sessionScope.FULLNAME}</h1>
                    <h3>Start your quiz online test</h3>
                    <c:if test="${requestScope.ERROR != null}">
                        <font color="red">${requestScope.ERROR}</font>
                    </c:if>
                    <c:if test="${requestScope.SUCCESS != null}">
                        <font color="green">${requestScope.SUCCESS}</font>
                    </c:if>
                    <form action="MainController" method="POST" class="form-inline">
                        <select name="txtSubject" class="form-control">
                            <option value>Select subject</option>
                            <c:forEach items="${requestScope.subjectList}" var="dto">
                                <option value="${dto.id}">${dto.id} - ${dto.name}</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" value="StartQuiz" name="action"/>
                        <input type="submit" value="Start" class="btn btn-primary"/>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
