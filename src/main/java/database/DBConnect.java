/*
 * getConnection -> db 연결 함수
 * closeConnection -> db 연결 해제 함수
 * (connection 뿐만 아니라 resultSet과 preparedStatement도 해제해야하는 경우가 대부분이라 활용X -> 이후 디벨롭할 시간이 생기면 함수 수정 예정)
 */

package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
	static Connection connection = null;
	static final String DATABASE_FILE_PATH = "/Users/hyunjulee/Desktop/zerobase/project/publicWifiProject/publicWifi.db";
	static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_FILE_PATH;
	
	public static Connection getConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(DATABASE_URL);
			if (connection != null && !connection.isClosed()) {
				System.out.println("데이터베이스 연결을 성공 했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void closeConnection() {
		if(connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
/*	
	public static void main(String[] args) {
		try {
			connection = getConnection();
			if (connection != null && !connection.isClosed()) {
				System.out.println("데이터베이스 연결 성공");
			} else {
				System.out.println("데이터베이스 연결 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
*/	
}
