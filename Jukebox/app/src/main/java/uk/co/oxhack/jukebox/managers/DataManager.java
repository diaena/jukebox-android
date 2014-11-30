package uk.co.oxhack.jukebox.managers;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

import uk.co.oxhack.jukebox.components.Audio;

public class DataManager {

    private static String orderBy = MediaStore.Audio.Media.DISPLAY_NAME;

    private static Cursor initiateAudioQuery(Context context) {
        return context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.DISPLAY_NAME },
                MediaStore.Audio.Media.IS_MUSIC,
                null,
                orderBy);
    }

    public static boolean isSDPresent() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static int getAudioCount(Context context) {
        if (isSDPresent()) {
            return initiateAudioQuery(context).getCount();
        }

        return 0;
    }

    public static String getOrderBy() {
        return orderBy;
    }

    public static void setOrderBy(String value) {
        orderBy = value;
    }

    public static ArrayList<Audio> getAudio(Context context) {
        // Initializes the audio list.
        ArrayList<Audio> audioList = new ArrayList<Audio>();

        // Continues only if the SD is present.
        if (isSDPresent()) {
            Cursor cursor = initiateAudioQuery(context);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        Audio audio = new Audio(
                                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)),
                                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)),
                                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)),
                                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                        );

                        audioList.add(audio);
                    } while (cursor.moveToNext());
                }

                cursor.close();
            }
        }

        return audioList;
    }

}
