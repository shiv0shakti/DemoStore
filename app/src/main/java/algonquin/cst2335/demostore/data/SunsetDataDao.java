package algonquin.cst2335.demostore.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * SunsetData data access object to perform operations on SunsetData entities
 *
 * @author Roy S
 * @version 1.0
 */
@Dao
public interface SunsetDataDao {
    /**
     * Create SunsetData entity operation
     *
     * @param sunsetData
     * @return The id of the inserted entity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertSunsetData(SunsetData sunsetData);

    /**
     * Query to select all SunsetData entities from database
     *
     * @return List of SunsetData entities
     */
    @Query("SELECT * FROM SunsetData")
    public List<SunsetData> getAllSunsetData();

    /**
     * Delete SunsetData entity operation
     *
     * @param sunsetData The object/entity to delete
     */
    @Delete
    public void deleteSunsetData(SunsetData sunsetData);
}
