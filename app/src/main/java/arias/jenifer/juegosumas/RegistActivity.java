package arias.jenifer.juegosumas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import arias.jenifer.juegosumas.database.UserContract;
import arias.jenifer.juegosumas.database.UserSQLiteHelper;

public class RegistActivity extends AppCompatActivity {

    RelativeLayout rl_login, rl_registry;
    Button btn_login, btn_registry, btn_back, btn_go;
    TextView tv_name, tv_lastname1, tv_lastname2,
            tv_name_2, tv_lastname1_2, tv_lastname2_2, tv_age;
    EditText login_name, login_lastname1, login_lastname2,
            registry_name, registry_lastname1, registry_lastname2, registry_age;

   // UserSQLiteHelper mUsers;
    //SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        //Abrimos la base de datos 'DBUsers' en modo escritura
      /*  mUsers = new UserSQLiteHelper(
                this,
                UserSQLiteHelper.DATABASE_NAME,
                null,
                UserSQLiteHelper.DATABASE_VERSION);

        db = mUsers.getWritableDatabase(); */

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_registry = (Button) findViewById(R.id.btn_registry);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_go = (Button) findViewById(R.id.btn_go);

        rl_login = (RelativeLayout) findViewById(R.id.rl_login);
        tv_name = (TextView) findViewById(R.id.tv_name);
        login_name = (EditText) findViewById(R.id.login_name);
        tv_lastname1 = (TextView) findViewById(R.id.tv_lastname1);
        login_lastname1 = (EditText) findViewById(R.id.login_lastname1);
        tv_lastname2 = (TextView) findViewById(R.id.tv_lastname2);
        login_lastname2 = (EditText) findViewById(R.id.login_lastname2);

        rl_registry = (RelativeLayout) findViewById(R.id.rl_registry);
        tv_name_2 = (TextView) findViewById(R.id.tv_name_2);
        registry_name = (EditText) findViewById(R.id.registry_name);
        tv_lastname1_2 = (TextView) findViewById(R.id.tv_lastname1_2);
        registry_lastname1 = (EditText) findViewById(R.id.registry_lastname1);
        tv_lastname2_2 = (TextView) findViewById(R.id.tv_lastname2_2);
        registry_lastname2 = (EditText) findViewById(R.id.registry_lastname2);
        tv_age = (TextView) findViewById(R.id.tv_age);
        registry_age = (EditText) findViewById(R.id.registry_age);

        rl_login.setVisibility(View.INVISIBLE);
        rl_registry.setVisibility(View.INVISIBLE);
        btn_back.setVisibility(View.INVISIBLE);
        btn_go.setVisibility(View.INVISIBLE);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_login.setVisibility(View.VISIBLE);
                btn_login.setVisibility(View.GONE);
                btn_registry.setVisibility(View.GONE);
                btn_back.setVisibility(View.VISIBLE);
                btn_go.setVisibility(View.VISIBLE);
            }
        });

        btn_registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_registry.setVisibility(View.VISIBLE);
                btn_login.setVisibility(View.GONE);
                btn_registry.setVisibility(View.GONE);
                btn_back.setVisibility(View.VISIBLE);
                btn_go.setVisibility(View.VISIBLE);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_login.setVisibility(View.VISIBLE);
                btn_registry.setVisibility(View.VISIBLE);
                rl_login.setVisibility(View.INVISIBLE);
                rl_registry.setVisibility(View.INVISIBLE);
                btn_back.setVisibility(View.INVISIBLE);
                btn_go.setVisibility(View.INVISIBLE);
            }
        });

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Registro del usuario
               /* String name = registry_name.getText().toString();
                String apellido1 = registry_lastname1.getText().toString();
                String apellido2 = registry_lastname2.getText().toString();
                int edad = Integer.parseInt(registry_age.getText().toString());

                if (db != null) {
                    //Insertamos usuarios de ejemplo
                    ContentValues values = new ContentValues();
                    values.put(UserContract.UserScheme.COLUMN_NAME_USER_ID, 1);
                    values.put(UserContract.UserScheme.COLUMN_NAME_NOMBRE, name);
                    values.put(UserContract.UserScheme.COLUMN_NAME_LASTANME_1, apellido1);
                    values.put(UserContract.UserScheme.COLUMN_NAME_LASTNAME_2, apellido2);
                    values.put(UserContract.UserScheme.COLUMN_NAME_AGE, edad);
                    values.put(UserContract.UserScheme.COLUMN_NAME_LEVEL, 1);
                    values.put(UserContract.UserScheme.COLUMN_NAME_EXERCISE, 1);

                    db.insert(UserContract.UserScheme.TABLE_NAME, null, values);
                    db.close();
                }*/

                Intent intent = new Intent(RegistActivity.this, SumActivity.class);
                startActivity(intent);
            }
        });

    }

}

