<%@ tag dynamic-attributes="attrs" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="type" %>

<div class="form-group">
    <label class="control-label col-sm-2"><spring:message code="${attrs.label}"/></label>

    <div class="col-sm-10">
        <p class="form-control-static">
            <span class="${empty attrs.cssClass ? '' : attrs.cssClass}">
                <c:choose>
                    <c:when test="${type == 'date'}">
                        <fmt:formatDate value="${attrs.value}" type="date" pattern="dd.MM.yyyy"/>
                    </c:when>
                    <c:when test="${type == 'datetime'}">
                        <fmt:formatDate value="${attrs.value}" type="both" pattern="dd.MM.yyyy HH:mm:ss"/>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${attrs.value}"/>
                    </c:otherwise>
                </c:choose>
            </span>
        </p>
    </div>
</div>