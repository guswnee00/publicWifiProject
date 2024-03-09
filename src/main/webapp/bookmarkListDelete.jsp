<%-- 북마크 보기에서 북마크를 삭제하는 jsp --%>
<%@ page import="function.Wifi"%>
<%@ page import="data.APIData"%>
<%@ page import="function.BookmarkGroup"%>
<%@ page import="data.BookmarkGroupData"%>
<%@ page import="function.BookmarkList"%>
<%@ page import="data.BookmarkListData"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>북마크 삭제</title>
	<style>
        table {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            border: 1px solid #ddd;
            width: 100%;
            margin-top: 10px;
        }

        tr {
            padding: 8px;
            font-size: 13px;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #ddd;
        }

        th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: center;
            border: 1px solid #ddd;
            background-color: #04AA6D;
            color: white;
        }

        td {
            border: 1px solid #ddd;
            padding: 8px;
            font-weight: normal;
            font-size: 13px;
        }

        #delete {
            text-align: center;
        }

    </style>
</head>

<body>
    <h1>북마크 삭제</h1>
    <%@include file="links.jsp"%>
    <div>북마크를 삭제하시겠습니까?</div>

    <%
        String bookmarkListId = request.getParameter("bookmarkListId");

        BookmarkList bookmarkList = new BookmarkList();
        BookmarkGroup bookmarkGroup = new BookmarkGroup();
        Wifi wifi = new Wifi();

        BookmarkListData bookmarkListData = bookmarkList.getBookmarkList(Integer.parseInt(bookmarkListId));
        BookmarkGroupData bookmarkGroupData = bookmarkGroup.getBookmark(bookmarkListData.getBookmarkGroupId());
        APIData apiData = wifi.getWifi(bookmarkListData.getWifiMgrNo());
    %>

    <form method="post" action="bookmarkListDeleteComplete.jsp">
        <table>
            <tr>
                <th>북마크 이름</th>
                <td><%=bookmarkGroupData.getBookmarkName()%></td>
            </tr>
            <tr>
                <th>와이파이명</th>
                <td><%=apiData.getxSwifiMainNm()%></td>
            </tr>
            <tr>
                <th>등록일자</th>
                <td><%=bookmarkListData.getRegisterDttm()%></td>
            </tr>
            <tr>
                <td colspan="3" id="delete">
                    <a href="bookmarkList.jsp">돌아가기</a>
                    <span>|</span>
                    <input type="submit" value="삭제">
                    <input type="hidden" name="bookmarkListId" value="<%=bookmarkListData.getId()%>">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>