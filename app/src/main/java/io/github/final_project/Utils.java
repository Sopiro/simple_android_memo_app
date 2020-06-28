package io.github.final_project;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils
{
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final int MILLISECOND = 1000;
    private static final int SECOND = 60;
    private static final int MINUTE = SECOND * 60;
    private static final int HOUR = MINUTE * 24;
    private static final int DAY = MINUTE * 30;

    // 해당 문제의 해쉬를이용해 색을 만듦.
    public static int getHashColor(String string)
    {
        return (string.hashCode() & 0xffffff) + (0xff << 28);
    }

    public static void log(Object msg)
    {
        msg = msg == null ? "null!" : msg;

        Log.d("@@@", msg.toString());
    }

    public static void toast(Context context)
    {
        Toast.makeText(context, "Work!", Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context context, int msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static String getFormattedCurrentTime()
    {
        SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_FORMAT);

        return formatter.format(new Date());
    }

    // 지난 시간을 계산하여 표시해주기위한 메소드
    public static String getDateString(String date)
    {
        long now = System.currentTimeMillis();
        long passed = 0;

        try
        {
            Date last = new SimpleDateFormat(DATETIME_FORMAT).parse(date);
            passed = now - last.getTime();

        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        passed /= MILLISECOND;
        passed++;
        if (passed <= SECOND) return passed + "초전";
        passed /= SECOND;
        passed++;
        if (SECOND > passed) return passed + "분전";
        passed /= MINUTE;
        passed++;
        if (MINUTE > passed) return passed + "시간전";
        passed /= HOUR;
        passed++;
        if (HOUR > passed) return passed + "일전 수정됨";
        else
        {
            return date.substring(0, 10).replaceFirst("-", "년 ").replaceFirst("-", "월 ") + "일 수정됨";
        }
    }
}
