<%-- 
    Document   : request_list_manager
    Created on : Jul 17, 2020, 3:29:16 PM
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
        <title>Request List Manager Page</title>
        <s:if test="%{#session.USER == null}">
            <meta http-equiv="refresh" content="0;URL=login.jsp">
        </s:if>
        <s:if test="%{#session.USER != null}">
            <s:if test="%{#session.ROLE != 'Manager'}">
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
        <div class="container">
            <s:if test="%{#request.ERROR != null}">
                <font color="red"><s:property value="%{#request.ERROR}" /></font>
            </s:if>
            <s:if test="%{#request.SUCCESS != null}">
                <font color="green"><s:property value="%{#request.SUCCESS}" /></font>
            </s:if>
            <div class="card">
                <div class="card-body">
                    <s:form action="SearchRequestAction" method="GET" cssClass="form-inline d-flex justify-content-between" theme="simple">
                        <s:textfield value="%{#request.searchResourceName}" name="searchResourceName" placeholder="Search resource name..." cssClass="form-control col-sm-3"/>
                        <s:textfield value="%{#request.searchUser}" name="searchUser" placeholder="Search user..." cssClass="form-control col-sm-3"/>
                        <s:select headerKey="" headerValue="-- Select status --" list="%{#session.requestStatusList}" listKey="name" listValue="name" value="%{#request.searchStatus}" name="searchStatus" cssClass="form-control col-sm-3"/>
                        <sx:datetimepicker value="%{#request.searchRequestDate}" name="searchRequestDate" displayFormat="dd/MM/yyyy" cssClass="form-control w-75" />
                        <s:submit value="Search" cssClass="btn btn-outline-success col-sm-3" />
                        <s:reset value="Reset" cssClass="btn btn-outline-warning mt-2" />
                    </s:form>
                </div>
            </div>
            <s:if test="%{#request.requestList != null}">
                <s:if test="%{#request.requestList.size > 0}">
                    <nav aria-label="Navigation for requestList" class="mt-2">
                        <ul class="pagination">
                            <s:if test="%{#request.currentPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="SearchRequestAction.action?searchResourceName=<s:property value="%{#request.searchResourceName}" />&searchUser=<s:property value="%{#request.searchUser}" />&searchStatus=<s:property value="%{#request.searchStatus}" />&searchRequestDate=<s:property value="%{#request.searchRequestDate}" />&dojo.searchRequestDate=&recordsPerPage=<s:property value="%{#request.recordsPerPage}" />&currentPage=<s:property value="%{#request.currentPage - 1}" />">Previous</a>
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
                                        <a class="page-link" href="SearchRequestAction.action?searchResourceName=<s:property value="%{#request.searchResourceName}" />&searchUser=<s:property value="%{#request.searchUser}" />&searchStatus=<s:property value="%{#request.searchStatus}" />&searchRequestDate=<s:property value="%{#request.searchRequestDate}" />&dojo.searchRequestDate=&recordsPerPage=<s:property value="%{#request.recordsPerPage}" />&currentPage=<s:property value="%{#i}" />"><s:property value="%{#i}" /></a>
                                    </li>
                                </s:else>
                            </s:iterator>  
                            <s:if test="%{#request.currentPage < #request.noOfPages}">
                                <li class="page-item">
                                    <a class="page-link" href="SearchRequestAction.action?searchResourceName=<s:property value="%{#request.searchResourceName}" />&searchUser=<s:property value="%{#request.searchUser}" />&searchStatus=<s:property value="%{#request.searchStatus}" />&searchRequestDate=<s:property value="%{#request.searchRequestDate}" />&dojo.searchRequestDate=&recordsPerPage=<s:property value="%{#request.recordsPerPage}" />&currentPage=<s:property value="%{#request.currentPage + 1}" />">Next</a>
                                </li>
                            </s:if>       
                        </ul>
                    </nav>
                    <s:iterator value="%{#request.requestList}" var="dto">
                        <div class="d-flex flex-row border border-primary rounded mb-2 p-2">
                            <div>
                                <p class="m-0"><b>User request:</b> <s:property value="%{#dto.userRequest}" /></p>
                                <p class="m-0"><b>Request date:</b> <s:property value="%{#dto.requestDate}" /></p>
                                <div class="d-flex flex-row align-items-center">
                                    <s:if test="%{#dto.status == 'Pending'}">
                                        <font color="grey"><b>Status: </b><s:property value="%{#dto.status}" /></font>
                                        <s:form method="POST" action="HandleRequestAction" theme="simple">
                                            <s:hidden name="id" value="%{#dto.id}" />
                                            <s:hidden name="searchRequestDate" value="%{searchRequestDate}" />
                                            <s:hidden name="searchUser" value="%{searchUser}" />
                                            <s:hidden name="searchResourceName" value="%{searchResourceName}" />
                                            <s:hidden name="searchStatus" value="%{searchStatus}" />
                                            <s:submit value="Approve" name="buttonHandle" cssClass=" mx-1 btn btn-success" />
                                            <s:submit value="Cancel" name="buttonHandle" cssClass="mx-1 btn btn-danger" />
                                        </s:form>
                                    </s:if>
                                    <s:if test="%{#dto.status == 'Canceled'}">
                                        <font color="red"><b>Status: </b><s:property value="%{#dto.status}" /></font>
                                    </s:if>
                                    <s:if test="%{#dto.status == 'Approved'}">
                                        <font color="green"><b>Status: </b><s:property value="%{#dto.status}" /></font>
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
