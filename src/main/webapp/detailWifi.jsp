<%-- 와이파이 세부정보를 보여주는 jsp --%>
<%@ page import="java.util.*"%>
<%@ page import="function.Wifi"%>
<%@ page import="function.BookmarkGroup"%>
<%@ page import="data.APIData"%>
<%@ page import="data.BookmarkGroupData"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>와이파이 세부정보</title>
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

    </style>
</head>

<body>
	<h1>와이파이 정보 구하기</h1>
    <%@include file="links.jsp"%>

    <%
        String wifiMgrNo = request.getParameter("wifiMgrNo");
        String distance = request.getParameter("distance");

        BookmarkGroup bookmarkGroup = new BookmarkGroup();
        List<BookmarkGroupData> bookmarks = bookmarkGroup.getShowBookmark();
        request.setAttribute("bookmarkList", bookmarks);
    %>

    <form action="bookmarkListAdd.jsp" name="bookmarkList">
        <select name="bookmarkId">
            <option value="none" selected>북마크 그룹 이름 선택</option>
            <% for (BookmarkGroupData data: bookmarks) { %>
            <option value="<%=data.getBookmarkId()%>">
                <%=data.getBookmarkName()%>
            </option>
            <% } %>
        </select>
        <input type="submit" value="북마크 추가하기">
        <input type="hidden" name="wifiMgrNo" value="<%=wifiMgrNo%>">
    </form>

    <%
        Wifi wifi = new Wifi();
        List<APIData> apiList = wifi.getOneWifi(wifiMgrNo, Double.parseDouble(distance));
        for (APIData apiData: apiList) {
    %>

    <table>
        <tr>
            <th>거리(Km)</th>
            <td><%=apiData.getDistance()%></td>
        </tr>
        <tr>
            <th>관리번호</th>
            <td><%=apiData.getxSwifiMgrNo()%></td>
        </tr>
        <tr>
            <th>자치구</th>
            <td><%=apiData.getxSwifiWrdocf()%></td>
        </tr>
        <tr>
            <th>와이파이명</th>
            <td><a href=""><%=apiData.getxSwifiMainNm()%></a></td>
        </tr>
        <tr>
            <th>도로명주소</th>
            <td><%=apiData.getxSwifiAdres1()%></td>
        </tr>
        <tr>
            <th>상세주소</th>
            <td><%=apiData.getxSwifiAdres2()%></td>
        </tr>
        <tr>
            <th>설치위치(층)</th>
            <td><%=apiData.getxSwifiInstlFloor()%></td>
        </tr>
        <tr>
            <th>설치유형</th>
            <td><%=apiData.getxSwifiInstlTy()%></td>
        </tr>
        <tr>
            <th>설치기관</th>
            <td><%=apiData.getxSwifiInstlMby()%></td>
        </tr>
        <tr>
            <th>서비스구분</th>
            <td><%=apiData.getxSwifiSvcSe()%></td>
        </tr>
        <tr>
            <th>망종류</th>
            <td><%=apiData.getxSwifiCmcwr()%></td>
        </tr>
        <tr>
            <th>설치년도</th>
            <td><%=apiData.getxSwifiCnstcYear()%></td>
        </tr>
        <tr>
            <th>실내외구분</th>
            <td><%=apiData.getxSwifiInoutDoor()%></td>
        </tr>
        <tr>
            <th>WIFI접속환경</th>
            <td><%=apiData.getxSwifiRemars3()%></td>
        </tr>
        <tr>
            <th>X좌표</th>
            <td><%=apiData.getLat()%></td>
        </tr>
        <tr>
            <th>Y좌표</th>
            <td><%=apiData.getLnt()%></td>
        </tr>
        <tr>
            <th>작업일자</th>
            <td><%=apiData.getWorkDttm()%></td>
        </tr>
        <%}%>
    </table>
</body>

</html>