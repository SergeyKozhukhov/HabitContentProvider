package ru.sergeykozhukhov.habitcontentprovider.pattern.model;

import java.util.Objects;

public class Habit {

    private long id;
    private String title;
    private String description;

    public Habit(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habit habit = (Habit) o;
        return id == habit.id &&
                title.equals(habit.title) &&
                description.equals(habit.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }

    @Override
    public String toString() {
        return "Habit{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
