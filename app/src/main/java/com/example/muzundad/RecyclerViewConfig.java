/*package com.example.muzundad;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muzundad.databinding.RecyclerrowBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class RecyclerViewConfig {
    private Context context;
    private BooksAdapter booksAdapter;
    View headerView ;

    RecyclerrowBinding recyclerrowBinding;
    public void setConfig(RecyclerView recyclerView,Context context,List <User>users  , List <String> keys  ){
this.context = context;

    booksAdapter = new BooksAdapter(users,keys);
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
recyclerView.setAdapter(booksAdapter);

}

class UserItemView extends RecyclerView.ViewHolder {


    String key;

    private TextView score ;
    private TextView name;
    public UserItemView(ViewGroup parent) {

       super(LayoutInflater.from(context).inflate(R.layout.recyclerrow,parent,false));



        recyclerrowBinding = RecyclerrowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);


      score =  parent.findViewById(R.id.recyclerRowScoreText);

       name =parent.findViewById(R.id.recyclerRowNameText);




    }


    public void Bind(User user, String key) {

        System.out.println(score.getText().toString());
        System.out.println("sa beyler");

        score.setText(String.valueOf(user.getScore()));

        name.setText(user.getName());
        this.key = key;

    }
}
class BooksAdapter extends  RecyclerView.Adapter<UserItemView>{

    private List<User> userList;
    private List<String> mKeys;

    public BooksAdapter(List <User> userList, List<String> mKeys){
        this.mKeys = mKeys;
        this.userList = userList;

    }


    @NonNull
    @Override
    public UserItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserItemView(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull UserItemView holder, int position) {
holder.Bind(userList.get(position), mKeys.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}


}*/
