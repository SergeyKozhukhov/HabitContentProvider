package ru.sergeykozhukhov.habitcontentprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ru.sergeykozhukhov.habitcontentprovider.pattern.InsertHabitActivity;
import ru.sergeykozhukhov.habitcontentprovider.pattern.db.HabitsModel;

public class MainActivity extends AppCompatActivity {

    private TextView listHabitsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listHabitsTextView = findViewById(R.id.load_text_view);

        findViewById(R.id.load_resolver_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listHabitsTextView.setText(loadHabits());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_insert, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_insert_habit) {
            startActivity(new Intent(this, InsertHabitActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    private CharSequence loadHabits() {

        StringBuilder stringBuilder = new StringBuilder();
        Cursor cursor = getContentResolver().query(HabitsModel.Columns.URI, null, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    stringBuilder.append(getString(R.string.test_load_1));
                    stringBuilder.append(cursor.getString(cursor.getColumnIndex(HabitsModel.Columns.ID)));
                    stringBuilder.append(getString(R.string.test_load_2));
                    stringBuilder.append(cursor.getString(cursor.getColumnIndex(HabitsModel.Columns.TITLE)));
                    stringBuilder.append(getString(R.string.test_load_3));
                    stringBuilder.append(cursor.getString(cursor.getColumnIndex(HabitsModel.Columns.DESCRIPTION)));
                    stringBuilder.append("\n");
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return stringBuilder;
    }

}
