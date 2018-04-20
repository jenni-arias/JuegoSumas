package arias.jenifer.juegosumas.database;

import android.provider.BaseColumns;

/**
 * Created by j.arias.gallego on 10/04/2018.
 */

public class LevelContract {

    private LevelContract() {}

    // Inner class that defines the table contents
    public static class LevelScheme implements BaseColumns {
        public static final String TABLE_NAME = "Level";

        public static final String COLUMN_LEVEL_ID = "LevelId";
        public static final String COLUMN_LEVEL = "LEVEL";
        public static final String COLUMN_EXERCISE = "EXERCISE";

        private static final String TEXT_TYPE = " TEXT";
        private static final String INT_TYPE = " INTEGER DEFAULT 0";
        private static final String COMMA_SEP = ",";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_LEVEL_ID + INT_TYPE + COMMA_SEP +
                        COLUMN_LEVEL + INT_TYPE + COMMA_SEP +
                        COLUMN_EXERCISE + INT_TYPE + COMMA_SEP +
                        " PRIMARY KEY (" + COLUMN_LEVEL_ID + ")" + " )";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
