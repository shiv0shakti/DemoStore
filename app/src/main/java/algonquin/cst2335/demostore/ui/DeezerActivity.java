package algonquin.cst2335.demostore.ui;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.demostore.R;
import algonquin.cst2335.demostore.data.ArtistSearch;
import algonquin.cst2335.demostore.data.DeezerViewModel;
import algonquin.cst2335.demostore.data.FavouriteSong;
import algonquin.cst2335.demostore.databinding.DeezerLayoutBinding;

public class DeezerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DeezerLayoutBinding deezerLayoutBinding;
    private ArrayList<ArtistSearch.Song> songList = new ArrayList<>();
    private FavouritesActivity favouritesActivity;
    private ArtistSearch artistSearch = new ArtistSearch();
    Toolbar toolbar;
    FavouriteSong fDAO;
    ImageView albumCoverImageView;
    ArtistSearch.Artist artist;
    RecyclerView.Adapter<MyRowHolder> adapter;
    DeezerViewModel viewModel;
    EditText searchEditText;
    Button searchButton;
    Toolbar deezerToolbar;
    RecyclerView deezerRecyclerView;
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView artistTextView;
        ImageView albumCoverImageView;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            artistTextView = itemView.findViewById(R.id.artistTextView);
            albumCoverImageView = itemView.findViewById(R.id.albumCoverImageView);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();

                // Ensure the position is valid
                if (position != RecyclerView.NO_POSITION) {
                    ArtistSearch.Song clickedSong = songList.get(position);
                    onItemClick(clickedSong);
                }
            });

        }

        private int getBindingAdapterPosition() {
            return 0;
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deezerLayoutBinding = DeezerLayoutBinding.inflate(getLayoutInflater());
        setContentView(deezerLayoutBinding.getRoot());
        setContentView(R.layout.activity_deezer);
         searchEditText = findViewById(R.id.searchEditText);
         searchButton = findViewById(R.id.searchButton);



        albumCoverImageView = findViewById(R.id.albumCoverImageView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar = findViewById(R.id.deezerToolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.deezer_menu);


        adapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate(R.layout.deezer_layout, parent, false);
                return new MyRowHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ArtistSearch.Song currentSong = songList.get(position);
                holder.titleTextView.setText(currentSong.getTitle());
                holder.artistTextView.setText(currentSong.getAlbumName());

                holder.itemView.setOnClickListener(v -> {
                    onItemClick(currentSong);
                });
                holder.itemView.setOnClickListener(v -> {
                    onItemClick(currentSong);
                });
            }

            @Override
            public int getItemCount() {
                return songList.size();
            }
        };
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(view -> onSearchButtonClick());

        // Set onEditorActionListener for the search EditText
        searchEditText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onSearchButtonClick();
                return true;
            }
            return false;
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {

    }


    private void onItemClick(@NonNull ArtistSearch.Song song) {
        Intent detailsIntent = new Intent(this, ArtistSearch.Song.class);
        detailsIntent.putExtra("title", song.getTitle());
        detailsIntent.putExtra("duration", song.getDuration());
        detailsIntent.putExtra("albumName", song.getAlbumName());
        detailsIntent.putExtra("albumCover", song.getAlbumCover());
        startActivity(detailsIntent);
    }

    private void onItemClick(@NonNull ArtistSearch.Artist artist) {
        // Implement action for item click
        String tracklist = artist.getTracklist();
        // Show or use the tracklist information as needed
        Toast.makeText(this, "Tracklist: " + tracklist, Toast.LENGTH_SHORT).show();
    }


    private void showFavorites() {
        Intent intent = new Intent(this, FavouritesActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_help) {
            showHelpDialog();
            return true;
        } else if (itemId == R.id.action_favorites) {
            showFavourites();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    private void showFavourites() {
        Intent intent = new Intent(this, FavouritesActivity.class);
        startActivity(intent);
    }

    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage("Instructions for how to use the interface.\n\nYou type the song you want to see and click on it for more information regarding it");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }


    private void onSearchButtonClick() {
        String searchQuery = searchEditText.getText().toString();
        artist = new ArtistSearch().new Artist();
        artist.setArtistName(searchQuery);
        String stringURL = "https://api.deezer.com/search/artist/?q=" + artist.getArtistName();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                response -> {
                    try {
                        JSONArray data = response.getJSONArray("data");

                        if (data.length() > 0) {
                            JSONObject firstArtist = data.getJSONObject(0);
                            artist.setArtistName(firstArtist.getString("name"));


                            if (firstArtist.has("tracklist")) {
                                String tracklistUrl = firstArtist.getString("tracklist");


                                List<ArtistSearch.Song> topTracks = parseTopTracks(tracklistUrl);
                            }

                            runOnUiThread(() -> {
                                Toast.makeText(DeezerActivity.this, "Artist: " + artist.getArtistName(), Toast.LENGTH_SHORT).show();
                            });

                        } else {
                            Toast.makeText(DeezerActivity.this, "No artists found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(DeezerActivity.this, "Error parsing artist data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(DeezerActivity.this, "Error retrieving artist data", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(request);
    }




    private void updateRecyclerView(List<ArtistSearch.Song> songs) {
        songList.clear();
        songList.addAll(songs);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private List<ArtistSearch.Song> parseTopTracks(String tracklistUrl) throws JSONException {
        List<ArtistSearch.Song> songs = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, tracklistUrl, null,
                response -> {
                    try {

                        JSONArray tracksArray = response.getJSONArray("data");

                        for (int i = 0; i < tracksArray.length(); i++) {
                            JSONObject trackObject = tracksArray.getJSONObject(i);
                            ArtistSearch.Song song = new ArtistSearch.Song();

                            // Assuming "title" is the key for the song title
                            if (trackObject.has("title")) {
                                song.setTitle(trackObject.getString("title"));
                            }

                            // Assuming "duration" is the key for the song duration
                            if (trackObject.has("duration")) {
                                song.setDuration(trackObject.getString("duration"));
                            }

                            // Assuming "album" is an object containing the album information
                            if (trackObject.has("album")) {
                                JSONObject albumObject = trackObject.getJSONObject("album");

                                // Assuming "title" is the key for the album name
                                if (albumObject.has("title")) {
                                    song.setAlbumName(albumObject.getString("title"));
                                }

                                // Assuming "cover" is the key for the album cover
                                if (albumObject.has("cover")) {
                                    song.setAlbumCover(albumObject.getString("cover"));
                                }
                            }

                            songs.add(song);
                        }

                        // Notify that the songs have been updated (you may need to implement this method)
                        runOnUiThread(() -> updateRecyclerView(songs));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(DeezerActivity.this, "Error parsing top tracks data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(DeezerActivity.this, "Error retrieving top tracks data", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(request);

        return songs;


    }


}