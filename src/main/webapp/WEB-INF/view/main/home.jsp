<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<security:authentication property="principal.username" var="principalUsername"/>
<h2><spring:message code="header.welcome" arguments="${principalUsername}"/></h2>

<div class="row">
    <div class="col-sm-6">
        <h3><spring:message code="header.created"/></h3>
        <table class="table">
            <thead>
            <tr>
                <th><spring:message code="home.created"/></th>
                <th><spring:message code="unit.designation"/></th>
                <th><spring:message code="unit.code"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${lastCreated}" var="unit">
                <tr>
                    <td>
                        <fmt:formatDate value="${unit.creationDate}" type="both" pattern="dd.MM.yyyy HH:mm:ss"/>
                    </td>
                    <td>
                        <a href="<c:url value="/units/${unit.id}"/>">
                            <c:out value="${unit.designation}"/>
                        </a>
                    </td>
                    <td><c:out value="${unit.code}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="col-sm-6">
        <h3><spring:message code="header.modified"/></h3>
        <table class="table">
            <thead>
            <tr>
                <th><spring:message code="home.modified"/></th>
                <th><spring:message code="unit.designation"/></th>
                <th><spring:message code="unit.code"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${lastModified}" var="unit">
                <tr>
                    <td>
                        <fmt:formatDate value="${unit.lastModificationDate}" type="both" pattern="dd.MM.yyyy HH:mm:ss"/>
                    </td>
                    <td>
                        <a href="<c:url value="/units/${unit.id}"/>">
                            <c:out value="${unit.designation}"/>
                        </a>
                    </td>
                    <td><c:out value="${unit.code}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>