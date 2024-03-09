/*
* DATA_SIZE_PER_PAGE -> 한번에 1000건씩만 데이터 요청 가능해서 변수 지정
* totalPageCount ->  전체 와이파이 개수 가져오기 위한 함수
* loadWifiData -> 테이블에 보여주기 위해 데이터베이스에서 와이파이 정보 가져오는 함수
* saveWifiData -> API로 가져온 와이파이 정보 데이터베이스에 저장하는 함수
* chooseOneWifi -> detail 보여줄 와이파이 정보 가져오는 함수
* nearestWifiData -> 근처 20개 와이파이 가져오는 함수
* setWifi -> 와이파이 관리번호로 와이파이 정보 가져오는 함수
*/

package function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import data.APIData;
import api.OpenApi;
import database.DBConnect;

public class Wifi {
	
	private static final int DATA_SIZE_PER_PAGE = 1000;
	
	private static int totalPageCount() {
		int totalWifiCnt = OpenApi.getTotalWifiCount();
		int totalPages = totalWifiCnt / DATA_SIZE_PER_PAGE;
		int remainder = totalWifiCnt % DATA_SIZE_PER_PAGE;
		if (remainder != 0) {
			totalPages++;
		}
		return totalPages;
	}
	
	private static boolean loadWifiData() {
		Connection connection = DBConnect.getConnection();
		
		try {
			connection.setAutoCommit(false);
			int totalPageCnt = totalPageCount();
			
			for (int i = 1; i <= totalPageCnt; i++) {
				int startIdx = (i - 1) * DATA_SIZE_PER_PAGE + 1;
				int endIdx = i * DATA_SIZE_PER_PAGE;
				
				List<APIData> dataList = OpenApi.getFetchData(startIdx, endIdx);
				
				saveWifiData(connection, dataList);	
			}
			
			connection.commit();
			System.out.println("데이터베이스에 public_wifi 데이터 적재를 성공했습니다.");
			return true;
			
		} catch (Exception e) {
			try {
                connection.rollback(); 
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true); 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	private static void saveWifiData(Connection connection, List<APIData> dataList) throws Exception {

	    String sql = "INSERT INTO public_wifi (x_swifi_mgr_no, x_swifi_wrdofc, x_swifi_main_nm, x_swifi_adres1, x_swifi_adres2,"
					+ " x_swifi_instl_floor, x_swifi_instl_ty, x_swifi_instl_mby, x_swifi_svc_se, x_swifi_cmcwr, "
					+ " x_swifi_cnstc_year, x_swifi_inout_door, x_swifi_remars3, lat, lnt, work_dttm) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	    String existingDataSql = "SELECT x_swifi_mgr_no FROM public_wifi";

	    PreparedStatement checkStmt = connection.prepareStatement(existingDataSql);
	    ResultSet resultSet = checkStmt.executeQuery();
	    Set<String> existingData = new HashSet<>();

	    while (resultSet.next()) {
	        existingData.add(resultSet.getString("x_swifi_mgr_no"));
	    }

	    PreparedStatement pstmt = connection.prepareStatement(sql);

	    int cnt = 0;

	    for (APIData data : dataList) {
	        if (existingData.contains(data.getxSwifiMgrNo())) {
	            continue;
	        }

	        pstmt.setString(1, data.getxSwifiMgrNo());
	        pstmt.setString(2, data.getxSwifiWrdocf());
	        pstmt.setString(3, data.getxSwifiMainNm());
	        pstmt.setString(4, data.getxSwifiAdres1());
	        pstmt.setString(5, data.getxSwifiAdres2());
	        pstmt.setString(6, data.getxSwifiInstlFloor());
	        pstmt.setString(7, data.getxSwifiInstlTy());
	        pstmt.setString(8, data.getxSwifiInstlMby());
	        pstmt.setString(9, data.getxSwifiSvcSe());
	        pstmt.setString(10, data.getxSwifiCmcwr());
	        pstmt.setString(11, data.getxSwifiCnstcYear());
	        pstmt.setString(12, data.getxSwifiInoutDoor());
	        pstmt.setString(13, data.getxSwifiRemars3());
	        pstmt.setString(14, data.getLat());
	        pstmt.setString(15, data.getLnt());
	        pstmt.setString(16, data.getWorkDttm());

	        pstmt.addBatch();

	        cnt++;

	        if (cnt % DATA_SIZE_PER_PAGE == 0) {
	            pstmt.executeBatch();
	            cnt = 0;
	        }
	    }
	    pstmt.executeBatch();

	    pstmt.close();
	    checkStmt.close();
	}
	
	private static List<APIData> chooseOneWifi(String xSwifiMgrNo, double distance) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		List<APIData> apiList = new ArrayList<>();;
		
		try {
			connection = DBConnect.getConnection();

			String sql = " SELECT * FROM public_wifi "
						+ " WHERE x_swifi_mgr_no = ? ";

			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, xSwifiMgrNo);

			resultSet = pstmt.executeQuery();
			
			if (resultSet.next()) {
				APIData apiData = new APIData();
				
				apiData.setDistance(distance);
				apiData.setxSwifiMgrNo(resultSet.getString("x_swifi_mgr_no"));
				apiData.setxSwifiWrdocf(resultSet.getString("x_swifi_wrdofc"));
				apiData.setxSwifiMainNm(resultSet.getString("x_swifi_main_nm"));
				apiData.setxSwifiAdres1(resultSet.getString("x_swifi_adres1"));
				apiData.setxSwifiAdres2(resultSet.getString("x_swifi_adres2"));
				apiData.setxSwifiInstlFloor(resultSet.getString("x_swifi_instl_floor"));
				apiData.setxSwifiInstlTy(resultSet.getString("x_swifi_instl_ty"));
				apiData.setxSwifiInstlMby(resultSet.getString("x_swifi_instl_mby"));
				apiData.setxSwifiSvcSe(resultSet.getString("x_swifi_svc_se"));
				apiData.setxSwifiCmcwr(resultSet.getString("x_swifi_cmcwr"));
				apiData.setxSwifiCnstcYear(resultSet.getString("x_swifi_cnstc_year"));
				apiData.setxSwifiInoutDoor(resultSet.getString("x_swifi_inout_door"));
				apiData.setxSwifiRemars3(resultSet.getString("x_swifi_remars3"));
				apiData.setLat(resultSet.getString("lnt"));
				apiData.setLnt(resultSet.getString("lat"));
				apiData.setWorkDttm(resultSet.getString("work_dttm"));

				apiList.add(apiData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) resultSet.close();
				if (pstmt != null) pstmt.close();
				if (connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return apiList;
		
	}
	
	public static List<APIData> getOneWifi(String xSwifiMgrNo, double distance) {
		return chooseOneWifi(xSwifiMgrNo, distance);
	}
	
	
	private List<APIData> nearestWifiData(String latitude, String longitude) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		List<APIData> nearestWifiList = new ArrayList<>();
		
		try {
			connection = DBConnect.getConnection();

			String sql = " SELECT ROUND(6371 * acos(cos(radians(lat)) * cos(radians(?)) * cos(radians(?) - radians(lnt)) + sin(radians(lat)) * sin(radians(?))), 4) AS distance, "
						+ " * "
						+ " FROM public_wifi "
						+ " ORDER BY distance "
						+ " LIMIT 20 ";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setDouble(1, Double.parseDouble(latitude));
			pstmt.setDouble(2, Double.parseDouble(longitude));
			pstmt.setDouble(3, Double.parseDouble(latitude));
			
			resultSet = pstmt.executeQuery();
			
			while (resultSet.next()) {
				APIData apiData = new APIData();
				
				apiData.setDistance(resultSet.getDouble("distance"));
				apiData.setxSwifiMgrNo(resultSet.getString("x_swifi_mgr_no"));
				apiData.setxSwifiWrdocf(resultSet.getString("x_swifi_wrdofc"));
				apiData.setxSwifiMainNm(resultSet.getString("x_swifi_main_nm"));
				apiData.setxSwifiAdres1(resultSet.getString("x_swifi_adres1"));
				apiData.setxSwifiAdres2(resultSet.getString("x_swifi_adres2"));
				apiData.setxSwifiInstlFloor(resultSet.getString("x_swifi_instl_floor"));
				apiData.setxSwifiInstlTy(resultSet.getString("x_swifi_instl_ty"));
				apiData.setxSwifiInstlMby(resultSet.getString("x_swifi_instl_mby"));
				apiData.setxSwifiSvcSe(resultSet.getString("x_swifi_svc_se"));
				apiData.setxSwifiCmcwr(resultSet.getString("x_swifi_cmcwr"));
				apiData.setxSwifiCnstcYear(resultSet.getString("x_swifi_cnstc_year"));
				apiData.setxSwifiInoutDoor(resultSet.getString("x_swifi_inout_door"));
				apiData.setxSwifiRemars3(resultSet.getString("x_swifi_remars3"));
				apiData.setLat(resultSet.getString("lat"));
				apiData.setLnt(resultSet.getString("lnt"));
				apiData.setWorkDttm(resultSet.getString("work_dttm"));
				
				nearestWifiList.add(apiData);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) resultSet.close();
				if (pstmt != null) pstmt.close();
				if (connection != null) {
					System.out.println("데이터베이스 연결을 종료합니다.");
					connection.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		LatLntHistory.saveSearchHistory(latitude, longitude);
		
		return nearestWifiList;
	}
	
	public List<APIData> getNearestWifiData(String latitude, String longitude) {
		return nearestWifiData(latitude, longitude);
	}

	private APIData setWifi(String xSwifiMgrNo) {
		APIData apiData = new APIData();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			connection = DBConnect.getConnection();

			String sql = " SELECT * "
						+ " FROM public_wifi "
						+ " WHERE x_swifi_mgr_no = ? ";

			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, xSwifiMgrNo);
			resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				apiData.setxSwifiMgrNo(resultSet.getString("x_swifi_mgr_no"));
				apiData.setxSwifiWrdocf(resultSet.getString("x_swifi_wrdofc"));
				apiData.setxSwifiMainNm(resultSet.getString("x_swifi_main_nm"));
				apiData.setxSwifiAdres1(resultSet.getString("x_swifi_adres1"));
				apiData.setxSwifiAdres2(resultSet.getString("x_swifi_adres2"));
				apiData.setxSwifiInstlFloor(resultSet.getString("x_swifi_instl_floor"));
				apiData.setxSwifiInstlTy(resultSet.getString("x_swifi_instl_ty"));
				apiData.setxSwifiInstlMby(resultSet.getString("x_swifi_instl_mby"));
				apiData.setxSwifiSvcSe(resultSet.getString("x_swifi_svc_se"));
				apiData.setxSwifiCmcwr(resultSet.getString("x_swifi_cmcwr"));
				apiData.setxSwifiCnstcYear(resultSet.getString("x_swifi_cnstc_year"));
				apiData.setxSwifiInoutDoor(resultSet.getString("x_swifi_inout_door"));
				apiData.setxSwifiRemars3(resultSet.getString("x_swifi_remars3"));
				apiData.setLat(resultSet.getString("lat"));
				apiData.setLnt(resultSet.getString("lnt"));
				apiData.setWorkDttm(resultSet.getString("work_dttm"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) resultSet.close();
				if (pstmt != null) pstmt.close();
				if (connection != null) {
					System.out.println("데이터베이스 연결을 종료합니다.");
					connection.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return apiData;
	}

	public APIData getWifi(String xSwifiMgrNo) {
		return setWifi(xSwifiMgrNo);
	}
/*
	public static void main(String[] args) {
		//loadWifiData();
		Wifi wifi = new Wifi();
		List<APIData> dataList= wifi.getNearestWifiData("37", "132");
		for (APIData apiData : dataList) {
			System.out.println(apiData.getDistance());
			System.out.println(apiData.getxSwifiMgrNo());
			System.out.println(apiData.getxSwifiWrdocf());
			System.out.println(apiData.getxSwifiMainNm());
			System.out.println(apiData.getxSwifiAdres1());
			System.out.println(apiData.getxSwifiAdres2());
			System.out.println(apiData.getxSwifiInstlFloor());
			System.out.println(apiData.getxSwifiInstlTy());
			System.out.println(apiData.getxSwifiInstlMby());
			System.out.println(apiData.getxSwifiSvcSe());
			System.out.println(apiData.getxSwifiCmcwr());
			System.out.println(apiData.getxSwifiCnstcYear());
			System.out.println(apiData.getxSwifiInoutDoor());
			System.out.println(apiData.getxSwifiRemars3());
			System.out.println(apiData.getLat());
			System.out.println(apiData.getLnt());
			System.out.println(apiData.getWorkDttm());

		}
	}
*/
}
