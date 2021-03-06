package me.kevinlutz.paceeats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.SimpleDateFormat;
import java.util.Date;


public class SettingsActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser;
    EditText inputNewPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        String todayString = dateFormat.format(today).toString();


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        inputNewPassword = findViewById(R.id.inputNewPassword);
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

    public void launchAbout(View view) {
        startActivity(new Intent(this, AboutActivity.class));
    }

    public void changePassword(View view) {
        mAuth.getCurrentUser().updatePassword(inputNewPassword.getText().toString());
        Toast.makeText(getApplicationContext(), "Password successfully changed.", Toast.LENGTH_SHORT).show();
    }

    public void signOut(View view) {
        mAuth.signOut();
        startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
    }

    public void openGithub(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/paceuniversity/cs389f2021team2"));
        startActivity(browserIntent);
    }
}