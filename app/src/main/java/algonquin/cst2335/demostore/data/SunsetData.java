package algonquin.cst2335.demostore.data;

public class SunsetData {
    private String lat;
    private String lng;

    public SunsetData(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "SunsetData{" +
                    "lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                '}';
    }
}
