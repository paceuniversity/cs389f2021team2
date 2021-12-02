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


public class NutritionActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView calorieGoalString;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        calorieGoalString = findViewById(R.id.calorieGoal);

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
                    calorieGoalString.setText(calorieGoal);
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
        Intent intent = new Intent (this, NutritionActivity.class);
        startActivity(intent);
    }
}