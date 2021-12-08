package com.example.paceeats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddWorkout extends AppCompatActivity {
    EditText workoutInput;
    EditText dateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
        workoutInput = findViewById(R.id.enterWorkout);
        dateInput = findViewById(R.id.enterDate);

    }


    public void onAdd(View view) {
         String workout,date;
         workout = String.valueOf(workoutInput.getText());
         date = String.valueOf(dateInput.getText());

         Intent intent = new Intent(this,WorkoutActitivity.class);
         startActivity(intent);
    }
}