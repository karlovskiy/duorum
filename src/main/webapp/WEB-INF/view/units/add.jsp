<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="duorum" tagdir="/WEB-INF/tags/duorum" %>

<c:url var="formAction" value="/units"/>
<form:form commandName="unit" action="${formAction}" cssClass="form-horizontal" role="form">

    <spring:bind path="designation">
        <div class="form-group ${status.error or not empty exist ? "has-error" : ""}">
            <label class="control-label col-sm-2" for="designation"><spring:message code="unit.designation"/></label>

            <div class="col-sm-3">
                <form:input id="designation" path="designation" cssClass="form-control "/>
                <form:errors path="designation" cssClass="help-block"/>
                <c:if test="${not empty exist}">
                        <span class="help-block">
                            <spring:message code="unit.withDestination"/>
                            <a href="<c:url value="/units/${exist.id}"/>" target="_blank">
                                <c:out value="${exist.designation}"/>
                            </a>
                            <spring:message code="alreadyExists"/>!
                        </span>
                </c:if>
            </div>
        </div>
    </spring:bind>

    <duorum:input path="code" label="unit.code" length="3"/>
    <duorum:input path="name" label="unit.name"/>
    <duorum:input path="description" label="unit.description" length="10"/>
    <duorum:input path="labelName" label="unit.labelName"/>
    <duorum:input path="labelDescription" label="unit.labelDescription" length="10"/>
    <duorum:input path="lot" label="unit.lot" length="3"/>
    <duorum:input path="serialNumber" label="unit.serialNumber" length="3"/>
    <spring:bind path="language">
        <div class="form-group ${status.error ? "has-error" : ""}">
            <label class="control-label col-sm-2" for="language"><spring:message code="unit.language"/></label>
            <div class="col-sm-3">
                <form:select id="language" path="language" cssClass="form-control">
                    <form:option value="" label=""/>
                    <form:option value="01" label="RU"/>
                    <form:option value="02" label="ENG"/>
                </form:select>
                <form:errors path="language" cssClass="help-block"/>
            </div>
        </div>
    </spring:bind>

    <div class="form-group">
        <div class="btn-group col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-success">
                <spring:message code="button.save"/>
            </button>
            <a href="<c:url value="/units"/>" class="btn btn-default">
                <spring:message code="button.cancel"/>
            </a>
        </div>
    </div>

</form:form>