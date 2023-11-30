package algonquin.cst2335.demostore.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 *
 *
 * @author Roy S
 */
@Database(entities = {SunsetData.class}, version = 1)
public abstract class SunsetDatabase extends RoomDatabase {
    public abstract SunsetDataDao sunsetDataDao();
}
