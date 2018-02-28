package arias.jenifer.juegosumas;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class SumAdapter extends RecyclerView.Adapter<SumAdapter.SumViewHolder> {

    private ArrayList<SumItem> datos;

    public static class SumViewHolder extends RecyclerView.ViewHolder {
        private ImageButton num_level;

        public SumViewHolder(View itemView) {
            super(itemView);
            num_level = (ImageButton) itemView.findViewById(R.id.num_level);
        }

        public void bindSum(SumItem t) {
            // num_level.setText(t.getNum_level()); //TODO: Añadir el número del nivel al ImageButton
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


/**************************************************************************************
 * TIPO 2
 ***************************************************************************************/
/*
public class SumAdapter extends RecyclerView.Adapter<SumAdapter.ViewHolder> {

    private String [] datos = new String[48];
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    //Los datos se pasan por el constructor
    SumAdapter(Context context, String[] data) {
        this.inflater = LayoutInflater.from(context);
        this.datos = data;
    }

    // Infla la celda del layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.sum_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = datos[position];
        holder.myTextView.setText(animal);
    }

    // Número total de celdas
    @Override
    public int getItemCount() {
        return datos.length;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.num_level);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // getting
    String getItem(int id) {
        return datos[id];
    }

    // clicks
    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
*/

