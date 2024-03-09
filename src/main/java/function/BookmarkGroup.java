/*
* saveNewBookmark -> 입력창에서 입력받은 북마크그룹 이름과 순서를 데이터베이스에 저장
* showBookmark -> 테이블에 보여줄 북마크그룹 리스트 (데이터베이스에서 가져와서 sequence 순서대로 배열 (작 -> 큰))
* deleteBookmark -> 비고란에서 삭제를 선택할 경우 해당 북마크그룹 삭제
* modifyBookmark -> 비고란에서 수정을 선택할 경우 해당 북마크그룹 수정
* setBookmark -> id에 해당하는 북마크그룹 정보 가져오기
*/

package function;

import data.BookmarkGroupData;
import database.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookmarkGroup {

    private BookmarkGroupData setBookmark(int bookmarkId) {
        BookmarkGroupData bookmarkGroupData = new BookmarkGroupData();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnect.getConnection();

            String sql = " SELECT * "
                        + " FROM bookmark_group "
                        + " WHERE bookmark_id = ? ";

            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, bookmarkId);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                bookmarkGroupData.setBookmarkId(resultSet.getInt("bookmark_id"));
                bookmarkGroupData.setBookmarkName(resultSet.getString("name"));
                bookmarkGroupData.setSequence(resultSet.getInt("sequence"));
                bookmarkGroupData.setRegisterDttm(resultSet.getString("register_dttm"));
                bookmarkGroupData.setModifyDttm(resultSet.getString("modify_dttm"));
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
        return bookmarkGroupData;
    }

    public BookmarkGroupData getBookmark(int bookmarkId) {
        return setBookmark(bookmarkId);
    }

    private int modifyBookmark(int bookmarkId, String name, int sequence) {
        int modifyBookmark = 0;
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DBConnect.getConnection();

            String sql = " UPDATE bookmark_group "
                    + " SET name = ?, sequence = ?, modify_dttm = ?"
                    + " WHERE bookmark_id = ? ";

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS");
            String modifyDateTime = now.format(dateTimeFormatter);

            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setInt(2, sequence);
            pstmt.setString(3, modifyDateTime);
            pstmt.setInt(4, bookmarkId);
            modifyBookmark = pstmt.executeUpdate();

            if (modifyBookmark > 0) {
                System.out.println("데이터베이스의 데이터가 업데이트 되었습니다.");
            } else {
                System.out.println("데이터베이스의 데이터 업데이트를 실패하였습니다.");
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
        return modifyBookmark;
    }

    public int getModifyBookmark(int bookmarkId, String name, int sequence) {
        return modifyBookmark(bookmarkId, name, sequence);
    }

    private int deleteBookmark(int bookmarkId) {
        int deleteBookmark = 0;
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DBConnect.getConnection();

            // 외래키 설정인데 일단 없이 해보기
            //String foreignKey = " PRAGMA foreign_keys = ON ";

            //pstmt = connection.prepareStatement(foreignKey);
            //pstmt.executeUpdate();

            String sql = " DELETE FROM bookmark_group "
                        + " WHERE bookmark_id = ? ";

            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, bookmarkId);
            deleteBookmark = pstmt.executeUpdate();

            if (deleteBookmark > 0) {
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
        return deleteBookmark;
    }

    public int getDeleteBookmark(int bookmarkId) {
        return deleteBookmark(bookmarkId);
    }

    private List<BookmarkGroupData> showBookmark() {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        List<BookmarkGroupData> groupList = new ArrayList<>();

        try {
            connection = DBConnect.getConnection();

            String sql = " SELECT * "
                        + " FROM bookmark_group "
                        + " ORDER BY sequence ";

            pstmt = connection.prepareStatement(sql);

            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                BookmarkGroupData bookmarkGroupData = new BookmarkGroupData();

                bookmarkGroupData.setBookmarkId(resultSet.getInt("bookmark_id"));
                bookmarkGroupData.setBookmarkName(resultSet.getString("name"));
                bookmarkGroupData.setSequence(resultSet.getInt("sequence"));
                bookmarkGroupData.setRegisterDttm(resultSet.getString("register_dttm"));
                bookmarkGroupData.setModifyDttm(resultSet.getString("modify_dttm"));

                groupList.add(bookmarkGroupData);

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
        return groupList;
    }

    public List<BookmarkGroupData> getShowBookmark() {
        return showBookmark();
    }

    private int saveNewBookmark(BookmarkGroupData bookmarkGroupData) {
        int saveBookmark = 0;
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DBConnect.getConnection();

            String sql = " INSERT INTO bookmark_group (name, sequence, register_dttm) "
                        + " VALUES (?, ?, ?) ";

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS");
            String saveDateTime = now.format(dateTimeFormatter);

            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, bookmarkGroupData.getBookmarkName());
            pstmt.setInt(2, bookmarkGroupData.getSequence());
            pstmt.setString(3, saveDateTime);

            saveBookmark = pstmt.executeUpdate();

            if (saveBookmark > 0) {
                System.out.println("데이터베이스에 bookmark_group 데이터 적재를 성공했습니다.");
            } else {
                System.out.println("데이터베이스에 bookmark_group 데이터 적재를 실패했습니다.");
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
        return saveBookmark;
    }

    public int getSaveNewBookmark(BookmarkGroupData bookmarkGroupData) {
        return saveNewBookmark(bookmarkGroupData);
    }
/*
    public static void main(String[] args) {
        BookmarkGroup bookmarkGroup = new BookmarkGroup();
        List<BookmarkGroupData> list = bookmarkGroup.getShowBookmark();
        if (!list.isEmpty()) {
            for (BookmarkGroupData data : list) {
                System.out.println(data.getBookmarkId());
                System.out.println(data.getBookmarkName());
                System.out.println(data.getSequence());
                System.out.println(data.getRegisterDttm());
                System.out.println(data.getModifyDttm());
            }
        } else {
            System.out.println("데이터 없음");
        }
        int delete = bookmarkGroup.getDeleteBookmark(1);
        String deleteMessage = delete > 0 ? "삭제완료" : "삭제실패";
        System.out.println(deleteMessage);
    }
*/
}
