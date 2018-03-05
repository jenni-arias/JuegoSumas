package arias.jenifer.juegosumas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


/**************************************************************************************
 *TIPO 1
 ***************************************************************************************/
/*
public class SumActivity extends AppCompatActivity {

    private RecyclerView recView;
    private ArrayList<SumItem> nums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum);

        // Inicialización de la lista de datos de ejemplo con 49 niveles
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
*/

/**************************************************************************************
 * TIPO 3
 ***************************************************************************************/

public class SumActivity extends AppCompatActivity {
    private GridView gridView;
    private SumAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum);

        // Adaptador
        adaptador = new SumAdapter(this);
        adaptador.setOnLevelClickListener(new SumAdapter.OnLevelClickListener() {
            @Override
            public void onLevelClick(Level level) {
                Intent intent = new Intent(SumActivity.this, ExerciseActivity.class);
                intent.putExtra("Nivel", level.getLevel());
                startActivity(intent);
            }
        });

        //GridView
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(adaptador);
    }
}
