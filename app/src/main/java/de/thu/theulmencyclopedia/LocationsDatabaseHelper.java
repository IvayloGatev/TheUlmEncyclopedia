package de.thu.theulmencyclopedia;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class LocationsDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LocationsDatabase.db";
    private String DATABASE_PATH;

    private SQLiteDatabase dataBase;
    private Context context;
    private boolean needsUpdate = false;

    public static final String TABLE = "Location";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_CATEGORY = "category";

    public LocationsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DATABASE_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.context = context;

        copyDataBase();
        this.getReadableDatabase();

    }

    public void updateDataBase() throws IOException {
        if (needsUpdate) {
            File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
            if (dbFile.exists()) {
                dbFile.delete();
            }

            copyDataBase();
            needsUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File file = new File(DATABASE_PATH + DATABASE_NAME);
        return file.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDataBaseFile();
            } catch (IOException ioException) {
                throw new Error("Error copying the database!");
            }
        }
    }

    private void copyDataBaseFile() throws IOException {
        InputStream input = context.getAssets().open(DATABASE_NAME);
        OutputStream output = new FileOutputStream(DATABASE_PATH + DATABASE_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0)
            output.write(buffer, 0, length);
        output.flush();
        output.close();
        input.close();
    }

    public boolean openDataBase() throws SQLException {
        dataBase = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME,
                null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return dataBase != null;
    }

    @Override
    public synchronized void close() {
        if (dataBase != null)
            dataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            needsUpdate = true;
    }
}
