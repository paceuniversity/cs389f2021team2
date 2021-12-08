package com.example.paceeats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class SetGoalsActivity extends AppCompatActivity {

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
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

        if ((!radioMale.isChecked() && !radioFemale.isChecked()) ||
                (!radioGain.isChecked() && !radioLose.isChecked() && !radioMaintain.isChecked()) ||
                inputWeight.getText().toString().equals("") ||
                inputHeightFeet.getText().toString().equals("") ||
                inputHeightInches.getText().toString().equals("") ||
                inputAge.getText().toString().equals("")) {

            Toast.makeText(SetGoalsActivity.this, "All fields must be filled out!", Toast.LENGTH_SHORT).show();
        }
        else {

            double bmr = 0;
            int goalCalories = 0;
            int age = Integer.parseInt(inputAge.getText().toString());
            int height = Integer.parseInt(inputHeightInches.getText().toString()) + (Integer.parseInt(inputHeightFeet.getText().toString()) * 12);
            int weight = Integer.parseInt(inputWeight.getText().toString());
            if (radioMale.isChecked()) {
                bmr = (10 * (weight / 2.205)) + (6.25 * (height * 2.54) - (5 * age) + 5);
            } else if (radioFemale.isChecked()) {
                bmr = (10 * (weight / 2.205)) + (6.25 * (height * 2.54) - (5 * age) + 161);
            }

            if (radioLose.isChecked()) {
                goalCalories = (int) Math.round(bmr - 500);
            } else if (radioGain.isChecked()) {
                goalCalories = (int) Math.round(bmr + 500);
            } else {
                goalCalories = (int) Math.round(bmr);
            }

            Date today = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
            String todayString = dateFormat.format(today).toString();
            String firstEntry = (weight + " lbs - " + todayString);

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
            DatabaseReference ref = mDatabase.child(currentUser.getUid());


            //LinkedList<Integer> weights = new LinkedList();
            //weights.add(weight);

            ref.child("calorieGoal").setValue(goalCalories);
            ref.child("startingWeight").setValue(weight);
            ref.child("currentWeight").setValue(weight);
            ref.child("currentCalories").setValue(0);
            ref.child("currentCO2").setValue(0);

            startActivity(new Intent(SetGoalsActivity.this, MainActivity.class));
        }
    }
}