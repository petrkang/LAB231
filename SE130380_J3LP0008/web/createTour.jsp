<%-- 
    Document   : createTour
    Created on : Jun 7, 2020, 3:51:15 PM
    Author     : Peter
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <script src="bootstrap/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>
        <link href="bootstrap/css/fonts.css" rel="stylesheet" type="text/css" >
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="bootstrap/js/popper.min.js" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <c:if test="${sessionScope.USER != null}">
            <c:if test="${sessionScope.ROLE != 'Admin'}">
                <c:redirect url="ErrorController">
                    <c:param name="ERROR" value="You don't have permission to access this page">
                    </c:param>
                </c:redirect>
            </c:if>
        </c:if>
        <c:if test="${sessionScope.USER == null}">
            <c:redirect url="login.jsp">
                <c:param name="pageAfterLogin" value="createTour.jsp">
                </c:param>
            </c:redirect>
        </c:if>
        <title>Create Tour Page</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="collapse navbar-collapse">
                <div class="navbar-nav">
                    <a class="nav-item nav-link active text-light bg-primary" href="Home">Home <span class="sr-only">(current)</span></a>
                    <c:if test="${sessionScope.ROLE eq 'Admin'}">
                        <a class="nav-item nav-link active" href="createTour.jsp">Create New Tour</a>
                    </c:if>
                </div>
            </div>
            <div class="navbar-nav">
                <c:if test="${sessionScope.USER != null}">
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
                <div class="card-body">
                    <h3 class="text-center">
                        Create New Tour
                    </h3>
                    <c:if test="${requestScope.ERROR != null}">
                        <font color="red">${requestScope.ERROR}</font>
                    </c:if>
                    <c:if test="${requestScope.SUCCESS != null}">
                        <font color="green">${requestScope.SUCCESS}</font>
                    </c:if>
                    <form action="CreateTour" method="POST">
                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Name: </label>
                            <div class="col-sm-10">
                                <input type="text" name="txtName" value="<c:if test="${requestScope.ERROR != null}">${param.txtName}</c:if>" class="form-control <c:if test="${requestScope.tourErr.name != null}">is-invalid</c:if><c:if test="${requestScope.tourErr.name == null}">is-valid</c:if>" placeholder="Name...">
                                    <div class="invalid-feedback">
                                    ${requestScope.tourErr.name}  
                                </div>    
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Select place: </label>
                            <div class="d-flex flex-column col-sm-5">
                                <select name="departure" class="form-control <c:if test="${requestScope.tourErr.departure != null}">is-invalid</c:if><c:if test="${requestScope.tourErr.departure == null}">is-valid</c:if>">
                                        <option value>--Select departure--</option>
                                    <c:forEach items="${sessionScope.placeList}" var="dto">
                                        <option value="${dto.name}" <c:if test="${sessionScope.departureSelected == dto.name}">selected</c:if>>${dto.name}</option>
                                    </c:forEach>
                                </select>
                                <div class="invalid-feedback">
                                    ${requestScope.tourErr.departure}  
                                </div>
                            </div>
                            <div class="d-flex flex-column col-sm-5">
                                <select name="destination" class="form-control <c:if test="${requestScope.tourErr.destination != null}">is-invalid</c:if><c:if test="${requestScope.tourErr.destination == null}">is-valid</c:if>">
                                        <option value>--Select destination--</option>
                                    <c:forEach items="${sessionScope.placeList}" var="dto">
                                        <option value="${dto.name}" <c:if test="${sessionScope.destinationSelected == dto.name}">selected</c:if>>${dto.name}</option>
                                    </c:forEach>
                                </select>
                                <div class="invalid-feedback">
                                    ${requestScope.tourErr.destination}  
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Select time: </label>
                            <div class="d-flex flex-column col-sm-5">
                                <input type="datetime-local" value="<c:if test="${requestScope.ERROR != null}">${param.fromDate}</c:if>" name="fromDate" class="form-control <c:if test="${requestScope.tourErr.fromDate != null}">is-invalid</c:if><c:if test="${requestScope.tourErr.fromDate == null}">is-valid</c:if>"/>
                                    <div class="invalid-feedback">
                                    ${requestScope.tourErr.fromDate}  
                                </div>
                            </div>
                            <div class="d-flex flex-column col-sm-5">
                                <input type="datetime-local" value="<c:if test="${requestScope.ERROR != null}">${param.toDate}</c:if>" name="toDate"  class="form-control <c:if test="${requestScope.tourErr.toDate != null}">is-invalid</c:if><c:if test="${requestScope.tourErr.toDate == null}">is-valid</c:if>"/>
                                    <div class="invalid-feedback">
                                    ${requestScope.tourErr.toDate}  
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Price: </label>
                            <div class="col-sm-10">
                                <input type="text" name="txtPrice" value="<c:if test="${requestScope.ERROR != null}">${param.txtPrice}</c:if>" class="form-control <c:if test="${requestScope.tourErr.price != null}">is-invalid</c:if><c:if test="${requestScope.tourErr.price == null}">is-valid</c:if>" placeholder="Price...">
                                    <div class="invalid-feedback">
                                    ${requestScope.tourErr.price}  
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Quota </label>
                            <div class="col-sm-10">
                                <input type="text" name="txtQuota" value="<c:if test="${requestScope.ERROR != null}">${param.txtQuota}</c:if>" class="form-control <c:if test="${requestScope.tourErr.quota != null}">is-invalid</c:if><c:if test="${requestScope.tourErr.quota == null}">is-valid</c:if>" placeholder="Quota...">
                                    <div class="invalid-feedback">
                                    ${requestScope.tourErr.quota}  
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-2 col-form-label">Image: </label>
                            <div class="col-sm-10">
                                <input id="file-upload" type="file" accept="image/*" class="form-control <c:if test="${requestScope.tourErr.image != null}">is-invalid</c:if><c:if test="${requestScope.tourErr.image == null}">is-valid</c:if>" placeholder="Image...">
                                    <div class="invalid-feedback">
                                    ${requestScope.tourErr.image}  
                                </div>
                                <div class="w-50 mt-3">
                                    <img id="image-show" width="100%" height="100%"/>
                                </div>
                            </div>
                        </div>
                        <div class="w-100 d-flex justify-content-center mt-3">
                            <input type="hidden" name="txtImage" id="image-receive"/>
                            <input type="submit" name="action" value="Insert" class="btn btn-outline-success w-50"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        document.getElementById("file-upload").onchange = function (event) {
            var reader = new FileReader();
            reader.readAsDataURL(event.srcElement.files[0]);
            reader.onload = function () {
                var fileContent = reader.result;
                document.getElementById("image-show").setAttribute("src", fileContent);
                document.getElementById("image-receive").value = fileContent;
            };
        };
    </script>
</html>
