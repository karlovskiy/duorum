<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <title><spring:message code="login.signIn"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/${application.version}/css/bootstrap/cosmo/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/${application.version}/css/login.css"/>">
</head>
<body>
<div class="container">
    <c:if test="${empty sessionScope['CREDENTIALS_EXPIRED_USERNAME']}">
        <form class="form-signin" action="<c:url value='j_spring_security_check'/>" method='POST' role="form">
            <h2><spring:message code="login.signIn"/></h2>
            <c:if test="${not empty sessionScope['SPRING_SECURITY_LAST_EXCEPTION']}">
                <div class="alert alert-danger">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>
            </c:if>
            <c:if test="${not empty sessionScope['LOGIN_INFO_MESSAGE']}">
                <div class="alert alert-success"><spring:message code="${sessionScope['LOGIN_INFO_MESSAGE']}"/></div>
            </c:if>
            <input class="form-control" name="j_username" type="text" autofocus=""
                   placeholder="<spring:message code="placeholder.username"/>">
            <input class="form-control pass" name="j_password" type="password"
                   placeholder="<spring:message code="placeholder.password"/>">
            <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="button.sigIn"/></button>
        </form>
    </c:if>
    <c:if test="${not empty sessionScope['CREDENTIALS_EXPIRED_USERNAME']}">
        <c:url var="formAction" value="/mustchangepassword"/>
        <form:form commandName="changePassword" action="${formAction}" cssClass="form-signin" role="form">
            <h2><spring:message code="header.changePassword"/></h2>
            <c:if test="${not empty sessionScope['SPRING_SECURITY_LAST_EXCEPTION']}">
                <div class="alert alert-danger">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>
            </c:if>
            <input id="username" class="form-control" type="text"
                   placeholder="<spring:message code="placeholder.username"/>"
                   value="${sessionScope['CREDENTIALS_EXPIRED_USERNAME']}" disabled>
            <spring:bind path="oldPassword">
                <div class="form-group ${status.error ? "has-error" : ""}">
                    <spring:message code="placeholder.oldPassword" var="oldPasswordPlaceholder"/>
                    <form:password id="oldPassword" path="oldPassword" cssClass="form-control pass-old" autofocus="autofocus"
                                   placeholder="${oldPasswordPlaceholder}"/>
                    <form:errors path="oldPassword" cssClass="help-block"/>
                </div>
            </spring:bind>
            <spring:bind path="newPassword">
                <div class="form-group ${status.error ? "has-error" : ""}">
                    <spring:message code="placeholder.newPassword" var="newPasswordPlaceholder"/>
                    <form:password id="newPassword" path="newPassword" cssClass="form-control pass-new"
                                   placeholder="${newPasswordPlaceholder}" />
                    <form:errors path="newPassword" cssClass="help-block"/>
                </div>
            </spring:bind>
            <spring:bind path="repeatNewPassword">
                <div class="form-group ${status.error ? "has-error" : ""}">
                    <spring:message code="placeholder.repeatNewPassword" var="repeatNewPasswordPlaceholder"/>
                    <form:password id="repeatNewPassword" path="repeatNewPassword" cssClass="form-control pass-repeat"
                                   placeholder="${repeatNewPasswordPlaceholder}" />
                    <form:errors path="repeatNewPassword" cssClass="help-block"/>
                </div>
            </spring:bind>
            <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="button.changePassword"/></button>
        </form:form>
    </c:if>
</div>
</body>
</html>