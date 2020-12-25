<%-- 
    Document   : history_request
    Created on : Jul 19, 2020, 3:22:10 PM
    Author     : Peter
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <sx:head />
        <script src="bootstrap/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/fonts.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/font-awesome.min.css" rel="stylesheet" type="text/css" >
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Request Page</title>
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
                        <a href="request_list_manager.jsp" class="nav-item nav-link text-primary">Show Request</a>
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
            <s:if test="%{#request.ERROR != null}">
                <font color="red"><s:property value="%{#request.ERROR}" /></font>
            </s:if>
            <s:if test="%{#request.SUCCESS != null}">
                <font color="green"><s:property value="%{#request.SUCCESS}" /></font>
            </s:if>
            <div class="card">
                <div class="card-body">
                    <s:form action="SearchHistoryRequestAction" method="GET" cssClass="form-inline d-flex justify-content-between" theme="simple">
                        <sx:datetimepicker value="%{#request.searchRequestDate}" name="searchRequestDate" displayFormat="dd/MM/yyyy" cssClass="form-control w-75" />
                        <s:submit value="Search" cssClass="btn btn-outline-success col-sm-3" />
                        <s:reset value="Reset" cssClass="btn btn-outline-warning mt-2" />
                    </s:form>
                </div>
            </div>
            <s:if test="%{#request.requestList != null}">
                <s:if test="%{#request.requestList.size > 0}">
                    <s:iterator value="%{#request.requestList}" var="dto">
                        <div class="d-flex flex-row border border-primary rounded mb-2 p-2">
                            <div>
                                <p class="m-0"><b>User request:</b> <s:property value="%{#dto.userRequest}" /></p>
                                <p class="m-0"><b>Request date:</b> <s:property value="%{#dto.requestDate}" /></p>
                                <div class="d-flex flex-row align-items-center">
                                    <s:if test="%{#dto.status == 'Pending'}">
                                        <font color="grey"><b>Status: </b><s:property value="%{#dto.status}" /></font>
                                        <s:form method="POST" action="DeleteRequestAction" theme="simple">
                                            <s:hidden name="id" value="%{#dto.id}"/>
                                            <s:hidden name="searchRequestDate" value="%{searchRequestDate}" />
                                            <s:submit value="Delete" name="buttonHandle" cssClass="mx-1 btn btn-danger" />
                                        </s:form>
                                    </s:if>
                                    <s:if test="%{#dto.status == 'Canceled'}">
                                        <font color="red"><b>Status: </b><s:property value="%{#dto.status}" /></font>
                                    </s:if>
                                    <s:if test="%{#dto.status == 'Approved'}">
                                        <font color="green"><b>Status: </b><s:property value="%{#dto.status}" /></font>
                                    </s:if>
                                    <s:if test="%{#dto.status == 'Deactive'}">
                                        <font color="blue"><b>Status: </b><s:property value="%{#dto.status}" /></font>
                                    </s:if>
                                </div>
                            </div>
                            <div class="ml-4">
                                <b>Resources request</b>
                                <s:iterator value="%{#dto.resources}" status="counter" var="rs">
                                    <p class="m-0"><s:property value="%{#counter.count}" />) <s:property value="%{#rs.name}" /></p>
                                </s:iterator>
                            </div>
                        </div>
                    </s:iterator>
                </s:if>
                <s:if test="%{#request.requestList.size == 0}">
                    No records found!
                </s:if>
            </s:if>
        </div>
    </body>
</html>
