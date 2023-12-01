package algonquin.cst2335.demostore.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Database to store saved favourite locations as SunsetData Entities
 *
 * @author Roy S
 * @version 1.0
 */
@Database(entities = {SunsetData.class}, version = 1)
public abstract class SunsetDatabase extends RoomDatabase {
    /**
     * Return data access object to perform operations on favourite locations database
     *
     * @return SunsetData data access object
     */
    public abstract SunsetDataDao sunsetDataDao();
}
