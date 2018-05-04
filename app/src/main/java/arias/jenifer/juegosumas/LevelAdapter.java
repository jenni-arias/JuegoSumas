package arias.jenifer.juegosumas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import arias.jenifer.juegosumas.database.LevelSQLiteHelper;


/**
 * Created by j.arias.gallego on 24/02/2018.
 */

public class LevelAdapter extends BaseAdapter {
    private String TAG = "Proceso";
    private Context context;
    private OnLevelClickListener levelClickListener;

    private LevelSQLiteHelper mLevel;
    private SQLiteDatabase db;
    private String[] args;

    private Level level;
    private int[] depend;

    private TextView correct[] = new TextView[5];

    public LevelAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Level.ALL_LEVELS.length;
    }

    @Override
    public Object getItem(int position) {
        return Level.ALL_LEVELS[position];
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup)
    {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sum_item, viewGroup, false);
        }

        correct[0] = (TextView) view.findViewById(R.id.correct_1);
        correct[1] = (TextView) view.findViewById(R.id.correct_2);
        correct[2] = (TextView) view.findViewById(R.id.correct_3);
        correct[3] = (TextView) view.findViewById(R.id.correct_4);
        correct[4] = (TextView) view.findViewById(R.id.correct_5);

        //Abrimos la base de datos 'DBLevel' en modo escritura
        mLevel = new LevelSQLiteHelper(
                context,
                LevelSQLiteHelper.DATABASE_NAME,
                null,
                LevelSQLiteHelper.DATABASE_VERSION);

        db = mLevel.getWritableDatabase();

        final int levelNum = position + 1;
        Button btn_level = (Button) view.findViewById(R.id.btn_level);
        btn_level.setText(String.valueOf(levelNum));

        //Obtener dependencias del nivel
        level = Level.ALL_LEVELS[position];
        depend = level.getDependencies();

        if (levelNum == 1 || enableLevel(depend)) {
            btn_level.setEnabled(true);
            btn_level.setBackgroundResource(R.drawable.oval_enabled);
        }

        btn_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelClickListener.onLevelClick(position);
            }
        });

        if(levelNum > 0) {
            args = new String[] {String.valueOf(levelNum)};
            String query = String.format(" SELECT EXERCISE FROM Level WHERE LEVEL=" + args[0]);
            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();
            try {
                setColors(c.getInt(0));
               // Log.i(TAG, "ProgressBar del nivel: "+ levelNum + ", coloreada correctamente en SumActivity.");
            }catch (Exception e) {
               // Log.i(TAG, "Nivel " + levelNum + " todavía no empezado. Error al colorear la progressBar en SumActivity.");
            }
        }
        return view;
    }

    private boolean enableLevel (int[] depend) {
        String query = "";
        boolean enableLevel = true;
        boolean[] pass = new boolean[depend.length];

        for (int i = 0; i < depend.length; i++) {
            try {
                query = "SELECT COMPLETE FROM Level WHERE LEVEL=" + depend[i];
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

    private void setColors(int mCurrentExercise) {
        if (mCurrentExercise == 5) {
            String query = "SELECT COMPLETE FROM Level WHERE LEVEL=" + args[0];
            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();
            if(c.getString(0).equals("YES")) {
                mCurrentExercise++;
            }
        }
        for (int i = 0; i < mCurrentExercise - 1; i++) {
            correct[i].setBackgroundResource(R.color.light_green);
        }
        if (mCurrentExercise <= 5) {
            correct[mCurrentExercise-1].setBackgroundResource(R.color.yellow);
        }
    }

    interface OnLevelClickListener {
        void onLevelClick(int level);
    }

    void setOnLevelClickListener(OnLevelClickListener listener) {
        this.levelClickListener = listener;
    }

}
