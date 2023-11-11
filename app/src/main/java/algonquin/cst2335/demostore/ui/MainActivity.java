package algonquin.cst2335.demostore.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import algonquin.cst2335.demostore.R;

public class MainActivity extends AppCompatActivity {
    private ImageButton Dictionary;
    private ImageButton Deezer;
    private ImageButton Recipe;
    private ImageButton Sun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}