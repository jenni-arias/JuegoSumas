package arias.jenifer.juegosumas;

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

import java.util.Locale;


public class ExerciseActivity extends AppCompatActivity
{
    private String prefixes[] = { "unid", "dec", "cent", "mil" };
    private Level level;
    private int levelIndex;

    private Toolbar toolbar_exercise;
    private TextView digitsUp[], digitsDown[];
    private EditText results[];
    private int[] numbersUp;
    private int[] numbersDown;
    private boolean[] posCarry;
    private boolean acceptCarry;

    private int getId(String digit, String position) {
        String name = String.format("%s_%s", digit, position);
        return getResources().getIdentifier(name, "id", getPackageName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        digitsUp = new TextView[4];
        digitsDown = new TextView[4];
        results = new EditText[4];
        for (int i = 0; i < prefixes.length; i++) {
            digitsUp[i] = (TextView) findViewById(getId(prefixes[i],"up"));
            digitsDown[i] = (TextView) findViewById(getId(prefixes[i], "down"));
            results[i] = (EditText) findViewById(getId(prefixes[i], "res"));
        }

        //Intent SumActivity
        levelIndex = getIntent().getIntExtra("Nivel", -1);
        int showNum = levelIndex + 1;
        String title = String.format(Locale.getDefault(), "Ejercicio Nivel %d", showNum);

        // Obtenemos el nivel y le pedimos que nos genere unos números concretos
        // a partir de la plantilla
        level = Level.ALL_LEVELS[levelIndex];
        numbersUp = level.generateUp();
        numbersDown = level.generateDown();

        //Comprobar si el nivel acepta acarreo o no
        acceptCarry = level.acceptCarry();

        //Obtener las posiciones de los acarreos
        posCarry = level.posCarry();

        //Comprobar si la suma tiene los acarreos en las posiciones correctas
        boolean correctCarry = checkCarry(numbersUp, numbersDown, acceptCarry, posCarry);

        if (correctCarry) { 
            //Mostrar ejercicio dependiendo de los datos que se reciben
            setDigits(digitsUp, numbersUp);
            setDigits(digitsDown, numbersDown);
            // TODO: Aquí falta hacer invisibles los resultados pero esto implica mirar el acarreo
            // TODO: porque ciertos niveles puede ser que generen un número de 3 cifras sumando números de 2, etc.
        } else {
            recreate();
        }

        //Configuración actionbar - toolbar
        toolbar_exercise = (Toolbar) findViewById(R.id.toolbar_exercise);
        setSupportActionBar(toolbar_exercise);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        getSupportActionBar().setTitle(title);

        //Ocultar teclado
        for (EditText e : results) {
            hideKeyboard(e);
        }
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

    //Comprobar si la suma tiene los acarreos en las posiciones correctas
    private boolean checkCarry(int[] numbersUp, int[] numbersDown, boolean acceptCarry, boolean[] posCarry) {
        boolean correctCarry = true;
        int max;

        if (numbersUp.length <= numbersDown.length) {
            max = numbersUp.length;
        } else {max = numbersDown.length; }

        boolean [] acarreo = new boolean[max];

        if (!acceptCarry) {     //No acepta acarreo
            for (int i = 0; i < max; i++) {
                if (numbersUp[i] + numbersDown[i] > 9) {
                    acarreo[i] = false;     //Incorrecto
                }
                else if (numbersUp[i] + numbersDown[i] < 9){
                    acarreo[i] = true;      //Correcto
                }
            }
        } else {                //Acepta acarreo
            for (int i = 0; i < max; i++) {
                if (numbersUp[i] + numbersDown[i] > 9 && posCarry[i]) {
                    acarreo[i] = true;      //Correcto
                }
                else if (numbersUp[i] + numbersDown[i] < 9 && !posCarry[i]){
                    acarreo[i] = true;      //Correcto
                }
                else {acarreo[i] = false; }     //Incorrecto
            }
        }

        for (int i = 0; i< acarreo.length; i++) {
            if (acarreo[i] && correctCarry) {
                correctCarry = true;
            } else {correctCarry = false; }
        }
        return correctCarry;
    }

    //Insertar los números en los TextView.
    private static void setDigits(TextView[] digits, int[] numbers) {
        for (int i = 0; i < digits.length; i++) {
            if (i < numbers.length) {
                digits[i].setText(String.valueOf(numbers[i]));
            } else {
                digits[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    public void clicat(View view) {
        // TODO: hacer algo, lo he puesto para que no pete
    }
}
