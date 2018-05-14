package arias.jenifer.juegosumas;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import arias.jenifer.juegosumas.database.LevelContract;
import arias.jenifer.juegosumas.database.LevelSQLiteHelper;


public class SumActivity extends AppCompatActivity {
    private GridView gridView;
    private LevelAdapter adaptador;
    private Toolbar toolbar_sumactivity;
    private ProgressDialog progressDialog;
    private Dialog dialog;

    private LevelSQLiteHelper mLevel;
    private SQLiteDatabase db;
    private Cursor c;

    private String TAG = "Proceso";
    private int nextId, nextEx;
    private boolean exist = false;
    private String scomplete;
    private boolean complete;

    //TODO: traducir toooodos los Strings

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
        adaptador = new LevelAdapter(this, db);
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
                        MakeToast.showToast(SumActivity.this, "Nivel ya completado!", 3);
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
                    showProgressDialog("Cargando...");
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

    //Consulta para obtener ejercicio
    public static int queryExerciseLevel(SQLiteDatabase db, int levelNum) {
        int num = 0;
        String query = String.format(" SELECT EXERCISE FROM Level WHERE LEVEL=" + levelNum);
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        try {
            num = c.getInt(0);
        } catch (Exception e){
            e.printStackTrace();
        }
        return num;
    }

    //Consulta para obtener si el nivel está completado o no
    public static String queryCompleteLevel(SQLiteDatabase db, int levelNum) {
        String complete = null;
        String query = "SELECT COMPLETE FROM Level WHERE LEVEL=" + levelNum;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        try {
            complete = c.getString(0);
        } catch (Exception e){
            e.printStackTrace();
        }
        return complete;
    }

    //Consulta para habilitar los niveles
    public static boolean queryEnableLevel(SQLiteDatabase db, int[] depend) {
        boolean enableLevel = true;
        boolean[] pass = new boolean[depend.length];

        for (int i = 0; i < depend.length; i++) {
            try {
                String query = "SELECT COMPLETE FROM Level WHERE LEVEL=" + depend[i];
                Cursor c = db.rawQuery(query, null);
                c.moveToFirst();
                if(c.getString(0).equals("YES")) {
                    pass[i] = true;
                } else {
                    pass[i] = false;}
            } catch (Exception e) { //El nivel todavía no se ha empezado, no existe en la BBDD.
                enableLevel = false;
            }
        }

        for (int i = 0; i < pass.length; i++) {
            if(!pass[i]) {
                enableLevel = false;
            } else if (pass[i] && enableLevel){
                enableLevel = true;
            }
        }
        return enableLevel;
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
            case R.id.btn_delete:
                showDialog("¿Estás seguro?",
                            "Se borrarán todos los datos.",
                            1);
                return true;

            case R.id.btn_statistics:
                String[] data = statistics(c);
                showDialog("Estadísticas",
                            data[0] + "\n" + data[1] + "\n" + data[2] + "\n" + data[3],
                            2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialog(String title, String message, int type) {
        dialog = new Dialog(this, R.style.Theme_Dialog_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        TextView titulo = (TextView) dialog.findViewById(R.id.titulo);
        titulo.setText(title);
        TextView contenido = (TextView) dialog.findViewById(R.id.contenido);
        contenido.setText(message);
        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

        if(type == 1) {
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showProgressDialog("Actualizando...");
                    db.delete(LevelContract.LevelScheme.TABLE_NAME, null, null);
                    Intent intent = new Intent(SumActivity.this, SumActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });
        } else {
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });
            btn_cancel.setVisibility(View.INVISIBLE);
        }

        dialog.show();
    }

    private void showProgressDialog(String message) {
        progressDialog = new ProgressDialog(SumActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    // Estadísticas del juego
    private String[] statistics(Cursor c) {
        String[] data = new String[4];
        int complete = 0, fails = 0, activated = 1;
        boolean contain = false;

        Level all_levels[] = Level.ALL_LEVELS;
        ArrayList num_level = new ArrayList();

        //Recorremos el cursor de la BBDD
        if(c.moveToFirst()) {
            for(int i = 0; i < c.getCount(); i++) {
                if (c.getString(4).equals("YES")) {
                    complete++;
                    num_level.add(c.getInt(1));
                }
                fails = fails + c.getInt(3);
                c.moveToNext();
            }

            for(int j = 0; j < all_levels.length; j++) {
                int[] depend = all_levels[j].getDependencies();

                for (int n = 0; n < depend.length; n++) {
                    if (num_level.contains(depend[n])) {
                        contain = true;
                    } else { contain = false; }
                }

                if(contain) {
                    activated++;
                    contain = false;
                }
            }
        }

        //TODO: quizás se pueden poner porcentages y no nº de niveles completados
        data[0] = String.format("Niveles completados: " + complete + "/47");
        data[1] = String.format("Niveles activos: " + (activated - complete));
        data[2] = String.format("Niveles no activos: " + (adaptador.getCount() - activated));
        data[3] = String.format("Fallos: " + fails);

        return data;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("¿Seguro que quieres salir?")
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
