package data;

public class BookmarkListData {
    private int id;                 // 북마크리스트id

    private String wifiMgrNo;       // 관리번호(APIData)
    private int bookmarkGroupId;    // 북마크그룹id(BookmarkGroupData)
    private String registerDttm;    // 북마크리스트등록일자

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWifiMgrNo() {
        return wifiMgrNo;
    }

    public void setWifiMgrNo(String wifiMgrNo) {
        this.wifiMgrNo = wifiMgrNo;
    }

    public int getBookmarkGroupId() {
        return bookmarkGroupId;
    }

    public void setBookmarkGroupId(int bookmarkGroupId) {
        this.bookmarkGroupId = bookmarkGroupId;
    }

    public String getRegisterDttm() {
        return registerDttm;
    }

    public void setRegisterDttm(String registerDttm) {
        this.registerDttm = registerDttm;
    }



}
