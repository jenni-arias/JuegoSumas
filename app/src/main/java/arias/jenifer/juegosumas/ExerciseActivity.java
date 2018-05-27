package arias.jenifer.juegosumas;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import arias.jenifer.juegosumas.database.LevelContract;
import arias.jenifer.juegosumas.database.LevelSQLiteHelper;


public class ExerciseActivity extends AppCompatActivity
{
    static final String CURRENT_EXERCISE = "current_exercise";
    int mCurrentExercise = 1;
    String exerciseComplete;
    private Bundle savedInstanceState = null;
    private Bundle nextExercise = new Bundle();

    private String prefixes_1[] = { "unid", "dec", "cent", "mil" };
    private String prefixes_2[] = { "unid", "dec", "cent", "unidMil", "decMil" };
    private int correct[] = { 1, 2, 3, 4, 5 };
    private int btn_nums[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
    private Level level;
    private int levelIndex,numLevel;

    private Bundle ejerAct = new Bundle();
    private ProgressDialog progressDialog;
    private Toolbar toolbar_exercise;
    private TextView digitsUp[], digitsDown[], correct_result[];
    private EditText results[], carry[];
    private Button nums[];
    private int[] numbersUp, numbersDown;
    private int[] Result;
    private boolean[] posCarry;
    private int[] depend;
    private boolean firstcompletelevel = false;

    private LevelSQLiteHelper mLevel;
    private SQLiteDatabase db;
    private String TAG = "TAG";

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
        results = new EditText[5];
        carry = new EditText[5];
        for (int i = 0; i < prefixes_1.length; i++) {
            digitsUp[i] = (TextView) findViewById(getId(prefixes_1[i],"up"));
            digitsDown[i] = (TextView) findViewById(getId(prefixes_1[i], "down"));
        }
        for (int i = 0; i < prefixes_2.length; i++) {
            results[i] = (EditText) findViewById(getId(prefixes_2[i], "res"));
            carry[i] = (EditText) findViewById(getId(prefixes_2[i], "carry"));
        }

        correct_result = new TextView[5];
        for (int i = 0; i < correct.length; i++) {
            correct_result[i] = (TextView) findViewById(getId("correct", String.valueOf(correct[i])));
        }

        nums = new Button[10];
        for (int i = 0; i < btn_nums.length; i++) {
            nums[i] = (Button) findViewById(getId("btn", String.valueOf(btn_nums[i])));
        }

        if (savedInstanceState != null) {
            mCurrentExercise = savedInstanceState.getInt(CURRENT_EXERCISE);
            init();
        } else {
            init();
        }

        //Listener para indicar el número del resultado que se quiere modificar
        for(int i = 0; i < results.length; i++) {
            final int pos = i;
            results[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        results[pos].setBackgroundResource(R.drawable.rounded_edittext);
                        showBtnNumbers(nums);
                    } else {
                        results[pos].setBackgroundResource(0);
                    }
                }
            });
        }
        //Listener para indicar el número del acarreo que se quiere modificar
        //(quizás se puede quitar ya que el acarreo siempre será 1)
        for(int i = 0; i < carry.length; i++) {
            final int pos = i;
            carry[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        carry[pos].setBackgroundResource(R.drawable.rounded_edittext);
                        hideBtnNumbers(posCarry, nums);
                    } else {
                        carry[pos].setBackgroundResource(0);
                    }
                }
            });
        }

    }

    public void init() {
        //Abrimos la base de datos 'DBLevel' en modo escritura
        Log.i(TAG, "Abrir BBDD DBLevel.db");
        mLevel = new LevelSQLiteHelper(
                this,
                LevelSQLiteHelper.DATABASE_NAME,
                null,
                LevelSQLiteHelper.DATABASE_VERSION);

        db = mLevel.getWritableDatabase();

        //Intent SumActivity
        levelIndex = getIntent().getIntExtra("Nivel", -1);
        numLevel = levelIndex + 1;
        String title = String.format(Locale.getDefault(), getString(R.string.exercise_title) + " %d", numLevel);

        // Obtenemos el nivel y le pedimos que nos genere unos números concretos
        // a partir de la plantilla
        level = Level.ALL_LEVELS[levelIndex];
        Level.Instance instance = level.generateInstance();
        Result = instance.getResult();
        numbersUp = instance.getUp();
        numbersDown = instance.getDown();

        String[] campos = new String[] {"levelId", "level", "exercise", "fails", "complete"};
        Cursor c = db.query(LevelContract.LevelScheme.TABLE_NAME, campos,
                null, null, null, null, null);
        //Recorremos el cursor de la BBDD
        if(c.moveToFirst()) {
            for(int i = 0; i < c.getCount(); i++) {
                if (c.getInt(1) == numLevel) {
                    //Si el nivel existe obtenemos el Ejercicio en el que nos quedamos
                    mCurrentExercise = c.getInt(2);
                    exerciseComplete = c.getString(4);
                }
                c.moveToNext();
            }
        }

        //Colorear los ejercicio del nivel en la progressBar
        if (mCurrentExercise == 1) {
            setColors(mCurrentExercise, false);
        } else if (mCurrentExercise <= 5) {
            setColors(mCurrentExercise, true);
        } else {
            setColors(mCurrentExercise, true);
            finish();
        }

        //Obtener las posiciones de los acarreos
        posCarry = level.getCarry();

        //Obtener dependencias del nivel
        depend = level.getDependencies();

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
        results[0].setBackgroundResource(R.drawable.rounded_edittext);

    }

    @Override
    public void onBackPressed() {
        showProgressDialog(getString(R.string.loading));
        returnSumActivity(ejerAct);
        finish();
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
                showProgressDialog(getString(R.string.loading));
                returnSumActivity(ejerAct);
                finish();
                return true;

            case R.id.action_check:
                boolean empty = empty();

                if(!empty) {
                    checkResult(Result, results, numLevel);
                    if (mCurrentExercise > 5) {
                        setColors(mCurrentExercise, true);
                        if(firstcompletelevel) {
                            showProgressDialog(getString(R.string.loading));
                            returnSumActivity(ejerAct);
                            finish();
                        } else {
                            firstcompletelevel = false;
                            onDestroy();
                            onCreate(nextExercise); }
                    } else {
                        onDestroy();
                        onCreate(nextExercise); }
                } else {
                    MakeToast.showToast(ExerciseActivity.this, getString(R.string.complete), 4);
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //ProgressDialog
    private void showProgressDialog(String message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    //Comprobar si falta algún EditText del resultado por completar
    private boolean empty() {
        boolean empty = false;

        for (int i = 0; i< Result.length; i++) {
            if(results[i].getText().toString().isEmpty()) {
                empty = true;
            }
        }
        return empty;
    }

    //Comprobar que el resultado es correcto
    private void checkResult(int[] Result, EditText results[], int numLevel) {
        boolean correct = true;
        int nextEx = 0, fail = 0;
        ContentValues values = new ContentValues();

        for (int i = 0; i<Result.length; i ++) {
            int result = Integer.parseInt(results[i].getText().toString());
            if (Result[i] != result) {
                correct = false;
            }
        }

        String[] campos = new String[] {"levelId", "level", "exercise", "fails", "complete"};
        Cursor c = db.query(LevelContract.LevelScheme.TABLE_NAME, campos,
                null, null, null, null, null);

        //Recorremos el cursor de la BBDD
        if(c.moveToFirst()) {
            for(int i = 0; i < c.getCount(); i++) {
                if (c.getInt(1) == numLevel) {
                    nextEx = Integer.parseInt(c.getString(2)) + 1;
                    fail = Integer.parseInt(c.getString(3)) + 1;
                }
                c.moveToNext();
            }
        }

        //Actualizamos la BBDD
        if (correct) {
            if(mCurrentExercise >= 5) {
                values.put(LevelContract.LevelScheme.COLUMN_COMPLETE, "YES");
            } else {
                values.put(LevelContract.LevelScheme.COLUMN_EXERCISE, nextEx);
            }
            mCurrentExercise++;
            nextExercise(mCurrentExercise);
            MakeToast.showToast(ExerciseActivity.this, getString(R.string.bien_hecho), 1);
            db.update(LevelContract.LevelScheme.TABLE_NAME, values, "level=" + numLevel, null);

        } else {
            if(mCurrentExercise >= 5) {
                MakeToast.showToast(ExerciseActivity.this, getString(R.string.volver_intentar), 2);
            } else {
                nextEx = 1;
                values.put(LevelContract.LevelScheme.COLUMN_EXERCISE, nextEx);
                values.put(LevelContract.LevelScheme.COLUMN_FAILS, fail);
                db.update(LevelContract.LevelScheme.TABLE_NAME, values, "level=" + numLevel, null);

                MakeToast.showToast(ExerciseActivity.this, getString(R.string.volver_intentar), 2);
                mCurrentExercise = 1;
                nextExercise(mCurrentExercise);
            }
        }
    }

    //Establece el siguiente ejercicio del nivel a generar
    private void nextExercise(int mCurrentExercise) {
        nextExercise.putInt(CURRENT_EXERCISE, mCurrentExercise);
        onRestoreInstanceState(nextExercise);
    }

    //Colorear la progressBar del nivel según si el resultado es correcto o no.
    private void setColors(int mCurrentExercise, boolean correct) {
        if (correct) {
            if(exerciseComplete.equals("NO")) {
                mCurrentExercise = mCurrentExercise -1 ;
            }
            if(mCurrentExercise == 5 && exerciseComplete.equals("NO")) {
                firstcompletelevel = true;
            }
            if(mCurrentExercise > 5 && exerciseComplete.equals("YES")) {
                mCurrentExercise = mCurrentExercise -1 ;
            }

            for (int i = 0; i < mCurrentExercise; i++) {
                correct_result[i].setBackground(getResources()
                        .getDrawable(R.drawable.progressbar_green));
            }
            if (mCurrentExercise < 5) {
                correct_result[mCurrentExercise].setBackground(getResources()
                        .getDrawable(R.drawable.progressbar_yellow));
            }
        } else {
            for (int i = 1; i < 5; i++) {
                correct_result[i].setBackground(getResources()
                        .getDrawable(R.drawable.progressbar_gray));
            } correct_result[mCurrentExercise-1].setBackground(getResources()
                    .getDrawable(R.drawable.progressbar_yellow));
        }
    }

    //Devolver ejercicio actual a SumActivity
    private void returnSumActivity (Bundle ejerAct) {
        ejerAct.putString("Ejercicio", String.valueOf(mCurrentExercise));
        ejerAct.putString("Nivel", String.valueOf(numLevel));
        Intent i = new Intent(this, SumActivity.class);
        i.putExtras(ejerAct);
        startActivity(i);
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

        boolean[] pos = new boolean[5];
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

    //Ocultar los Botones de los Números, excepto el 1, según el acarreo
    private void hideBtnNumbers(boolean[] posCarry, Button[] btns) {
        for (int i = 0; i < posCarry.length; i++) {
            if(posCarry[i]) {
                btns[0].setVisibility(View.VISIBLE);
                for (int j = 1; j < 10; j++) {
                    btns[j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    //Mostrar los Botones de los Números
    private void showBtnNumbers(Button[] btns) {
        for (int i = 0; i < 10; i++) {
            btns[i].setVisibility(View.VISIBLE);
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

        boolean[] pos = new boolean[5];
        for (int i = 0; i < posCarry.length; i++) {
            pos[i] = posCarry[i];
        }

        if (results[0].hasFocus()) {
            results[0].setText(number);
            results[0].setBackgroundResource(0); //Transparente
            if(pos[0]) {
                carry[1].requestFocus();
                carry[1].setBackgroundResource(R.drawable.rounded_edittext);
                hideBtnNumbers(posCarry, nums);
            } else { results[1].requestFocus();
                results[1].setBackgroundResource(R.drawable.rounded_edittext);}
        }
        else if (carry[1].hasFocus()) {
            carry[1].setText(number);
            carry[1].setBackgroundResource(0);
            results[1].requestFocus();
            results[1].setBackgroundResource(R.drawable.rounded_edittext);
            showBtnNumbers(nums);
        }
        else if (results[1].hasFocus()) {
            results[1].setText(number);
            results[1].setBackgroundResource(0);
            if(pos[1]) {
                carry[2].requestFocus();
                carry[2].setBackgroundResource(R.drawable.rounded_edittext);
                hideBtnNumbers(posCarry, nums);
            } else { results[2].requestFocus();
                results[2].setBackgroundResource(R.drawable.rounded_edittext);}
        }
        else if (carry[2].hasFocus()) {
            carry[2].setText(number);
            carry[2].setBackgroundResource(0);
            results[2].requestFocus();
            results[2].setBackgroundResource(R.drawable.rounded_edittext);
            showBtnNumbers(nums);
        }
        else if (results[2].hasFocus()) {
            results[2].setText(number);
            results[2].setBackgroundResource(0);
            if(pos[2]) {
                carry[3].requestFocus();
                carry[3].setBackgroundResource(R.drawable.rounded_edittext);
                hideBtnNumbers(posCarry, nums);
            } else { results[3].requestFocus();
                results[3].setBackgroundResource(R.drawable.rounded_edittext);}
        }
        else if (carry[3].hasFocus()) {
            carry[3].setText(number);
            carry[3].setBackgroundResource(0);
            results[3].requestFocus();
            results[3].setBackgroundResource(R.drawable.rounded_edittext);
            showBtnNumbers(nums);
        }
        else if (results[3].hasFocus()) {
            results[3].setText(number);
            results[3].setBackgroundResource(0);
            if(pos[3]) {
                carry[4].requestFocus();
                carry[4].setBackgroundResource(R.drawable.rounded_edittext);
                hideBtnNumbers(posCarry, nums);
            } else { results[4].requestFocus();
                results[4].setBackgroundResource(R.drawable.rounded_edittext);}
        }
        else if (carry[4].hasFocus()) {
            carry[4].setText(number);
            carry[4].setBackgroundResource(0);
            results[4].requestFocus();
            results[4].setBackgroundResource(R.drawable.rounded_edittext);
            showBtnNumbers(nums);
        }
        else if (results[4].hasFocus()) {
            results[4].setText(number);
            results[4].setBackgroundResource(0);
        }
    }
}