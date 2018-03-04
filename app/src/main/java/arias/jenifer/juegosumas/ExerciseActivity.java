package arias.jenifer.juegosumas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;


public class ExerciseActivity extends AppCompatActivity {

    Toolbar toolbar_exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        //Intent SumActivity
        Intent intent = getIntent();
        String message = intent.getStringExtra("Nivel");
        String title = String.format("%s", "Ejercicio Nivel " + message);

        //Configuración actionbar - toolbar
        toolbar_exercise = (Toolbar) findViewById(R.id.toolbar_exercise);
        setSupportActionBar(toolbar_exercise);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setTitle(title);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_exercise, menu);
        return true;
    }
}
