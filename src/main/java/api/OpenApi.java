/*
 * countPublicWifi -> 몇 건의 데이터가 있는지 확인하는 함수(xml 파일 속 '총 데이터 건수' 태그 활용)
 * fetchData -> API에서 가져온 xml을 API 데이터리스트로 만드는 함수
 * parseData -> 태그 이름에 해당하는 데이터를 APIData와 맞추는 함수
 * getTagValue -> xml 태그 이름 가져오는 함수
 */

package api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import data.APIData;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class OpenApi {
	private static final String API_BASE_URL = "http://openapi.seoul.go.kr:8088/";
	private static final String API_KEY = "*****";
	private static int totalWifiCnt;

	private static int countPublicWifi() throws IOException {
		totalWifiCnt = 0;
		try {
			HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(API_BASE_URL + API_KEY + "/xml/TbPublicWifiInfo/1/5/");
			HttpResponse httpResponse = httpClient.execute(httpGet);
			
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				System.out.println("API 호출을 성공 했습니다.");
				
				InputStream inputStream = httpResponse.getEntity().getContent();
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(inputStream);
				document.getDocumentElement().normalize();
				
				String totalCnt = document.getElementsByTagName("list_total_count").item(0).getTextContent();
				//System.out.println("총 " + totalCnt + "건의 데이터를 가져왔습니다.");
				
				totalWifiCnt = Integer.parseInt(totalCnt);
			} else {
				System.out.println("API 호출을 실패했습니다.");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalWifiCnt;
	}
	
	public static int getTotalWifiCount() {
		try {
			countPublicWifi();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return	totalWifiCnt;
	}
	
	private static List<APIData> fetchData(int startIdx, int endIdx) throws Exception {
		List<APIData> dataList = new ArrayList<>();
		
		String apiUrl = API_BASE_URL + API_KEY + "/xml/TbPublicWifiInfo/" + startIdx + "/" + endIdx;
		URL url = new URL(apiUrl);
		InputStream inputStream = url.openStream();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputStream);
		document.getDocumentElement().normalize();
		
		NodeList rowList = document.getElementsByTagName("row");
		for (int i = 0; i < rowList.getLength(); i++) {
			Element rowElement = (Element) rowList.item(i);
			APIData data = parseData(rowElement);
			dataList.add(data);
		}
		return dataList;
	}
	
	public static List<APIData> getFetchData(int startIdx, int endIdx) throws Exception {
		List<APIData> dataList = fetchData(startIdx, endIdx);
		return dataList;
		
	}
	
	private static APIData parseData(Element rowElement) {
		APIData apiData = new APIData();
		
		apiData.setxSwifiMgrNo(getTagValue(rowElement, "X_SWIFI_MGR_NO"));
		apiData.setxSwifiWrdocf(getTagValue(rowElement, "X_SWIFI_WRDOFC"));
		apiData.setxSwifiMainNm(getTagValue(rowElement, "X_SWIFI_MAIN_NM"));
		apiData.setxSwifiAdres1(getTagValue(rowElement, "X_SWIFI_ADRES1"));
		apiData.setxSwifiAdres2(getTagValue(rowElement, "X_SWIFI_ADRES2"));
		apiData.setxSwifiInstlFloor(getTagValue(rowElement, "X_SWIFI_INSTL_FLOOR"));
		apiData.setxSwifiInstlTy(getTagValue(rowElement, "X_SWIFI_INSTL_TY"));
		apiData.setxSwifiInstlMby(getTagValue(rowElement, "X_SWIFI_INSTL_MBY"));
		apiData.setxSwifiSvcSe(getTagValue(rowElement, "X_SWIFI_SVC_SE"));
		apiData.setxSwifiCmcwr(getTagValue(rowElement, "X_SWIFI_CMCWR"));
		apiData.setxSwifiCnstcYear(getTagValue(rowElement, "X_SWIFI_CNSTC_YEAR"));
		apiData.setxSwifiInoutDoor(getTagValue(rowElement, "X_SWIFI_INOUT_DOOR"));
		apiData.setxSwifiRemars3(getTagValue(rowElement, "X_SWIFI_REMARS3"));
		apiData.setLat(getTagValue(rowElement, "LAT"));
	    apiData.setLnt(getTagValue(rowElement, "LNT"));
	    apiData.setWorkDttm(getTagValue(rowElement, "WORK_DTTM"));

	    return apiData;
	}
	
	public static APIData getParseData(Element element) {
		APIData apiData = parseData(element);
		return apiData;
	}
	
	private static String getTagValue(Element element, String tagName) {
		NodeList nodeList = (element).getElementsByTagName(tagName);
		if (nodeList != null && nodeList.getLength() > 0) {
			return nodeList.item(0).getTextContent();
		} else {
			return "";
		}
	}
/*
	public static void main(String[] args) {
		try {
			int totalCount = getTotalWifiCount();
			System.out.println("총 " + totalCount + "건의 데이터를 가져왔습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
*/
}
