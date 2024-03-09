/*
* saveSearchHistory -> 위도 경도 검색일자 데이터베이스에 저장하는 함수
* searchHistory -> 데이터베이스에 저장된 검색기록 가져오는 함수
* deleteSearchHistory -> 검색 기록 삭제하는 함수
*/

package function;

import data.LatLntData;
import database.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LatLntHistory {
	
	static void saveSearchHistory(String latitude, String longitude) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = DBConnect.getConnection();
			
			String sql = "INSERT INTO search_history (lat, lnt, search_dttm) "
						+ " VALUES (?, ?, ?) ";
			
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS");
			String searchDateTime = now.format(dateTimeFormatter);
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setDouble(1, Double.parseDouble(latitude));
			pstmt.setDouble(2, Double.parseDouble(longitude));
			pstmt.setString(3, searchDateTime);
			int insertData = pstmt.executeUpdate();

			if (insertData > 0) {
				System.out.println("데이터베이스에 search_history 데이터 적재를 성공했습니다.");
			} else {
				System.out.println("데이터베이스에 search_history 데이터 적재를 실패했습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (connection != null) {
					System.out.println("데이터베이스 연결을 종료합니다.");
					connection.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	private List<LatLntData> searchHistory() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		List<LatLntData> historyList = new ArrayList<>();

		try {
			connection = DBConnect.getConnection();

			String sql = " SELECT * "
						+ " FROM search_history "
						+ " ORDER BY history_id "
						+ " DESC";

			pstmt = connection.prepareStatement(sql);

			resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				LatLntData latLntData = new LatLntData();

				latLntData.setHistoryId(resultSet.getInt("history_id"));
				latLntData.setLat(resultSet.getString("lat"));
				latLntData.setLnt(resultSet.getString("lnt"));
				latLntData.setSearchDttm(resultSet.getString("search_dttm"));

				historyList.add(latLntData);

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
		return historyList;
	}

	public List<LatLntData> getSearchHistory() {
		return searchHistory();
	}

	private void deleteSearchHistory(String id) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = DBConnect.getConnection();

			String sql = " DELETE FROM search_history "
						+ " WHERE history_id = ? ";

			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));
			int deleteData = pstmt.executeUpdate();

			if (deleteData > 0) {
				System.out.println("데이터베이스에서 데이터가 삭제되었습니다.");
			} else {
				System.out.println("데이터베이스에서 데이터 삭제를 실패하였습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (connection != null) {
					System.out.println("데이터베이스 연결을 종료합니다.");
					connection.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public void getDeleteSearchHistory(String id) {
		deleteSearchHistory(id);
	}

/*
	public static void main(String[] args) {
		LatLntHistory latLntHistory = new LatLntHistory();
		latLntHistory.getDeleteSearchHistory("30");
		List<LatLntData> list = latLntHistory.getSearchHistory();
		for (LatLntData latLntData: list) {
			System.out.println(latLntData.getHistoryId());
			System.out.println(latLntData.getLat());
			System.out.println(latLntData.getLnt());
			System.out.println(latLntData.getSearchDttm());
		}

	}
*/
}
