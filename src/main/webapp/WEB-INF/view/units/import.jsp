<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form method="post" action="<c:url value="/units/import"/>" enctype="multipart/form-data" class="form-horizontal" role="form">
    <h3><spring:message code="header.importUnits"/></h3>
    <div class="form-group">
        <div class="col-sm-10">
            <input type="file" name="file"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-10">
            <button class="btn btn-primary" type="submit">
                <spring:message code="button.import"/>
            </button>
            <a href="<c:url value="/units"/>" class="btn btn-default">
                <spring:message code="button.cancel"/>
            </a>
        </div>
    </div>
</form>
<c:if test="${not error and not empty result}">
    <table style="width: 100%">
        <tr>
            <td valign="top">
                <table class="table table-hover table-condensed">
                    <tr>
                        <td>
                                <span class="text-success">
                                    <spring:message code="unit.successAdded" arguments="${result.addedCount}"/>
                                </span>
                        </td>
                    </tr>
                    <c:forEach items="${result.added}" var="added">
                        <tr>
                            <td>
                                    <span class="text-success">
                                        <spring:message code="unit.withDestination"/>
                                        <a href="/units/${added.id}" target="_blank">
                                            <c:out value="${added.designation}"/>
                                        </a>
                                        <spring:message code="successfullyAdded"/>!
                                    </span>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
            <td valign="top">
                <c:if test="${not empty result.exists}">
                    <table class="table table-hover table-condensed">
                        <tr>
                            <td>
                                    <span class="text-danger">
                                        <spring:message code="unit.existsAndIgnored" arguments="${result.existsCount}"/>
                                    </span>
                            </td>
                        </tr>
                        <c:forEach items="${result.exists}" var="exists">
                            <tr>
                                <td>
                                        <span class="text-danger">
                                            <spring:message code="unit.withDestination"/>
                                            <a href="/units/${exists.id}" target="_blank">
                                                <c:out value="${exists.designation}"/>
                                            </a>
                                            <spring:message code="alreadyExists"/>!
                                        </span>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </td>
        </tr>
    </table>

</c:if>