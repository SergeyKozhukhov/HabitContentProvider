package ru.sergeykozhukhov.habitcontentprovider.pattern.db;

import android.net.Uri;

public class HabitsModel {

    /**
     * ru.sergeykozhukhov.habitcontentprovider.provider.HabitProvider — так называемый Authority, "адрес" ContentProvider.
     */
    public static final String AUTHORITY = "ru.sergeykozhukhov.habitcontentprovider.provider.HabitProvider";

    /**
     * Базовый URI для доступа к контенту.
     * content:// — схема. Она сообщает, что мы хотим получить доступ к контенту.
     */
    public static final String URI = "content://" + AUTHORITY;

    public static final String TABLE_NAME = "habits";

    public static final class Columns {

        /**
         * URI для доступа к конкретному виду данных, а именно к списку привычке
         */
        public static final Uri URI = Uri.parse(HabitsModel.URI + "/" + TABLE_NAME);

        /**
         * Обозначение списка всех привычек
         */
        public static final String URI_TYPE_HABIT_DIR = "vnd.android.cursor.dir/vnd.sergeykozhukhov.habit";

        /**
         * Обозначение одной привычки
         */
        public static final String URI_TYPE_HABIT_ITEM = "vnd.android.cursor.item/vnd.sergeykozhukhov.habit";


        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";

        public static final String[] ALL = new String[] {
                ID, TITLE, DESCRIPTION
        };
    }

}
