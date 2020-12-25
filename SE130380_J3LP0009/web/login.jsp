<%-- 
    Document   : login
    Created on : Jul 1, 2020, 11:40:53 PM
    Author     : Peter
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/fonts.css" rel="stylesheet" type="text/css" >
        <link href="bootstrap/css/font-awesome.min.css" rel="stylesheet" type="text/css" >
        <link href=“https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css”/>
        <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="bootstrap/js/jquery-3.4.1.slim.min.js" type="text/javascript"></script>
        <script src="bootstrap/js/popper.min.js" type="text/javascript"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <!-- reCAPTCHA Libary -->
        <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>
        <s:if test="%{#session.USER != null}">
            <meta http-equiv="refresh" content="0;URL=LogoutAction">
        </s:if>
    </head>
    <body>
        <div class="container d-flex flex-column align-items-center">
            <h1>Login</h1>
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
            <s:form action="LoginAction" method="POST">
                <s:textfield name="email" label="Email" />
                <s:password name="password" label="Password" />
                <s:submit value="Login" cssClass="btn btn-primary"/>
                <div class="g-recaptcha" data-sitekey="6LclKrMZAAAAAKJ8y3hZXf-P63NN_TaDQ_xHhFOW"></div>
            </s:form>
            <a href="register.jsp">Register Account</a>
        </div>
    </body>
</html>
