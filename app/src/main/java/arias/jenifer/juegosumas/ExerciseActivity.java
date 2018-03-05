package arias.jenifer.juegosumas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class ExerciseActivity extends AppCompatActivity {

    Toolbar toolbar_exercise;

    TextView unid_up;
    TextView dec_up;
    TextView cent_up;
    TextView mil_up;
    TextView unid_down;
    TextView dec_down;
    TextView cent_down;
    TextView mil_down;

    EditText res_unid;
    EditText res_dec;
    EditText res_cent;
    EditText res_mil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        unid_up = (TextView) findViewById(R.id.unid_up);
        dec_up = (TextView) findViewById(R.id.dec_up);
        cent_up = (TextView) findViewById(R.id.cent_up);
        mil_up = (TextView) findViewById(R.id.mil_up);
        unid_down = (TextView) findViewById(R.id.unid_down);
        dec_down = (TextView) findViewById(R.id.dec_down);
        cent_down = (TextView) findViewById(R.id.cent_down);
        mil_down = (TextView) findViewById(R.id.mil_down);

        res_unid = (EditText) findViewById(R.id.res_unid);
        res_dec = (EditText) findViewById(R.id.res_dec);
        res_cent = (EditText) findViewById(R.id.res_cent);
        res_mil = (EditText) findViewById(R.id.res_mil);

        //Intent SumActivity
        Bundle datos = this.getIntent().getExtras();
        int nivel = datos.getInt("Nivel", -1);
        String title = String.format("Ejercicio Nivel %d", nivel);

        //Mostrar ejercicio dependiendo de los datos que se reciben
        showExercise(datos);

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

    //Ocultar teclado del móvil
    private void hideKeyboard(EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
    }

    //Mostrar ejercicio
    private void showExercise(Bundle datos) {

        int nivel = datos.getInt("Nivel", -1);
        String Num_UnidUp = String.valueOf(datos.getInt("Unid_up", -1));
        String Num_DecUp = String.valueOf(datos.getInt("Dec_up", -1));
        String Num_CentUp = String.valueOf(datos.getInt("Cent_up", -1));
        String Num_MilUp = String.valueOf(datos.getInt("Mil_up", -1));
        String Num_UnidDown = String.valueOf(datos.getInt("Unid_down", -1));
        String Num_DecDown = String.valueOf(datos.getInt("Dec_down", -1));
        String Num_CentDown = String.valueOf(datos.getInt("Cent_down", -1));
        String Num_MilDown = String.valueOf(datos.getInt("Mil_down", -1));

        switch (nivel) {
            case 1:
                unid_up.setText(Num_UnidUp);
                dec_up.setVisibility(View.INVISIBLE);
                cent_up.setVisibility(View.INVISIBLE);
                mil_up.setVisibility(View.INVISIBLE);
                unid_down.setText(Num_UnidDown);
                dec_down.setVisibility(View.INVISIBLE);
                cent_down.setVisibility(View.INVISIBLE);
                mil_down.setVisibility(View.INVISIBLE);

                res_dec.setVisibility(View.INVISIBLE);
                res_cent.setVisibility(View.INVISIBLE);
                res_mil.setVisibility(View.INVISIBLE);
                break;

            case 2:
                unid_up.setText(Num_UnidUp);
                dec_up.setVisibility(View.INVISIBLE);
                cent_up.setVisibility(View.INVISIBLE);
                mil_up.setVisibility(View.INVISIBLE);
                unid_down.setText(Num_UnidDown);
                dec_down.setText(Num_DecDown);
                cent_down.setVisibility(View.INVISIBLE);
                mil_down.setVisibility(View.INVISIBLE);

                res_cent.setVisibility(View.INVISIBLE);
                res_mil.setVisibility(View.INVISIBLE);
                break;

            case 3:
                unid_up.setText(Num_UnidUp);
                dec_up.setText(Num_DecUp);
                cent_up.setVisibility(View.INVISIBLE);
                mil_up.setVisibility(View.INVISIBLE);
                unid_down.setText(Num_UnidDown);
                dec_down.setVisibility(View.INVISIBLE);
                cent_down.setVisibility(View.INVISIBLE);
                mil_down.setVisibility(View.INVISIBLE);

                res_cent.setVisibility(View.INVISIBLE);
                res_mil.setVisibility(View.INVISIBLE);
                break;

            case 4:
                unid_up.setText(Num_UnidUp);
                dec_up.setText(Num_DecUp);
                cent_up.setText(Num_CentUp);
                mil_up.setVisibility(View.INVISIBLE);
                unid_down.setText(Num_UnidDown);
                dec_down.setVisibility(View.INVISIBLE);
                cent_down.setVisibility(View.INVISIBLE);
                mil_down.setVisibility(View.INVISIBLE);

                res_mil.setVisibility(View.INVISIBLE);
                break;

            case 5:
                unid_up.setText(Num_UnidUp);
                dec_up.setVisibility(View.INVISIBLE);
                cent_up.setVisibility(View.INVISIBLE);
                mil_up.setVisibility(View.INVISIBLE);
                unid_down.setText(Num_UnidDown);
                dec_down.setText(Num_DecDown);
                cent_down.setText(Num_CentDown);
                mil_down.setVisibility(View.INVISIBLE);

                res_mil.setVisibility(View.INVISIBLE);
                break;

            case 6:
                unid_up.setText(Num_UnidUp);
                dec_up.setText(Num_DecUp);
                cent_up.setVisibility(View.INVISIBLE);
                mil_up.setVisibility(View.INVISIBLE);
                unid_down.setText(Num_UnidDown);
                dec_down.setText(Num_DecDown);
                cent_down.setVisibility(View.INVISIBLE);
                mil_down.setVisibility(View.INVISIBLE);

                res_cent.setVisibility(View.INVISIBLE);
                res_mil.setVisibility(View.INVISIBLE);
                break;

            case 7:
                unid_up.setText(Num_UnidUp);
                dec_up.setText(Num_DecUp);
                cent_up.setVisibility(View.INVISIBLE);
                mil_up.setVisibility(View.INVISIBLE);
                unid_down.setText(Num_UnidDown);
                dec_down.setText(Num_DecDown);
                cent_down.setText(Num_CentDown);
                mil_down.setVisibility(View.INVISIBLE);

                res_mil.setVisibility(View.INVISIBLE);
                break;

            case 8:
                unid_up.setText(Num_UnidUp);
                dec_up.setText(Num_DecUp);
                cent_up.setText(Num_CentUp);
                mil_up.setVisibility(View.INVISIBLE);
                unid_down.setText(Num_UnidDown);
                dec_down.setText(Num_DecDown);
                cent_down.setVisibility(View.INVISIBLE);
                mil_down.setVisibility(View.INVISIBLE);

                res_mil.setVisibility(View.INVISIBLE);
                break;

            case 9:
                unid_up.setText(Num_UnidUp);
                dec_up.setText(Num_DecUp);
                cent_up.setVisibility(View.INVISIBLE);
                mil_up.setVisibility(View.INVISIBLE);
                unid_down.setText(Num_UnidDown);
                dec_down.setText(Num_DecDown);
                cent_down.setText(Num_CentDown);
                mil_down.setText(Num_MilDown);

                break;

            case 10:
                unid_up.setText(Num_UnidUp);
                dec_up.setText(Num_DecUp);
                cent_up.setText(Num_CentUp);
                mil_up.setText(Num_MilUp);
                unid_down.setText(Num_UnidDown);
                dec_down.setText(Num_DecDown);
                cent_down.setVisibility(View.INVISIBLE);
                mil_down.setVisibility(View.INVISIBLE);

                break;
        }

    }


}
