package algonquin.cst2335.demostore.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

    @Entity(tableName = "favourite_song_table")
    public class FavouriteSong {
        @ColumnInfo(name = "title")
        public String title;
        // ...

        @ColumnInfo(name = "id")
        public int id;

        // Add this constructor
        public FavouriteSong(String title, String duration, String albumName, String albumCover) {
            this.title = title;
            this.id = id;
        }
    }


