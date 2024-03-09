<%-- 삭제될 북마크 내용이 전송된 jsp --%>
<%@ page import="function.BookmarkList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>북마크 삭제</title>
</head>

<body>
    <h1>북마크 삭제</h1>
    <%@include file="links.jsp"%>

    <%
        BookmarkList bookmarkList = new BookmarkList();

        request.setCharacterEncoding("UTF-8");

        String bookmarkListId = request.getParameter("bookmarkListId");

        int deleteData = bookmarkList.getDeleteBookmarkList(Integer.parseInt(bookmarkListId));
    %>
    <script>
        <%
            String delete = deleteData > 0 ? "북마크 정보를 삭제하였습니다." : "북마크 정보 삭제를 실패했습니다.";
        %>
        alert("<%=delete%>");
        location.href = "bookmarkList.jsp";
    </script>
</body>
</html>

