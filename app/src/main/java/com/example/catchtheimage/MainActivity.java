package com.example.catchtheimage;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.catchtheimage.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.Random;






public class MainActivity extends AppCompatActivity {

    MediaPlayer player,player2;
    TextView scoreText;
    TextView timeText;
    ActivityMainBinding binding;
    int score;
    ImageView imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9;

    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;


    FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;
    // Adaptor adaptor;
    String playerName;

    String muz = "muz";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        firebaseDatabase=FirebaseDatabase.getInstance("https://muzundadi-default-rtdb.europe-west1.firebasedatabase.app/");


        Intent intent = getIntent();
        playerName = intent.getStringExtra("username");





        if(player == null){
            player = MediaPlayer.create(MainActivity.this,R.raw.backgroundmusic);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        player.start();
        //initialize

        timeText = (TextView) findViewById(R.id.timeText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);

        imageArray = new ImageView[] {imageView, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9};

        hideImages();
        binding.usernameTextView.setText("Username: "+playerName);

        score = 0;

        new CountDownTimer(20000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {


                timeText.setText("Time: " + millisUntilFinished/1000);

                if(score >=10 ){
                    cancel();
                }

            }

            @Override
            public void onFinish() {

                stopPlayer();

                timeText.setText("Time Off");
                handler.removeCallbacks(runnable);
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }


                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                alert.setTitle("First Part Is Over");
                alert.setMessage("Start Again ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //restart

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Toast.makeText(MainActivity.this, "Bende döl yok", Toast.LENGTH_SHORT).show();

                        if(!isNetworkAvailable()){
                            Toast.makeText(MainActivity.this,"your connection is off ",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(MainActivity.this,LoginScreen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }

                        //  binding.layout.setBackgroundResource(R.drawable.muz);
                        else{
                            // readData();
                            // writeNewUser();
                            Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish(); // Optionally, call finish() if you want to finish the current activity

                        }

                    }
                });

                alert.show();

            }
        }.start();


    }
    public void stopPlayer(){
        if(player != null){
            player.release();
            player=null;
        }
    }

    public void readData(){

        Intent intent = new Intent(MainActivity.this,Scoreboard.class);
        startActivity(intent);

    }






    public void increaseScore (View view) {


        player2 = MediaPlayer.create(MainActivity.this,R.raw.click);
        player2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(player2 != null){
                    player2.release();

                }
            }
        });

        player2.start();
        score++;

        if(score >= 10){
            stopPlayer();
            for (ImageView image : imageArray) {
                image.setVisibility(View.INVISIBLE);
            }

            final Handler handler = new Handler();
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("First part of this game is over ");
            builder.setTitle("Catch The Image");
            final AlertDialog dialog= builder.create();
            dialog.show();



            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this,SecondPartOfGame.class);
                            intent.putExtra("score",score);
                            intent.putExtra("playerName",playerName);
                            dialog.dismiss();
                            startActivity(intent);
                            finish();
                            // your code here
                        }
                    },
                    3000
            );

        }
        scoreText.setText("Score: " +  score);



    }

    public void hideImages() {

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {

                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                int i = random.nextInt(9);
                imageArray[i].setVisibility(View.VISIBLE);

                handler.postDelayed(this,1500);

            }
        };


        handler.post(runnable);


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}