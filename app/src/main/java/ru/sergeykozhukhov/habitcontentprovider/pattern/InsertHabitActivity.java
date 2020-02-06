package ru.sergeykozhukhov.habitcontentprovider.pattern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.sergeykozhukhov.habitcontentprovider.R;
import ru.sergeykozhukhov.habitcontentprovider.pattern.db.HabitsModel;
import ru.sergeykozhukhov.habitcontentprovider.pattern.presenter.InsertHabitPresenter;
import ru.sergeykozhukhov.habitcontentprovider.pattern.presenter.InsertHabitView;

public class InsertHabitActivity extends AppCompatActivity implements InsertHabitView {

    private InsertHabitPresenter insertHabitPresenter;
    private EditText habitTitleEditText;
    private EditText habitDescriptionEditText;
    private Button insertHabitButton;
    private Button insertHabitResolverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_habit);

        if (savedInstanceState == null) {
            ((HabitsApplication) getApplication()).createInsertHabitPresenter();
        }

        insertHabitPresenter = ((HabitsApplication) getApplication()).getInsertHabitPresenter();

        habitTitleEditText = findViewById(R.id.habit_title_edit_text);
        habitDescriptionEditText = findViewById(R.id.habit_description_edit_text);
        insertHabitButton = findViewById(R.id.insert_habit_button);
        insertHabitResolverButton = findViewById(R.id.insert_habit_resolver_button);

        insertHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertHabitPresenter.insertHabit(
                        habitTitleEditText.getText().toString(),
                        habitDescriptionEditText.getText().toString());
            }
        });

        insertHabitResolverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert(habitTitleEditText.getText().toString(), habitDescriptionEditText.getText().toString());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        insertHabitPresenter.attachView(this);
    }

    @Override
    protected void onStop() {
        insertHabitPresenter.detachView();
        super.onStop();
    }

    @Override
    public void onHabitInserted() {
        Toast.makeText(this, getString(R.string.insert_success_toast), Toast.LENGTH_SHORT).show();
    }

    private void insert(String title, String description) {
        ContentResolver contentResolver = getContentResolver();

        ContentValues contentValues = new ContentValues();
        contentValues.put(HabitsModel.Columns.TITLE, title);
        contentValues.put(HabitsModel.Columns.DESCRIPTION, description);

        Uri uri = contentResolver.insert(HabitsModel.Columns.URI, contentValues);
        // Log.i("Test", "URI: " + uri);
    }
}
