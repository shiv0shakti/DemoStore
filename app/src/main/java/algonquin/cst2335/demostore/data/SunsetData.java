package algonquin.cst2335.demostore.data;

public class SunsetData {
    private String sunrise;
    private String sunset;
    private String firstLight;
    private String lastLight;
    private String dawn;
    private String dusk;
    private String solarNoon;
    private String goldenHour;
    private String dayLength;


    public SunsetData(String sunrise, String sunset, String firstLight, String lastLight,
                      String dawn, String dusk, String solarNoon, String goldenHour,
                      String dayLength) {
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.firstLight = firstLight;
        this.lastLight = lastLight;
        this.dawn = dawn;
        this.dusk = dusk;
        this.solarNoon = solarNoon;
        this.goldenHour = goldenHour;
        this.dayLength = dayLength;
    }

    public String getSunrise() {
        return this.sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getFirstLight() {
        return firstLight;
    }

    public void setFirstLight(String firstLight) {
        this.firstLight = firstLight;
    }

    public String getLastLight() {
        return lastLight;
    }

    public void setLastLight(String lastLight) {
        this.lastLight = lastLight;
    }

    public String getDawn() {
        return dawn;
    }

    public void setDawn(String dawn) {
        this.dawn = dawn;
    }

    public String getDusk() {
        return dusk;
    }

    public void setDusk(String dusk) {
        this.dusk = dusk;
    }

    public String getSolarNoon() {
        return solarNoon;
    }

    public void setSolarNoon(String solarNoon) {
        this.solarNoon = solarNoon;
    }

    public String getGoldenHour() {
        return goldenHour;
    }

    public void setGoldenHour(String goldenHour) {
        this.goldenHour = goldenHour;
    }

    public String getDayLength() {
        return dayLength;
    }

    public void setDayLength(String dayLength) {
        this.dayLength = dayLength;
    }

    @Override
    public String toString() {
        return "SunsetData{" +
                    "sunrise='" + sunrise + '\'' +
                    ", sunset='" + sunset + '\'' +
                    ", firstLight='" + firstLight + '\'' +
                    ", lastLight='" + lastLight + '\'' +
                    ", dawn='" + dawn + '\'' +
                    ", dusk='" + dusk + '\'' +
                    ", solarNoon='" + solarNoon + '\'' +
                    ", goldenHour='" + goldenHour + '\'' +
                    ", dayLength='" + dayLength + '\'' +
                '}';
    }
}
