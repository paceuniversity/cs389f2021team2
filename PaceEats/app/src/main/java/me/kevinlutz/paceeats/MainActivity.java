package me.kevinlutz.paceeats;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView textCalorieGoal;
    Button footprint;
    Button weight;
    Button appStreak;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        textCalorieGoal = findViewById(R.id.textCalorieGoal);
        appStreak = findViewById(R.id.appStreak);
        footprint = findViewById(R.id.currentFootprint);
        weight = findViewById(R.id.currentWeight);

        mDatabase.child("Users").child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(Objects.requireNonNull(task.getResult()).getValue()));
                    Log.d("firebase", Objects.requireNonNull(task.getResult().child("calorieGoal").getValue()).toString());
                    String calorieGoal = Objects.requireNonNull(task.getResult().child("calorieGoal").getValue()).toString();
                    textCalorieGoal.setText(calorieGoal);

                    //Receive starting date
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Log.d("firebase", Objects.requireNonNull(task.getResult().child("startingDate").getValue()).toString());
                    String startDate = task.getResult().child("startingDate").getValue().toString();

                    //Get todays date
                    Date today = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String todayString = dateFormat.format(today).toString();

                    //Change to local date
                    LocalDate firstDate = LocalDate.parse(startDate);
                    LocalDate todaysDate = LocalDate.parse(todayString);

                    //Get days between and set text
                    long streak = ChronoUnit.DAYS.between(firstDate, todaysDate);
                    String streakString = String.valueOf(streak);
                    streakString = streakString + " Days";
                    appStreak.setText(streakString);

                    //Get and set carbon footprint text
                    String currentCO2 = Objects.requireNonNull(task.getResult().child("currentCO2").getValue()).toString();
                    currentCO2 = currentCO2 + " lbs of CO2";
                    footprint.setText(currentCO2);

                    //Get and set current weight text
                    String currentWeight = Objects.requireNonNull(task.getResult().child("currentWeight").getValue()).toString();
                    currentWeight = currentWeight + " lbs";
                    weight.setText(currentWeight);
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

    public void launchSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void launchFoodRecs(View view) {
        startActivity(new Intent(this, FoodRecommendations.class));
    }

}