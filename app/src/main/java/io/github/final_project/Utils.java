package io.github.final_project;

import android.util.Log;

public class Utils
{
    public static int getHashColor(String string)
    {
        // Generate hash color based on title
        int hashColor = (string.hashCode() & 0xffffff) + (0xff << 28);

        return hashColor;
    }

    public static void log()
    {
        log("@@@");
    }

    public static void log(Object msg)
    {
        Log.d("@@@", msg.toString());
    }
}
