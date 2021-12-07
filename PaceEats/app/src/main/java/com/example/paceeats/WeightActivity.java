package com.example.paceeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class WeightActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView startWeight;
    TextView currentWeightShow;
    TextView progressShow;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        startWeight = findViewById(R.id.user_start_weight);
        currentWeightShow = findViewById(R.id.user_current_weight);
        progressShow = findViewById(R.id.user_weight_progress);

        mDatabase.child("Users").child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    //Get and show starting weight
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Log.d("firebase", task.getResult().child("startingWeight").getValue().toString());
                    String startingWeight = task.getResult().child("startingWeight").getValue().toString();
                    startWeight.setText(startingWeight);

                    //Get and current weight
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Log.d("firebase", task.getResult().child("currentWeight").getValue().toString());
                    String currentWeight = task.getResult().child("currentWeight").getValue().toString();
                    currentWeightShow.setText(currentWeight);

                    //Get and show progress
                    double startingWeightDouble = Double.parseDouble(startingWeight);
                    double currentWeightDouble = Double.parseDouble(currentWeight);
                    double progress = currentWeightDouble - startingWeightDouble;
                    //Log.i("Hello", progress);
                    DecimalFormat df = new DecimalFormat("#.#");
                    String progressString = df.format(progress);
                    progressString = progressString + "lbs";
                    Log.i("Hello", progressString);
                    progressShow.setText(progressString);
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

    public void addWeight(View view) {
        Intent intent = new Intent(this, AddWeightActivity.class);
        startActivity(intent);
    }
}