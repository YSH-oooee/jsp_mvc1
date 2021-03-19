<%@page import="step3_00_boardEx.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
		request.setCharacterEncoding("utf-8");
	%>
	
	<jsp:useBean id="boardDTO" class="step3_00_boardEx.BoardDTO">
		<jsp:setProperty name="boardDTO" property="*" />
	</jsp:useBean>
	
	<%
		boolean isDelete = BoardDAO.getInstance().deleteBoard(boardDTO);
	
		if(isDelete) {
	%>
		<script type="text/javascript">
			alert("게시글이 삭제되었습니다.");
			location.href="04_bList.jsp";
		</script>
	<%
		} else {
	%>
		<script type="text/javascript">
			alert("비밀번호를 확인하세요.");
			history.go(-1);
		</script>
	<%
		}
	%>

</body>
</html>