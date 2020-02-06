package ru.sergeykozhukhov.habitcontentprovider.pattern.usecase;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitcontentprovider.pattern.model.Habit;
import ru.sergeykozhukhov.habitcontentprovider.pattern.repository.HabitsRepository;

public class InsertHabitInteractorImpl implements InsertHabitInteractor {

    private final HabitsRepository habitsRepository;


    public InsertHabitInteractorImpl(HabitsRepository habitsRepository) {
        this.habitsRepository = habitsRepository;
    }

    @Override
    public Single<Habit> insertHabit(final String title, final String description) {

        Single<Habit> result = Single.fromCallable(new Callable<Habit>() {
            @Override
            public Habit call() throws Exception {
                return habitsRepository.insertHabit(title, description);
            }
        }).subscribeOn(Schedulers.io());

        return result;
    }
}
