package algonquin.cst2335.demostore.data;
import java.util.List;

public class ArtistSearch {

    // Model classes

    // Response class for searching artists
    public class ArtistSearchResponse {
        private List<Artist> data;

        // Getter for data
        public List<Artist> getData() {
            return data;
        }

        // Setter for data
        public void setData(List<Artist> data) {
            this.data = data;
        }
    }

    // Artist class representing an artist
    public class Artist {
        private String artistName;
        private String tracklist;

        // Getter for artistName
        public String getArtistName() {
            return artistName;
        }

        // Setter for artistName
        public void setArtistName(String artistName) {
            this.artistName = artistName;
        }

        // Getter for tracklist
        public String getTracklist() {
            return tracklist;
        }

        // Setter for tracklist
        public void setTracklist(String tracklist) {
            this.tracklist = tracklist;
        }
    }

    // Response class for album data
    public class AlbumResponse {
        private List<Song> data;

        // Getter for data
        public List<Song> getData() {
            return data;
        }

        // Setter for data
        public void setData(List<Song> data) {
            this.data = data;
        }
    }

    // Song class representing a song
    public static class Song {
        private String title;
        private String duration;
        private String albumName;
        private String albumCover;

        // Getter for title
        public String getTitle() {
            return title;
        }

        // Setter for title
        public void setTitle(String title) {
            this.title = title;
        }

        // Getter for duration
        public String getDuration() {
            return duration;
        }

        // Setter for duration
        public void setDuration(String duration) {
            this.duration = duration;
        }

        // Getter for albumName
        public String getAlbumName() {
            return albumName;
        }

        // Setter for albumName
        public void setAlbumName(String albumName) {
            this.albumName = albumName;
        }

        // Getter for albumCover
        public String getAlbumCover() {
            return albumCover;
        }

        // Setter for albumCover
        public void setAlbumCover(String albumCover) {
            this.albumCover = albumCover;
        }
    }
}

