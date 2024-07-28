package com.example.catchtheimage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.catchtheimage.databinding.RecyclerrowBinding;

import java.util.ArrayList;
import java.util.List;

public class Adaptor extends  RecyclerView.Adapter <Adaptor.UserHolder> {

    private ArrayList<User> userArrayList;
private Context mContext;
private Adaptor adaptor ;

/*
public void setConfig(RecyclerView recyclerView, Context context, List<User>users   , List<String> keys){
mContext = context;
adaptor = new Adaptor();

    }*/


    public Adaptor(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;

    }

    class UserHolder  extends  RecyclerView.ViewHolder{
        private String key;
        RecyclerrowBinding recyclerRowBinding;
        public UserHolder(@NonNull RecyclerrowBinding recyclerRowBinding) {

            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;

        }
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerrowBinding recyclerRowBinding = RecyclerrowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new UserHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
holder.recyclerRowBinding.recyclerRowNameText.setText(userArrayList.get(position).name);

holder.recyclerRowBinding.recyclerRowScoreText.setText( String.valueOf(userArrayList.get(position).score)  );



    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }



}