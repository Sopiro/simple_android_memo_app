package io.github.final_project.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

import io.github.final_project.Utils;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "final";
    public static final String TABLE_NAME = "memo";

    private static final HashMap<Integer, String[]> schema;

    private SQLiteDatabase db;

    static
    {
        schema = new HashMap<>();
        schema.put(1, new String[]{"title", "varchar(100) not null"});
        schema.put(2, new String[]{"content", "text not null"});
        schema.put(3, new String[]{"tags", "text"});
        schema.put(4, new String[]{"creation_date", "datetime not null primary key"});
        schema.put(5, new String[]{"last_date", "datetime not null"});
        schema.put(6, new String[]{"star", "int not null"});
    }

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE " + TABLE_NAME + "(");
        for (int i = 1; i <= schema.size(); i++)
        {
            String[] line = schema.get(i);
            assert line != null;

            sqlBuilder.append(line[0]).append(" ").append(line[1]).append(", ");
        }
        String sql = sqlBuilder.toString();

        sql = sql.substring(0, sql.length() - 2).concat(")");

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor select(SQLiteDatabase db, int... columns)
    {
        StringBuilder sql = new StringBuilder("SELECT ");

        if (columns.length == 1 && columns[0] == -1)
        {
            sql.append("* FROM " + TABLE_NAME);
        } else
        {
            for (int column : columns)
            {
                String[] line = schema.get(column);
                assert line != null;

                sql.append(line[0]).append(", ");
            }

            sql = new StringBuilder(sql.substring(0, sql.length() - 2) + " FROM " + TABLE_NAME);
        }

        return db.rawQuery(sql.toString(), null);
    }

    public Cursor selectWhere(SQLiteDatabase db, int thisColumn, String equalsToThis, int... columns)
    {
        String sql = "SELECT ";

        if (columns.length == 1 && columns[0] == -1)
        {
            sql += "* FROM " + TABLE_NAME;
        } else
        {
            for (int column : columns)
            {
                String[] line = schema.get(column);
                assert line != null;

                sql += line[0] + ", ";
            }

            sql = sql.substring(0, sql.length() - 2) + " FROM " + TABLE_NAME;
        }

        return db.rawQuery(sql, null);
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
