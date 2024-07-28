package com.example.catchtheimage;

import static android.content.ContentValues.TAG;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.catchtheimage.databinding.ActivityLoginScreenBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginScreen extends AppCompatActivity {
    private Button button;
    private String username;
    private ActivityLoginScreenBinding binding;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersReference;
    private EditText editText;
    private ArrayList<User> userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        userArrayList = new ArrayList<>();
        editText = findViewById(R.id.editTextTextPersonName);

        String firebaseUrl = BuildConfig.FIREBASE_DATABASE_URL;
        firebaseDatabase = FirebaseDatabase.getInstance(firebaseUrl);
        usersReference = firebaseDatabase.getReference("users");

        setupDatabaseListener();

        button = findViewById(R.id.button);

        final ImageView backgroundOne = findViewById(R.id.background_one);
        final ImageView backgroundTwo = findViewById(R.id.background_two);
        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000L);
        animator.addUpdateListener(animation -> {
            final float progress = (float) animation.getAnimatedValue();
            final float width = backgroundOne.getWidth();
            final float translationX = width * progress;
            backgroundOne.setTranslationX(translationX);
            backgroundTwo.setTranslationX(translationX - width);
        });
        animator.start();

        binding.editTextTextPersonName.requestFocus();
    }

    private void setupDatabaseListener() {
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userArrayList.clear(); // Clear list to avoid duplication
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        userArrayList.add(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
            }
        });
    }

    public void onClick(View view) {
        username = editText.getText().toString().trim();
        if (username.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean userExists = false;
        for (User user : userArrayList) {
            if (username.equals(user.getName())) {
                userExists = true;
                break;
            }
        }

        if (userExists) {
            showConfirmationDialog();
        } else {
            proceedToMainActivity();
        }
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("There is already a user named " + username + ". Do you wish to continue? Your score will be overwritten.")
                .setPositiveButton("YES", (dialog, which) -> proceedToMainActivity())
                .setNegativeButton("NO", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void proceedToMainActivity() {
        Intent intent = new Intent(LoginScreen.this, MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void scoreboardClicked(View view) {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, "You are not connected to the internet", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(LoginScreen.this, Scoreboard.class);
            startActivity(intent);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
