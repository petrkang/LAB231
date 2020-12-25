<%-- 
    Document   : cart
    Created on : Jun 16, 2020, 9:14:07 AM
    Author     : Peter
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="assets/css/cart.css" rel="stylesheet" type="text/css" >
        <script src="bootstrap/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/fonts.css" rel="stylesheet" type="text/css" >
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
        </c:if>
        <c:if test="${sessionScope.USER == null}">
            <c:redirect url="login.jsp">
                <c:param name="pageAfterLogin" value="cart.jsp">
                </c:param>
            </c:redirect>
        </c:if>
        <title>Cart Page</title>
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
                    <!--<a class="nav-item nav-link text-light btn btn-warning" href="register.jsp">Register</a>-->
                </c:if>
            </div>
        </nav>
        <div class="container mt-3" id="cart-list">
            <div class="card">
                <div class="card-body d-flex flex-column align-items-center">
                    <h3>Your Cart</h3>
                    <c:if test="${requestScope.ERROR != null}">
                        <font color="red">${requestScope.ERROR}</font>
                    </c:if>
                    <c:if test="${requestScope.SUCCESS != null}">
                        <font color="green">${requestScope.SUCCESS}</font>
                    </c:if>
                    <c:if test="${sessionScope.CART != null}">
                        <c:if test="${not empty sessionScope.CART}" var="cartList">
                            <div class="list-group mt-3">
                                <c:forEach items="${CART.getItems()}" var="entry" varStatus="count">
                                    <div class="d-flex flex-row border border-primary rounded p-3 mb-3">
                                        <div class="col-4">
                                            <img src="${entry.key.image}" width="100%" height="100%"/>
                                        </div>
                                        <div class="col-8">
                                            <div class="d-flex justify-content-end">
                                                <span class="text-danger" onclick="toggleModal(${entry.key.id})">&times;</span>
                                            </div>
                                            <p class="m-0"><b>1. Name:</b> ${entry.key.name}</p>
                                            <b>2. Price:</b>
                                            <input type="hidden" value="${entry.key.price}" class="tmpPrice" />
                                            <span class="price m-0">
                                                ${entry.key.price}đ
                                            </span>
                                            <p class="m-0"><b>3. Quota:</b> ${entry.key.quota}</p>
                                            <p class="m-0"><b>4. From date:</b> ${entry.key.fromDate} - <b>To date:</b> ${entry.key.toDate}</p>
                                            <p class="m-0"><b>5. From:</b> ${entry.key.departure} - <b>To:</b> ${entry.key.destination}</p>
                                            <b>=> Total price:</b> <span class="total" id="total${entry.key.id}"></span>đ
                                            <div class="mt-2" id="div-quantity${entry.key.id}">
                                                <form method="POST">
                                                    <div class="d-flex justify-content-between">
                                                        <input type="button" value="-" onclick="decrease(${entry.key.id})"/>
                                                        <input class="amount" type="number" readonly min="0" value="${entry.value}" style="width: 40px" id="amount${entry.key.id}"/>
                                                        <input type="button" value="+" onclick="increase(${entry.key.id})"/>
                                                    </div>
                                                    <div class="mt-2 d-flex justify-content-between">
                                                        <input type="hidden" value="${entry.key.name}" id="name${entry.key.id}" />
                                                        <input type="hidden" value="${entry.key.fromDate}" id="fromDate${entry.key.id}" />
                                                        <input type="hidden" value="${entry.key.toDate}" id="toDate${entry.key.id}" />
                                                        <input type="hidden" value="${entry.key.price}" id="price${entry.key.id}" />
                                                        <input type="hidden" value="${entry.key.quota}" id="quota${entry.key.id}" />
                                                        <input type="hidden" value="${entry.key.image}" id="image${entry.key.id}" />
                                                        <input type="hidden" value="${entry.key.departure}" id="departure${entry.key.id}" />
                                                        <input type="hidden" value="${entry.key.destination}" id="destination${entry.key.id}" />
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="my-modal" id="modal${entry.key.id}">
                                        <div class="my-modal-content">
                                            <span class="my-close-button" onclick="cancelDelete(${entry.key.id})">&times;</span>
                                            <div class="d-flex flex-column align-items-center">
                                                <h5>Are you sure you want to delete this tour</h5>
                                                <div class="d-flex flex-row justify-content-around w-50 mt-3">
                                                    <form method="POST" action="DeleteTour">
                                                        <input type="hidden" name="txtId" value="${entry.key.id}" />
                                                        <input class="btn btn-success" type="submit" name="action" value="OK" />
                                                    </form>
                                                    <button class="btn btn-danger" onclick="cancelDelete(${entry.key.id})">Cancel</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <form method="POST" action="CheckoutCart" class="d-flex flex-column w-100 mt-3">
                                <div class="w-100 d-flex flex-row justify-content-between">
                                    <h4 class="col-sm-9 p-0"><b>Discount:</b></h4>
                                    <h4 class="col-sm-2 p-0 d-flex justify-content-end" id="discount-price"></h4>
                                </div>
                                <div class="w-100 form-inline justify-content-between">
                                    <input type="hidden" value="" id="txtDiscountPercent" name="txtDiscountPercent"/>
                                    <input type="hidden" value="" id="txtTmpDiscountCode" name="txtTmpDiscountCode"/>
                                    <input class="form-control col-sm-9" type="text" placeholder="Input your discount code..." id="txtDiscountCode" name="txtDiscountCode"/>
                                    <input type="button" value="Apply" class="col-sm-2 form-control btn btn-outline-primary" onclick="checkDiscountCode()" />
                                </div>
                                <div class="mt-3 w-100 d-flex flex-row justify-content-between">
                                    <h4 class="col-sm-9 p-0"><b>Total Price:</b></h4>
                                    <input type="hidden" value="" id="tmp-total-price"/>
                                    <h4 class="col-sm-2 p-0 d-flex justify-content-end" id="total-price"></h4>
                                </div>
                                <input type="submit" value="Checkout" class="btn btn-dark"/>
                            </form>
                        </c:if>
                        <c:if test="${!cartList}">
                            <p class="m-0 my-2">Your cart is empty</p>
                        </c:if>
                    </c:if>
                    <c:if test="${sessionScope.CART == null}">
                        You still not book any tour
                    </c:if>
                </div>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        window.onload = function () {
            calculateTotalPrice();
            formatTotalPrice();
            formatPrice();
        };
        var percent = 0;
        function formatMoney(val) {
            var pattern = /(-?\d+)(\d{3})/;
            while (pattern.test(val))
                val = val.replace(pattern, "$1,$2");
            return val;
        }

        function formatPrice() {
            var listVal = document.getElementsByClassName("price");
            var listTmp = document.getElementsByClassName("tmpPrice");
            for (var i = 0; i < listVal.length; i++) {
                val = listTmp[i].value;
                val = formatMoney(val);
                listVal[i].innerHTML = val;
            }
        }

        function formatTotalPrice() {
            var val;
            var listTotal = document.getElementsByClassName("total");
            var listTmp = document.getElementsByClassName("tmpPrice");
            var listAmount = document.getElementsByClassName("amount");
            for (var i = 0; i < listTotal.length; i++) {
                val = parseInt(listTotal[i].innerHTML, 10);
                val = parseInt(listTmp[i].value, 10) * parseInt(listAmount[i].value, 10);
                val = formatMoney(val.toString());
                listTotal[i].innerHTML = val;
            }
        }

        function calculateTotalPrice() {
            var sum = 0;
            var val;
            var listTmp = document.getElementsByClassName("tmpPrice");
            var listAmount = document.getElementsByClassName("amount");
            for (var i = 0; i < listTmp.length; i++) {
                val = parseInt(listTmp[i].value, 10);
                val = parseInt(listTmp[i].value, 10) * parseInt(listAmount[i].value, 10);
                sum += val;
            }
            if (percent !== 0) {
                var discountPrice = sum * percent / 100;
                sum -= discountPrice;
                discountPrice = formatMoney(discountPrice.toString());
                document.getElementById("discount-price").innerHTML = discountPrice + "đ";
            }
            document.getElementById("tmp-total-price").value = sum;
            var tmpSum = formatMoney(sum.toString());
            document.getElementById("total-price").innerHTML = tmpSum + "đ";
        }

        function formatEachTotalPrice(id, amount) {
            var price = parseInt(document.getElementById("price" + id).value, 10);
            var total = amount * price;
            var total = formatMoney(total.toString());
            document.getElementById("total" + id).innerHTML = total;
        }

        function toggleModal(id) {
            document.getElementById("modal" + id).classList.add("my-show-modal");
        }

        function checkDiscountCode() {
            var code = document.getElementById("txtDiscountCode").value;
            var params = "code=" + code;
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    if (xhr.responseText === "Your code is invalid or expired") {
                        alert(xhr.responseText);
                        window.location.reload();
                    } else {
                        percent = parseInt(xhr.responseText, 10);
                        document.getElementById("txtDiscountPercent").value = percent;
                        calculateTotalPrice();
                        document.getElementById("txtDiscountCode").disabled = true;
                        document.getElementById("txtTmpDiscountCode").value = code;
                    }
                }
            };
            xhr.open("POST", "${pageContext.request.contextPath}/CheckDiscount", true);
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.send(params);
        }


        function checkUpdate(id) {
            var amount = document.getElementById("amount" + id).value;
            var params = "id=" + id + "&amount=" + amount;
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
            }
            ;
            xhr.open("POST", "${pageContext.request.contextPath}/UpdateCart", true);
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.send(params);
        }
        ;

        function increase(id) {
            var value = parseInt(document.getElementById('amount' + id).value, 10);
            value = isNaN(value) ? 0 : value;
            value++;
            formatEachTotalPrice(id, value);
            document.getElementById('amount' + id).value = value;
            checkUpdate(id);
            calculateTotalPrice();
        }

        function decrease(id) {
            var value = parseInt(document.getElementById('amount' + id).value, 10);
            value = isNaN(value) ? 0 : value;
            if (value === 1) {
                return document.getElementById("modal" + id).classList.add("my-show-modal");
            }
            if (value > 0) {
                value--;
                formatEachTotalPrice(id, value);
                document.getElementById('amount' + id).value = value;
                checkUpdate(id);
                calculateTotalPrice();
            }
        }

        function cancelDelete(id) {
            return document.getElementById("modal" + id).classList.remove("my-show-modal");
        }

    </script>
</html>
