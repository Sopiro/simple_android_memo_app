package io.github.final_project;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils
{
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

        Log.d("@@@", msg.toString());
    }

    public static void toast(Context context)
    {
        Toast.makeText(context, "Work!", Toast.LENGTH_SHORT).show();
    }

    public static String getFormattedCurrentTime()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return formatter.format(new Date(System.currentTimeMillis() + 32400000));
    }
}
