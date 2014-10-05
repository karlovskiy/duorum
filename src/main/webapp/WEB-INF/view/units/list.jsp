<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row">
    <div class="col-sm-8">
        <div class="btn-group" style="margin-bottom: 5px;">
            <a href="<c:url value="/units/new"/>" class="btn btn-primary">
                <span class="glyphicon glyphicon-plus-sign"></span> <spring:message code="button.add"/>
            </a>
            <a href="<c:url value="/units/import"/>" class="btn btn-primary">
                <span class="glyphicon glyphicon-upload"></span> <spring:message code="button.import"/>
            </a>
            <a href="<c:url value="/units/export"/>" class="btn btn-primary">
                <span class="glyphicon glyphicon-download"></span> <spring:message code="button.export"/>
            </a>
            <a href="<c:url value="/stickers"/>" class="btn btn-warning">
                <span class="glyphicon glyphicon-barcode"></span> <spring:message code="button.stickers"/>
            </a>
        </div>
    </div>
    <div class="col-sm-4">
        <form class="form-inline pull-right" action="<c:url value="/units"/>">
            <div class="form-group">
                <input type="text" name="search" value="${search}" class="form-control"
                       placeholder="<spring:message code="placeholder.search"/>" >
            </div>
            <button type="submit" class="btn btn-primary">
                <span class="glyphicon glyphicon-search"></span>
            </button>
        </form>
    </div>
</div>
<div class="row">
    <div class="col-sm-12">
        <table class="table table-hover table-condensed table-bordered">
            <thead>
            <tr>
                <th><spring:message code="unit.designation"/></th>
                <th><spring:message code="unit.code"/></th>
                <th><spring:message code="unit.name"/></th>
                <th><spring:message code="unit.description"/></th>
                <th><spring:message code="unit.language"/></th>
                <th><spring:message code="actions"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${units}" var="unit">
                <tr class="${unit.stickerStatus eq 'QUEUED_ON_PRINT' ? 'warning' : ''}">
                    <td><c:out value="${unit.designation}"/></td>
                    <td><c:out value="${unit.code}"/></td>
                    <td><c:out value="${unit.name}"/></td>
                    <td><c:out value="${unit.description}"/></td>
                    <td title="${unit.language}"><c:out value="${unit.language eq '01' ? 'RU' : (unit.language eq '02' ? 'ENG' : '')}"/></td>
                    <td>
                        <div class="btn-group">
                            <a href="<c:url value="/units/${unit.id}"/>"
                               class="btn btn-primary btn-xs"
                               title="<spring:message code="button.view"/>">
                                <span class="glyphicon glyphicon-tag"></span>
                            </a>
                            <c:if test="${unit.stickerStatus eq 'DEFAULT'}">
                                <a href="<c:url value="/units/${unit.id}/print"/>"
                                   class="btn btn-warning btn-xs" style="margin-left: 5px;"
                                   title="<spring:message code="button.printSticker"/>">
                                    <span class="glyphicon glyphicon-print"></span>
                                </a>
                            </c:if>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>