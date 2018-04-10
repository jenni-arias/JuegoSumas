package arias.jenifer.juegosumas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import arias.jenifer.juegosumas.database.UserContract;
import arias.jenifer.juegosumas.database.UserSQLiteHelper;


public class RegistryActivity extends AppCompatActivity {

    private RelativeLayout rl_login, rl_registry;
    private Button btn_login, btn_registry, btn_back, btn_go;
    private TextView tv_name, tv_lastname1, tv_lastname2,
            tv_name_2, tv_lastname1_2, tv_lastname2_2, tv_age;
    private EditText login_name, login_lastname1, login_lastname2,
            registry_name, registry_lastname1, registry_lastname2, registry_age;

    private Boolean login = false;
    private UserSQLiteHelper mUsers;
    private SQLiteDatabase db;

    private String TAG = "Proceso";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

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

        //Abrimos la base de datos 'DBUsers' en modo escritura
        Log.i(TAG, "Abrir BBDD DBUsers.db");
        mUsers = new UserSQLiteHelper(
                this,
                UserSQLiteHelper.DATABASE_NAME,
                null,
                UserSQLiteHelper.DATABASE_VERSION);

        db = mUsers.getWritableDatabase();

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Click botón Go");
                String name, apellido1, apellido2;
                int edad;
                String[] campos = new String[] {"userId", "name", "lastname_1", "lastname_2"};
                Cursor c = db.query(UserContract.UserScheme.TABLE_NAME, campos,
                        null, null, null, null, null);

                if(login) {
                    //Login del usuario
                    Log.i(TAG, "Click botón Login");
                    name = login_name.getText().toString();
                    apellido1 = login_lastname1.getText().toString();
                    apellido2 = login_lastname2.getText().toString();

                    goLogin(c, name, apellido1, apellido2);

                } else {
                    //Registro del usuario
                    Log.i(TAG, "Click botón Registro");
                    name = registry_name.getText().toString();
                    apellido1 = registry_lastname1.getText().toString();
                    apellido2 = registry_lastname2.getText().toString();
                    edad = Integer.parseInt(registry_age.getText().toString());

                    goRegist(c, name, apellido1, apellido2, edad);
                }
                c.close();
                //db.close();
            }
        });
    }

    //Si el usuario se ha logado correctamente, accede a SumActivity
    private void goLogin(Cursor c, String name, String apellido1, String apellido2) {
        boolean find = false;

        //Recorremos el cursor de la BBDD
        if(c.moveToFirst()) {
            for(int i = 0; i < c.getCount(); i++) {
                if (name.equals(c.getString(1)) &&
                        apellido1.equals(c.getString(2)) &&
                        apellido2.equals(c.getString(3))) {
                    find = true;
                    Log.i(TAG, "Usuario " + name + " encontrado en la BBDD");
                    Toast.makeText(RegistryActivity.this, "Hola " + name + "!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistryActivity.this, SumActivity.class);
                    startActivity(intent);
                    finish();
                }
                c.moveToNext();
            }
        }

        if(!find) {
            login_name.setText("");
            login_lastname1.setText("");
            login_lastname2.setText("");
            login_name.requestFocus();
            Toast.makeText(RegistryActivity.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Usuario " + name + " no existe en la BBDD");
        }
    }

    //Si el registro es correcto se guardan los datos en la BBDD
    private void goRegist(Cursor c, String name, String apellido1, String apellido2, int edad) {
        int nextId;
        boolean find = false;

        //Recorremos el cursor de la BBDD
        if(c.moveToFirst()) {
            for(int i = 0; i < c.getCount(); i++) {
                if (name.equals(c.getString(1)) &&
                        apellido1.equals(c.getString(2)) &&
                        apellido2.equals(c.getString(3))) {
                    find = true;
                    registry_name.setText("");
                    registry_lastname1.setText("");
                    registry_lastname2.setText("");
                    registry_age.setText("");
                    registry_name.requestFocus();
                    Toast.makeText(RegistryActivity.this, "Usuario ya registrado", Toast.LENGTH_SHORT).show();
                }
                c.moveToNext();
            }
        }

        if(!find && db != null ) {
            if(c.getCount() == 0) {
                nextId = 1;
            } else {
                c.moveToLast();
                nextId = Integer.parseInt(c.getString(0)) + 1;
            }

            //Insertamos usuarios a la BBDD
            ContentValues newRegist = new ContentValues();
            newRegist.put(UserContract.UserScheme.COLUMN_NAME_USER_ID, nextId);
            newRegist.put(UserContract.UserScheme.COLUMN_NAME_NOMBRE, name);
            newRegist.put(UserContract.UserScheme.COLUMN_NAME_LASTANME_1, apellido1);
            newRegist.put(UserContract.UserScheme.COLUMN_NAME_LASTNAME_2, apellido2);
            newRegist.put(UserContract.UserScheme.COLUMN_NAME_AGE, edad);
            newRegist.put(UserContract.UserScheme.COLUMN_NAME_LEVEL, 1);
            newRegist.put(UserContract.UserScheme.COLUMN_NAME_EXERCISE, 1);

            try {
                db.insert(UserContract.UserScheme.TABLE_NAME, null, newRegist);
                Log.i(TAG, "Usuario " + name + " añadido a la BBDD");
            } catch (Exception e) {
                Log.i(TAG, "No se pudo añadir el nuevo usuario a la BBDD");
            }

            Intent intent = new Intent(RegistryActivity.this, SumActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(RegistryActivity.this, "Hola " + name + "!", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Usuario nuevo registrado: "+ name + ". Ir a SumActivity");
        }
    }


    public void pressed (View v) {
        switch(v.getId())
        {
            case R.id.btn_login:
                rl_login.setVisibility(View.VISIBLE);
                btn_login.setVisibility(View.GONE);
                btn_registry.setVisibility(View.GONE);
                btn_back.setVisibility(View.VISIBLE);
                btn_go.setVisibility(View.VISIBLE);
                login = true;
                break;

            case R.id.btn_registry:
                rl_registry.setVisibility(View.VISIBLE);
                btn_login.setVisibility(View.GONE);
                btn_registry.setVisibility(View.GONE);
                btn_back.setVisibility(View.VISIBLE);
                btn_go.setVisibility(View.VISIBLE);
                login = false;
                break;

            case R.id.btn_back:
                Log.i(TAG, "Click botón Back");
                btn_login.setVisibility(View.VISIBLE);
                btn_registry.setVisibility(View.VISIBLE);
                rl_login.setVisibility(View.INVISIBLE);
                rl_registry.setVisibility(View.INVISIBLE);
                btn_back.setVisibility(View.INVISIBLE);
                btn_go.setVisibility(View.INVISIBLE);
                break;
        }
    }

}

