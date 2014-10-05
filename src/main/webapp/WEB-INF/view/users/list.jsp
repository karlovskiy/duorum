<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row">
    <div class="col-sm-12">
        <div class="btn-group" style="margin-bottom: 5px;">
            <a href="<c:url value="/users/new"/>" class="btn btn-primary">
                <span class="glyphicon glyphicon-plus-sign"></span> <spring:message code="button.add"/>
            </a>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-sm-12">
        <table class="table table-hover table-condensed table-bordered">
            <thead>
            <tr>
                <th><spring:message code="user.username"/></th>
                <th><spring:message code="user.status"/></th>
                <th><spring:message code="user.authority"/></th>
                <th><spring:message code="actions"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>
                        <c:out value="${user.username}"/>
                    </td>
                    <td>
                <span class="${user.userStatus eq 'BLOCKED' ? 'text-danger' : 'text-success'}">
                    <spring:message code="${user.userStatus}"/>
                </span>
                    </td>
                    <td><c:out value="${user.authorities}"/></td>
                    <td>
                        <a href="<c:url value="/users/${user.id}"/>"
                           class="btn btn-primary btn-xs"
                           title="<spring:message code="button.view"/>">
                            <span class="glyphicon glyphicon-tag"></span>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>