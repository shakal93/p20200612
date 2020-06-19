<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>게시판 글쓰기</title>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css"
	rel="stylesheet"/>
</head>
<body>
	<div class="container">
		<div style="width:700px; padding:50px; border:1px solid #cccccc;">
		<form action="/board/insert" method="post" enctype="multipart/form-data">
			<div class="form-inline" style="margin:5px;">
				<label style="width:100px;">제목</label>
				<input type="text" class="form-control" name="brd_title" placeholder="제목" />
			</div>
			<div class="form-inline" style="margin:5px;">
				<label style="width:100px;">내용</label>
				<textarea class="form-control" rows="10" cols="50" name="brd_content" placeholder="내용"></textarea>
			</div>
			<div class="form-inline" style="margin:5px;">
				<label style="width:100px;">이미지</label>
				<input type="file" class="form-control" name="imgs"/>
			</div>
			<input type="hidden" name="brd_id" value="${sessionScope.SESSION_ID}"/>
			<div class="form-inline" style="margin:5px;">
				<label style="width:100px;"></label>
				<input type="submit" class="btn btn-success" value="글쓰기" />&nbsp;
				<a href="/" class="btn btn-secondary">홈으로</a>
			</div>
		</form>
		</div>
	</div>
</body>
</html>