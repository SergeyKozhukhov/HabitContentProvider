package ru.sergeykozhukhov.habitcontentprovider.pattern.presenter;

public interface InsertHabitView {

    void onHabitInserted();

    class Empty implements InsertHabitView {

        @Override
        public void onHabitInserted() {

        }
    }
}
