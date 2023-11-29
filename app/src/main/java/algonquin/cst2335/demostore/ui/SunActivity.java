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

public class SunActivity extends AppCompatActivity {
    private static final String TAG = SunActivity.class.getName();
    private ActivitySunBinding binding;
    private ArrayList<SunsetData> dataList = new ArrayList<>();
    private SunsetViewModel sunsetViewModel;
    private SunsetDataDao sunsetDataDao;
    private RecyclerView.Adapter<MyRowHolder> myAdapter;
    private int selectedItemPos = -1;
    private boolean canDelete = false;
    private RequestQueue requestQueue = null;
    private String reqUrl;

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView lat;
        TextView lng;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            lat = itemView.findViewById(R.id.sunsetFavTitleLat);
            lng = itemView.findViewById(R.id.sunsetFavTitleLng);

            itemView.setOnClickListener(view -> {
                selectedItemPos = getAdapterPosition();
                canDelete = true;

                SunsetData selectedData = dataList.get(getAdapterPosition());
                sunsetViewModel.selectedSunsetData.postValue(selectedData);

                reqUrl = "https://api.sunrisesunset.io/json?lat=" + selectedData.getLat() + "&lng=" + selectedData.getLng() +"&timezone=EST&date=today";

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

                            SunDetailsFragment detailsFragment = new SunDetailsFragment(detailsData, true);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragmentLocation, detailsFragment)
                                    .addToBackStack("")
                                    .commit();
                        } catch (JSONException exception) {
                            Toast.makeText(SunActivity.this, R.string.sunset_error, Toast.LENGTH_LONG).show();
                        }
                    }, (error) -> {
                        Toast.makeText(SunActivity.this, R.string.sunset_http_error, Toast.LENGTH_LONG).show();
                    }
                );
                requestQueue.add(request);
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySunBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.sunsetMainToolbar);

        requestQueue = Volley.newRequestQueue(this);

        SunsetDatabase database = Room.databaseBuilder(
            getApplicationContext(),
            SunsetDatabase.class,
            "sunset-database"
        ).build();

        sunsetDataDao = database.sunsetDataDao();

        SharedPreferences preferences = getSharedPreferences("SunsetData", Context.MODE_PRIVATE);
        String latData = preferences.getString("Latitude", "");
        String longData = preferences.getString("Longitude", "");

        Button searchBtn = binding.btnSearch;
        EditText latField = binding.latText;
        EditText longField = binding.longText;

        latField.setText(latData);
        longField.setText(longData);


        sunsetViewModel = new ViewModelProvider(this).get(SunsetViewModel.class);

        if (dataList == null || dataList.size() == 0) {
            //dataList = sunsetViewModel.dataList.getValue();
            Executors.newSingleThreadExecutor().execute(() -> {
                dataList.addAll(sunsetDataDao.getAllSunsetData());
                runOnUiThread(() -> binding.sunsetFavsRecyclerView.setAdapter(myAdapter));
            });
            sunsetViewModel.dataList.postValue(dataList = new ArrayList<SunsetData>());
        }

        binding.sunsetFavsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.sunsetFavsRecyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SunsetFavItemBinding svBinding = SunsetFavItemBinding.inflate(getLayoutInflater());
                svBinding.getRoot().setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                return new MyRowHolder(svBinding.getRoot());
            }

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

            @Override
            public int getItemCount() {
                return dataList.size();
            }
        });

        sunsetViewModel.dataList.postValue(dataList);
        myAdapter.notifyItemInserted(dataList.size() - 1);

        sunsetViewModel.dataList.observe(this, (list) -> {
            if (list.size() > 0) {
                int pos = list.size() - 1;

                Executors.newSingleThreadExecutor().execute(() -> {
                    sunsetDataDao.insertSunsetData(list.get(list.size() - 1));
                });
                myAdapter.notifyItemInserted(pos);
            }
        });

        searchBtn.setOnClickListener(click -> {
            SharedPreferences.Editor editor = preferences.edit();
            String latText = latField.getText().toString();
            String longText = longField.getText().toString();

            editor.putString("Latitude", latText);
            editor.putString("Longitude", longText);
            editor.apply();

            if (!longText.isEmpty() && !latText.isEmpty()) {
                reqUrl = "https://api.sunrisesunset.io/json?lat=" + latText + "&lng=" + longText +"&timezone=EST&date=today";

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.sunset_menu, menu);
        return true;
    }

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
            if (!canDelete) {
                Toast.makeText(this, R.string.sunset_nothing_to_del, Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String msgText = getResources().getString(R.string.sunset_del_fav_msg);
                String undoTextPos = String.valueOf(selectedItemPos + 1);

                builder.setMessage(msgText + undoTextPos)
                    .setTitle(R.string.sunset_del_fav_title)
                    .setNegativeButton(R.string.sunset_no, (dialogInterface, i) -> {})
                    .setPositiveButton(R.string.sunset_yes, (dialogInterface, i) -> {
                        SunsetData data = dataList.get(selectedItemPos);
                        String undoText = getResources().getString(R.string.sunset_del_succ);

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