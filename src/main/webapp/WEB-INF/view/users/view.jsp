<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="duorum" tagdir="/WEB-INF/tags/duorum" %>

<div class="form-horizontal">

    <duorum:field label="user.username" value="${user.username}"/>
    <duorum:field label="user.status" value="${user.userStatus}"/>
    <spring:message code="${user.credentialsNonExpired ? 'no' : 'yes'}" var="defaultPassword"/>
    <duorum:field label="user.defaultPassword" value="${defaultPassword}"/>
    <duorum:field label="user.authority" value="${user.authorities}"/>
    <duorum:field label="user.firstName" value="${user.firstName}"/>
    <duorum:field label="user.lastName" value="${user.lastName}"/>
    <duorum:field label="user.email" value="${user.email}"/>
    <duorum:field label="user.theme" value="${user.theme}"/>
    <spring:message code="language.${user.language}" var="userLanguage"/>
    <duorum:field label="user.language" value="${userLanguage}"/>

    <div class="form-group">
        <div class="btn-group col-sm-offset-2 col-sm-10">

            <a href="<c:url value="/users/${user.id}/edit"/>" class="btn btn-primary">
                <spring:message code="button.edit"/>
            </a>
            <a href="<c:url value="/users/${user.id}/reset"/>" class="btn btn-danger">
                <spring:message code="button.resetPassword"/>
            </a>
            <c:if test="${user.userStatus == 'BLOCKED'}">
                <a href="<c:url value="/users/${user.id}/unblock"/>" class="btn btn-success">
                    <spring:message code="button.unblock"/>
                </a>
            </c:if>
            <c:if test="${user.userStatus == 'ACTIVE'}">
                <a href="<c:url value="/users/${user.id}/block"/>" class="btn btn-warning">
                    <spring:message code="button.block"/>
                </a>
            </c:if>
            <a href="<c:url value="/users"/>" class="btn btn-default">
                <spring:message code="button.cancel"/>
            </a>
        </div>
    </div>
</div>