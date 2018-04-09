package arias.jenifer.juegosumas.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by j.arias.gallego on 06/04/2018.
 */

public class UserSQLiteHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DBUsers.db";

    public UserSQLiteHelper(Context context, String name,
                                  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(UserContract.UserScheme.SQL_CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla
        db.execSQL(UserContract.UserScheme.SQL_DELETE_TABLE);
        //Se crea la nueva versión de la tabla
        onCreate(db);
    }

}
