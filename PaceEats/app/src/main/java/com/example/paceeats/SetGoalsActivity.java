package com.example.paceeats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SetGoalsActivity extends AppCompatActivity {
    RadioButton radioMale;
    RadioButton radioFemale;
    EditText inputAge;
    EditText inputWeight;
    EditText inputHeightFeet;
    EditText inputHeightInches;
    RadioButton radioLose;
    RadioButton radioMaintain;
    RadioButton radioGain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);

        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        inputAge = findViewById(R.id.inputAge);
        inputWeight = findViewById(R.id.inputWeight);
        inputHeightFeet = findViewById(R.id.inputHeightFeet);
        inputHeightInches = findViewById(R.id.inputHeightInches);
        radioLose = findViewById(R.id.radioLose);
        radioMaintain = findViewById(R.id.radioMaintain);
        radioGain = findViewById(R.id.radioGain);
    }

    public void onSubmit(View view) {
        double bmr = 0;
        int goalCalories = 0;
        int age = Integer.parseInt(inputAge.getText().toString());
        int height = Integer.parseInt(inputHeightInches.getText().toString()) + (Integer.parseInt(inputHeightFeet.getText().toString()) * 12);
        int weight = Integer.parseInt(inputWeight.getText().toString());
        if (radioMale.isChecked()) {
            bmr = (10 * (weight / 2.205)) + (6.25 * (height * 2.54) - (5 * age) + 5);
        }
        else if (radioFemale.isChecked()) {
            bmr = (10 * (weight / 2.205)) + (6.25 * (height * 2.54) - (5 * age) + 161);
        }

        if (radioLose.isChecked()) {
            goalCalories = (int)Math.round(bmr - 500);
        }
        else if (radioGain.isChecked()) {
            goalCalories = (int)Math.round(bmr + 500);
        }
        else {
            goalCalories = (int)Math.round(bmr);
        }

        Toast.makeText(SetGoalsActivity.this, Integer.toString(goalCalories), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SetGoalsActivity.this, MainActivity.class));
    }
}