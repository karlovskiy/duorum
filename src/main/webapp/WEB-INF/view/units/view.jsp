<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="duorum" tagdir="/WEB-INF/tags/duorum" %>

<div class="form-horizontal">

    <duorum:field label="unit.designation" value="${unit.designation}"/>
    <duorum:field label="unit.code" value="${unit.code}"/>
    <duorum:field label="unit.name" value="${unit.name}"/>
    <duorum:field label="unit.description" value="${unit.description}"/>
    <duorum:field label="unit.labelName" value="${unit.labelName}"/>
    <duorum:field label="unit.labelDescription" value="${unit.labelDescription}"/>
    <duorum:field label="unit.lot" value="${unit.lot}"/>
    <duorum:field label="unit.serialNumber" value="${unit.serialNumber}"/>
    <div class="form-group">
        <label class="control-label col-sm-2"><spring:message code="unit.language"/></label>

        <div class="col-sm-10">
            <p class="form-control-static">
                <span title="${unit.language}">
                    <c:out value="${unit.language eq '01' ? 'RU' : (unit.language eq '02' ? 'ENG' : '')}"/>
                </span>
            </p>
        </div>
    </div>
    <duorum:field label="creationDate" value="${unit.creationDate}" type="datetime"/>
    <duorum:field label="lastModificationDate" value="${unit.lastModificationDate}" type="datetime"/>

    <div class="form-group">
        <div class="btn-group col-sm-offset-2 col-sm-10">
            <security:authorize access="hasRole('ADMINISTRATOR')">
                <a href="<c:url value="/units/${unit.id}/edit"/>"
                   class="btn btn-primary"><spring:message code="button.edit"/></a>
            </security:authorize>
            <a href="<c:url value="/units"/>" class="btn btn-default">
                <spring:message code="button.cancel"/>
            </a>
        </div>
    </div>
</div>