package arias.jenifer.juegosumas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;


/**************************************************************************************
 *TIPO 1
 ***************************************************************************************/

public class SumActivity extends AppCompatActivity {

    private RecyclerView recView;
    private ArrayList<SumItem> nums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum);

        // Inicialización de la lista de datos de ejemplo con 50 niveles
        nums = new ArrayList<>();
        for (int i = 0; i < 48; i++) {
            nums.add(new SumItem(i));
        }

        //Adaptador
        final SumAdapter adaptador = new SumAdapter(nums);

        //Inicialización RecyclerView
        recView = (RecyclerView) findViewById(R.id.recView);
        recView.setHasFixedSize(true);                          // setHasFixedSize: Fijar tamaño
        recView.setAdapter(adaptador);

        //GridView
        recView.setLayoutManager(new GridLayoutManager(this, 4));

        //Divisiones niveles dificultad
        recView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recView.setItemAnimator(new DefaultItemAnimator());

    }
}


/**************************************************************************************
 * TIPO 2
 ***************************************************************************************/
/*
public class SumActivity extends AppCompatActivity implements SumAdapter.ItemClickListener{

    private SumAdapter adapter;
    private RecyclerView recView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum);

        // Inicialización de la lista de datos de ejemplo con 50 niveles
        String[] data = new String[48];
        for (int i = 1; i < 49; i++) {
            data[i-1] = String.valueOf(i);
        }

        // Adaptador
        adapter = new SumAdapter(this, data);
        adapter.setClickListener(this);

        // RecyclerView
        recView = (RecyclerView) findViewById(R.id.recView);
        recView.setHasFixedSize(true);                           // setHasFixedSize: Fijar tamaño
        recView.setAdapter(adapter);

        //GridView
        recView.setLayoutManager(new GridLayoutManager(this, 4));

        //Divisiones niveles dificultad
        recView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "Nivel " + adapter.getItem(position));  //+ ", en la posición " + position);
    }
}
*/

