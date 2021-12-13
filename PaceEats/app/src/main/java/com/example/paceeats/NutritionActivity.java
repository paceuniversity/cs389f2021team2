package com.example.paceeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NutritionActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView calorieGoalString, currentCaloriesText;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser;
    ProgressBar calorieBar;

    Spinner foodSpinner;

    EditText customCals, customCO2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        calorieGoalString = findViewById(R.id.calorieGoal);
        currentCaloriesText = findViewById(R.id.currentCaloriesText);

        calorieBar = findViewById(R.id.calorieProgressBar);

        foodSpinner = findViewById(R.id.foodSpinner);
        String[] suggestedFoods = getResources().getStringArray(R.array.suggested_foods);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, suggestedFoods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodSpinner.setAdapter(adapter);

        customCals = findViewById(R.id.inputCustomCals);
        customCO2 = findViewById(R.id.inputCustomCO2);

        mDatabase.child("Users").child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());

                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Log.d("firebase", task.getResult().child("calorieGoal").getValue().toString());
                    String calorieGoal = task.getResult().child("calorieGoal").getValue().toString();
                    String currentCalories = task.getResult().child("currentCalories").getValue().toString() + " of";
                    calorieGoalString.setText(calorieGoal);
                    currentCaloriesText.setText(currentCalories);
                    calorieBar.setProgress(Integer.parseInt(calorieGoal));
                }
            }
        });
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

    public void submitFood(View view) {
        String foodName = foodSpinner.getSelectedItem().toString();

        mDatabase.child("Users").child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    int currCals = Integer.parseInt(task.getResult().child("currentCalories").getValue().toString());
                    int newCals = currCals;
                    if (foodName.equals("Rice Bowl"))
                        newCals = 575 + currCals;
                    else if (foodName.equals("Wrap"))
                        newCals = 540 + currCals;
                    else if (foodName.equals("Omelette"))
                        newCals = 250 + currCals;

                    // Update current calories display and current calories in the databse
                    mDatabase.child("Users").child(currentUser.getUid()).child("currentCalories").setValue(newCals);
                    String currCalsString = String.valueOf(newCals);
                    currentCaloriesText.setText(currCalsString + " of");
                }
            }
        });
    }


    // Log custom foods (calories and CO2)
    // TODO: Add Functionality for CO2 tracking, it's currently disabled!
    public void logCustom(View view) {
        final int cals, co2;
        cals = Integer.parseInt(customCals.getText().toString());
        //co2 = Integer.parseInt(customCO2.getText().toString());

        mDatabase.child("Users").child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    int currCals = Integer.parseInt(task.getResult().child("currentCalories").getValue().toString());
                    int newCals = cals + currCals;

                    // Update current calories display and current calories in the databse
                    mDatabase.child("Users").child(currentUser.getUid()).child("currentCalories").setValue(newCals);
                    String currCalsString = String.valueOf(newCals);
                    currentCaloriesText.setText(currCalsString + " of");
                }
            }
        });
    }

    public void resetCals(View view) {
        mDatabase.child("Users").child(currentUser.getUid()).child("currentCalories").setValue(0);
        currentCaloriesText.setText("0 of");
    }
}