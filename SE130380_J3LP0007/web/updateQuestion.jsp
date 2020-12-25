<%-- 
    Document   : updateQuestion.jsp
    Created on : Jun 2, 2020, 10:40:20 PM
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
        <title>Update Question Page</title>
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
        <div class="container mt-3">
            <div class="card">
                <div class="card-body">
                    <h3 class="text-center">Update Question</h3>
                    <c:if test="${requestScope.ERROR != null}">
                        <font color="red">${requestScope.ERROR}</font>
                    </c:if>
                    <c:if test="${requestScope.SUCCESS != null}">
                        <font color="green">${requestScope.SUCCESS}</font>
                    </c:if>
                    <form action="MainController" method="POST">
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <span>Question ID: </span>
                                </div>
                            </div>
                            <input type="text" readonly name="txtId" value="${requestScope.questionId}" class="form-control"/>
                        </div>
                        <c:if test="${requestScope.questionErr.cont != null}">
                            <font color="red">${requestScope.questionErr.cont}</font>
                        </c:if>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <span>Content: </span>
                                </div>
                            </div>
                            <input type="text" name="txtCont" value="<c:if test="${requestScope.ERROR != null}">${param.txtCont}</c:if>" class="form-control"/>
                        </div>
                        <c:if test="${requestScope.radioErr != null}">
                            <font color="red">${requestScope.radioErr}</font>
                        </c:if>
                        <h5 class="text-success">Please click radio button to set the correct answer, rest is the wrong answer</h5>
                        <c:if test="${requestScope.questionErr.answer1 != null}">
                            <font color="red">${requestScope.questionErr.answer1}</font>
                        </c:if>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <p class="m-0 mr-4">Answer 1:</p>
                                    <input type="radio" name="txtCorrectAnswer" value="answer1">
                                </div>
                            </div>
                            <input type="text" name="txtAnswer1" value="<c:if test="${requestScope.ERROR != null}">${param.txtAnswer1}</c:if>" class="form-control"/>

                        </div>
                        <c:if test="${requestScope.questionErr.answer2 != null}">
                            <font color="red">${requestScope.questionErr.answer2}</font>
                        </c:if>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <p class="m-0 mr-4">Answer 2:</p>
                                    <input type="radio" name="txtCorrectAnswer" value="answer2">
                                </div>
                            </div>
                            <input type="text" name="txtAnswer2" value="<c:if test="${requestScope.ERROR != null}">${param.txtAnswer2}</c:if>" class="form-control"/>
                        </div>
                        <c:if test="${requestScope.questionErr.answer3 != null}">
                            <font color="red">${requestScope.questionErr.answer3}</font>
                        </c:if>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <p class="m-0 mr-4">Answer 3:</p>
                                    <input type="radio" name="txtCorrectAnswer"  value="answer3">
                                </div>
                            </div>
                            <input type="text" name="txtAnswer3" value="<c:if test="${requestScope.ERROR != null}">${param.txtAnswer3}</c:if>" class="form-control" />

                        </div>
                        <c:if test="${requestScope.questionErr.answer4 != null}">
                            <font color="red">${requestScope.questionErr.answer4}</font>
                        </c:if>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <div class="input-group-text">
                                    <p class="m-0 mr-4">Answer 4:</p>
                                    <input type="radio" name="txtCorrectAnswer" value="answer4">
                                </div>
                            </div>
                            <input type="text" name="txtAnswer4" value="<c:if test="${requestScope.ERROR != null}">${param.txtAnswer4}</c:if>" class="form-control" />
                        </div>
                        <c:if test="${requestScope.questionErr.subject != null}">
                            <font color="red">${requestScope.questionErr.subject}</font>
                        </c:if>
                        <select name="txtSubject" class="form-control mb-3">
                            <option value>Select subject</option>
                            <c:forEach items="${requestScope.subjectList}" var="dto">
                                <option value="${dto.id}" <c:if test="${requestScope.ERROR != null}"><c:if test="${requestScope.subjectChosen == dto.id}">selected</c:if></c:if>>${dto.id} - ${dto.name}</option>
                            </c:forEach>
                        </select>
                        <div class="w-100 d-flex flex-column align-items-center">
                            <input type="hidden" name="action" value="Edit"/>
                            <input type="submit" value="Update" class="btn btn-primary w-25"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
