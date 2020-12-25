<%-- 
    Document   : index
    Created on : May 26, 2020, 8:02:55 AM
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
        <title>Login Page</title>
    </head>
    <body>
        <div class="container w-30 d-flex flex-column align-items-center">
            <img src="https://upload.wikimedia.org/wikipedia/vi/8/80/FPT_New_Logo.png" width="250" height="120" class="mt-5 mb-3"/>
            <div class="border border-primary p-4 mt-3 mb-3 rounded">
                <h2>Take Quiz Online Program</h2>
            </div>
            <h4>Login Page</h4>
            <c:if test="${requestScope.ERROR != null}">
                <font color="red">${requestScope.ERROR}</font>
            </c:if>
            <c:if test="${requestScope.SUCCESS != null}">
                <font color="green">${requestScope.SUCCESS}</font>
            </c:if>
            <div class="card mt-3">
                <div class="card-body">
                    <form action="MainController" method="POST">
                        <div class="form-group">
                            <label for="txtEmail" class="text-primary">
                                Email:
                            </label>
                            <input class="form-control <c:if test="${requestScope.userErr.email != null}">is-invalid</c:if><c:if test="${requestScope.userErr.email == null}">is-valid</c:if>" type="text" name="txtEmail" placeholder="Email..." value="${param.txtEmail}"/>
                                <div class="invalid-feedback">
                                ${requestScope.userErr.email}   
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="txtPassword" class="text-primary"> 
                                Password:
                            </label>
                            <input class="form-control <c:if test="${requestScope.userErr.password != null}">is-invalid</c:if><c:if test="${requestScope.userErr.password == null}">is-valid</c:if>" type="password" name="txtPassword" placeholder="Password..."/>
                                <div class="invalid-feedback">
                                ${requestScope.userErr.password}  
                            </div>
                        </div>
                        <div class="w-100 d-flex flex-column align-items-center">
                            <input type="submit" name="action" value="Login" class="btn btn-primary mb-3"/>
                            <a href="register.jsp">Register account</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
<footer class="page-footer font-small blue">

    <!-- Copyright -->
    <div class="footer-copyright text-center py-3">© 2020 Copyright:
        <a href="https://www.facebook.com/haphongpk12/" target="_blank">Peter</a>
    </div>
    <!-- Copyright -->

</footer>
