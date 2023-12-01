package algonquin.cst2335.demostore.data;

/**
 * Extended SunsetData class to hold details returned from the Sunset &amp; Sunrise API
 *
 * @author Roy S
 * @version 1.0
 */
public class SunsetApiData extends SunsetData {
    /** String representing time of sunrise */
    private String sunrise;

    /** String representing time of sunset */
    private String sunset;

    /** String representing time of first light */
    private String firstLight;

    /** String representing time of last light */
    private String lastLight;

    /** String representing time of dawn */
    private String dawn;

    /** String representing time of dusk */
    private String dusk;

    /** String representing time of solar noon */
    private String solarNoon;

    /** String representing the time of the golden hour */
    private String goldenHour;

    /** String representing the day length */
    private String dayLength;

    /**
     * Constructor to create Sunset API Data
     *
     * @param lat Latitude of the location
     * @param lng Longitude of the location
     * @param sunrise Time of sunrise
     * @param sunset Time of sunset
     * @param firstLight Time of first light
     * @param lastLight Time of last light
     * @param dawn Time of dawn
     * @param dusk Time of dusk
     * @param solarNoon Time of solar noon
     * @param goldenHour Time of golden hour
     * @param dayLength Length of day
     */
    public SunsetApiData(String lat, String lng, String sunrise, String sunset,
                         String firstLight, String lastLight, String dawn, String dusk,
                         String solarNoon, String goldenHour, String dayLength) {
        super(lat, lng);
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

    /**
     * Getter for the time of sunrise
     *
     * @return The time of sunrise
     */
    public String getSunrise() {
        return sunrise;
    }

    /**
     * Setter for the time of sunrise
     *
     * @param sunrise The time of sunrise
     */
    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    /**
     * Getter for the time of sunset
     *
     * @return The time of sunset
     */
    public String getSunset() {
        return sunset;
    }

    /**
     * Setter for the time of sunsete
     *
     * @param sunset The time of sunset
     */
    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    /**
     * Getter for first light time
     *
     * @return The first light time
     */
    public String getFirstLight() {
        return firstLight;
    }

    /**
     * Setter for first light time
     *
     * @param firstLight The first light time
     */
    public void setFirstLight(String firstLight) {
        this.firstLight = firstLight;
    }

    /**
     * Getter for last light time
     *
     * @return The last light time
     */
    public String getLastLight() {
        return lastLight;
    }

    /**
     * Setter for last light time
     *
     * @param lastLight The last light time
     */
    public void setLastLight(String lastLight) {
        this.lastLight = lastLight;
    }

    /**
     * Getter for time of dawn
     *
     * @return The time of dawn
     */
    public String getDawn() {
        return dawn;
    }

    /**
     * Setter for time of dawn
     *
     * @param dawn The time of dawn
     */
    public void setDawn(String dawn) {
        this.dawn = dawn;
    }

    /**
     * Getter for time of dusk
     *
     * @return The time of dusk
     */
    public String getDusk() {
        return dusk;
    }

    /**
     * Setter for time of dusk
     *
     * @return The time of dusk
     */
    public void setDusk(String dusk) {
        this.dusk = dusk;
    }

    /**
     * Getter for the time of solar noon
     *
     * @return The time of solar noon
     */
    public String getSolarNoon() {
        return solarNoon;
    }

    /**
     * Setter for the time of solar noon
     *
     * @return The time of solar noon
     */
    public void setSolarNoon(String solarNoon) {
        this.solarNoon = solarNoon;
    }

    /**
     * Getter for the time of the golden hour
     *
     * @return The time of the golden hour
     */
    public String getGoldenHour() {
        return goldenHour;
    }

    /**
     * Setter for the time of the golden hour
     *
     * @return The time of the golden hour
     */
    public void setGoldenHour(String goldenHour) {
        this.goldenHour = goldenHour;
    }

    /**
     * Getter for the length of the day
     *
     * @return The length of the day
     */
    public String getDayLength() {
        return dayLength;
    }

    /**
     * Setter for the length of the day
     *
     * @return The length of the day
     */
    public void setDayLength(String dayLength) {
        this.dayLength = dayLength;
    }

    /**
     * String representation of Sunset API data
     *
     * @return String representing Sunset API data
     */
    @Override
    public String toString() {
        return "SunsetApiData{" +
                    "id=" + super.getId() +
                    ", lat='" + super.getLat() + '\'' +
                    ", lng='" + super.getLng() + '\'' +
                    ", sunrise='" + sunrise + '\'' +
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
