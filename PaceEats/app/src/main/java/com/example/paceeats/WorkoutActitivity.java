package com.example.paceeats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class WorkoutActitivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_actitivity);
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

    private int count=0;
    public void addWorkout(View view) {
//        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.constraint);
//        EditText workout = new EditText(this);
//        Intent intent = new Intent(this, AddWorkout.class);
//        startActivity(intent)
//        workout.setHint("Name");
//        int number = workout.generateViewId();
//        workout.setId(number);
//        layout.addView(workout);
//        ConstraintSet set = new ConstraintSet();
//        set.clone(layout);
//        set.connect(R.id.constraint,set.RIGHT,R.id.constraint,set.RIGHT,0);
        EditText name2 = (EditText) findViewById(R.id.editTextTextPersonName2);
        EditText name3 = (EditText) findViewById(R.id.editTextTextPersonName3);
        EditText name4 = (EditText) findViewById(R.id.editTextTextPersonName4);
        EditText date2 = (EditText) findViewById(R.id.editTextDate2);
        EditText date3 = (EditText) findViewById(R.id.editTextDate3);
        EditText date4 = (EditText) findViewById(R.id.editTextDate4);

        count++;
        if(count==1){
            name2.setVisibility(View.VISIBLE);
            date2.setVisibility(View.VISIBLE);
        }
        if(count==2){
            name3.setVisibility(View.VISIBLE);
            date3.setVisibility(View.VISIBLE);
        }
        if(count==3){
            name4.setVisibility(View.VISIBLE);
            date4.setVisibility(View.VISIBLE);
        }
    }
}