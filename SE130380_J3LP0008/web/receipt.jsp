<%-- 
    Document   : receipt
    Created on : Jun 22, 2020, 9:58:17 AM
    Author     : Peter
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="bootstrap/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/fonts.css" rel="stylesheet" type="text/css" >
        <script src="bootstrap/js/popper.min.js" type="text/javascript"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Receipt Page</title>
        <c:if test="${sessionScope.USER != null}">
            <c:if test="${sessionScope.ROLE != 'Customer'}">
                <c:redirect url="ErrorController">
                    <c:param name="ERROR" value="You don't have permission to access this page">
                    </c:param>
                </c:redirect>
            </c:if>
            <c:if test="${requestScope.transaction == null}">
                <c:redirect url="Home">
                </c:redirect>
            </c:if>
        </c:if>
        <c:if test="${sessionScope.USER == null}">
            <c:redirect url="login.jsp">
                <c:param name="pageAfterLogin" value="review.jsp">
                </c:param>
            </c:redirect>
        </c:if>
    </head>
    <body>
        <div class="container mt-3">
            <div class="card">
                <div class="card-body d-flex flex-column align-items-center">
                    <div>
                        <c:if test="${requestScope.transaction != null}">
                            <h3>Your receipt</h3>
                            <p class="p-0"><b>1. Transaction details:</b></p>
                            <p class="p-0"><b>- Total:</b> ${requestScope.transaction.amount.total}$</p>
                            <p class="p-0"><b>2. Payer Information:</b></p>
                            <p class="p-0"><b>- First name:</b> ${requestScope.PAYER.firstName}</p>
                            <p class="p-0"><b>- Last name:</b> ${requestScope.PAYER.lastName}</p>
                            <p class="p-0"><b>- Email:</b> ${requestScope.PAYER.email}</p>
                            <p class="p-0"><b>3. Item list:</b></p>
                            <c:forEach items="${requestScope.itemList}" var="item">
                                <p>- ${item.quantity} ${item.name}: ${item.price}$</p>
                            </c:forEach>
                            <a href="Home" class="nav-item nav-link active text-light bg-primary text-center">Back to home</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
