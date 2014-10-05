<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="duorum" tagdir="/WEB-INF/tags/duorum" %>

<c:url var="formAction" value="/users"/>
<form:form id="form" commandName="user" action="${formAction}" cssClass="form-horizontal" role="form">

    <spring:bind path="username">
        <div class="form-group ${status.error or not empty existsUser ? "has-error" : ""}">
            <label class="control-label col-sm-2" for="username"><spring:message code="user.username"/></label>

            <div class="col-md-5">
                <form:input id="username" path="username" cssClass="form-control"/>
                <form:errors path="username" cssClass="help-block"/>
                <c:if test="${not empty existsUser}">
                    <span class="help-block">
                        <spring:message code="user.withUsername"/>
                        <a href="<c:url value="/users/${existsUser.id}"/>" target="_blank">
                            <c:out value="${existsUser.username}"/>
                        </a>
                        <spring:message code="alreadyExists"/>!
                    </span>
                </c:if>
            </div>
        </div>
    </spring:bind>

    <div class="form-group">
        <label class="control-label col-sm-2"><spring:message code="user.authority"/></label>

        <div class="col-sm-5">
            <c:forEach items="${user.allAuthorities}" var="authority">
                <div class="checkbox">
                    <label class="authority">
                        <input id="${authority.authority}" type="checkbox" value="">
                        <span>${authority}</span>
                    </label>
                </div>
            </c:forEach>
        </div>
        <form:hidden id="authorities" path="authorities"/>
    </div>

    <duorum:input path="email" label="user.email"/>
    <duorum:input path="firstName" label="user.firstName"/>
    <duorum:input path="lastName" label="user.lastName"/>

    <spring:bind path="theme">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-sm-2" for="theme"><spring:message code="user.theme"/></label>
            <div class="col-sm-3">
                <form:select id="theme" path="theme" cssClass="form-control">
                    <form:option value="default" label="Default"/>
                    <form:option value="amelia" label="Amelia"/>
                    <form:option value="cerulean" label="Cerulean"/>
                    <form:option value="cosmo" label="Cosmo"/>
                    <form:option value="cyborg" label="Cyborg"/>
                    <form:option value="flatly" label="Flatly"/>
                    <form:option value="journal" label="Journal"/>
                    <form:option value="lumen" label="Lumen"/>
                    <form:option value="readable" label="Readable"/>
                    <form:option value="simplex" label="Simplex"/>
                    <form:option value="slate" label="Slate"/>
                    <form:option value="spacelab" label="Spacelab"/>
                    <form:option value="superhero" label="Superhero"/>
                    <form:option value="united" label="United"/>
                    <form:option value="yeti" label="Yeti"/>
                </form:select>
                <form:errors path="theme" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <spring:bind path="language">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-sm-2" for="language"><spring:message code="user.language"/></label>
            <div class="col-sm-3">
                <spring:message code="language.ru" var="rus"/>
                <spring:message code="language.en" var="eng"/>
                <form:select id="language" path="language" cssClass="form-control">
                    <form:option value="ru" label="${rus}"/>
                    <form:option value="en" label="${eng}"/>
                </form:select>
                <form:errors path="language" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <div class="form-group">
        <div class="btn-group col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary"><spring:message code="button.save"/></button>
            <a href="<c:url value="/users"/>" class="btn btn-default">
                <spring:message code="button.cancel"/>
            </a>
        </div>
    </div>

</form:form>