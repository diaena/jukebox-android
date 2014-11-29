package uk.co.oxhack.jukebox;

import android.content.Context;
import android.widget.Toast;

public class MessageBox
{
    public static void show(Context applicationContext, CharSequence message)
    {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show();
    }
}