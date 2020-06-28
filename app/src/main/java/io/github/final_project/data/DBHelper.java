package io.github.final_project.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

import io.github.final_project.Utils;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME;
    private static final String TABLE_NAME;
    private static final HashMap<Integer, String[]> schema;

    private SQLiteDatabase db;

    // static block에서 DB와 관련된 상수 초기화
    static
    {
        DB_NAME = "final";
        TABLE_NAME = "memo";

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
        // schema 스트링 배열로부터 테이블 생성 SQL을 만들어서 실행함
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

    // select로 지정한 column을 리턴함.
    /**
     * @param select -1 will return all column info.
     */
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

    // select from where에 대한 wrapper 메소드.
    // 지정한 column과 같은 data를 리턴함.
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

    // select where like에 대한 wrapper 메소드.
    // 지정한 column의 데이터와 like 명령이 같은 data를 리턴함. 검색기능 수행.
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

    // 메모를 업데이트하는 메소드.
    public void updateMemo(String creationDate, String title, String content, String tags, int stars)
    {
        db = getWritableDatabase();

        db.execSQL("UPDATE " + TABLE_NAME +
                        " SET title = ?, content = ?, tags = ?, last_date = ?, star = ?" +
                        "WHERE creation_date = '" + creationDate + "'",
                new Object[]{title, content, tags, Utils.getFormattedCurrentTime(), stars});

        db.close();
    }

    // 새 메모를 추가하는 메소드.
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

    // 메모를 삭제하는 메소드.
    public void deleteMemo(String creationDate)
    {
        db = getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE creation_date = '" + creationDate + "'");

        db.close();
    }
}
