package ru.sergeykozhukhov.habitcontentprovider.pattern.usecase;

import io.reactivex.Single;
import ru.sergeykozhukhov.habitcontentprovider.pattern.model.Habit;

public interface InsertHabitInteractor {

    Single<Habit> insertHabit(String title, String description);

}
