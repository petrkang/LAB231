<%-- 
    Document   : home
    Created on : Jun 6, 2020, 6:11:46 PM
    Author     : Peter
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ page language="Java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <link href="assets/css/home.css" rel="stylesheet" type="text/css" >
        <script src="bootstrap/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/fonts.css" rel="stylesheet" type="text/css" >
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="collapse navbar-collapse">
                <div class="navbar-nav">
                    <a href="Home" class="nav-item nav-link active text-light bg-primary">Home <span class="sr-only">(current)</span></a>
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
                <div class="card-body">
                    <c:if test="${sessionScope.USER != null}">
                        <h1 class="mb-3">Welcome ${sessionScope.ROLE}, ${sessionScope.FULLNAME}</h1>
                    </c:if>
                    <h3 class="text-center mb-3">Search your tour</h3>
                    <form action="Search" method="GET">
                        <div class="row align-items-center justify-content-between">
                            <label class="col-sm-3">Select your place: </label>
                            <div class="d-flex flex-column col-sm-4 p-0">
                                <select name="departure" class="form-control <c:if test="${requestScope.tourErr.departure != null}">is-invalid</c:if><c:if test="${requestScope.tourErr.departure == null}">is-valid</c:if>">
                                        <option value>--Select your departure--</option>
                                    <c:forEach items="${sessionScope.placeList}" var="dto">
                                        <option value="${dto.name}" <c:if test="${requestScope.departureSelected == dto.name}">selected</c:if>>${dto.name}</option>
                                    </c:forEach>
                                </select>
                                <div class="invalid-feedback">
                                    ${requestScope.tourErr.departure}  
                                </div>
                            </div>
                            <div class="d-flex flex-column col-sm-4 p-0">
                                <select name="destination" class="form-control <c:if test="${requestScope.tourErr.destination != null}">is-invalid</c:if><c:if test="${requestScope.tourErr.destination == null}">is-valid</c:if>">
                                        <option value>--Select your destination--</option>
                                    <c:forEach items="${sessionScope.placeList}" var="dto">
                                        <option value="${dto.name}" <c:if test="${requestScope.destinationSelected == dto.name}">selected</c:if>>${dto.name}</option>
                                    </c:forEach>
                                </select>
                                <div class="invalid-feedback">
                                    ${requestScope.tourErr.destination}  
                                </div>
                            </div>
                        </div>
                        <div class="row align-items-center justify-content-between mt-2">
                            <label class="col-sm-3">Select your time: </label>
                            <div class="d-flex flex-column col-sm-4 p-0">
                                <input type="datetime-local" value="${requestScope.fromDate}" name="fromDate" class="form-control <c:if test="${requestScope.tourErr.fromDate != null}">is-invalid</c:if><c:if test="${requestScope.tourErr.fromDate == null}">is-valid</c:if>"/>
                                    <div class="invalid-feedback">
                                    ${requestScope.tourErr.fromDate}  
                                </div>
                            </div>
                            <div class="d-flex flex-column col-sm-4 p-0">
                                <input type="datetime-local" value="${requestScope.toDate}" name="toDate"  class="form-control <c:if test="${requestScope.tourErr.toDate != null}">is-invalid</c:if><c:if test="${requestScope.tourErr.toDate == null}">is-valid</c:if>"/>
                                    <div class="invalid-feedback">
                                    ${requestScope.tourErr.toDate}  
                                </div>
                            </div>
                        </div>
                        <div class="row align-items-center justify-content-between mt-2">
                            <label class="col-sm-3">Choose min/ max price: </label>
                            <div class="d-flex flex-column col-sm-4 p-0">
                                <input type="text" name="txtMin" placeholder="Min price..." value="${requestScope.txtMin}" class="form-control <c:if test="${requestScope.tourErr.minPrice != null}">is-invalid</c:if><c:if test="${requestScope.tourErr.minPrice == null}">is-valid</c:if>"/>
                                    <div class="invalid-feedback">
                                    ${requestScope.tourErr.minPrice}  
                                </div>    
                            </div>
                            <div class="d-flex flex-column col-sm-4 p-0">
                                <input type="text" name="txtMax" placeholder="Max price..." value="${requestScope.txtMax}" class="form-control <c:if test="${requestScope.tourErr.maxPrice != null}">is-invalid</c:if><c:if test="${requestScope.tourErr.maxPrice == null}">is-valid</c:if>"/>
                                    <div class="invalid-feedback">
                                    ${requestScope.tourErr.maxPrice}  
                                </div>
                            </div>
                        </div>
                        <div class="w-100 d-flex justify-content-between mt-2">
                            <input type="reset" value="Reset" class="btn btn-outline-warning">
                            <input type="submit" name="action" value="Search" class="btn btn-outline-success"/>
                        </div>
                    </form>
                </div>
            </div>
            <c:if test="${requestScope.tourList != null}">
                <c:if test="${not empty requestScope.tourList}" var="tourList">
                    <div class="list-group mt-3">

                        <nav aria-label="Navigation for tourList">
                            <ul class="pagination">
                                <c:if test="${requestScope.currentPage gt 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="Search?departure=${requestScope.departure}&destination=${requestScope.destination}&fromDate=${requestScope.fromDate}&toDate=${requestScope.toDate}&txtMin=${requestScope.txtMin}&txtMax=${requestScope.txtMax}&action=Search&recordsPerPage=${requestScope.recordsPerPage}&currentPage=${requestScope.currentPage-1}">Previous</a>
                                    </li>
                                </c:if>

                                <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">
                                    <c:choose>
                                        <c:when test="${requestScope.currentPage eq i}">
                                            <li class="page-item active"><a class="page-link">
                                                    ${i} <span class="sr-only">(current)</span></a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item">
                                                <a class="page-link" href="Search?departure=${requestScope.departure}&destination=${requestScope.destination}&fromDate=${requestScope.fromDate}&toDate=${requestScope.toDate}&txtMin=${requestScope.txtMin}&txtMax=${requestScope.txtMax}&action=Search&recordsPerPage=${requestScope.recordsPerPage}&currentPage=${i}">${i}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>

                                <c:if test="${requestScope.currentPage lt noOfPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="Search?departure=${requestScope.departure}&destination=${requestScope.destination}&fromDate=${requestScope.fromDate}&toDate=${requestScope.toDate}&txtMin=${requestScope.txtMin}&txtMax=${requestScope.txtMax}&action=Search&recordsPerPage=${requestScope.recordsPerPage}&currentPage=${requestScope.currentPage+1}">Next</a>
                                    </li>
                                </c:if>              
                            </ul>
                        </nav>
                        <c:forEach items="${requestScope.tourList}" var="dto" varStatus="count">
                            <div class="d-flex flex-row border p-3">
                                <div class="col-4">
                                    <img src="${dto.image}" width="100%" height="100%"/>
                                </div>
                                <div class="col-8">
                                    <p class="m-0"><b>1. Name:</b> ${dto.name}</p>
                                    <p class="price m-0">
                                        <script type="text/javascript">
                                            window.onload = function () {
                                                var listVal = document.getElementsByClassName("price");
                                                var pattern = /(-?\d+)(\d{3})/;
                                                for (var i = 0; i < listVal.length; i++) {
                                                    val = listVal[i].innerHTML;
                                                    while (pattern.test(val))
                                                        val = val.replace(pattern, "$1,$2");
                                                    listVal[i].innerHTML = val;
                                                }
                                            }
                                        </script>
                                        <b>2. Price:</b> ${dto.price}đ
                                    </p>
                                    <p class="m-0"><b>3. Quota:</b> ${dto.quota}</p>
                                    <p class="m-0"><b>4. From date:</b> ${dto.fromDate} - <b>To date:</b> ${dto.toDate}</p>
                                    <p class="m-0"><b>5. From:</b> ${dto.departure} - <b>To:</b> ${dto.destination}</p>
                                    <c:if test="${sessionScope.ROLE != 'Admin'}">
                                        <jsp:useBean id="currentDate" class="java.util.Date"/>
                                        <fmt:formatDate var="now" value="${currentDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        <c:if test="${dto.fromDate > now}">
                                            <div id="btn-add${dto.id}">
                                                <p id="alert-success${dto.id}" class="m-0 text-success"></p>
                                                <p id="alert-fail${dto.id}" class="m-0 text-danger"></p>
                                                <input type="button" value="Add to cart" class="btn btn-primary" onclick="checkAddToCart(${dto.id})"/>
                                            </div>
                                            <div class="quantity mt-2" id="div-quantity${dto.id}">
                                                <div class="d-flex justify-content-between">
                                                    <input type="button" value="-" onclick="decrease(${dto.id})"/>
                                                    <input type="number" min="0" value="0" style="width: 50px" id="amount${dto.id}"/>
                                                    <input type="button" value="+" onclick="increase(${dto.id})"/>
                                                </div>
                                                <div class="mt-2 d-flex justify-content-between">
                                                    <input type="hidden" value="${dto.name}" id="name${dto.id}" />
                                                    <input type="hidden" value="${dto.fromDate}" id="fromDate${dto.id}" />
                                                    <input type="hidden" value="${dto.toDate}" id="toDate${dto.id}" />
                                                    <input type="hidden" value="${dto.price}" id="price${dto.id}" />
                                                    <input type="hidden" value="${dto.quota}" id="quota${dto.id}" />
                                                    <input type="hidden" value="${dto.image}" id="image${dto.id}" />
                                                    <input type="hidden" value="${dto.departure}" id="departure${dto.id}" />
                                                    <input type="hidden" value="${dto.destination}" id="destination${dto.id}" />
                                                    <input type="button" value="Confirm" class="btn btn-success" onclick="checkAddCart(${dto.id})" />
                                                    <input type="button" value="Cancel" class="btn btn-danger" onclick="cancelAddToCart(${dto.id})"/>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${dto.fromDate < now}">
                                            <p class="text-danger">This tour has expired</p>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${sessionScope.ROLE == 'Admin'}">
                                        <p class="text-danger">(Admin cannot book tour)</p>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${!tourList}">
                    <p class="m-0 my-2">No results found</p>
                </c:if>
            </c:if>
        </div>
        <div class="my-modal">
            <div class="my-modal-content">
                <span class="my-close-button">&times;</span>
                <div class="d-flex flex-column align-items-center">
                    <h5>Please login if you want to book tour</h5>
                    <c:url var="loginLink" value="login.jsp" >
                        <c:param name="pageAfterLogin" value="home.jsp" />
                    </c:url>
                    <a class="nav-item nav-link text-primary" href="${loginLink}">OK</a>
                </div>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        <c:set var="USER" value="${sessionScope.USER}"/>
        var modal = document.querySelector(".my-modal");
        var closeButton = document.querySelector(".my-close-button");
        function toggleModal() {
            modal.classList.toggle("my-show-modal");
        }

        function checkAddCart(id) {
            if (document.getElementById("amount" + id).value === "0") {
                return alert("Please input a number greater than 0");
            } else {
                var xhr = new XMLHttpRequest();
                var name = document.getElementById("name" + id).value;
                var fromDate = document.getElementById("fromDate" + id).value;
                var toDate = document.getElementById("toDate" + id).value;
                var price = document.getElementById("price" + id).value;
                var quota = document.getElementById("quota" + id).value;
                var image = document.getElementById("image" + id).value;
                var departure = document.getElementById("departure" + id).value;
                var destination = document.getElementById("destination" + id).value;
                var amount = document.getElementById("amount" + id).value;
                var params = "id=" + id + "&name=" + name + "&fromDate=" + fromDate + "&toDate=" + toDate + "&price=" + price + "&quota=" + quota + "&image=" + image + "&departure=" + departure + "&destination=" + destination + "&amount=" + amount;
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        if (xhr.responseText === "Add cart successful") {
                            document.getElementById("amount" + id).value = 0;
                            document.getElementById("div-quantity" + id).style.display = "none";
                            document.getElementById("btn-add" + id).style.display = "block";
                            var alertSuccess = setInterval(function () {
                                document.getElementById("alert-success" + id).innerHTML = xhr.responseText;
                            }, 0);
                            setTimeout(function () {
                                document.getElementById("alert-success" + id).innerHTML = null;
                                clearInterval(alertSuccess);
                            }, 2000);
                            $("#view-cart").load(window.location.href + " #view-cart");
                        } else {
                            document.getElementById("div-quantity" + id).style.display = "none";
                            document.getElementById("btn-add" + id).style.display = "block";
                            var alertFail = setInterval(function () {
                                document.getElementById("alert-fail" + id).innerHTML = xhr.responseText;
                            }, 0);
                            setTimeout(function () {
                                document.getElementById("alert-fail" + id).innerHTML = null;
                                clearInterval(alertFail);
                            }, 2000);
                        }
                    }
                };
                xhr.open("POST", "${pageContext.request.contextPath}/AddToCart", true);
                xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhr.send(params);
            }
        }

        function windowOnClick(event) {
            if (event.target === modal) {
                toggleModal();
            }
        }

        function checkAddToCart(id) {
            var user = "${USER}";
            if (user === "") {
                modal.classList.toggle("my-show-modal");
            } else {
                document.getElementById("div-quantity" + id).style.display = "block";
                document.getElementById("btn-add" + id).style.display = "none";
            }
        }

        function cancelAddToCart(id) {
            document.getElementById("div-quantity" + id).style.display = "none";
            document.getElementById("btn-add" + id).style.display = "block";
        }
        closeButton.addEventListener("click", toggleModal);
        window.addEventListener("click", windowOnClick);

        function increase(id) {
            var value = parseInt(document.getElementById('amount' + id).value, 10);
            value = isNaN(value) ? 0 : value;
            value++;
            document.getElementById('amount' + id).value = value;
        }

        function decrease(id) {
            var value = parseInt(document.getElementById('amount' + id).value, 10);
            value = isNaN(value) ? 0 : value;
            if (value > 0) {
                value--;
            }
            document.getElementById('amount' + id).value = value;
        }

    </script>
</html>
