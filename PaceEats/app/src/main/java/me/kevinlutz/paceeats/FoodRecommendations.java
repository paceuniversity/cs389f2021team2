package me.kevinlutz.paceeats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FoodRecommendations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_recommendations);
    }

    public void launchHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void launchNutrition(View view) {
        Intent intent = new Intent(this, NutritionActivity.class);
        startActivity(intent);
    }

    public void launchWeight(View view) {
        Intent intent = new Intent(this, WeightActivity.class);
        startActivity(intent);
    }

    public void launchMore(View view) {
        Intent intent = new Intent(this, MoreActivity.class);
        startActivity(intent);
    }
}