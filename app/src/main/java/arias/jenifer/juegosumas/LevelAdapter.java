package arias.jenifer.juegosumas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;


/**
 * Created by j.arias.gallego on 24/02/2018.
 */

public class LevelAdapter extends BaseAdapter {
    private Context context;
    private OnLevelClickListener levelClickListener;


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

        Button btn_level = (Button) view.findViewById(R.id.btn_level);

        final int showNum = position + 1; //
        btn_level.setText(String.valueOf(showNum));
        btn_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelClickListener.onLevelClick(position);
            }
        });
        return view;
    }

    interface OnLevelClickListener {
        void onLevelClick(int level);
    }

    void setOnLevelClickListener(OnLevelClickListener listener) {
        this.levelClickListener = listener;
    }

}
