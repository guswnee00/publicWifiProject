<%-- 추가될 북마크 내용이 전송된 jsp --%>
<%@ page import="function.BookmarkList"%>
<%@ page import="data.BookmarkListData"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>북마크 추가</title>
</head>

<body>
    <%
        request.setCharacterEncoding("UTF-8");

        String wifiMgrNo = request.getParameter("wifiMgrNo");
        String bookmarkId = request.getParameter("bookmarkId");

        BookmarkList bookmarkList = new BookmarkList();
        BookmarkListData bookmarkListData = new BookmarkListData();
        bookmarkListData.setWifiMgrNo(wifiMgrNo);
        bookmarkListData.setBookmarkGroupId(Integer.parseInt(bookmarkId));
        int saveData = bookmarkList.getSaveBookmarkList(bookmarkListData);
    %>
    <script>
        <%
            String save = saveData > 0 ? "북마크 정보를 저장하였습니다." : "북마크 정보 저장을 실패했습니다.";
        %>
        alert("<%=save%>");
        location.href = "bookmarkList.jsp";
    </script>
</body>
</html>

