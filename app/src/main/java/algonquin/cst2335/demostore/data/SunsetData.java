package algonquin.cst2335.demostore.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Database SunsetData Entity to store the latitude and longitude of the users favourite locations
 *
 * @author Roy S
 * @version 1.0
 */
@Entity
public class SunsetData {
    /** Primary key / Entity id */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    /** Latitude of favourite location */
    @ColumnInfo(name = "lat")
    private String lat;

    /** Longitude of favourite location */
    @ColumnInfo(name = "lng")
    private String lng;

    /**
     * Constructor to create SunsetData entity
     *
     * @param lat The latitude of favourite location
     * @param lng the longitude of favourite location
     */
    public SunsetData(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * Get the Entity's Id
     *
     * @return The Entity id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the Entity Id
     *
     * @param id Entity Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the latitude of the favourite location
     *
     * @return The latitude of the favourite location
     */
    public String getLat() {
        return lat;
    }

    /**
     * Set the latitude of the favourite location
     *
     * @param lat The latitude of the favourite location
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * Get the longitude of the favourite location
     *
     * @return The longitude of the favourite location
     */
    public String getLng() {
        return lng;
    }

    /**
     * Set the longitude of the favourite location
     *
     * @param lng The longitude of the favourite location
     */
    public void setLng(String lng) {
        this.lng = lng;
    }

    /**
     * Represent the Entity as a String
     *
     * @return String representing the SunsetData Entity
     */
    @Override
    public String toString() {
        return "SunsetData{" +
                    "id=" + id +
                    ", lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                '}';
    }
}
