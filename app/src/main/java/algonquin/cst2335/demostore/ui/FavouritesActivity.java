package algonquin.cst2335.demostore.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import algonquin.cst2335.demostore.R;
import algonquin.cst2335.demostore.databinding.ActivityFavouritesBinding;
import algonquin.cst2335.demostore.databinding.DeezerLayoutBinding;

public class FavouritesActivity extends AppCompatActivity {

    DeezerLayoutBinding binding;
    private RecyclerView recyclerView2;
    private RecyclerView.Adapter favouritesAdapter;

    public void showFavorites() {
        Intent intent = new Intent(this, FavouritesActivity.class);
        startActivity(intent);
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            itemView = itemView.findViewById(R.id.titleTextView);
        }

        public void bind(String itemText) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DeezerLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> favouriteList = new ArrayList<>(); // Sample data
        favouriteList.add("Favourite 1");
        favouriteList.add("Favourite 2");
        favouriteList.add("Favourite 3");

        RecyclerView.Adapter<FavouritesActivity.MyRowHolder> FavouritesAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.deezer_layout, parent, false);
                return new MyRowHolder(itemView);
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.bind(favouriteList.get(position));
            }

            @Override
            public int getItemCount() {
                return favouriteList.size();
            }

        };
    }
}
