package arias.jenifer.juegosumas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by j.arias.gallego on 24/02/2018.
 */

public class LevelAdapter extends BaseAdapter {
    private String TAG = "Proceso";
    private Context context;
    private OnLevelClickListener levelClickListener;

    private SQLiteDatabase db;

    private Level level;
    private int[] depend;
    private int levelNum;

    private TextView correct[] = new TextView[5];

    public LevelAdapter(Context context, SQLiteDatabase db) {
        this.context = context;
        this.db = db;
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

        levelNum = position + 1;
        Button btn_level = (Button) view.findViewById(R.id.btn_level);
        btn_level.setText(String.valueOf(levelNum));

        //Obtener dependencias del nivel
        level = Level.ALL_LEVELS[position];
        depend = level.getDependencies();

        if (levelNum == 1 || SumActivity.queryEnableLevel(db, depend)) {
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
            //Consulta para obtener ejercicio
            int query = SumActivity.queryExerciseLevel(db, levelNum);
            try {
                setColors(query);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    private void setColors(int mCurrentExercise) {
        if (mCurrentExercise == 5) {
            //Consulta para obtener si el nivel está completado o no
            String query = SumActivity.queryCompleteLevel(db, levelNum);
            if(query.equals("YES")) {
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
