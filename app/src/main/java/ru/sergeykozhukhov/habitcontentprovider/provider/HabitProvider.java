package ru.sergeykozhukhov.habitcontentprovider.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

import ru.sergeykozhukhov.habitcontentprovider.pattern.db.HabitsDatabase;
import ru.sergeykozhukhov.habitcontentprovider.pattern.db.HabitsModel;


/**
 * Класс, для получения записей из таблицы "habits" и добавления привычек.
 * Работает через ContentProvider.
 */
public class HabitProvider extends ContentProvider {

    /**
     * Идентификатор, определяющий список всех привычек.
     */
    private static final int HABITS = 1;

    /**
     * Идентификатор, определяющий одну конкретную привычку.
     */
    private static final int HABIT = 2;

    /**
     * Класс UriMatcher принимает на вход URI и возвращает числовую константу, соответствующую определённому типу данных.
     *
     * Если Uri будет подходить под комбинацию authority и path, ранее добавленных в addURI,
     * то UriMatcher вернет константу из того же набора.
     *
     * Т.е. в данном случае при получении URI вида
     * content://ru.sergeykozhukhov.habitcontentprovider.provider.HabitProvider/habits
     * он вернет константу HABITS.
     *
     * UriMatcher.No_MATCH - параметр конструктора, константа, которую нужно возвращать,
     * если путь (после HabitsModel.AUTHORITY - ?) не будет задан
     *
     * В static блоке определяются возможные соотношения URI и констант.
     * Cпецсимволы: * - строка любых символов любой длины, # - строка цифр любой длины
     */
    private static final UriMatcher URI_MATCHER;
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(HabitsModel.AUTHORITY, "habits", HABITS);
        URI_MATCHER.addURI(HabitsModel.AUTHORITY, "habits/#", HABIT);
    }

    private HabitsDatabase habitsDatabase;

    @Override
    public boolean onCreate() {
        habitsDatabase = new HabitsDatabase(getContext());
        return true;
    }

    /**
     * Метод, использующийся для получения данных, соответствующих определённым критериям.
     *
     * getLastPathSegment()- получение идентификатор конкретной привычки.
     *
     * @param uri - URI контента.
     * @param projection - массив столбцов, которые нужно выбрать из таблицы. Опциональный параметр.
     * @param selection - параметры выборки (условие после WHERE в SQL). Опциональный параметр.
     * @param selectionArgs - аргументы выборки. Опциональный параметр.
     * @param sortOrder - столбец, по которому сортировать данные и направление сортировки. Опциональный параметр.
     * @return Cursor.
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase db = habitsDatabase.getReadableDatabase();


        switch (URI_MATCHER.match(uri)) {
            case HABITS:
                if (sortOrder == null || Objects.equals(sortOrder, "")) {
                    sortOrder = HabitsModel.Columns.TITLE;
                }
                return db.query(HabitsModel.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

            default:
                return null;
        }
    }

    /**
     * Принимает URI контента в качестве параметра и возвращает тип контента
     * (не числовую константу, а вида, е.g, vnd.android.cursor.dir/vnd.sergeykozhukhov.habit).
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (URI_MATCHER.match(uri)) {
            case HABITS:
                return HabitsModel.Columns.URI_TYPE_HABIT_DIR;
            case HABIT:
                return HabitsModel.Columns.URI_TYPE_HABIT_ITEM;
            default:
                return null;
        }
    }

    /**
     * Метод insert(), что логично, отвечает за вставку данных в БД.
     *
     * ContentUris - набор полезных методов для работы с URI.
     * withAppendedId - присоедние данного id к обозначенному URI в самый конец.
     *
     * @param uri - URI, как и в query.
     * @param values -  набор пар "ключ - значение" ("столбец - данные").
     *                ContentValues по существу представляет собой набор пар ключ-значение,
     *                где ключ представляет столбец для таблицы,
     *                а значение – значение, которое должно быть вставлено в этот столбец.
     * @return Uri созданного элемента (если он был создан).
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = habitsDatabase.getWritableDatabase();

        switch (URI_MATCHER.match(uri)) {
            case HABITS:
                long rowId = db.insert(HabitsModel.TABLE_NAME,
                        null,
                        values);

                if (rowId > 0) {
                    Uri noteUri = ContentUris.withAppendedId(HabitsModel.Columns.URI, rowId);
                    if (getContext() != null){
                        getContext().getContentResolver().notifyChange(uri, null);
                        return noteUri;
                    }
                }
                return null;

            default:
                return null;
        }

    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
