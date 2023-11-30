package algonquin.cst2335.demostore.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 *
 *
 * @author Roy S
 */
@Dao
public interface SunsetDataDao {
    @Insert
    public long insertSunsetData(SunsetData sunsetData);

    @Query("SELECT * FROM SunsetData")
    public List<SunsetData> getAllSunsetData();

    @Delete
    public void deleteSunsetData(SunsetData sunsetData);
}
