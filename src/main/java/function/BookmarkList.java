/*
* saveBookmarkList -> 북마크 저장
* showBookmarkList -> 테이블에 북마크 리스트 보여주기
* deleteBookmarkList -> 삭제할 북마크
* setBookmarkList -> id에 해당하는 북마크 정보
*/

package function;

import data.BookmarkListData;
import database.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookmarkList {

    private BookmarkListData setBookmarkList(int id) {
        BookmarkListData bookmarkListData = new BookmarkListData();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnect.getConnection();

            String sql = " SELECT * "
                    + " FROM bookmark_list "
                    + " WHERE id = ? ";

            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                bookmarkListData.setId(resultSet.getInt("id"));
                bookmarkListData.setWifiMgrNo(resultSet.getString("wifi_mgr_no"));
                bookmarkListData.setBookmarkGroupId(resultSet.getInt("bookmark_id"));
                bookmarkListData.setRegisterDttm(resultSet.getString("register_dttm"));
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
        return bookmarkListData;
    }

    public BookmarkListData getBookmarkList(int id) {
        return setBookmarkList(id);
    }

    private int deleteBookmarkList(int id) {
        int deleteBookmarkList = 0;
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DBConnect.getConnection();

            String sql = " DELETE FROM bookmark_list "
                        + " WHERE id = ? ";

            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            deleteBookmarkList = pstmt.executeUpdate();

            if (deleteBookmarkList > 0) {
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
        return deleteBookmarkList;
    }

    public int getDeleteBookmarkList(int id) {
        return deleteBookmarkList(id);
    }

    private List<BookmarkListData> showBookmarkList() {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        List<BookmarkListData> list = new ArrayList<>();

        try {
            connection = DBConnect.getConnection();

            String sql = " SELECT * "
                        + " FROM bookmark_list "
                        + " INNER JOIN bookmark_group "
                        + " ON bookmark_list.bookmark_id = bookmark_group.bookmark_id ";

            pstmt = connection.prepareStatement(sql);

            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                BookmarkListData bookmarkListData = new BookmarkListData();

                bookmarkListData.setId(resultSet.getInt("id"));
                bookmarkListData.setWifiMgrNo(resultSet.getString("wifi_mgr_no"));
                bookmarkListData.setBookmarkGroupId(resultSet.getInt("bookmark_id"));
                bookmarkListData.setRegisterDttm(resultSet.getString("register_dttm"));

                list.add(bookmarkListData);

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
        return list;
    }

    public List<BookmarkListData> getShowBookmarkList() {
        return showBookmarkList();
    }

    private int saveBookmarkList(BookmarkListData bookmarkListData) {
        int saveBookmarkList = 0;
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DBConnect.getConnection();

            String sql = " INSERT INTO bookmark_list (wifi_mgr_no, bookmark_id, register_dttm) "
                        + " VALUES (?, ?, ?) ";

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS");
            String saveDateTime = now.format(dateTimeFormatter);

            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, bookmarkListData.getWifiMgrNo());
            pstmt.setInt(2, bookmarkListData.getBookmarkGroupId());
            pstmt.setString(3, saveDateTime);

            saveBookmarkList = pstmt.executeUpdate();

            if (saveBookmarkList > 0) {
                System.out.println("데이터베이스에 bookmark_list 데이터 적재를 성공했습니다.");
            } else {
                System.out.println("데이터베이스에 bookmark_list 데이터 적재를 실패했습니다.");
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
        return saveBookmarkList;
    }

    public int getSaveBookmarkList(BookmarkListData bookmarkListData) {
        return saveBookmarkList(bookmarkListData);
    }
}
