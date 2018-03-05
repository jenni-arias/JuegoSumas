package arias.jenifer.juegosumas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;


public class ExerciseActivity extends AppCompatActivity {

    Toolbar toolbar_exercise;
    EditText res_unid;
    EditText res_dec;
    EditText res_cent;
    EditText res_mil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        res_unid = (EditText) findViewById(R.id.res_unid);
        res_dec = (EditText) findViewById(R.id.res_dec);
        res_cent = (EditText) findViewById(R.id.res_cent);
        res_mil = (EditText) findViewById(R.id.res_mil);

        //Intent SumActivity
        Intent intent = getIntent();
        int nivel = intent.getIntExtra("Nivel", -1);
        String title = String.format("Ejercicio Nivel %d", nivel);

        //Configuración actionbar - toolbar
        toolbar_exercise = (Toolbar) findViewById(R.id.toolbar_exercise);
        setSupportActionBar(toolbar_exercise);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setTitle(title);

        //Ocultar teclado
        hideKeyboard(res_unid);
        hideKeyboard(res_dec);
        hideKeyboard(res_cent);
        hideKeyboard(res_mil);
    }

    //Inflamos el menú en la Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_exercise, menu);
        return true;
    }

    // Acciones para el menú del Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Ocultar teclado
    private void hideKeyboard(EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
    }
}
