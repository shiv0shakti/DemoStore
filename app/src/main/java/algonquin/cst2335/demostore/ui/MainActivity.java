package algonquin.cst2335.demostore.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import algonquin.cst2335.demostore.R;

/**
 * Creates the toolbar with icons and the Image buttons for opening the intent for each members application.
 *
 * @author Rishabh Puri and Roy Sullivan
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    private ImageButton Dictionary;
    private ImageButton Deezer;
    private ImageButton Recipe;
    private ImageButton Sun;

    /**
     * Creates and handles clicks for the Image buttons that open each members application
     *
     * @param savedInstanceState The applications saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Dictionary = findViewById(R.id.Dictionary);
        Deezer = findViewById(R.id.Deezer);
        Recipe = findViewById(R.id.recipe);
        Sun = findViewById(R.id.SunActivity);

        Dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DictionaryApi.class);
                startActivity(intent);
            }
        });
        Deezer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DeezerActivity.class);
                startActivity(intent);
            }
        });
        Recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RecipeActivity.class);
                startActivity(intent);
            }
        });
        Sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SunActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Inflate the options menu
     *
     * @param menu The menu to inflate
     * @return If the inflation was successful
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    /**
     * Handle clicks on menu items and open the activity for each members application
     *
     * @param item The selected menu item
     * @return True if successful
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int choice = item.getItemId();

        if (choice == R.id.app_sunset_menu_item) {
            Intent sunset = new Intent(MainActivity.this, SunActivity.class);
            startActivity(sunset);
        } else if (choice == R.id.app_recipe_menu_item) {
            Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
            startActivity(intent);
        }  else if (choice == R.id.app_dictionary_menu_item) {
            Intent intent = new Intent(MainActivity.this, DictionaryApi.class);
            startActivity(intent);
        }  else if (choice == R.id.app_deezer_menu_item) {
            Intent intent = new Intent(MainActivity.this, DeezerActivity.class);
            startActivity(intent);
        }

        return true;
    }
}