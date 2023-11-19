package algonquin.cst2335.demostore.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.demostore.R;
import algonquin.cst2335.demostore.data.SunsetData;
import algonquin.cst2335.demostore.data.SunsetViewModel;
import algonquin.cst2335.demostore.databinding.ActivitySunFavsBinding;
import algonquin.cst2335.demostore.databinding.SunsetFavItemBinding;

public class SunFavsActivity extends AppCompatActivity {

    private static final String TAG = SunFavsActivity.class.getName();
    private ActivitySunFavsBinding binding;
    private SunsetViewModel sunsetViewModel;
    private RecyclerView.Adapter<MyRowHolder> myAdapter;
    private ArrayList<SunsetData> dataList = new ArrayList<>();
    private int deletePosition;
    private View posView;
    private boolean canDelete = false;

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView pos;
        TextView sunrise;
        TextView sunset;
        TextView dawn;
        TextView dusk;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            pos = itemView.findViewById(R.id.sunsetPosSunriseValue);
            sunrise = itemView.findViewById(R.id.sunsetFavSunriseValue);
            sunset = itemView.findViewById(R.id.sunsetFavSunsetValue);
            dawn = itemView.findViewById(R.id.sunsetFavDawnValue);
            dusk = itemView.findViewById(R.id.sunsetFavDuskValue);

            itemView.setOnClickListener(view -> {
                deletePosition = getAdapterPosition();
                posView = pos;
                canDelete = true;
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySunFavsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.sunsetFavsToolbar);

        sunsetViewModel = new ViewModelProvider(this).get(SunsetViewModel.class);
        binding.sunsetFavsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = sunsetViewModel.dataList.getValue();

        if (dataList == null) {
            sunsetViewModel.dataList.postValue(dataList = new ArrayList<SunsetData>());
        }

        binding.sunsetFavsRecyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SunsetFavItemBinding svBinding = SunsetFavItemBinding.inflate(getLayoutInflater());
                return new MyRowHolder(svBinding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                SunsetData sunsetData = dataList.get(position);
                String posText = String.valueOf(position + 1);

                holder.pos.setText(posText);
                holder.sunrise.setText(sunsetData.getSunrise());
                holder.sunset.setText(sunsetData.getSunset());
                holder.dawn.setText(sunsetData.getDawn());
                holder.dusk.setText(sunsetData.getDusk());
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }
        });

        // add test data
        SunsetData sunsetDataTest1 = new SunsetData(
                "6:00 AM",
                "7:30 PM",
                "5:15 AM",
                "8:15 PM"
        );
        SunsetData sunsetDataTest2 = new SunsetData(
                "7:15 AM",
                "8:00 PM",
                "6:45 AM",
                "8:30 PM"
        );
        dataList.add(sunsetDataTest1);
        myAdapter.notifyItemInserted(dataList.size() - 1);
        dataList.add(sunsetDataTest2);
        myAdapter.notifyItemInserted(dataList.size() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.sunset_favs_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int choice = item.getItemId();

        if (choice == R.id.sunsetFavsSearch) {
            Intent intent = new Intent(SunFavsActivity.this, SunActivity.class);
            startActivity(intent);
        } else if (choice == R.id.sunsetFavsHelp) {
            // show alert dialog help
            AlertDialog.Builder builder = new AlertDialog.Builder(SunFavsActivity.this);
            builder.setMessage(R.string.sunset_help_favs)
                    .setTitle(R.string.sunset_help)
                    .setPositiveButton(R.string.sunset_ok, (dialogInterface, i) -> {})
                    .create().show();
        } else if (choice == R.id.sunsetFavsDelete) {
            if (!canDelete) {
                Toast.makeText(this, R.string.sunset_nothing_to_del, Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(SunFavsActivity.this);
                String msgText = getResources().getString(R.string.sunset_del_fav_msg);
                String undoTextPos = String.valueOf(deletePosition + 1);

                builder.setMessage(msgText + undoTextPos)
                    .setTitle(R.string.sunset_del_fav_title)
                    .setNegativeButton(R.string.sunset_no, (dialogInterface, i) -> {})
                    .setPositiveButton(R.string.sunset_yes, (dialogInterface, i) -> {
                        SunsetData data = dataList.get(deletePosition);
                        String undoText = getResources().getString(R.string.sunset_del_succ);

                        dataList.remove(deletePosition);
                        myAdapter.notifyItemRemoved(deletePosition);

                        Snackbar.make(
                                        posView,
                                        undoText + undoTextPos,
                                        Snackbar.LENGTH_LONG)
                                .setAction(R.string.sunset_undo, clk -> {
                                    dataList.add(deletePosition, data);
                                    myAdapter.notifyItemInserted(deletePosition);
                                })
                                .show();
                        canDelete = false;
                    })
                    .create().show();
            }
        }

        return true;
    }
}