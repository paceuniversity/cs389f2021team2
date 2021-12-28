package me.kevinlutz.paceeats;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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
import java.util.Objects;

public class WeightActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView startWeight;
    TextView currentWeightShow;
    TextView progressShow;
    TextView feedback;
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
        feedback = findViewById(R.id.feedback_textView);

        mDatabase.child("Users").child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    //Get and show starting weight
                    Log.d("firebase", String.valueOf(Objects.requireNonNull(task.getResult()).getValue()));
                    Log.d("firebase", Objects.requireNonNull(task.getResult().child("startingWeight").getValue()).toString());
                    String startingWeight = Objects.requireNonNull(task.getResult().child("startingWeight").getValue()).toString();
                    startWeight.setText(startingWeight);

                    //Get and current weight
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Log.d("firebase", Objects.requireNonNull(task.getResult().child("currentWeight").getValue()).toString());
                    String currentWeight = Objects.requireNonNull(task.getResult().child("currentWeight").getValue()).toString();
                    currentWeightShow.setText(currentWeight);

                    //Get and show progress
                    double startingWeightDouble = Double.parseDouble(startingWeight);
                    double currentWeightDouble = Double.parseDouble(currentWeight);
                    double progress = currentWeightDouble - startingWeightDouble;
                    DecimalFormat df = new DecimalFormat("#.#");
                    String progressString = df.format(progress);
                    String progressWithPounds = progressString + "lbs";
                    if(progress > 0)
                    {
                        progressWithPounds = "+" + progressWithPounds;
                    }
                    Log.i("Hello", progressWithPounds);
                    progressShow.setText(progressWithPounds);

                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Log.d("firebase", Objects.requireNonNull(task.getResult().child("goal").getValue()).toString());
                    String userGoal = Objects.requireNonNull(task.getResult().child("goal").getValue()).toString();
                    String feedbackText;
                    if(userGoal.equals("lose"))
                    {
                        if(progress < 0) {
                            feedbackText = "Great job! You have lost " + progressString + " pounds. Keep up the good work!";
                            progressShow.setTextColor(Color.rgb(0, 200, 0));
                        }
                        else if(progress > 0)
                        {
                            feedbackText = "Consider using some of our food recommendations for some healthy options and try using our Nutrition Activity!";
                            progressShow.setTextColor(Color.rgb(200 , 0, 0));
                        }
                        else
                        {
                            feedbackText = "Consider using some of our food recommendations for some healthy options and try using our Nutrition Activity!";
                        }
                        feedback.setText(feedbackText);
                    }
                    else if(userGoal.equals("maintain"))
                    {
                        if(progress == 0)
                        {
                            feedbackText = "Consider using using our nutrition activity to help maintain your weight!";
                            progressShow.setTextColor(Color.rgb(0, 200, 0));
                        }
                        else if(progress <= 5 && progress >= -5)
                        {
                            feedbackText = "Great job! You have been maintaining your weight well, keep up the good work!";
                            progressShow.setTextColor(Color.rgb(0, 200, 0));
                        }
                        else
                        {
                            feedbackText = "Consider using using our nutrition activity to help maintain your weight!";
                            progressShow.setTextColor(Color.rgb(200, 0, 0));
                        }
                        feedback.setText(feedbackText);
                    }
                    else if(userGoal.equals("gain"))
                    {
                        if(progress > 0)
                        {
                            feedbackText = "Great job! You have gained " + progressString + " pounds. Keep up the good work!";
                            progressShow.setTextColor(Color.rgb(0, 200, 0));
                        }
                        else if(progress == 0)
                        {
                            feedbackText = "Consider using some of our food recommendations for some healthy options and try using our Nutrition Activity!";
                        }
                        else
                        {
                            feedbackText = "Consider using some of our food recommendations for some healthy options and try using our Nutrition Activity!";
                            progressShow.setTextColor(Color.rgb(200, 0, 0));
                        }
                        feedback.setText(feedbackText);
                    }
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