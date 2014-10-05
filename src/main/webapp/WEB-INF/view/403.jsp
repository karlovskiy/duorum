<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="<c:url value="/resources/${application.version}/css/bootstrap/${application.theme}/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/${application.version}/css/bootstrap/${application.theme}/${application.theme}.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/${application.version}/css/duorum.css"/>">
    <title><spring:message code="application"/></title>
</head>
<body>
<div class="container">
    <!-- Static navbar -->
    <div class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="<c:url value="/"/>"><spring:message code="application"/></a>
            </div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <security:authorize access="hasRole('UNITS')">
                        <li>
                            <a href="<c:url value="/units"/>">
                                <spring:message code="menu.units"/>
                            </a>
                        </li>
                    </security:authorize>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <security:authorize access="hasRole('ADMINISTRATOR')">
                        <li class="dropdown">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <spring:message code="menu.administration"/> <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="<c:url value="/users"/>"><spring:message code="menu.users"/></a></li>
                            </ul>
                        </li>
                    </security:authorize>
                    <li class="dropdown">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <spring:message code="menu.settings"/> <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="<c:url value="/changepassword"/>"><spring:message code="menu.changePassword"/></a></li>
                            <li><a href="<c:url value="/changeuserinfo"/>"><spring:message code="menu.changeSettings"/></a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="glyphicon glyphicon-log-out"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <security:authentication property="principal.username" var="principalUsername"/>
                            <li class="dropdown-header"><spring:message code="menu.signedAs" arguments="${principalUsername}"/> </li>
                            <li><a href="<c:url value="/contact"/>"><spring:message code="menu.contact"/></a></li>
                            <li><a href="<c:url value="/j_spring_security_logout"/>"><spring:message code="menu.logout"/> </a></li>
                        </ul>
                    </li>
                </ul>
            </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
    </div>

    <div class="row">
        <div class="col-sm-12">
            <div class="alert alert-danger">
                <spring:message code="access_denied"/>
            </div>
        </div>
    </div>

    <hr>
    <footer>
        <p class="pull-left">&copy; Karlovskiy ${application.year}</p>
        <p class="pull-right"><spring:message code="menu.version" arguments="${application.version}"/></p>
    </footer>
</div>
<script type="text/javascript" src="<c:url value="/resources/${application.version}/js/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/${application.version}/js/bootstrap.min.js"/>"></script>
</body>
</html>
