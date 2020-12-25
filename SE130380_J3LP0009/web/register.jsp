<%-- 
    Document   : register
    Created on : Jul 1, 2020, 11:41:03 PM
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
        <title>Register Page</title>
    </head>
    <body>
        <div class="container">
            <div class="card">
                <div class="card-body">
                    <h1>Register</h1>
                    <s:form action="RegisterAction" method="POST">
                        <s:textfield name="firstname" label="Firstname" />
                        <s:textfield name="lastname" label="Lastname" />
                        <s:textfield name="email" label="Email"/>
                        <s:password name="password" label="Password" />
                        <s:password name="confirm" label="Confirm password" />
                        <s:submit value="Register" />
                    </s:form>
                    <a href="login.jsp">Login Account</a>
                </div>
            </div>
        </div>
    </body>
</html>
