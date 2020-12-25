<%-- 
    Document   : error
    Created on : May 26, 2020, 11:27:33 AM
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
        <title>Error Page</title>
    </head>
    <body>
        <div class="container w-50 mt-5 d-flex justify-content-center">
            <div class="card">
                <div class="card-body d-flex flex-column justify-content-center align-items-center">
                    <h3>Error Page</h3>
                    <h4>
                        <c:if test="${requestScope.ERROR != null}">
                            <font color="red">${requestScope.ERROR}</font>
                        </c:if>
                    </h4>
                    <a href="login.jsp">Back to Previous Page</a>
                </div>
            </div>
        </div>
    </body>
</html>
