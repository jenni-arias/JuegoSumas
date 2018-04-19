package arias.jenifer.juegosumas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

//import arias.jenifer.juegosumas.database2.AppDatabase;
//import arias.jenifer.juegosumas.database2.UserData;



public class ExerciseActivity extends AppCompatActivity
{
    static final String CURRENT_EXERCISE = "current_exercise";
    int mCurrentExercise = 0;
    boolean correct_exercise = false;
    private Bundle savedInstanceState = null;

    private String prefixes[] = { "unid", "dec", "cent", "mil" };
    private int correct[] = { 1, 2, 3, 4, 5 };
    private Level level;
    private int levelIndex;

    private Toolbar toolbar_exercise;
    private TextView digitsUp[], digitsDown[], correct_result[];
    private EditText results[], carry[];
    private int[] numbersUp, numbersDown;
    private int[] Result;
    private boolean[] posCarry;

  //  private AppDatabase db; //DB
   // private UserData data;

    private int getId(String digit, String position) {
        String name = String.format("%s_%s", digit, position);
        return getResources().getIdentifier(name, "id", getPackageName());
    }

    //Guardar estado adicional
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt(CURRENT_EXERCISE, mCurrentExercise);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state members from saved instance
        mCurrentExercise = savedInstanceState.getInt(CURRENT_EXERCISE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        digitsUp = new TextView[4];
        digitsDown = new TextView[4];
        results = new EditText[4];
        carry = new EditText[4];
        for (int i = 0; i < prefixes.length; i++) {
            digitsUp[i] = (TextView) findViewById(getId(prefixes[i],"up"));
            digitsDown[i] = (TextView) findViewById(getId(prefixes[i], "down"));
            results[i] = (EditText) findViewById(getId(prefixes[i], "res"));
            carry[i] = (EditText) findViewById(getId(prefixes[i], "carry"));
        }

        correct_result = new TextView[5];
        for (int i = 0; i < correct.length; i++) {
            correct_result[i] = (TextView) findViewById(getId("correct", String.valueOf(correct[i])));
        }

        if (savedInstanceState != null) {
            mCurrentExercise = savedInstanceState.getInt(CURRENT_EXERCISE);
        } else {
            //Intent SumActivity
            levelIndex = getIntent().getIntExtra("Nivel", -1);
            int showNum = levelIndex + 1;
            String title = String.format(Locale.getDefault(), "Ejercicios Nivel %d", showNum);

            // Obtenemos el nivel y le pedimos que nos genere unos números concretos
            // a partir de la plantilla
            level = Level.ALL_LEVELS[levelIndex];
            Level.Instance instance = level.generateInstance();
            Result = instance.getResult();
            numbersUp = instance.getUp();
            numbersDown = instance.getDown();

            //Colorear el primer ejercicio del nivel de la progressBar
            correct_result[mCurrentExercise].setBackground(getResources()
                    .getDrawable(R.drawable.progressbar_yellow));

            //Obtener las posiciones de los acarreos
            posCarry = level.getCarry();

            //Mostrar ejercicio dependiendo de los datos que se reciben
            setDigits(digitsUp, numbersUp);
            setDigits(digitsDown, numbersDown);

            //Mostrar / Ocultar los EditText del resultado según acarreo del nivel.
            hideEditResult(numbersUp, numbersDown, posCarry, results);
            hideEditCarry(posCarry, carry);

            //Configuración actionbar - toolbar
            toolbar_exercise = (Toolbar) findViewById(R.id.toolbar_exercise);
            setSupportActionBar(toolbar_exercise);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
            getSupportActionBar().setTitle(title);

            //Ocultar teclado del móvil
            for (EditText e : results) {
                hideKeyboard(e);
            }
            for (EditText e : carry) {
                hideKeyboard(e);
            }

            //Indicar primera posición del número a introducir
            results[0].requestFocus();
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
            case R.id.action_check:
                checkResult(Result, results);

            default:
                return super.onOptionsItemSelected(item);
        }
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

    //Mostrar / Ocultar los EditText del resultado según acarreo del nivel.
    private void hideEditResult(int[] numbersUp, int[] numbersDown, boolean[] posCarry, EditText results[]) {
        int max;
        if (numbersUp.length >= numbersDown.length) {
            max = numbersUp.length;
        } else {max = numbersDown.length; }

        for (int i = max; i < results.length; i++) {
            results[i].setVisibility(View.INVISIBLE);
        }

        if (posCarry[posCarry.length-1]) {
            results[posCarry.length].setVisibility(View.VISIBLE);
        }
    }

    //Mostrar / Ocultar los EditText del acarreo según el nivel.
    private void hideEditCarry(boolean[] posCarry, EditText[] carry) {

        //TODO: se repite, crear método
        boolean[] pos = new boolean[4];
        for (int i = 0; i < posCarry.length; i++) {
            pos[i] = posCarry[i];
        }

        carry[0].setVisibility(View.INVISIBLE);
        for (int i = 1; i < pos.length; i++) {
            if(pos[i-1]) {
                carry[i].setVisibility(View.VISIBLE);
            } else {
                carry[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    //Ocultar teclado del móvil
    private void hideKeyboard(EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
    }

    //Captura click del número seleccionado
    public void clicat(View view) {
        Button boton = (Button) view;
        int num = Integer.parseInt(boton.getText().toString());
        setResults(results, carry, posCarry, num);
    }

    //Insertar número seleccionado en el EditText y organización del Focus.
    //TODO: Falta deshabilitar el último EditText una vez introducido todos los números
    public void setResults (EditText results[], EditText carry[], boolean[] posCarry, int num) {
        String number = String.valueOf(num);

        //TODO: se repite, crear método
        boolean[] pos = new boolean[4];
        for (int i = 0; i < posCarry.length; i++) {
            pos[i] = posCarry[i];
        }

        if (results[0].hasFocus()) {
            results[0].setText(number);
            if(pos[0]) {
                carry[1].requestFocus();
            } else { results[1].requestFocus(); }
        }
        else if (carry[1].hasFocus()) {
            carry[1].setText(number);
            results[1].requestFocus();
        }
        else if (results[1].hasFocus()) {
            results[1].setText(number);
            if(pos[1]) {
                carry[2].requestFocus();
            } else { results[2].requestFocus(); }
        }
        else if (carry[2].hasFocus()) {
            carry[2].setText(number);
            results[2].requestFocus();
        }
        else if (results[2].hasFocus()) {
            results[2].setText(number);
            if(pos[2]) {
                carry[3].requestFocus();
            } else { results[3].requestFocus(); }
        }
        else if (carry[3].hasFocus()) {
            carry[3].setText(number);
            results[3].requestFocus();
        }
        else if (results[3].hasFocus()) {
            results[3].setText(number);
        }
    }

    //Comprobar que el resultado es correcto
    private void checkResult(int[] Result, EditText results[]) {
        boolean correct = true;

        for (int i = 0; i<Result.length; i ++) {
            int result = Integer.parseInt(results[i].getText().toString());
            if (Result[i] != result) {
                correct = false;
            }
        }

        if (correct) {
            Toast.makeText(this, "Bien hecho!", Toast.LENGTH_SHORT).show();
            nextExercise(mCurrentExercise, correct);
        } else {
            Toast.makeText(this, "Vuelve a intentarlo...", Toast.LENGTH_SHORT).show();
            mCurrentExercise = 0;
            setColors(mCurrentExercise, correct);
        }
    }

    //Muestra el siguiente ejercicio del nivel
    private void nextExercise(int mCurrentExercise, boolean correct) {
        setColors(mCurrentExercise, correct);
        mCurrentExercise++;
        Bundle nextExercise = new Bundle();
        nextExercise.putInt(CURRENT_EXERCISE, mCurrentExercise);
        onRestoreInstanceState(nextExercise);
        //  onCreate(nextExercise);
    }

    //Establece los colores en la progressBar del nivel según si el resultado es correcto o no.
    private void setColors(int mCurrentExercise, boolean correct) {
        if (correct) {
            for (int i = 0; i < mCurrentExercise+1; i++) {
                correct_result[mCurrentExercise].setBackground(getResources()
                        .getDrawable(R.drawable.progressbar_green));
            }
            if (mCurrentExercise < 4) {
                correct_result[mCurrentExercise+1].setBackground(getResources()
                        .getDrawable(R.drawable.progressbar_yellow));
            }
        } else {
            for (int i = 1; i < 5; i++) {
                correct_result[i].setBackground(getResources()
                        .getDrawable(R.drawable.progressbar_gray));
            } correct_result[mCurrentExercise].setBackground(getResources()
                    .getDrawable(R.drawable.progressbar_yellow));
        }
    }
}