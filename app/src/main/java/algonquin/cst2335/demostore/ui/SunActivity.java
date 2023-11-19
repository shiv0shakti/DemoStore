package algonquin.cst2335.demostore.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import algonquin.cst2335.demostore.R;
import algonquin.cst2335.demostore.data.SunsetData;
import algonquin.cst2335.demostore.data.SunsetViewModel;
import algonquin.cst2335.demostore.databinding.ActivitySunBinding;
import algonquin.cst2335.demostore.databinding.ActivitySunFavsBinding;

public class SunActivity extends AppCompatActivity {

    private static final String TAG = SunActivity.class.getName();
    private ActivitySunBinding binding;
    private ArrayList<SunsetData> dataList = new ArrayList<>();
    private SunsetViewModel sunsetViewModel;
    private boolean hasSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySunBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.sunsetMainToolbar);

        SharedPreferences preferences = getSharedPreferences("SunsetData", Context.MODE_PRIVATE);
        String latData = preferences.getString("Latitude", "");
        String longData = preferences.getString("Longitude", "");

        Button searchBtn = binding.btnSearch;
        EditText latField = binding.latText;
        EditText longField = binding.longText;

        latField.setText(latData);
        longField.setText(longData);
        hasSearchResult = false;

        sunsetViewModel = new ViewModelProvider(this).get(SunsetViewModel.class);
        dataList = sunsetViewModel.dataList.getValue();

        if (dataList == null) {
            sunsetViewModel.dataList.postValue(dataList = new ArrayList<SunsetData>());
        }

        searchBtn.setOnClickListener(click -> {
            SharedPreferences.Editor editor = preferences.edit();
            String latText = latField.getText().toString();
            String longText = longField.getText().toString();

            editor.putString("Latitude", latText);
            editor.putString("Longitude", longText);
            editor.apply();

            hasSearchResult = true;

            if (!longText.isEmpty() && !latText.isEmpty()) {
                // do stuff...
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
        } else if (choice == R.id.sunsetFavs) {
            Intent intent = new Intent(SunActivity.this, SunFavsActivity.class);
            startActivity(intent);
        } else if (choice == R.id.sunsetMakeFav) {
            if (hasSearchResult) {
                Toast.makeText(this, R.string.sunset_saved_to_favs, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.sunset_nothing, Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }
}