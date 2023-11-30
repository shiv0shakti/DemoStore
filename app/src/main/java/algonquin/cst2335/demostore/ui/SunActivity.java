package algonquin.cst2335.demostore.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.demostore.R;
import algonquin.cst2335.demostore.data.SunsetApiData;
import algonquin.cst2335.demostore.data.SunsetData;
import algonquin.cst2335.demostore.data.SunsetDataDao;
import algonquin.cst2335.demostore.data.SunsetDatabase;
import algonquin.cst2335.demostore.data.SunsetViewModel;
import algonquin.cst2335.demostore.databinding.ActivitySunBinding;
import algonquin.cst2335.demostore.databinding.SunsetFavItemBinding;

/**
 * Main Activity for Sunset and Sunrise API application. Uses Volley to make a request to a Sunset
 * and Sunrise API and displays details based on Latitude and Longitude entered by the user.
 * Uses Room DB and a RecyclerView to let the user save and delete favourite locations and then
 * displays them in a RecyclerView.
 *
 * @version 1.0
 * @author Roy S
 */
public class SunActivity extends AppCompatActivity {

    /** Class Tag for Logcat */
    private static final String TAG = SunActivity.class.getName();

    /** Sunset Main Activity view binder */
    private ActivitySunBinding binding;

    /** ArrayList holding saved data */
    private ArrayList<SunsetData> dataList = new ArrayList<>();

    /** ViewModel for saved data */
    private SunsetViewModel sunsetViewModel;

    /** Data access object to call database functions */
    private SunsetDataDao sunsetDataDao;

    /** Adapter for favourites RecylerView */
    private RecyclerView.Adapter<MyRowHolder> myAdapter;

    /** Index of selected favourite item  */
    private int selectedItemPos = -1;

    /** Boolean determining whether a favourite item is selected and can be deleted  */
    private boolean canDelete = false;

    /** Request Queue for making Sunset API requests */
    private RequestQueue requestQueue = null;

    /** String of API request URL */
    private String reqUrl;

    /**
     * Row holder for favourite locations RecyclerView. Displays the Latitude and Longitude of the
     * saved favourite location. When row is selected a new Volley request is made and the details
     * are displayed in a fragment.
     *
     * @version 1.0
     * @author Roy S
     */
    class MyRowHolder extends RecyclerView.ViewHolder {
        /** TextView for saved Latitude */
        TextView lat;

        /** TextView for saved Longitude */
        TextView lng;

        /**
         * Favourite item RowHolder to hold the sunset_fav_item.xml layout. Makes a request to
         * to a Sunset and Sunrise API with Volley when Row is pressed and displays the details
         * in a fragment.
         *
         * @param itemView The View holding the sunset_fav_item.xml layout
         */
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            lat = itemView.findViewById(R.id.sunsetFavTitleLat);
            lng = itemView.findViewById(R.id.sunsetFavTitleLng);

            // if item in the RowHolder is pressed
            itemView.setOnClickListener(view -> {
                selectedItemPos = getAdapterPosition();
                canDelete = true;

                SunsetData selectedData = dataList.get(getAdapterPosition());
                sunsetViewModel.selectedSunsetData.postValue(selectedData);

                reqUrl = "https://api.sunrisesunset.io/json?lat=" + selectedData.getLat() + "&lng=" + selectedData.getLng() +"&timezone=EST&date=today";

                // if item in RecyclerView is pressed make request with saved Lat and Lng
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, reqUrl, null,
                    (response) -> {
                        try {
                            JSONObject result = response.getJSONObject("results");
                            Log.d(TAG, result.toString());

                            String sunrise = result.getString("sunrise");
                            String sunset = result.getString("sunset");
                            String firstLight = result.getString("first_light");
                            String lastLight = result.getString("last_light");
                            String dawn = result.getString("dawn");
                            String dusk = result.getString("dusk");
                            String solarNoon = result.getString("solar_noon");
                            String goldenHour = result.getString("golden_hour");
                            String dayLength = result.getString("day_length");

                            SunsetApiData detailsData = new SunsetApiData(
                                    selectedData.getLat(), selectedData.getLng(),
                                    sunrise, sunset, firstLight,
                                    lastLight, dawn, dusk, solarNoon,
                                    goldenHour, dayLength);

                            // need this here to show background color change when selected
                            myAdapter.notifyDataSetChanged();

                            // open details in fragment
                            SunDetailsFragment detailsFragment = new SunDetailsFragment(detailsData, true);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragmentLocation, detailsFragment)
                                    .addToBackStack("")
                                    .commit();
                        } catch (JSONException exception) {
                            // bad request
                            Toast.makeText(SunActivity.this, R.string.sunset_error, Toast.LENGTH_LONG).show();
                        }
                    }, (error) -> {
                        // cant make request (ex: no internet)
                        Toast.makeText(SunActivity.this, R.string.sunset_http_error, Toast.LENGTH_LONG).show();
                    }
                );
                requestQueue.add(request);
            });
        }
    }

    /**
     * Create the activity and handle user interaction. Retreives text from Lat and Lng EditTexts and
     * uses SharedPreferences to save the users search query. Sets the RecyclerView adapter and also
     * populates the ViewModel with users saved favourite locations.
     *
     * @param savedInstanceState saved app state Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySunBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.sunsetMainToolbar);

        requestQueue = Volley.newRequestQueue(this);

        // Create/Get DB
        SunsetDatabase database = Room.databaseBuilder(
            getApplicationContext(),
            SunsetDatabase.class,
            "sunset-database"
        ).build();

        sunsetDataDao = database.sunsetDataDao();

        // save lat and lng search params in SharedPreferences
        SharedPreferences preferences = getSharedPreferences("SunsetData", Context.MODE_PRIVATE);
        String latData = preferences.getString("Latitude", "");
        String longData = preferences.getString("Longitude", "");

        Button searchBtn = binding.btnSearch;
        EditText latField = binding.latText;
        EditText longField = binding.longText;

        latField.setText(latData);
        longField.setText(longData);

        sunsetViewModel = new ViewModelProvider(this).get(SunsetViewModel.class);

        // populate dataList/ViewModel with saved favourite locations data from DB
        if (dataList == null || dataList.size() == 0) {
            Executors.newSingleThreadExecutor().execute(() -> {
                dataList.addAll(sunsetDataDao.getAllSunsetData());
                runOnUiThread(() -> binding.sunsetFavsRecyclerView.setAdapter(myAdapter));
            });
            sunsetViewModel.dataList.postValue(dataList = new ArrayList<SunsetData>());
        }

        binding.sunsetFavsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.sunsetFavsRecyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            /**
             * Function to create and inflate the Favourite locations RecyclerView RowHolder. Also
             * sets the width and height of the RowHolder.
             *
             * @param parent The ViewGroup into which the new View will be added after it is bound to
             *               an adapter position.
             * @param viewType The view type of the new View.
             *
             * @return The RecyclerView RowHolder holding the sunset_fav_item.xml layout.
             */
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SunsetFavItemBinding svBinding = SunsetFavItemBinding.inflate(getLayoutInflater());
                svBinding.getRoot().setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                return new MyRowHolder(svBinding.getRoot());
            }

            /**
             * Function to bind ViewHolder layout and set TextViews with appropriate data. Also sets
             * the background color when item is pressed/selected.
             *
             * @param holder The ViewHolder which should be updated to represent the contents of the
             *        item at the given position in the data set.
             * @param position The position of the item within the adapter's data set.
             */
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                SunsetData sunsetData = dataList.get(position);

                holder.lat.setText(sunsetData.getLat());
                holder.lng.setText(sunsetData.getLng());

                if (selectedItemPos == position) {
                    holder.itemView.setBackgroundColor(
                            getResources().getColor(R.color.sunset_highlight, getTheme()));
                } else {
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                }
            }

            /**
             * Gets the size of the favourite locations ArrayList
             *
             * @return size of favourite locations ArrayList
             */
            @Override
            public int getItemCount() {
                return dataList.size();
            }
        });

        sunsetViewModel.dataList.postValue(dataList);
        myAdapter.notifyItemInserted(dataList.size() - 1);

        // Listen for changes that happen when the details fragment save button is pressed
        // Listens for if the size of the ViewModel dataList has increased and then saves the new item
        sunsetViewModel.dataList.observe(this, (list) -> {
            // if size has increased
            if (list.size() > 0) {
                int pos = list.size() - 1;

                Executors.newSingleThreadExecutor().execute(() -> {
                    // save new item
                    sunsetDataDao.insertSunsetData(list.get(list.size() - 1));
                });
                myAdapter.notifyItemInserted(pos);
            }
        });

        // when search button is pressed
        searchBtn.setOnClickListener(click -> {
            SharedPreferences.Editor editor = preferences.edit();
            String latText = latField.getText().toString();
            String longText = longField.getText().toString();

            editor.putString("Latitude", latText);
            editor.putString("Longitude", longText);
            editor.apply();

            // make search API request
            if (!longText.isEmpty() && !latText.isEmpty()) {
                reqUrl = "https://api.sunrisesunset.io/json?lat=" + latText + "&lng=" + longText +"&timezone=EST&date=today";

                // Make request with Volley
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, reqUrl, null,
                    (response) -> {
                        try {
                            JSONObject result = response.getJSONObject("results");
                            Log.d(TAG, result.toString());

                            String sunrise = result.getString("sunrise");
                            String sunset = result.getString("sunset");
                            String firstLight = result.getString("first_light");
                            String lastLight = result.getString("last_light");
                            String dawn = result.getString("dawn");
                            String dusk = result.getString("dusk");
                            String solarNoon = result.getString("solar_noon");
                            String goldenHour = result.getString("golden_hour");
                            String dayLength = result.getString("day_length");

                            SunsetApiData detailsData = new SunsetApiData(latText, longText, sunrise, sunset, firstLight,
                                    lastLight, dawn, dusk, solarNoon, goldenHour, dayLength);

                            // open details in fragment
                            SunDetailsFragment detailsFragment = new SunDetailsFragment(detailsData, false);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragmentLocation, detailsFragment)
                                    .addToBackStack("")
                                    .commit();
                        } catch (JSONException exception) {
                            Toast.makeText(this, R.string.sunset_error, Toast.LENGTH_LONG).show();
                        }
                    }, (error) -> {
                        Toast.makeText(this, R.string.sunset_http_error, Toast.LENGTH_LONG).show();
                    }
                );
                requestQueue.add(request);
            } else {
                Toast.makeText(this, R.string.sunset_no_search_terms, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Inflate the sunset_menu.xml layout
     *
     * @param menu The Menu layout
     * @return If menu is created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.sunset_menu, menu);
        return true;
    }

    /**
     * Determine which menu item was pressed and determine what to do
     *
     * @param item The selected/pressed menu item
     * @return true if successful
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int choice = item.getItemId();

        if (choice == R.id.sunsetHelp) {
            // display help dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(SunActivity.this);
            builder.setMessage(R.string.sunset_help_main)
                .setTitle(R.string.sunset_help)
                .setPositiveButton(R.string.sunset_ok, (dialogInterface, i) -> {})
                .create().show();
        } else if (choice == R.id.sunsetFavsDelete) {
            // delete item
            if (!canDelete) {
                // no selectedItem - cant delete anything
                Toast.makeText(this, R.string.sunset_nothing_to_del, Toast.LENGTH_SHORT).show();
            } else {
                // delete item and run AlertDialog/SnackBar with undo option
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String msgText = getResources().getString(R.string.sunset_del_fav_msg);
                String undoTextPos = String.valueOf(selectedItemPos + 1);

                builder.setMessage(msgText + undoTextPos)
                    .setTitle(R.string.sunset_del_fav_title)
                    .setNegativeButton(R.string.sunset_no, (dialogInterface, i) -> {})
                    .setPositiveButton(R.string.sunset_yes, (dialogInterface, i) -> {
                        // save data for re-insertion if the user presses undo
                        SunsetData data = dataList.get(selectedItemPos);
                        String undoText = getResources().getString(R.string.sunset_del_succ);

                        // delete the item
                        Executors.newSingleThreadExecutor().execute(() -> {
                            SunsetData dbData = sunsetDataDao.getAllSunsetData().get(selectedItemPos);
                            sunsetDataDao.deleteSunsetData(dbData);
                            dataList.remove(selectedItemPos);
                        });
                        myAdapter.notifyItemRemoved(selectedItemPos);
                        canDelete = false;

                        Snackbar.make(
                            findViewById(R.id.fragmentLocation),
                            undoText + undoTextPos,
                            Snackbar.LENGTH_LONG)
                        .setAction(R.string.sunset_undo, clk -> {
                            // re-insert the data if the user presses undo
                            Executors.newSingleThreadExecutor().execute(() -> {
                                sunsetDataDao.insertSunsetData(data);
                                dataList.add(selectedItemPos, data);
                                canDelete = true;
                            });
                            myAdapter.notifyItemInserted(selectedItemPos);
                        })
                        .show();
                    })
                    .create().show();
            }
        }
        return true;
    }
}