package ru.sergeykozhukhov.habitcontentprovider.pattern.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ru.sergeykozhukhov.habitcontentprovider.pattern.db.HabitsDatabase;
import ru.sergeykozhukhov.habitcontentprovider.pattern.db.HabitsModel;
import ru.sergeykozhukhov.habitcontentprovider.pattern.model.Habit;

public class HabitsRepositoryImpl implements HabitsRepository {

    private Context context;
    private HabitsDatabase habitsDatabase;

    public HabitsRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public Habit insertHabit(String title, String description) {
        if (habitsDatabase == null) {
            habitsDatabase = new HabitsDatabase(context);
        }

        SQLiteDatabase db = habitsDatabase.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(HabitsModel.Columns.TITLE, title);
        cv.put(HabitsModel.Columns.DESCRIPTION, description);

        long id = db.insert(HabitsModel.TABLE_NAME, null, cv);
        db.close();

        return new Habit(id, title, description);
    }
}
