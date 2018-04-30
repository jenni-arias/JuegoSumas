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

        Button btn_level = (Button) view.findViewById(R.id.btn_level);

        final int showNum = position + 1; //
        btn_level.setText(String.valueOf(showNum));
        // TODO: descomentar este "if" cuando empiece las dependencias de niveles
     //   if (position == 0) {  
            btn_level.setEnabled(true);
            btn_level.setBackgroundResource(R.drawable.oval_enabled);
        //}
        btn_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelClickListener.onLevelClick(position);
            }
        });

        if(position > -1) {
            args = new String[] {String.valueOf(showNum)};
            String query = String.format(" SELECT EXERCISE FROM Level WHERE LEVEL=" + args[0]);
            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();
            try {
                setColors(c.getInt(0));
               // Log.i(TAG, "ProgressBar del nivel: "+ showNum + ", coloreada correctamente en SumActivity.");
            }catch (Exception e) {
               // Log.i(TAG, "Nivel " + showNum + " todavía no empezado. Error al colorear la progressBar en SumActivity.");
            }
        }

        return view;
    }

    public void setColors(int mCurrentExercise) {
        if (mCurrentExercise == 5) {
            String query = String.format(" SELECT COMPLETE FROM Level WHERE LEVEL=" + args[0]);
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
