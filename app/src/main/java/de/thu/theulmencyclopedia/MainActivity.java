package de.thu.theulmencyclopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        view.setClickable(false);
        String category;
        switch (view.getId()) {
            case R.id.imageInstitutions:
            case R.id.textInstitutions:
                category = "Institutions";
                break;
            case R.id.imageLandmarks:
            case R.id.textLandmarks:
                category = "Landmarks";
                break;
            case R.id.imageRestaurants:
            case R.id.textRestaurants:
                category = "Restaurants";
                break;
            case R.id.imageShops:
            case R.id.textShops:
                category = "Shops";
                break;
            case R.id.imageEducation:
            case R.id.textEducation:
                category = "Education";
                break;
            case R.id.imageHealthCare:
            case R.id.textHealthCare:
                category = "Health Care";
                break;
            default:
                category = "";
        }

        Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}
