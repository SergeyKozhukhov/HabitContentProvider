package ru.sergeykozhukhov.habitcontentprovider.pattern.repository;

import androidx.annotation.WorkerThread;

import ru.sergeykozhukhov.habitcontentprovider.pattern.model.Habit;

public interface HabitsRepository {

    @WorkerThread
    Habit insertHabit(String title, String description);

}
