<%-- 북마크 리스트 main jsp --%>
<%@ page import="function.Wifi"%>
<%@ page import="data.APIData"%>
<%@ page import="function.BookmarkGroup"%>
<%@ page import="data.BookmarkGroupData"%>
<%@ page import="function.BookmarkList"%>
<%@ page import="data.BookmarkListData"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>북마크 리스트</title>
    <style>
        table {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            border: 1px solid #ddd;
            width: 100%;
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

        #del {
            text-align: center;
        }

        #before_bmark {
            text-align: center;
        }
    </style>
</head>

<body>
    <h1>북마크 목록</h1>
    <%@include file="links.jsp"%>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>북마크 이름</th>
                <th>와이파이명</th>
                <th>등록일자</th>
                <th>비고</th>
            </tr>
        </thead>

        <tbody>
            <%
                BookmarkList bookmarkList = new BookmarkList();
                BookmarkGroup bookmarkGroup = new BookmarkGroup();
                Wifi wifi = new Wifi();

                List<BookmarkListData> bookmarkListData = bookmarkList.getShowBookmarkList();
                if (!bookmarkListData.isEmpty()) {
                    for (BookmarkListData data: bookmarkListData) {
                        BookmarkGroupData bookmarkGroupData = bookmarkGroup.getBookmark(data.getBookmarkGroupId());
                        APIData api = wifi.getWifi(data.getWifiMgrNo());
            %>
            <tr>
                <td><%=data.getBookmarkGroupId()%></td>
                <td><%=bookmarkGroupData.getBookmarkName()%></td>
                <td>
                    <a href="detailWifi2.jsp?wifiMgrNo=<%=api.getxSwifiMgrNo()%>"><%=api.getxSwifiMainNm()%></a>
                </td>
                <td><%=data.getRegisterDttm()%></td>
                <td id="del"><a href="bookmarkListDelete.jsp?bookmarkListId=<%=data.getId()%>">삭제</a></td>
            <%
                    }
                } else {
            %>
                <td colspan="5" id="before_bmark">정보가 존재하지 않습니다.</td>
            <%
                }
            %>
            </tr>
        </tbody>
    </table>
</body>

</html>