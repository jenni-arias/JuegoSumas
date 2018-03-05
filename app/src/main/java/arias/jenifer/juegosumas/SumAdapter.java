package arias.jenifer.juegosumas;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by j.arias.gallego on 24/02/2018.
 */

/**************************************************************************************
 *TIPO 1
 ***************************************************************************************/
/*
public class SumAdapter extends RecyclerView.Adapter<SumAdapter.SumViewHolder> {

    private ArrayList<SumItem> datos;
    private Button btn_level;


    public static class SumViewHolder extends RecyclerView.ViewHolder {


        public SumViewHolder(View itemView) {
            super(itemView);

        }

        public void bindSum(SumItem t) {
            // btn_level.setText(t.getNum_level()); 
        }
    }

    public SumAdapter(ArrayList<SumItem> datos) {
        this.datos = datos;
    }

    @Override
    public SumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.sum_item, parent, false);
        SumViewHolder svh = new SumViewHolder(itemView);
        return svh;
    }

    @Override
    public void onBindViewHolder(SumViewHolder holder, int position) {
        SumItem item = datos.get(position);
        holder.bindSum(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }
}

 */
/**************************************************************************************
 * TIPO 3
 ***************************************************************************************/

public class SumAdapter extends BaseAdapter {
    private Context context;
    private OnLevelClickListener levelClickListener;

    interface OnLevelClickListener {
        void onLevelClick(Level level);
    }

    public SumAdapter(Context context) {
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
