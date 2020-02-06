package ru.sergeykozhukhov.habitcontentprovider.pattern;

import android.app.Application;

import ru.sergeykozhukhov.habitcontentprovider.pattern.presenter.InsertHabitPresenter;
import ru.sergeykozhukhov.habitcontentprovider.pattern.repository.HabitsRepository;
import ru.sergeykozhukhov.habitcontentprovider.pattern.repository.HabitsRepositoryImpl;
import ru.sergeykozhukhov.habitcontentprovider.pattern.usecase.InsertHabitInteractorImpl;

public class HabitsApplication extends Application {

    private InsertHabitPresenter insertHabitPresenter;
    private HabitsRepository habitsRepository;

    public InsertHabitPresenter getInsertHabitPresenter(){
        return insertHabitPresenter;
    }

    public InsertHabitPresenter createInsertHabitPresenter(){
        insertHabitPresenter = new InsertHabitPresenter(new InsertHabitInteractorImpl(habitsRepository));
        return insertHabitPresenter;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        habitsRepository = new HabitsRepositoryImpl(this);

        /*StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyDialog()
                .build());*/
    }

}
