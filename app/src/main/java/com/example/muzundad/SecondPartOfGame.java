package com.example.muzundad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.muzundad.databinding.ActivitySecondPartOfGameBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class SecondPartOfGame extends AppCompatActivity {
    private  ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8;
   private FrameLayout frameLayout;
  ActivitySecondPartOfGameBinding binding;
  private int score;
  private int random;
  Handler handler,handler2,handler3;
  Runnable runnable,runnable2,runnable3;
MediaPlayer player;
FirebaseDatabase firebaseDatabase;
private DatabaseReference databaseReference;
public String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivitySecondPartOfGameBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        final Handler handler = new Handler();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Wellcome to part 2 of this game");
        builder.setTitle("Muzun dadı");
        final AlertDialog dialog= builder.create();

        firebaseDatabase=FirebaseDatabase.getInstance("https://muzundadi-default-rtdb.europe-west1.firebasedatabase.app/");
        Intent intent = getIntent();
        playerName =intent.getStringExtra("playerName");
        databaseReference= firebaseDatabase.getReference().child("users").child(playerName);

        System.out.println("PLAYER NAMEEEEE "+playerName);
        dialog.show();
        handler.postDelayed(new Runnable() {
            public void run() {

                dialog.dismiss();
                handleImages();
            }
        }, 1000);

        if(player == null){
            player = MediaPlayer.create(SecondPartOfGame.this,R.raw.bettersound);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    player.start();
                }
            });
        }
        player.start();

        new CountDownTimer(50000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

                if(score >= 20){
                    final Handler handler = new Handler();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(SecondPartOfGame.this);
                    builder.setMessage("Your score is "+ score);
                    builder.setTitle("End of Game");
                    builder.setPositiveButton("eyvallah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SecondPartOfGame.this,LoginScreen.class);
                            startActivity(intent);

                        }
                    });

                    User user = new User(playerName, score);
                    user.setName(playerName);
                    user.setScore(score);


                    databaseReference.setValue(user);

                    final AlertDialog dialog= builder.create();
                    stopEverything();
                    dialog.show();



                }

            }

            @Override
            public void onFinish() {
                User user = new User(playerName, score);
                user.setName(playerName);
                user.setScore(score);
                databaseReference.setValue(user);
                stopPlayer();
                stopEverything();
                final Handler handler = new Handler();
                final AlertDialog.Builder builder = new AlertDialog.Builder(SecondPartOfGame.this);
                builder.setMessage("Your score is "+ score);
                builder.setTitle("End of Game");

                final AlertDialog dialog= builder.create();

                dialog.show();


                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                System.exit(1);
                                // your code here
                            }
                        },
                        5000
                );

            }
        }.start();





        score = intent.getIntExtra("score",5);

        imageView1 =  findViewById(R.id.muf1);

     //   imageView3 = (ImageView) findViewById(R.id.muf3);
       // imageView4 = (ImageView) findViewById(R.id.muf4);
        //imageView5 = (ImageView) findViewById(R.id.muf5);
       // imageView6 = (ImageView) findViewById(R.id.muf6);
        //imageView7 = (ImageView) findViewById(R.id.muf7);
        //imageView8 = (ImageView) findViewById(R.id.muf8);


         frameLayout = (FrameLayout)  findViewById(R.id.frameLayout);


    }


    public void onClicked(View view){

        score ++;
        if(score >= 20){
            final Handler handler = new Handler();
            final AlertDialog.Builder builder = new AlertDialog.Builder(SecondPartOfGame.this);
            builder.setMessage("Your score is "+ score);
            builder.setTitle("End of Game");
            builder.setPositiveButton("eyvallah", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
              Intent intent = new Intent(SecondPartOfGame.this,LoginScreen.class);
              startActivity(intent);

                }
            });

            User user = new User(playerName, score);
            user.setName(playerName);
            user.setScore(score);


            databaseReference.setValue(user);

            final AlertDialog dialog= builder.create();
            stopEverything();
            dialog.show();



/*
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            System.exit(1);
                            // your code here
                        }
                    },
                    5000
            );*/

        }
        System.out.println(score);

    }

    public void handleImages(){
        ImageView[] arrayImageView  = new ImageView[]{binding.muf1,binding.muf3,binding.muf4,binding.muf5,
                binding.muf6,binding.muf7,binding.muf8,binding.muf9,binding.muf10,binding.muf11,binding.muf12,
                binding.muf13,binding.muf14,binding.muf15}  ;

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

              /*  for (ImageView image : arrayImageView) {
                    image.setVisibility(View.INVISIBLE);
                }*/

                Random random = new Random();
                int i = random.nextInt(14);
                arrayImageView[i].setVisibility(View.VISIBLE);

                handler.postDelayed(this,1000);

            }
        };

        handler.post(runnable);



        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1500L);

        handler2 = new Handler();
        runnable2 = new Runnable() {
            @Override
            public void run() {
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {

                        final float progress = (float) animation.getAnimatedValue();
                        final float width = imageView1.getWidth();
                        final float translationX = frameLayout.getWidth() * progress;

                        binding.muf1.setTranslationX(translationX);
                        binding.muf3.setTranslationX(translationX);
                        binding.muf4.setTranslationX(translationX);
                        binding.muf5.setTranslationX(translationX);
                        binding.muf6.setTranslationX(translationX);
                        binding.muf7.setTranslationX(translationX);
                        binding.muf8.setTranslationX(translationX);



                        //  backgroundTwo.setTranslationX(translationX - width);
                    }
                });
                animator.start();
                handler2.postDelayed(this,10000);
            }
        };

        handler2.post(runnable2);

     handler3=new Handler(Looper.getMainLooper());

handler3.postDelayed(new Runnable() {
    @Override
    public void run() {
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                final float progress = (float) animation.getAnimatedValue();
                final float width = imageView1.getWidth();
                final float translationY = frameLayout.getWidth() * progress;
                final float translationX = frameLayout.getWidth() * progress;


                binding.muf1.setTranslationY(translationY);
                binding.muf3.setTranslationY(translationY);
                binding.muf4.setTranslationY(translationY);
                binding.muf5.setTranslationY(translationY);
                binding.muf6.setTranslationY(-translationY);
                binding.muf7.setTranslationY(-translationY);
                binding.muf8.setTranslationY(-translationY);
                binding.muf9.setTranslationY(translationY);
                binding.muf10.setTranslationY(-translationY);
                binding.muf11.setTranslationY(-translationY);
                binding.muf9.setTranslationX(-translationX);
                binding.muf10.setTranslationX(-translationX);
                binding.muf11.setTranslationX(-translationX);
                binding.muf12.setTranslationY(-translationY);
                binding.muf13.setTranslationY(-translationY);
                binding.muf14.setTranslationY(translationY);
                binding.muf15.setTranslationY(translationY);


                //  backgroundTwo.setTranslationX(translationX - width);
            }
        });
        animator.start();


    }
},10000);






    }
    public void stopPlayer(){
        if(player != null){
            player.release();
            player=null;
        }
    }
    public void stopEverything(){
        binding.muf1.setTranslationY(0);
        binding.muf3.setTranslationY(0);
        binding.muf4.setTranslationY(0);
        binding.muf5.setTranslationY(0);
        binding.muf6.setTranslationY(0);
        binding.muf7.setTranslationY(0);
        binding.muf8.setTranslationY(0);

        binding.muf1.setTranslationX(0);
        binding.muf3.setTranslationX(0);
        binding.muf4.setTranslationX(0);
        binding.muf5.setTranslationX(0);
        binding.muf6.setTranslationX(0);
        binding.muf7.setTranslationX(0);
        binding.muf8.setTranslationX(0);


    }



}