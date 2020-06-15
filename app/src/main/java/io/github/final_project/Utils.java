package io.github.final_project;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils
{
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static int getHashColor(String string, int transparency)
    {
        // Generate hash color based on title
        int hashColor = (string.hashCode() & 0xffffff) + (transparency << 28);

        return hashColor;
    }

    public static int getHashColor(String string)
    {
        return getHashColor(string, 0xff);
    }

    public static void log()
    {
        log("@@@");
    }

    public static void log(Object msg)
    {
        msg = msg == null ? "null!" : msg;

//        try
//        {
//            throw new Exception();
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }

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
}
