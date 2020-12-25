<%-- 
    Document   : index
    Created on : Jun 3, 2020, 5:01:22 PM
    Author     : Peter
--%>

<%@page import="khangtl.utils.APIWrapper"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="bootstrap/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/fonts.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/font-awesome.min.css" rel="stylesheet" type="text/css" >
        <link href=“https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css”/>
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="bootstrap/js/popper.min.js" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <c:if test="${sessionScope.USER != null}">
            <c:redirect url="home.jsp">
            </c:redirect>
        </c:if>
        <title>Login Page</title>
    </head>
    <body>
        <div class="container w-30 d-flex flex-column align-items-center">
            <img src="https://upload.wikimedia.org/wikipedia/vi/8/80/FPT_New_Logo.png" width="250" height="120" class="mt-5 mb-3"/>
            <div class="border border-primary p-4 mt-3 mb-3 rounded">
                <h2>Dream Traveling</h2>
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
                    <form action="Login" method="POST">
                        <div class="form-group">
                            <label for="txtId" class="text-primary">
                                Id:
                            </label>
                            <input class="form-control <c:if test="${requestScope.userErr != null}">is-invalid</c:if><c:if test="${requestScope.userErr == null}">is-valid</c:if>" type="text" name="txtId" placeholder="Id..." value="${param.txtId}"/>
                                <div class="invalid-feedback">
                                ${requestScope.userErr.id}   
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="txtPassword" class="text-primary"> 
                                Password:
                            </label>
                            <input class="form-control <c:if test="${requestScope.userErr != null}">is-invalid</c:if><c:if test="${requestScope.userErr == null}">is-valid</c:if>" type="password" name="txtPassword" placeholder="Password..."/>
                                <div class="invalid-feedback">
                                ${requestScope.userErr.password}  
                            </div>
                        </div>
                        <div class="w-100 d-flex flex-column align-items-center">
                            <input type="submit" name="action" value="Login" class="btn btn-primary mb-3"/>
                            <!--<a href="register.jsp">Register account</a>-->
                        </div>
                        <input type="hidden" name="pageAfterLogin" value="${param.pageAfterLogin}"/>
                    </form>
                    <a href="${requestScope.FACEBOOK}" class="text-light" style="text-decoration: none">
                        <div class="text-ligh mt-2 rounded p-2 d-flex justify-content-between align-items-center" style="background-color: #3b5998">
                            Login with Facebook
                            <i class="fa fa-facebook-square"></i>
                        </div>
                    </a>
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
