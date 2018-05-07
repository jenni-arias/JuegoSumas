package arias.jenifer.juegosumas;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import arias.jenifer.juegosumas.database.LevelContract;
import arias.jenifer.juegosumas.database.LevelSQLiteHelper;


public class SumActivity extends AppCompatActivity {
    private GridView gridView;
    private LevelAdapter adaptador;
    private Toolbar toolbar_sumactivity;

    private LevelSQLiteHelper mLevel;
    private SQLiteDatabase db;
    private Cursor c;
    private String TAG = "Proceso";
    private int nextId, nextEx;
    private boolean exist = false;
    private String scomplete;
    private boolean complete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum);

        //Configuración actionbar - toolbar
        toolbar_sumactivity = (Toolbar) findViewById(R.id.toolbar_exercise);
        setSupportActionBar(toolbar_sumactivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //Abrimos la base de datos 'DBLevel' en modo escritura
        Log.i(TAG, "Abrir BBDD DBLevel.db");
        mLevel = new LevelSQLiteHelper(
                this,
                LevelSQLiteHelper.DATABASE_NAME,
                null,
                LevelSQLiteHelper.DATABASE_VERSION);

        db = mLevel.getWritableDatabase();

        String[] campos = new String[] {"levelId", "level", "exercise", "fails", "complete"};
        c = db.query(LevelContract.LevelScheme.TABLE_NAME, campos,
                null, null, null, null, null);

        // Adaptador
        adaptador = new LevelAdapter(this);
        adaptador.setOnLevelClickListener(new LevelAdapter.OnLevelClickListener() {
            @Override
            public void onLevelClick(int level) {

                int select_level = level + 1;

                //Recorremos el cursor de la BBDD
                if(c.moveToFirst()) {
                    for(int i = 0; i < c.getCount(); i++) {
                        if (c.getInt(1) == select_level) {
                            exist = true;
                            nextEx = c.getInt(2);
                            scomplete = c.getString(4);
                        }
                        c.moveToNext();
                    }
                }

                if (exist) {
                    complete = false;
                    ContentValues values = new ContentValues();
                    values.put(LevelContract.LevelScheme.COLUMN_EXERCISE, nextEx);
                    db.update(LevelContract.LevelScheme.TABLE_NAME, values, "level=" + select_level, null);
                    if(scomplete.equals("YES")) {
                        complete = true;
                        Toast.makeText(SumActivity.this, "Nivel ya completado!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    complete = false;
                    if(db != null ) {
                        if (c.getCount() == 0) {
                            nextId = 1;
                        } else {
                            c.moveToLast();
                            nextId = Integer.parseInt(c.getString(0)) + 1;
                        }

                        //Insertamos nivel a la BBDD
                        ContentValues newRegist = new ContentValues();
                        newRegist.put(LevelContract.LevelScheme.COLUMN_LEVEL_ID, nextId);
                        newRegist.put(LevelContract.LevelScheme.COLUMN_LEVEL, select_level);
                        newRegist.put(LevelContract.LevelScheme.COLUMN_EXERCISE, 1);
                        newRegist.put(LevelContract.LevelScheme.COLUMN_FAILS, 0);
                        newRegist.put(LevelContract.LevelScheme.COLUMN_COMPLETE, "NO");

                        try {
                            db.insert(LevelContract.LevelScheme.TABLE_NAME, null, newRegist);
                            Log.i(TAG, "Nivel " + select_level + " añadido a la BBDD");
                        } catch (Exception e) {
                            Log.i(TAG, "No se pudo añadir el nuevo nivel a la BBDD");
                        }
                    }
                }

                if(!complete) {
                    //Iniciar ExerciseActivity
                    Intent intent = new Intent(SumActivity.this, ExerciseActivity.class);
                    intent.putExtra("Nivel", level);
                    startActivity(intent);
                    finish();
                } else {
                    exist = false;
                }
            }
        });

        //GridView
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(adaptador);
    }

    //Inflamos el menú en la Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sumactivity, menu);
        return true;
    }

    // Acciones para el menú del Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                new AlertDialog.Builder(this)
                        .setTitle("Seguro que quieres borrar todos los datos?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                db.delete(LevelContract.LevelScheme.TABLE_NAME, null, null);
                                Intent intent = new Intent(SumActivity.this, SumActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .create()
                        .show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Seguro que quieres salir?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

}

        /*Divisiones niveles dificultad
        recView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recView.setItemAnimator(new DefaultItemAnimator()); */
