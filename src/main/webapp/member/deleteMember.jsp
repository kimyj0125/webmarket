<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="dbconn.jsp"%>

<c:set var="sessionId" value="${sessionScope.sessionId }"/>

<sql:update var="resultSet" dataSource="${dataSource}">
   DELETE FROM member WHERE id = ?
   <sql:param value="${sessionId}" />
</sql:update>

<c:if test="${resultSet>=1}">
	<c:import url="logoutMember.jsp" />
	<c:redirect url="resultMember.jsp" />
</c:if>
