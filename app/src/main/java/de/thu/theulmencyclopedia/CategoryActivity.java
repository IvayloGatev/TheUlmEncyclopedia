package de.thu.theulmencyclopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ColorStateListDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    LocationAdapter adapter = null;
    Location[] locations = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        LocationsDatabaseHelper dbHelper = new LocationsDatabaseHelper(this);
        try {
            dbHelper.updateDataBase();
        } catch (IOException e) {
            throw new Error("Unable to update database!");
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String projection[] = {
                BaseColumns._ID,
                LocationsDatabaseHelper.COL_NAME
        };

        String category = getIntent().getStringExtra("category");
        String selection = LocationsDatabaseHelper.COL_CATEGORY + " = ?";
        String[] selectionArgs = {category};

        Cursor c = db.query(LocationsDatabaseHelper.TABLE, projection, selection, selectionArgs,
                null, null, null);

        locations = new Location[c.getCount()];
        for (int i = 0; c.moveToNext(); i++) {
            int id = c.getInt(c.getColumnIndexOrThrow(BaseColumns._ID));
            String name = c.getString(c.getColumnIndexOrThrow(LocationsDatabaseHelper.COL_NAME));
            locations[i] = new Location(id, name);
        }
        dbHelper.close();
        adapter = new LocationAdapter(locations);

        ListView listView = (ListView) findViewById(R.id.locations_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = locations[i].getId();

                Intent intent = new Intent(CategoryActivity.this, LocationActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        getSupportActionBar().setTitle(category);
    }
}