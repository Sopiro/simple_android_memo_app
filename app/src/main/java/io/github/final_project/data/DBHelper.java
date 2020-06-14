package io.github.final_project.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.github.final_project.Utils;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "final";
    private static final String TABLE_NAME = "memo";

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    private SQLiteDatabase db;

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                "title varchar(100) not null," +
                "content text not null," +
                "tags text," +
                "creation_date datetime not null primary key," +
                "last_date datetime not null," +
                "star int not null)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void updateMemo(String creationDate, String title, String content, String tags, int stars)
    {
        db = getWritableDatabase();

        db.execSQL("UPDATE " + TABLE_NAME + " SET title = ?, content = ?, tags = ?, last_date = ?, star = ? WHERE creation_date = '" + creationDate + "'",
                new Object[]{title, content, tags, Utils.getFormattedCurrentTime(), stars});

        db.close();
    }

    public void newMemo(String title, String content, String tags, int stars)
    {
        if (content.equals(""))
            return;

        db = getWritableDatabase();

        String sql = "INSERT INTO " + TABLE_NAME + "(title, content, tags, creation_date, last_date, star) values(?, ?, ?, ?, ?, ?)";

        String currentTime = Utils.getFormattedCurrentTime();

        db.execSQL(sql, new Object[]{title, content, tags, currentTime, currentTime, stars});

        db.close();
    }

    public void deleteMemo(String creationDate)
    {
        db = getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE creation_date = '" + creationDate + "'");

        db.close();
    }
}
