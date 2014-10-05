<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="row">
    <div class="col-sm-12">
        <div class="btn-group" style="margin-bottom: 5px;">
            <a href="<c:url value="/stickers/removeall"/>" class="btn btn-danger">
                <span class="glyphicon glyphicon-trash"></span> <spring:message code="button.removeAllStickers"/>
            </a>
            <a href="<c:url value="/units"/> " class="btn btn-default">
                <spring:message code="button.cancel"/>
            </a>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-sm-12">
        <table class="table table-hover table-condensed table-bordered">
            <thead>
            <tr>
                <th><spring:message code="unit.designation"/></th>
                <th><spring:message code="unit.lot"/></th>
                <th><spring:message code="unit.serialNumber"/></th>
                <th><spring:message code="sticker.barcode"/></th>
                <th><spring:message code="actions"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${stickers}" var="sticker">
                <tr>
                    <td><c:out value="${sticker.designation}"/></td>
                    <td><c:out value="${sticker.lot}"/></td>
                    <td><c:out value="${sticker.serialNumber}"/></td>
                    <td><c:out value="${sticker.barcode}"/></td>
                    <td>
                        <div class="btn-group">
                            <a href="<c:url value="/units/${sticker.id}"/>"
                               class="btn btn-primary btn-xs"
                               title="<spring:message code="button.view"/>">
                                <span class="glyphicon glyphicon-tag"></span>
                            </a>
                            <a href="<c:url value="/stickers/${sticker.id}/remove"/>"
                               class="btn btn-danger btn-xs"
                               style="margin-left: 5px;"
                               title="<spring:message code="button.removeSticker"/>">
                                <span class="glyphicon glyphicon-trash"></span>
                            </a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>