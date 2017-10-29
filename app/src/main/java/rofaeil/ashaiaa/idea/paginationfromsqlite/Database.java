package rofaeil.ashaiaa.idea.paginationfromsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Rofaeil Ashaiaa
 *         Created on 29/10/17.
 */

public class Database {

    private static SQLiteDatabase database;

    public static SQLiteDatabase getDatabase(Context context, String assetFileName) {
        if (database == null) {
            try {
                InputStream input = context.getApplicationContext().getAssets().open(assetFileName);
                File outFile = new File(context.getFilesDir(), assetFileName);
                if (outFile.exists()) {
                    outFile.delete();
                }

                copyToFile(input, outFile);

                database = SQLiteDatabase.openOrCreateDatabase(outFile, null);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return database;
    }

    private static void copyToFile(InputStream input, File outFile) {

        try {
            FileOutputStream outputStream = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024 * 8];
            int numOfBytesToRead = input.read(buffer);

            while (numOfBytesToRead > 0) {
                outputStream.write(buffer, 0, numOfBytesToRead);
                numOfBytesToRead = input.read(buffer);
            }

            input.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
