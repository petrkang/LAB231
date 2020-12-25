<%-- 
    Document   : manager_home
    Created on : Jul 3, 2020, 12:28:56 AM
    Author     : Peter
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="assets/css/request_list.css" rel="stylesheet" type="text/css" >
        <link href=“https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css”/>
        <script src="bootstrap/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/fonts.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/font-awesome.min.css" rel="stylesheet" type="text/css" >
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Request List Page</title>
        <s:if test="%{#session.USER == null}">
            <meta http-equiv="refresh" content="0;URL=login.jsp">
        </s:if>
        <s:if test="%{#session.USER != null}">
            <s:if test="%{#session.ROLE == 'Manager'}">
                <meta http-equiv="refresh" content="0;URL=HomeAction">
            </s:if>
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
        <div class="container mt-3" id="cart-list">
            <div class="card">
                <div class="card-body d-flex flex-column align-items-center">
                    <h3>Your Request List</h3>
                    <s:if test="%{#request.ERROR != null}">
                        <font color="red"><s:property value="%{#request.ERROR}" /></font>
                    </s:if>
                    <s:if test="%{#request.SUCCESS != null}">
                        <font color="green"><s:property value="%{#request.SUCCESS}" /></font>
                    </s:if>
                    <s:if test="%{#session.CART != null}">
                        <s:if test="%{#session.CART.getItems().size > 0}">
                            <div class="list-group mt-3">
                                <table class="table mt-2">
                                    <thead>
                                        <tr>
                                            <th>No</th>
                                            <th>Name</th>
                                            <th>Quantity left</th>
                                            <th>Using Date</th>
                                            <th>Category</th>
                                            <th>Amount</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <s:iterator value="%{#session.CART.getItems()}" status="counter" var="entry">
                                            <tr>
                                                <td><s:property value="%{#counter.count}" /></td>
                                                <td><s:property value="%{#entry.key.name}" /></td>
                                                <td><s:property value="%{#entry.key.balance}" /></td>
                                                <td><s:property value="%{#entry.key.usingDate}" /></td>
                                                <td><s:property value="%{#entry.key.category}" /></td>
                                                <td>
                                                    <div class="quantity mt-2" id="div-quantity<s:property value="%{#entry.key.id}" />">
                                                        <div class="d-flex justify-content-between">
                                                            <input type="button" value="-" onclick="decrease('<s:property value="%{#entry.key.id}" />')"/>
                                                            <input class="amount" type="number" readonly min="0" value="<s:property value="%{#entry.value}" />" style="width: 40px" id="amount<s:property value="%{#entry.key.id}" />"/>
                                                            <input type="button" value="+" onclick="increase('<s:property value="%{#entry.key.id}" />')"/>
                                                        </div>
                                                        <div class="mt-2 d-flex justify-content-between">
                                                            <input type="hidden" id="name<s:property value="%{#entry.key.id}" />" value="<s:property value="%{#entry.key.name}" />" />
                                                            <input type="hidden" id="category<s:property value="%{#entry.key.id}" />" value="<s:property value="%{#entry.key.category}" />" />
                                                            <input type="hidden" id="usingDate<s:property value="%{#entry.key.id}" />" value="<s:property value="%{#entry.key.usingDate}" />" />
                                                            <input type="hidden" id="balance<s:property value="%{#entry.key.id}" />" value="<s:property value="%{#entry.key.balance}" />" />
                                                        </div>
                                                    </div>
                                                    <div class="my-modal" id="modal<s:property value="%{#entry.key.id}" />">
                                                        <div class="my-modal-content">
                                                            <span class="my-close-button" onclick="cancelDelete('<s:property value="%{#entry.key.id}" />')">&times;</span>
                                                            <div class="d-flex flex-column align-items-center">
                                                                <h5>Are you sure you want to delete this request</h5>
                                                                <div class="d-flex flex-row justify-content-around w-50 mt-3">
                                                                    <s:form method="POST" action="DeleteResourceRequestAction" cssClass="d-flex">
                                                                        <s:hidden value="%{#entry.key.id}" name="id"></s:hidden>
                                                                        <s:submit cssClass="btn btn-success" value="OK" />
                                                                    </s:form>
                                                                    <button class="btn btn-danger" onclick="cancelDelete('<s:property value="%{#entry.key.id}" />')">Cancel</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="d-flex justify-content-end">
                                                        <span class="text-danger" onclick="toggleModal('<s:property value="%{#entry.key.id}" />')">&times;</span>
                                                    </div>
                                                </td>
                                            </tr>
                                        </s:iterator>
                                    </tbody>
                                </table>
                            </div>
                            <s:form method="POST" action="SendRequestAction" cssClass="d-flex flex-column align-items-center w-100">
                                <s:submit value="Send" cssClass="btn btn-dark" />
                            </s:form>
                        </s:if>
                        <s:if test="%{#session.CART.size == 0}">
                            <p class="m-0 my-2">Your request list is empty</p>
                        </s:if>
                    </s:if>
                    <s:if test="%{#session.CART == null}">
                        You still not request any resource
                    </s:if>
                </div>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        function increase(id) {
            var value = parseInt(document.getElementById('amount' + id).value, 10);
            var balance = parseInt(document.getElementById('balance' + id).value, 10);
            value = isNaN(value) ? 0 : value;
            if (value < balance) {
                value++;
            }
            document.getElementById('amount' + id).value = value;
            checkUpdate(id);
        }

        function decrease(id) {
            var value = parseInt(document.getElementById('amount' + id).value, 10);
            value = isNaN(value) ? 0 : value;
            if (value === 1) {
                return document.getElementById("modal" + id).classList.add("my-show-modal");
            }
            if (value > 0) {
                value--;
                document.getElementById('amount' + id).value = value;
                checkUpdate(id);
            }
        }

        function toggleModal(id) {
            document.getElementById("modal" + id).classList.add("my-show-modal");
        }

        function cancelDelete(id) {
            return document.getElementById("modal" + id).classList.remove("my-show-modal");
        }

        function checkUpdate(id) {
            var amount = document.getElementById("amount" + id).value;
            var params = "id=" + id + "&amount=" + amount;
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
            }
            ;
            xhr.open("POST", "${pageContext.request.contextPath}/UpdateRequestListAction.action", true);
            xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhr.send(params);
        }
    </script>
</html>
