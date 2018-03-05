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

    interface OnLevelClickListener {
        void onLevelClick(Level level);
    }

    public LevelAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Level.ITEMS.length;
    }

    @Override
    public Level getItem(int position) {
        return Level.ITEMS[position];
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sum_item, viewGroup, false);
        }

        Button btn_level = (Button) view.findViewById(R.id.btn_level);
        final Level level = getItem(position);
        btn_level.setText(String.valueOf(level.getLevel()));
        btn_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelClickListener.onLevelClick(level);
            }
        });
        return view;
    }

    void setOnLevelClickListener(OnLevelClickListener listener) {
        this.levelClickListener = listener;
    }

}
