<%-- 
    Document   : index
    Created on : Jun 30, 2020, 11:03:18 PM
    Author     : Peter
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <sx:head />
        <link href=“https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css”/>
        <link href="assets/css/home.css" rel="stylesheet" type="text/css" >
        <script src="bootstrap/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/fonts.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/font-awesome.min.css" rel="stylesheet" type="text/css" >
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <s:if test="%{#session.USER == null}">
            <meta http-equiv="refresh" content="0;URL=login.jsp">
        </s:if>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="collapse navbar-collapse">
                <div class="navbar-nav">
                    <a href="HomeAction" class="nav-item nav-link active text-light bg-primary">Home <span class="sr-only">(current)</span></a>
                    <s:if test="%{#session.ROLE == 'Manager'}">
                        <s:url action="ShowRequestAction" id="ShowRequestLink">
                        </s:url>
                        <s:a href="%{ShowRequestLink}" cssClass="nav-item nav-link text-primary">Show Request</s:a>
                    </s:if>
                    <s:if test="%{#session.ROLE != 'Manager'}">
                        <a href="history_request.jsp" class="nav-item nav-link text-primary">Show History Request</a>
                    </s:if>
                </div>
            </div>
            <div class="navbar-nav">
                <s:if test="%{#session.USER != null}">
                    <div id="view-cart">
                        <s:if test="%{#session.ROLE != 'Manager'}">
                            <s:a href="request_list.jsp" cssClass="nav-item nav-link text-primary">View request list <s:if test="%{#session.CART != null}">(<s:property value="%{#session.CART.getItems().size}" />)</s:if></s:a>
                        </s:if>
                    </div>
                    <div class="d-flex align-items-center">
                        <p class="m-0">Hello, ${sessionScope.FULLNAME}</p>
                    </div>
                    <a class="nav-item nav-link text-danger" href="LogoutAction">Logout</a>
                </s:if>
                <s:if test="%{#session.USER == null}">
                    <a class="nav-item nav-link text-light btn btn-success mr-3" href="login.jsp">Login</a>
                </s:if>
            </div>
        </nav>
        <div class="container">
            <div class="card">
                <div class="card-body">
                    <h1 class="mb-3">Welcome <s:property value="%{#session.ROLE}" />, <s:property value="%{#session.FULLNAME}" /></h1>
                    <s:if test="%{#request.ERROR != null}">
                        <font color="red"><s:property value="%{#request.ERROR}" /></font>
                    </s:if>
                    <s:if test="%{#request.SUCCESS != null}">
                        <font color="green"><s:property value="%{#request.SUCCESS}" /></font>
                    </s:if>
                    <s:form action="SearchResourceAction" method="GET" cssClass="form-inline d-flex justify-content-between" theme="simple">
                        <s:textfield value="%{#request.name}" name="name" placeholder="Search resources..." cssClass="form-control col-sm-3"/>
                        <s:select headerKey="" headerValue="-- Select category --" list="%{#session.categories}" listKey="name" listValue="name" value="%{#request.category}" name="category" cssClass="form-control col-sm-3"/>
                        <sx:datetimepicker value="%{#request.usingDate}" name="usingDate" displayFormat="dd/MM/yyyy" cssClass="form-control w-75" />
                        <s:submit value="Search" cssClass="btn btn-outline-success col-sm-3" />
                        <s:reset value="Reset" cssClass="btn btn-outline-warning mt-2" />
                    </s:form>

                </div>
            </div>
            <s:if test="%{#request.resourceList != null}">
                <s:if test="%{#request.resourceList.size > 0}">
                    <nav aria-label="Navigation for resourceList" class="mt-2">
                        <ul class="pagination">
                            <s:if test="%{#request.currentPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="SearchResourceAction.action?name=<s:property value="%{#request.name}" />&category=<s:property value="%{#request.category}" />&usingDate=<s:property value="%{#request.usingDate}" />&dojo.usingDate=&recordsPerPage=<s:property value="%{#request.recordsPerPage}" />&currentPage=<s:property value="%{#request.currentPage - 1}" />">Previous</a>
                                </li>
                            </s:if>
                            <s:iterator begin="1" end="%{#request.noOfPages}" var="i">
                                <s:if test="%{#request.currentPage eq #i}">
                                    <li class="page-item active"><a class="page-link">
                                            ${i} <span class="sr-only">(current)</span></a>
                                    </li>
                                </s:if>
                                <s:else>
                                    <li class="page-item">
                                        <a class="page-link" href="SearchResourceAction.action?name=<s:property value="%{#request.name}" />&category=<s:property value="%{#request.category}" />&usingDate=<s:property value="%{#request.usingDate}" />&dojo.usingDate=&recordsPerPage=<s:property value="%{#request.recordsPerPage}" />&currentPage=<s:property value="%{#i}" />"><s:property value="%{#i}" /></a>
                                    </li>
                                </s:else>
                            </s:iterator>  
                            <s:if test="%{#request.currentPage < #request.noOfPages}">
                                <li class="page-item">
                                    <a class="page-link" href="SearchResourceAction.action?name=<s:property value="%{#request.name}" />&category=<s:property value="%{#request.category}" />&usingDate=<s:property value="%{#request.usingDate}" />&dojo.usingDate=&recordsPerPage=<s:property value="%{#request.recordsPerPage}" />&currentPage=<s:property value="%{#request.currentPage + 1}" />">Next</a>
                                </li>
                            </s:if>       
                        </ul>
                    </nav>
                    <table class="table mt-2">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Name</th>
                                <th>Quantity left</th>
                                <th>Using Date</th>
                                <th>Category</th>
                                    <s:if test="%{#session.ROLE != 'Manager'}">
                                    <th>Add to request list</th>
                                    </s:if>
                            </tr>
                        </thead>
                        <tbody>
                            <s:iterator value="%{#request.resourceList}" status="counter" var="dto">
                                <tr>
                                    <td><s:property value="%{#counter.count}" /></td>
                                    <td><s:property value="%{#dto.name}" /></td>
                                    <td><s:property value="%{#dto.balance}" /></td>
                                    <td><s:property value="%{#dto.usingDate}" /></td>
                                    <td><s:property value="%{#dto.category}" /></td>
                                    <s:if test="%{#session.ROLE != 'Manager'}">
                                        <s:date name="%{#request.NOW}" var="now" format="yyyy-MM-dd"/>
                                        <s:hidden value="%{#now}" />
                                        <s:if test="%{#dto.usingDate > #now}">
                                            <td>
                                                <div id="btn-add<s:property value="%{#dto.id}" />">
                                                    <p id="alert-success<s:property value="%{#dto.id}" />" class="m-0 text-success"></p>
                                                    <p id="alert-fail<s:property value="%{#dto.id}" />" class="m-0 text-danger"></p>
                                                    <input type="button" value="Add to request list" class="btn btn-primary" onclick="checkAddToRequestList('<s:property value="%{#dto.id}" />')"/>
                                                </div>
                                                <div class="quantity mt-2" id="div-quantity<s:property value="%{#dto.id}" />">
                                                    <div class="d-flex justify-content-between">
                                                        <input type="button" value="-" onclick="decrease('<s:property value="%{#dto.id}" />')"/>
                                                        <input type="number" onchange="detectChangeAmount(this)" min="0" max="<s:property value="%{#dto.balance}" />" value="0" style="width: 50px" id="amount<s:property value="%{#dto.id}" />"/>
                                                        <input type="button" value="+" onclick="increase('<s:property value="%{#dto.id}" />')"/>
                                                    </div>
                                                    <div class="mt-2 d-flex justify-content-between">
                                                        <input type="hidden" id="name<s:property value="%{#dto.id}" />" value="<s:property value="%{#dto.name}" />" />
                                                        <input type="hidden" id="category<s:property value="%{#dto.id}" />" value="<s:property value="%{#dto.category}" />" />
                                                        <input type="hidden" id="usingDate<s:property value="%{#dto.id}" />" value="<s:property value="%{#dto.usingDate}" />" />
                                                        <input type="hidden" id="balance<s:property value="%{#dto.id}" />" value="<s:property value="%{#dto.balance}" />" />
                                                        <input type="button" value="Confirm" class="btn btn-success" onclick="checkAddCart('<s:property value="%{#dto.id}" />')" />
                                                        <input type="button" value="Cancel" class="btn btn-danger" onclick="cancelAddToRequestList('<s:property value="%{#dto.id}" />')"/>
                                                    </div>
                                                </div>
                                            </td>
                                        </s:if>
                                        <s:if test="%{#dto.usingDate < #now}">
                                            <td>
                                                <p class="text-danger m-0">This resource has expired</p>
                                            </td>
                                        </s:if>
                                    </s:if>
                                </tr>
                            </s:iterator>
                        </tbody>
                    </table>
                </s:if>
                <s:if test="%{#request.resourceList.size == 0}">
                    No records found!
                </s:if>
            </s:if>
        </div>
    </body>
    <script type="text/javascript">
        function detectChangeAmount(elm) {
            if (elm.value < 0) {
                elm.value = 0;
            } else if (elm.value > elm.max) {
                elm.value = elm.max;
            }
        }

        function increase(id) {
            var value = parseInt(document.getElementById('amount' + id).value, 10);
            var balance = parseInt(document.getElementById('balance' + id).value, 10);
            value = isNaN(value) ? 0 : value;
            if (value < balance) {
                value++;
            }
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

        function checkAddCart(id) {
            if (document.getElementById("amount" + id).value === "0") {
                return alert("Please input a number greater than 0");
            } else {
                var xhr = new XMLHttpRequest();
                var name = document.getElementById("name" + id).value;
                var category = document.getElementById("category" + id).value;
                var usingDate = document.getElementById("usingDate" + id).value;
                var balance = document.getElementById("balance" + id).value;
                var amount = document.getElementById("amount" + id).value;
                var params = "id=" + id + "&name=" + name + "&category=" + category + "&usingDate=" + usingDate + "&balance=" + balance + "&amount=" + amount;
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        if (xhr.responseText === "Add to request list successful") {
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
                xhr.open("POST", "${pageContext.request.contextPath}/AddToRequestListAction.action", true);
                xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhr.send(params);
            }
        }

        function cancelAddToRequestList(id) {
            document.getElementById("div-quantity" + id).style.display = "none";
            document.getElementById("btn-add" + id).style.display = "block";
            document.getElementById('amount' + id).value = 0;
        }

        function checkAddToRequestList(id) {
            var user = "<s:property value="%{#session.USER}" />";
            if (user === "") {
                modal.classList.toggle("my-show-modal");
            } else {
                document.getElementById("div-quantity" + id).style.display = "block";
                document.getElementById("btn-add" + id).style.display = "none";
            }
        }
    </script>
</html>
