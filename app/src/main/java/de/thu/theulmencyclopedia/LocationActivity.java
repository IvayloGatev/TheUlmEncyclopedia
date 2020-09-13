package de.thu.theulmencyclopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class LocationActivity extends AppCompatActivity {
    Location location = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        LocationsDatabaseHelper dbHelper = new LocationsDatabaseHelper(this);
        try {
            dbHelper.updateDataBase();
        } catch (IOException e) {
            throw new Error("Unable to update database!");
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String projection[] = {
                LocationsDatabaseHelper.COL_NAME,
                LocationsDatabaseHelper.COL_DESCRIPTION
        };

        int id = getIntent().getIntExtra("id", 0);
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = {id + ""};

        Cursor c = db.query(LocationsDatabaseHelper.TABLE, projection, selection, selectionArgs,
                null, null, null);
        if (c.moveToFirst()) {
            String name = c.getString(c.getColumnIndexOrThrow(LocationsDatabaseHelper.COL_NAME));
            String description = c.getString(c.getColumnIndexOrThrow(LocationsDatabaseHelper.COL_DESCRIPTION));
            location = new Location(id, name, description);
        }

        getSupportActionBar().setTitle(location.getName());

        ImageView imageLocation = (ImageView) findViewById(R.id.imageLocation);
        String imageName = "image_location_" + location.getId();
        int imageId = getResources().getIdentifier(imageName,
                "drawable", getPackageName());
        imageLocation.setImageResource(imageId);


        TextView textDescription = (TextView) findViewById(R.id.textDescription);
        textDescription.setText(location.getDescription());
    }

    public void findOnMap(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0`?q=" + location.getName()));
        startActivity(intent);
    }
}
