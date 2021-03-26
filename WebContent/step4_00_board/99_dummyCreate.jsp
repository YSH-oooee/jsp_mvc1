<%@page import="step4_00_board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>테스트 데이터 생성</title>
</head>
<body>

	<%
		boolean isDummy = BoardDAO.getInstance().dummyCreate();
	
		if(isDummy) {
	%>
		<script type="text/javascript">
			alert("테스트 데이터가 등록되었습니다.");
			location.href='04_bList.jsp';
		</script>
	<%
		}
	%>

</body>
</html>