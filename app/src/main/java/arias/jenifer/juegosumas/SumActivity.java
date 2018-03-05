package arias.jenifer.juegosumas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;


public class SumActivity extends AppCompatActivity {
    private GridView gridView;
    private LevelAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum);

        // Adaptador
        adaptador = new LevelAdapter(this);
        adaptador.setOnLevelClickListener(new LevelAdapter.OnLevelClickListener() {
            @Override
            public void onLevelClick(Level level) {
                Intent intent = new Intent(SumActivity.this, ExerciseActivity.class);
                intent.putExtra("Nivel", level.getLevel());
                intent.putExtra("Unid_up", level.getUnid_up());
                intent.putExtra("Dec_up", level.getDec_up());
                intent.putExtra("Cent_up", level.getCent_up());
                intent.putExtra("Mil_up", level.getMil_up());
                intent.putExtra("Unid_down", level.getUnid_down());
                intent.putExtra("Dec_down", level.getDec_down());
                intent.putExtra("Cent_down", level.getCent_down());
                intent.putExtra("Mil_down", level.getMil_down());
                intent.putExtra("Acarreo", level.getAcarreo());
                startActivity(intent);
            }
        });

        //GridView
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(adaptador);
    }
}

        /*Divisiones niveles dificultad
        recView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recView.setItemAnimator(new DefaultItemAnimator()); */
