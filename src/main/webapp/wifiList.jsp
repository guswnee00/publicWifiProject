<%-- index.jsp에서 와이파이 리스트를 출력해줄 부분 --%>
<%@ page import="function.Wifi"%>
<%@ page import="data.APIData"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
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

        #before_search {
            text-align: center;
        }
    </style>
    <meta charset="UTF-8">
    <title>와이파이 리스트</title>
</head>

<body>
	<%
		String latInput = request.getParameter("latInput") == null ? "0.0" : request.getParameter("latInput");
		String lntInput = request.getParameter("lntInput") == null ? "0.0" : request.getParameter("lntInput");
	%>

	<div id="lat_lnt">
		<span>LAT: </span> 
		<input id="latInput" type="text" value="<%=latInput%>"> 
		<span>, LNT: </span> 
		<input id="lntInput" type="text" value="<%=lntInput%>">
		<button id="locationButton" type="button">내 위치 가져오기</button>
		<button id="wifiInfoButton" type="button">근처 WIFI 정보 보기</button>
	</div>

    <script>

        let latInput = null;
        let lntInput = null;

        window.onload = () => {
            latInput = document.getElementById("latInput").value;
            lntInput = document.getElementById("lntInput").value;
        }

        document.getElementById("locationButton").addEventListener("click",function() {
            if ("geolocation" in navigator) {
                navigator.geolocation.getCurrentPosition(function(position) {
                    let latitude = position.coords.latitude;
                    let longitude = position.coords.longitude;

                    document.getElementById("latInput").value = latitude;
                    document.getElementById("lntInput").value = longitude;

                });
            } else {
                alert("위치를 가져올 수 없습니다.")
            }
        });

        document.getElementById("wifiInfoButton").addEventListener("click", function() {
            let latitude = document.getElementById("latInput").value;
            let longitude = document.getElementById("lntInput").value;

            if (latitude != "" || longitude != "") {
                window.location.assign("http://localhost:8080?latInput=" + latitude + "&lntInput=" + longitude)
            } else {
                alert("위치 정보를 입력해주세요.")
            }
        })

    </script>

	<table>
		<thead>
			<tr>
				<th>거리(Km)</th>
				<th>관리번호</th>
				<th>자치구</th>
				<th>와이파이명</th>
				<th>도로명주소</th>
				<th>상세주소</th>
				<th>설치위치(층)</th>
				<th>설치유형</th>
				<th>설치기관</th>
				<th>서비스구분</th>
				<th>망종류</th>
				<th>설치년도</th>
				<th>실내외구분</th>
				<th>WIFI접속환경</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>작업일자</th>
			</tr>
		</thead>

		<tbody>

                <%
			        if (!("0.0").equals(latInput) && !("0.0").equals(lntInput)) {
                	    Wifi wifi = new Wifi();
                		List<APIData> nearWifi = wifi.getNearestWifiData(latInput, lntInput);
                		if (nearWifi != null) {
                		    for (APIData apiData: nearWifi) {
                %>
                        <tr>
                                <td><%=apiData.getDistance()%></td>
                                <td><%=apiData.getxSwifiMgrNo()%></td>
                                <td><%=apiData.getxSwifiWrdocf()%></td>
                                <td><a href ="detailWifi.jsp?wifiMgrNo=<%=apiData.getxSwifiMgrNo()%>&distance=<%=apiData.getDistance()%>"><%=apiData.getxSwifiMainNm()%></a></td>
                                <td><%=apiData.getxSwifiAdres1()%></td>
                                <td><%=apiData.getxSwifiAdres2()%></td>
                                <td><%=apiData.getxSwifiInstlFloor()%></td>
                                <td><%=apiData.getxSwifiInstlTy()%></td>
                                <td><%=apiData.getxSwifiInstlMby()%></td>
                                <td><%=apiData.getxSwifiSvcSe()%></td>
                                <td><%=apiData.getxSwifiCmcwr()%></td>
                                <td><%=apiData.getxSwifiCnstcYear()%></td>
                                <td><%=apiData.getxSwifiInoutDoor()%></td>
                                <td><%=apiData.getxSwifiRemars3()%></td>
                                <td><%=apiData.getLat()%></td>
                                <td><%=apiData.getLnt()%></td>
                                <td><%=apiData.getWorkDttm()%></td>
                        </tr>
                            <%}%>
                        <%}%>
                    <%} else {%>
                        <td colspan="17" id="before_search">위치 정보를 입력한 후에 조회해 주세요.</td>
			        <%}%>
		</tbody>
	</table>
</body>

</html>