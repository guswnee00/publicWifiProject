package data;

public class LatLntData {
    private int historyId;      // 위치검색기록id
    private String lat;         // Y좌표
    private String lnt;         // X좌표
    private String searchDttm;  // 검색일자

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLnt() {
        return lnt;
    }

    public void setLnt(String lnt) {
        this.lnt = lnt;
    }

    public String getSearchDttm() {
        return searchDttm;
    }

    public void setSearchDttm(String searchDttm) {
        this.searchDttm = searchDttm;
    }

}
