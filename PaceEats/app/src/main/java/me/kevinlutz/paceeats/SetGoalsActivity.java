package me.kevinlutz.paceeats;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
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

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
            DatabaseReference ref = mDatabase.child(currentUser.getUid());

            if (radioLose.isChecked()) {
                goalCalories = (int) Math.round(bmr - 500);
                ref.child("goal").setValue("lose");
            } else if (radioGain.isChecked()) {
                goalCalories = (int) Math.round(bmr + 500);
                ref.child("goal").setValue("gain");
            } else {
                goalCalories = (int) Math.round(bmr);
                ref.child("goal").setValue("maintain");
            }

            //Date today = new Date();
            //SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
            //String todayString = dateFormat.format(today).toString();
            //String firstEntry = (weight + " lbs - " + todayString);

            Date today = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String todayString = dateFormat.format(today).toString();
            LocalDate startingDate = LocalDate.parse(todayString);
            Log.i("Hello", String.valueOf(startingDate));

            ref.child("startingDate").setValue(todayString);
            ref.child("calorieGoal").setValue(goalCalories);
            ref.child("startingWeight").setValue(weight);
            ref.child("currentWeight").setValue(weight);
            ref.child("currentCalories").setValue(0);
            ref.child("currentCO2").setValue(0);

            startActivity(new Intent(SetGoalsActivity.this, MainActivity.class));
        }
    }
}