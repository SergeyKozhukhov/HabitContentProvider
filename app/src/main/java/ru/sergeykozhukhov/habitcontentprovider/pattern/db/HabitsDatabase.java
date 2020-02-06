package ru.sergeykozhukhov.habitcontentprovider.pattern.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habitcontentprovider.R;

public class HabitsDatabase extends SQLiteOpenHelper {

    public static final String DB_FILE_NAME = "habits.db";
    public static final int DB_VERSION = 1;

    private String createTableHabits;

    public HabitsDatabase(@Nullable Context context) {
        super(context, DB_FILE_NAME, null, DB_VERSION);

        createTableHabits = context.getString(R.string.create_table_habits);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableHabits);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
