package io.github.final_project.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

import io.github.final_project.R;
import io.github.final_project.Utils;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "final";
    public static final String TABLE_NAME = "memo";

    private static final HashMap<Integer, String[]> schema = new HashMap<>();

    private SQLiteDatabase db;

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, 1);

        if (schema.size() != 6)
        {
            schema.put(1, context.getResources().getStringArray(R.array.db_col_1));
            schema.put(2, context.getResources().getStringArray(R.array.db_col_2));
            schema.put(3, context.getResources().getStringArray(R.array.db_col_3));
            schema.put(4, context.getResources().getStringArray(R.array.db_col_4));
            schema.put(5, context.getResources().getStringArray(R.array.db_col_5));
            schema.put(6, context.getResources().getStringArray(R.array.db_col_6));
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        StringBuilder sqlBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(");
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

    public Cursor select(SQLiteDatabase db, int... select)
    {
        StringBuilder sql = new StringBuilder("SELECT ");

        if (select.length == 1 && select[0] == -1)
        {
            sql.append("* FROM " + TABLE_NAME);
        } else
        {
            for (int column : select)
            {
                String[] line = schema.get(column);
                assert line != null;

                sql.append(line[0]).append(", ");
            }

            sql = new StringBuilder(sql.substring(0, sql.length() - 2) + " FROM " + TABLE_NAME);
        }

        sql.append(" ORDER BY " + schema.get(4)[0] + " DESC");

        return db.rawQuery(sql.toString(), null);
    }

    public Cursor selectWhere(SQLiteDatabase db, int thisColumn, String equalsToThis, int... select)
    {
        StringBuilder sql = new StringBuilder("SELECT ");

        if (select.length == 1 && select[0] == -1)
        {
            sql.append("* FROM " + TABLE_NAME);
        } else
        {
            for (int column : select)
            {
                String[] line = schema.get(column);
                assert line != null;

                sql.append(line[0]).append(", ");
            }

            sql = new StringBuilder(sql.substring(0, sql.length() - 2) + " FROM " + TABLE_NAME);
        }

        sql.append(" WHERE ").append(schema.get(thisColumn)[0]).append(" = '").append(equalsToThis).append("'");
        sql.append(" ORDER BY " + schema.get(4)[0] + " DESC");

        return db.rawQuery(sql.toString(), null);
    }

    public Cursor selectWhereLike(SQLiteDatabase db, int thisColumn, String looksLikeThis, int... select)
    {
        StringBuilder sql = new StringBuilder("SELECT ");

        if (select.length == 1 && select[0] == -1)
        {
            sql.append("* FROM " + TABLE_NAME);
        } else
        {
            for (int column : select)
            {
                String[] line = schema.get(column);
                assert line != null;

                sql.append(line[0]).append(", ");
            }

            sql = new StringBuilder(sql.substring(0, sql.length() - 2) + " FROM " + TABLE_NAME);
        }

        sql.append(" WHERE ").append(schema.get(thisColumn)[0]).append(" LIKE '").append(looksLikeThis).append("'");
        sql.append(" ORDER BY " + schema.get(4)[0] + " DESC");

        return db.rawQuery(sql.toString(), null);
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
