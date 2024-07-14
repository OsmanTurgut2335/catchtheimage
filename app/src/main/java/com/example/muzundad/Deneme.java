/*package com.example.muzundad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.muzundad.databinding.ActivityScoreboardBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Deneme extends AppCompatActivity {
    public ArrayList<User> userArrayList = new ArrayList<>();


    FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;


    public interface  DataStatus{
        void DataIsLoaded(List<User> users, List<String> keys );
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }

public Deneme(){
    firebaseDatabase=FirebaseDatabase.getInstance("https://muzundadi-default-rtdb.europe-west1.firebasedatabase.app/");
    databaseReference= firebaseDatabase.getReference("users");
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deneme);



    }

public void readUsers(final DataStatus dataStatus){
    databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(userArrayList.size()>=1){  userArrayList.clear();   }

            List<String> keys= new ArrayList<>();
            for(DataSnapshot keynode :  snapshot.getChildren()){
                keys.add(keynode.getKey());
                User user =  keynode.getValue(User.class);
             //   System.out.println(user.name);
             //   System.out.println("sa kanka");

                userArrayList.add(user);

            }
            dataStatus.DataIsLoaded(userArrayList,keys);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}




}*/