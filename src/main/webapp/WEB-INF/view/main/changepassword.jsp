<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="duorum" tagdir="/WEB-INF/tags/duorum" %>

<c:url var="formAction" value="/changepassword"/>
<form:form commandName="changePassword" action="${formAction}" cssClass="form-horizontal">
    <div class="form-group">
        <label class="control-label col-sm-2"><spring:message code="user.username"/></label>
        <div class="col-sm-10">
            <p class="form-control-static"><security:authentication property="principal.username"/></p>
        </div>
    </div>

    <duorum:input path="oldPassword" label="changepassword.oldPassword" type="password" length="3"/>
    <duorum:input path="newPassword" label="changepassword.newPassword" type="password" length="3"/>
    <duorum:input path="repeatNewPassword" label="changepassword.repeatNewPassword" type="password" length="3"/>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary"><spring:message code="button.changePassword"/></button>
        </div>
    </div>
</form:form>