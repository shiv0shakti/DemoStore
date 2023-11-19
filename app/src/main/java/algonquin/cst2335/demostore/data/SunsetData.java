package algonquin.cst2335.demostore.data;

public class SunsetData {
    private String sunrise;
    private String sunset;
    private String dawn;
    private String dusk;

    public SunsetData(String sunrise, String sunset, String dawn, String dusk) {
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.dawn = dawn;
        this.dusk = dusk;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return this.sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
    public String getDawn() {
        return this.dawn;
    }

    public void setDawn(String dawn) {
        this.dawn = dawn;
    }

    public String getDusk() {
        return this.dusk;
    }

    public void setDusk(String dusk) {
        this.dusk = dusk;
    }


    @Override
    public String toString() {
        return "SunsetData {" +
                "sunrise='" + this.sunrise + '\'' +
                ", sunset='" + this.sunset + '\'' +
                ", dawn='" + this.dawn + '\'' +
                ", dusk='" + this.dusk + '\'' +
                '}';
    }
}
