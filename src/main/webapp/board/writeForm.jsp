<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>     --%>
<%
	String name = (String) request.getAttribute("name");
%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link rel="stylesheet" href="./resources/css/bootstrap.min.css">
<script type="text/javascript">
function checkForm(){
	var form = 	document.newWrite;
	if(!form.name.value){
		alert("성명을 입력하세요.");
		return false;
	}
	if (!form.subject.value) {
		alert("제목을 입력하세요.");
		return false;
	}
	if (!form.content.value) {
		alert("내용을 입력하세요.");
		return false;
	}	
}
</script>
</head>
<body>
	<!--<jsp:include page="/menu.jsp" />   -->
	<%@ include file="/menu.jsp" %>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">글쓰기</h1>
		</div>
	</div>
	<div class="container">
		<form action='<c:url value="/BoardWriteAction.do" />' method="post" 
			class="form-horizontal" 
			name="newWrite" onsubmit="return checkForm()">
			<input type="hidden" value="${sessionId}" name="id">
			<div class="form-group  row">
				<label class="col-sm-2">성명</label>
				<div class="col-sm-3">
					<input name="name" type="text" class="form-control"
						placeholder="name" value="<%=name%>">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2 control-label" >제목</label>
				<div class="col-sm-5">
					<input name="subject" type="text" class="form-control"
						placeholder="subject">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2 control-label" >내용</label>
				<div class="col-sm-8">
					<textarea name="content" cols="50" rows="5" 
						class="form-control" placeholder="content"></textarea>
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10 ">
				 <input type="submit" class="btn btn-primary" value="등록 ">				
					 <input type="reset" class="btn btn-primary" value="취소 ">
				</div>
			</div>	
		</form>
		<hr>
		<jsp:include page="../footer.jsp" />
	</div>
</body>
</html>