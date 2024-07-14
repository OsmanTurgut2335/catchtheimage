package com.example.muzundad;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.muzundad.databinding.ActivityMainBinding;
import com.example.muzundad.databinding.ActivityScoreboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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


/*
        ValueEventListener valueEventListener = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(userArrayList.size()>=1) userArrayList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()   ){
             User user = dataSnapshot.getValue(User.class);

                    Log.i(TAG, "onDataChange: "+user);
         userArrayList.add(user);
                    System.out.println(user.name);
                }
          adaptor.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);


*/


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}


     /*
new Deneme().readUsers(new Deneme.DataStatus() {
    @Override
    public void DataIsLoaded(List<User> users, List<String> keys) {
        new RecyclerViewConfig().setConfig(recyclerView,Scoreboard.this,users,keys);
    }

    @Override
    public void DataIsInserted() {

    }

    @Override
    public void DataIsUpdated() {

    }

    @Override
    public void DataIsDeleted() {

    }
});
*/



       // binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // adaptor = new Adaptor(userArrayList);
       // binding.recyclerView.setAdapter(adaptor);





         /*
        Intent intent = getIntent();
             name = intent.getStringExtra("playerName");
             score = intent.getIntExtra("score",1);
             System.out.println(name);
             System.out.println(score);

             User user = new User(name,score);
             User user2 = new User(name,score);
             User user3 = new User(name,score);
             User user4 = new User(name,score);

             userArrayList.add(user);
             userArrayList.add(user2);
             userArrayList.add(user3);
             userArrayList.add(user4);

             adaptor.notifyDataSetChanged();

             System.out.println(userArrayList.get(0).name);

      if( name == null || score == null ){
               User user = new User("osman",15);
               userArrayList.add(user);
               adaptor.notifyDataSetChanged();
               Toast.makeText(this," aga bunun içi boş",Toast.LENGTH_LONG).show();

           }*/


