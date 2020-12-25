<%-- 
    Document   : admin
    Created on : May 26, 2020, 2:50:38 PM
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
        <title>Admin Page</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <c:url var="homeLink" value="MainController" >
                        <c:param name="action" value="Home" />
                    </c:url>
                    <a class="nav-item nav-link active text-light bg-primary" href="${homeLink}">Home <span class="sr-only">(current)</span></a>
                    <c:url var="createQuestionLink" value="MainController">
                        <c:param name="action" value="NewQuestion"></c:param>
                    </c:url>
                    <a class="nav-item nav-link active" href="${createQuestionLink}">Create Question</a>
                    <c:url var="logoutLink" value="MainController" >
                        <c:param name="action" value="Logout" />
                    </c:url>
                    <a class="nav-item nav-link text-danger" href="${logoutLink}">Logout</a>
                </div>
            </div>
        </nav>
        <div class="container">
            <h1 class="my-3">Welcome admin, ${sessionScope.FULLNAME}</h1>
            <c:if test="${requestScope.ERROR != null}">
                <font color="red">${requestScope.ERROR}</font>
            </c:if>
            <c:if test="${requestScope.SUCCESS != null}">
                <font color="green">${requestScope.SUCCESS}</font>
            </c:if>
            <div class="card">
                <div class="card-body p-0 p-3">
                    <nav class="navbar navbar-light bg-light p-0">
                        <form action="MainController" method="POST" class="d-flex flex-row w-100 justify-content-between">
                            <input class="form-control" type="text" name="txtSearch" placeholder="Search question content..."/>
                            <input type="hidden" name="txtSearch" value="${param.txtSearch}" />
                            <select name="txtSubject" class="form-control">
                                <option value>Select subject</option>
                                <c:forEach items="${requestScope.subjectList}" var="dto">
                                    <option value="${dto.id}" <c:if test="${sessionScope.subjectSelected == dto.id}">selected</c:if>>${dto.id} - ${dto.name}</option>
                                </c:forEach>
                            </select>
                            <select name="txtStatus" class="form-control w-50">
                                <option value>Select status</option>
                                <c:forEach items="${requestScope.statusList}" var="dto">
                                    <option value="${dto.name}" <c:if test="${sessionScope.statusSelected == dto.name}">selected</c:if>>${dto.name}</option>
                                </c:forEach>
                            </select>
                            <input type="submit" name="action" value="Search" class="btn btn-outline-success"/>
                        </form>
                    </nav>

                    <c:if test="${requestScope.questionList != null}">
                        <c:if test="${not empty requestScope.questionList}" var="questionList">
                            <div class="w-100 d-flex flex-column align-items-center p-2">
                                <c:forEach items="${requestScope.questionList}" var="dto" varStatus="counter">
                                    <div class="w-100 mt-2 p-2 d-flex flex-row justify-content-between align-items-center border">
                                        <div class="dropdown w-50">
                                            <div class="p-0 btn dropdown-toggle d-flex flex-row align-items-center justify-content-between" type="button" id="dropdownMenuBtn" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <p class="m-0 text-left text-wrap"><b>${counter.count}. ${dto.cont} - ${dto.createDate}</b></p>
                                            </div>
                                            <div class="dropdown-menu w-100 pl-2" aria-labelledby="dropdownMenuBtn">
                                                <c:forEach items="${dto.answerList}" var="answer" varStatus="counterAns">
                                                    <c:if test="${answer.isCorrect == true}">
                                                        <font color="green">
                                                        <p class="m-0 m-2">
                                                            <span>&#${counterAns.index + 65};) </span>${answer.cont}</p>
                                                        </font>
                                                    </c:if>
                                                    <c:if test="${answer.isCorrect == false}">
                                                        <font color="red">
                                                        <p class="m-0 m-2">
                                                            <span>&#${counterAns.index + 65};) </span>${answer.cont}</p>
                                                        </font>
                                                    </c:if>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <form action="MainController" method="POST">
                                            <input type="hidden" name="txtSearch" value="${param.txtSearch}" />
                                            <input type="hidden" name="txtSubject" value="${param.txtSubject}" />
                                            <input type="hidden" name="txtStatus" value="${param.txtStatus}" />
                                            <input type="hidden" name="questionId" value="${dto.id}" />
                                            <input type="submit" name="action" value="Update" class="btn btn-primary" />
                                        </form>
                                        <c:url var="deleteQuestion" value="MainController" >
                                            <c:param name="action" value="Delete" />
                                            <c:param name="txtSearch" value="${param.txtSearch}" />
                                            <c:param name="txtSubject" value="${param.txtSubject}" />
                                            <c:param name="txtStatus" value="${param.txtStatus}" />
                                            <c:param name="questionId" value="${dto.id}" />
                                        </c:url>
                                        <a href="${deleteQuestion}">Delete</a>
                                    </div>
                                </c:forEach>
                                <div class="d-flex flex-row w-25 justify-content-center mt-4">
                                    <c:if test="${requestScope.currentPage != 1}">
                                        <c:url var="prevLink" value="SearchController" >
                                            <c:param name="action" value="Prev" />
                                            <c:param name="txtSearch" value="${param.txtSearch}" />
                                            <c:param name="txtSubject" value="${param.txtSubject}" />
                                            <c:param name="txtStatus" value="${param.txtStatus}" />
                                        </c:url>
                                        <a href="${prevLink}">Prev</a>
                                    </c:if>
                                    <input type="hidden" name="txtCurrentPage" value="${requestScope.currentPage}"/>
                                    <p class="m-0 mx-4">${requestScope.currentPage}</p>
                                    <c:url var="nextLink" value="SearchController" >
                                        <c:param name="action" value="Next" />
                                        <c:param name="txtSearch" value="${param.txtSearch}" />
                                        <c:param name="txtSubject" value="${param.txtSubject}" />
                                        <c:param name="txtStatus" value="${param.txtStatus}" />
                                    </c:url>
                                    <a href="${nextLink}">Next</a>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${!questionList}">
                            <p class="m-0 my-2">No results found</p>
                        </c:if>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
