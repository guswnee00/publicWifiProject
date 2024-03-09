package data;

public class BookmarkGroupData {
    private int bookmarkId;         // 북마크그룹id
    private String bookmarkName;    // 이름
    private int sequence;           // 순서
    private String registerDttm;    // 등록일자
    private String modifyDttm;      // 수정일자

    public int getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public String getBookmarkName() {
        return bookmarkName;
    }

    public void setBookmarkName(String bookmarkName) {
        this.bookmarkName = bookmarkName;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getRegisterDttm() {
        return registerDttm;
    }

    public void setRegisterDttm(String registerDttm) {
        this.registerDttm = registerDttm;
    }

    public String getModifyDttm() {
        return modifyDttm;
    }

    public void setModifyDttm(String modifyDttm) {
        this.modifyDttm = modifyDttm;
    }

}
