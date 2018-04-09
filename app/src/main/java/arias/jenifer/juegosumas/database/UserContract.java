package arias.jenifer.juegosumas.database;

import android.provider.BaseColumns;

/**
 * Created by j.arias.gallego on 06/04/2018.
 */

public class UserContract {

    private UserContract() {}

    // Inner class that defines the table contents
    public static class UserScheme implements BaseColumns {
        public static final String TABLE_NAME = "User";

        public static final String COLUMN_NAME_USER_ID = "userId";
        public static final String COLUMN_NAME_NOMBRE = "NAME";
        public static final String COLUMN_NAME_LASTANME_1 = "LASTNAME_1";
        public static final String COLUMN_NAME_LASTNAME_2 = "LASTNAME_2";
        public static final String COLUMN_NAME_AGE = "AGE";
        public static final String COLUMN_NAME_LEVEL = "LEVEL";
        public static final String COLUMN_NAME_EXERCISE = "EXERCISE";

        private static final String TEXT_TYPE = " TEXT";
        private static final String INT_TYPE = " INTEGER DEFAULT 0";
        private static final String COMMA_SEP = ",";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_NAME_USER_ID + INT_TYPE + COMMA_SEP +
                        COLUMN_NAME_NOMBRE + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_LASTANME_1 + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_LASTNAME_2 + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_AGE + INT_TYPE + COMMA_SEP +
                        COLUMN_NAME_LEVEL + INT_TYPE + COMMA_SEP +
                        COLUMN_NAME_EXERCISE + INT_TYPE + COMMA_SEP +
                        " PRIMARY KEY (" + COLUMN_NAME_USER_ID + ")" + " )";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
