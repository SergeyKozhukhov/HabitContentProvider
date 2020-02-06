package ru.sergeykozhukhov.habitcontentprovider.pattern.presenter;

import androidx.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ru.sergeykozhukhov.habitcontentprovider.pattern.model.Habit;
import ru.sergeykozhukhov.habitcontentprovider.pattern.usecase.InsertHabitInteractor;

public class InsertHabitPresenter {

    private final InsertHabitInteractor insertHabitInteractor;

    @NonNull
    private InsertHabitView insertHabitView = new InsertHabitView.Empty();
    private Disposable disposable;

    private Habit habitInserted;

    public InsertHabitPresenter(InsertHabitInteractor insertHabitInteractor) {
        this.insertHabitInteractor = insertHabitInteractor;
    }

    public void attachView(InsertHabitView insertHabitView) {
        this.insertHabitView = insertHabitView;
        notifyView();
    }

    public void detachView() {
        insertHabitView = new InsertHabitView.Empty();
    }

    public void insertHabit(String title, String description) {
        disposable = insertHabitInteractor.insertHabit(title, description)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Habit>() {
                               @Override
                               public void accept(Habit habit) throws Exception {
                                   habitInserted = habit;
                                   notifyView();
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        });
    }

    private void notifyView() {

        if (habitInserted != null) {
            insertHabitView.onHabitInserted();
        }
    }


}
