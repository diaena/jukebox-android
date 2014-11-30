package uk.co.oxhack.jukebox.managers;

import android.content.Context;
import android.widget.Toast;

public class MessageBox
{
    public static void show(Context context, CharSequence message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}