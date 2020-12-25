<%-- 
    Document   : startQuiz
    Created on : Jun 1, 2020, 10:15:54 PM
    Author     : Peter
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set value="${endTime}" var="countTime"></c:set>
    <!DOCTYPE html>
    <html>
        <head>
            <script>
                var countDownDate = new Date("${countTime}").getTime();

                var x = setInterval(function () {

                    // Get today's date and time
                    var now = new Date().getTime();

                    // Find the distance between now and the count down date
                    var distance = countDownDate - now;

                    // Time calculations for days, hours, minutes and seconds
                    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                    var seconds = Math.floor((distance % (1000 * 60)) / 1000);

                    // If the count down below 1 mins, warning
                    if (minutes === 1) {
                        document.getElementById("warning").innerHTML = "Hurry up...";
                        document.getElementById("warning").style.color = "orange";
                    } else if (minutes === 0) {
                        document.getElementById("warning").innerHTML = "Please finish your test...";
                        document.getElementById("warning").style.color = "red";
                    }
                    // Display the result in the element with id="demo"
                    if (minutes < 10) {
                        minutes = "0" + minutes;
                    }
                    if (seconds < 10) {
                        seconds = "0" + seconds;
                    }
                    document.getElementById("timer").innerHTML = minutes + ":" + seconds;

                    // If the count down is finished, submit test
                    if (distance <= 0) {
                        clearInterval(x);
                        submitTest();
                    }
                }, 1000);

                function submitTest() {
                    document.getElementById("submitForm").submit();
                }
        </script>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/fonts.css" rel="stylesheet" type="text/css" >
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="bootstrap/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>
        <script src="bootstrap/js/popper.min.js" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Start Quiz Page</title>
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
        <div class="container mt-3 w-50">
            <h1 class="my-3">Hi ${sessionScope.FULLNAME}, good luck!</h1>
            Time left: <span id="timer"></span>
            <p id="warning"></p>
            <div class="card">
                <div class="card-body">
                    <form action="MainController" method="POST" id="submitForm">
                        <p><b>${requestScope.currentQuestionIndex + 1}. ${requestScope.questionTest.cont}</b></p>
                        <c:forEach items="${requestScope.questionTest.answerList}" var="dto" varStatus="counter">
                            <div class="d-flex flex-row justify-content-between align-items-center">
                                <p class="m-0 m-2"><span>&#${counter.index + 65};) </span>${dto.cont}</p>
                                <input type="radio" name="txtSelect" value="${dto.id}"
                                       <c:forEach var="pair" items="${submitList}">
                                           <c:if test="${pair.value == dto.id}">checked</c:if>
                                       </c:forEach> />
                            </div>
                        </c:forEach>

                        <div class="d-flex flex-row w-100 justify-content-between align-items-center mt-4">
                            <c:if test="${requestScope.currentQuestionIndex + 1 != 1}">
                                <input type="submit" name="action" value="Prev" class="btn btn-primary"/>
                            </c:if>
                            <p class="m-0 mx-4">${requestScope.currentQuestionIndex + 1}</p>
                            <c:if test="${requestScope.currentQuestionIndex + 1 != sessionScope.questionListTestSize}">
                                <input type="submit" name="action" value="Next" class="btn btn-primary"/>
                            </c:if>
                        </div>
                        <input type="hidden" name="txtQuestion" value="${requestScope.questionTest.id}"/>
                        <input type="hidden" name="currentQuestionIndex" value="${requestScope.currentQuestionIndex}"/>
                        <input type="hidden" name="action" value="Submit" />
                        <div class="w-100 d-flex justify-content-center mt-4">
                            <input type="submit" value="Submit" class="btn btn-success"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
