package com.example.paceeats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddWeightActivity extends AppCompatActivity {

    EditText inputWeight;
    EditText inputDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight);
        inputWeight = findViewById(R.id.newWeight);
        inputDate = findViewById(R.id.newDate);
    }

    public void onAdd(View view) {
        //Concat the weight with the date to create an entry
        String newWeight = String.valueOf(inputWeight.getText());
        String newDate = String.valueOf(inputDate.getText());
        String newEntry = (newWeight + " lbs - " + newDate);
        double currentWeight = Double.parseDouble(inputWeight.getText().toString());



        Log.i("Hello", newWeight);
        Log.i("Hello", newDate);
        Log.i("Hello", newEntry);



        /*
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        String todayString = dateFormat.format(today).toString();

        String newDate =

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        DatabaseReference ref = mDatabase.child(currentUser.getUid());



        //LinkedList<Integer> weights = new LinkedList();
        //weights.add(weight);

        ref.child("calorieGoal").setValue(goalCalories);
        ref.child("startingWeight").setValue(weight);
        //ref.child("weightsList").setValue(weights);

        Toast.makeText(SetGoalsActivity.this, Integer.toString(goalCalories), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SetGoalsActivity.this, MainActivity.class));
         */

        //Go back to weight page
        Intent intent = new Intent(this, WeightActivity.class);
        startActivity(intent);
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