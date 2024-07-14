package com.example.muzundad;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.muzundad.databinding.ActivityLoginScreenBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;

public class LoginScreen extends AppCompatActivity {
Button button;
int i = 0;
String username;
ActivityLoginScreenBinding binding;
    FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;
    public  ArrayList<User> userArrayList;
   private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        userArrayList = new ArrayList<>();
binding.editTextTextPersonName.requestFocus();
        setContentView(view);
        editText = findViewById(R.id.editTextTextPersonName);
        firebaseDatabase=FirebaseDatabase.getInstance("https://muzundadi-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference= firebaseDatabase.getReference();
       button =findViewById(R.id.button);


        final ImageView backgroundOne = (ImageView) findViewById(R.id.background_one);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.background_two);
        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX - width);
            }
        });
        animator.start();
        binding.editTextTextPersonName.requestFocus();

    }

    public void onClick(View view){


        username =editText.getText().toString();



         databaseReference.addValueEventListener(new ValueEventListener() {

             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {

                 if(!snapshot.exists()){

                     Intent intent = new Intent(LoginScreen.this,MainActivity.class);
                     intent.putExtra("username",username);
                     startActivity(intent);
                 }

             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });


         ChildEventListener childEventListener = new ChildEventListener() {
             @SuppressLint("NotifyDataSetChanged")
             @Override
             public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                 for(DataSnapshot dataSnapshot : snapshot.getChildren()   ){


                     User user = dataSnapshot.getValue(User.class);


                     userArrayList.add(user);


                     if(username.equals(user.name) ){
                         AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreen.this);

                         builder.setTitle("Confirm");
                         builder.setMessage("There is already a user named "+username+".Do you wish to continue? " +
                                 "Your score will be overwritten");

                         builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                             public void onClick(DialogInterface dialog, int which) {
                                 // Do nothing but close the dialog
                                 Intent intent = new Intent(LoginScreen.this,MainActivity.class);
                                 intent.putExtra("username",username);
                                 startActivity(intent);


                             }
                         });

                         builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                             @Override
                             public void onClick(DialogInterface dialog, int which) {

                                 // Do nothing
                                 dialog.dismiss();
                             }
                         });

                         AlertDialog alert = builder.create();
                         alert.show();

                     }

                 }

             }

             @Override
             public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


             }

             @Override
             public void onChildRemoved(@NonNull DataSnapshot snapshot) {

             }

             @Override
             public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }

         };
         databaseReference.addChildEventListener(childEventListener);

         for(int i= 0 ; i<userArrayList.size();i++){

             if( !(userArrayList.get(i).getName().equals(username))){
                 System.out.println(" USER ARRAY LİST SİZE I"+userArrayList.size());
                 Intent intent = new Intent(LoginScreen.this,MainActivity.class);
                 intent.putExtra("username",username);
                 startActivity(intent);
             }

         }







    /*
        System.out.println("BU NE OLUM"+ userArrayList.size());

      if(userArrayList.size() == 0 || userArrayList.size() == 1){
          System.out.println("USER ARRAY LİST SİZEIIIII"+ userArrayList.size());
            Intent intent = new Intent(LoginScreen.this,MainActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
        }*/






      //  Intent intent = new Intent(LoginScreen.this,MainActivity.class);

    }
 public void scoreboardClicked(View view){
     if( ! isNetworkAvailable()  ){
     Toast.makeText(this,"You are not connected to the internet",Toast.LENGTH_LONG).show();

     }
        else{
         Intent intent = new Intent(LoginScreen.this,Scoreboard.class);

         startActivity(intent);
     }



 }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}