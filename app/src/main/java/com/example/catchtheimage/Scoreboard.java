package com.example.catchtheimage;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import android.view.View;


import com.example.catchtheimage.databinding.ActivityScoreboardBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scoreboard extends AppCompatActivity {
   public ArrayList<User> userArrayList;
   Adaptor adaptor;
    ActivityScoreboardBinding binding ;
    FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;


String name ;
int score;



    public  Scoreboard(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoreboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        userArrayList = new ArrayList<>();
        adaptor = new Adaptor(userArrayList);

        firebaseDatabase=FirebaseDatabase.getInstance("https://muzundadi-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference= firebaseDatabase.getReference();


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.recyclerView.setAdapter(adaptor);


        getData();



    }

    public void getData(){

        ChildEventListener childEventListener = new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(userArrayList.size()>=1) userArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()   ){
                    User user = dataSnapshot.getValue(User.class);

                    Log.i(TAG, "onDataChange: "+user);
                    userArrayList.add(user);
                    System.out.println(user.name);
                    System.out.println(userArrayList.size());
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    userArrayList.sort(new Comparator<User>() {
                        @Override
                        public int compare(User o1, User o2) {



                            return Integer.compare(o2.getScore(), o1.getScore());
                        }
                    });
                }


                adaptor.notifyDataSetChanged();


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




    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}




