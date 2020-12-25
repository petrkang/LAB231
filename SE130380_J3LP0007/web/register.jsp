<%-- 
    Document   : register
    Created on : May 26, 2020, 11:34:27 AM
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
        <title>Register Page</title>
    </head>
    <body>
        <div class="container w-30 d-flex flex-column align-items-center">
            <img src="https://upload.wikimedia.org/wikipedia/vi/8/80/FPT_New_Logo.png" width="250" height="120" class="mt-5 mb-3"/>
            <div class="border border-primary p-4 mt-3 mb-3 rounded">
                <h2>Take Quiz Online Program</h2>
            </div>
            <h4>Register Page</h4>
            <c:if test="${requestScope.ERROR != null}">
                <font color="red">${requestScope.ERROR}</font>
            </c:if>
            <c:if test="${requestScope.SUCCESS != null}">
                <font color="green">${requestScope.SUCCESS}</font>
            </c:if>
            <div class="card">
                <div class="card-body">
                    <form action="MainController" method="POST" accept-charset="UTF-8">
                        <div class="form-row">
                            <div class="col-md-7 mb-3">
                                <label for="txtLastname" class="text-primary">
                                    Firstname:
                                </label>
                                <input class="form-control <c:if test="${requestScope.userErr.firstname != null}">is-invalid</c:if><c:if test="${requestScope.userErr.firstname == null}">is-valid</c:if>" type="text" name="txtFirstname" placeholder="Firstname..." value="${param.txtFirstname}"/>
                                <c:if test="${requestScope.userErr.firstname != null}">
                                    <div class="invalid-feedback">
                                        ${requestScope.userErr.firstname}   
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-md-5 mb-3">
                                <label for="txtLastname" class="text-primary">
                                    Lastname:
                                </label>
                                <input class="form-control <c:if test="${requestScope.userErr.lastname != null}">is-invalid</c:if><c:if test="${requestScope.userErr.lastname == null}">is-valid</c:if>" type="text" name="txtLastname" placeholder="Lastname..." value="${param.txtLastname}"/>
                                <c:if test="${requestScope.userErr.lastname != null}">
                                    <div class="invalid-feedback">
                                        ${requestScope.userErr.lastname}   
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="txtEmail" class="text-primary">
                                Email:
                            </label>
                            <input class="form-control <c:if test="${requestScope.userErr.email != null}">is-invalid</c:if><c:if test="${requestScope.userErr.email == null}">is-valid</c:if>" type="text" name="txtEmail" placeholder="Email..." value="${param.txtEmail}"/>
                            <c:if test="${requestScope.userErr.email != null}">
                                <div class="invalid-feedback">
                                    ${requestScope.userErr.email}   
                                </div>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="txtPassword" class="text-primary"> 
                                Password:
                            </label>
                            <input class="form-control <c:if test="${requestScope.userErr.password != null}">is-invalid</c:if><c:if test="${requestScope.userErr.password == null}">is-valid</c:if>" type="password" name="txtPassword" placeholder="Password..."/>
                            <c:if test="${requestScope.userErr.password != null}">
                                <div class="invalid-feedback">
                                    ${requestScope.userErr.password}  
                                </div>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label for="txtConfirm" class="text-primary"> 
                                Password:
                            </label>
                            <input class="form-control <c:if test="${requestScope.userErr.confirm != null}">is-invalid</c:if><c:if test="${requestScope.userErr.confirm == null}">is-valid</c:if>" type="password" name="txtConfirm" placeholder="Confirm password..."/>
                            <c:if test="${requestScope.userErr.confirm != null}">
                                <div class="invalid-feedback">
                                    ${requestScope.userErr.confirm}  
                                </div>
                            </c:if>
                        </div>
                        Admin: <input type="checkbox" name="txtRole" value="Admin"/>
                        <br/>
                        <div class="w-100 d-flex flex-column align-items-center">
                            <input type="submit" name="action" value="Register" class="btn btn-primary mb-3"/>
                            Đã có tài khoản? <a href="login.jsp">Đăng nhập</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
