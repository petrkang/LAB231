<%-- 
    Document   : review
    Created on : Jun 21, 2020, 6:41:00 PM
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
        <title>Review Payment Page</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="collapse navbar-collapse">
                <div class="navbar-nav">
                    <a href="home.jsp" class="nav-item nav-link active text-light bg-primary">Home <span class="sr-only">(current)</span></a>
                    <c:if test="${sessionScope.ROLE eq 'Admin'}">
                        <a class="nav-item nav-link active" href="createTour.jsp">Create New Tour</a>
                    </c:if>
                </div>
            </div>
            <div class="navbar-nav">
                <c:if test="${sessionScope.USER != null}">
                    <div id="view-cart">
                        <c:if test="${sessionScope.ROLE == 'Customer'}">
                            <a href="cart.jsp" class="nav-item nav-link text-primary">View cart <c:if test="${sessionScope.CART != null}">(${sessionScope.CART.getItems().size()})</c:if></a>
                        </c:if>
                    </div>
                    <div class="d-flex align-items-center">
                        <p class="m-0">Hello, ${sessionScope.FULLNAME}</p>
                    </div>
                    <c:url var="logoutLink" value="Logout" >
                        <c:param name="action" value="Logout" />
                    </c:url>
                    <a class="nav-item nav-link text-danger" href="${logoutLink}">Logout</a>
                </c:if>
                <c:if test="${sessionScope.USER == null}">
                    <a class="nav-item nav-link text-light btn btn-success mr-3" href="login.jsp">Login</a>
<!--                    <a class="nav-item nav-link text-light btn btn-warning" href="register.jsp">Register</a>-->
                </c:if>
            </div>
        </nav>
        <div class="container mt-3">
            <div class="card">
                <div class="card-body d-flex flex-column align-items-center">
                    <h3>Please review before paying</h3>
                    <form action="ExecutePayment" method="POST">
                        <div>
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
                            <input type="hidden" name="paymentId" value="${param.paymentId}" />
                            <input type="hidden" name="PayerID" value="${param.PayerID}" />
                            <div class="d-flex justify-content-center">
                                <input type="submit" name="action" value="Pay" class="btn btn-outline-info"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
