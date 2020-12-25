<%-- 
    Document   : verify_account
    Created on : Jul 19, 2020, 6:44:10 PM
    Author     : Peter
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href=“https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css”/>
        <script src="bootstrap/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/fonts.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/font-awesome.min.css" rel="stylesheet" type="text/css" >
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify Account Page</title>
        <s:if test="%{#session.USER == null}">
            <meta http-equiv="refresh" content="0;URL=login.jsp">
        </s:if>
        <s:if test="%{#session.USER != null}">
            <s:if test="%{#session.STATUS != null}">
                <meta http-equiv="refresh" content="0;URL=LogoutAction">
            </s:if>
        </s:if>
    </head>
    <body>
        <div class="container">
            <div class="card">
                <div class="card-body">
                    <h3>Please verify your account before login</h3>
                    <s:if test="%{#request.SUCCESS != null}">
                        <font color="green">
                        <s:property value="%{#request.SUCCESS}" />
                        </font>
                    </s:if>
                    <s:if test="%{#request.ERROR != null}">
                        <font color="red">
                        <s:property value="%{#request.ERROR}" />
                        </font>
                    </s:if>
                    <s:form action="VerifyAction" method="POST" theme="simple">
                        <s:textfield name="code" label="Code" />
                        <s:submit value="Verify"/>
                    </s:form>
                    <a href="login.jsp" class="mt-3">Login Account</a>
                </div>
            </div>
        </div>
    </body>
</html>
