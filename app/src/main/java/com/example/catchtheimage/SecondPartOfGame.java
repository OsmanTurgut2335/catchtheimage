package com.example.catchtheimage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;

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
import android.widget.TextView;

import com.example.catchtheimage.databinding.ActivitySecondPartOfGameBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class SecondPartOfGame extends AppCompatActivity {
    private ImageView imageView1;
    private FrameLayout frameLayout;
    ActivitySecondPartOfGameBinding binding;
    private int score;

    TextView scoreText;

    Handler handler, handler2, handler3;
    Runnable runnable, runnable2, runnable3;
    MediaPlayer player, player2;
    ImageView[] arrayImageView;

    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondPartOfGameBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        scoreText = findViewById(R.id.scoreTextView);
        arrayImageView = new ImageView[]{
                binding.image1, binding.image3, binding.image4, binding.image5,
                binding.image6, binding.image7, binding.image8, binding.image9,
                binding.image10, binding.image12, binding.image13, binding.image14,
                binding.image15
        };

        final Handler handler = new Handler();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Welcome to part 2 of this game");
        builder.setTitle("Part two");
        final AlertDialog dialog = builder.create();

        firebaseDatabase = FirebaseDatabase.getInstance("https://muzundadi-default-rtdb.europe-west1.firebasedatabase.app/");
        Intent intent = getIntent();
        playerName = intent.getStringExtra("playerName");
        databaseReference = firebaseDatabase.getReference().child("users").child(playerName);


        dialog.show();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
                handleImages();
            }
        }, 1000);

        if (player == null) {
            player = MediaPlayer.create(SecondPartOfGame.this, R.raw.secondpartsong);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    player.start();
                }
            });
        }
        player.start();

        new CountDownTimer(50000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (score >= 20) {
                    final Handler handler = new Handler();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(SecondPartOfGame.this);
                    builder.setMessage("Your score is " + score);
                    builder.setTitle("End of Game");
                    stopPlayer();
                    stopEverything();
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SecondPartOfGame.this, LoginScreen.class);
                            startActivity(intent);
                        }
                    });

                    User user = new User(playerName, score);
                    user.setName(playerName);
                    user.setScore(score);

                    databaseReference.setValue(user);

                    final AlertDialog dialog = builder.create();
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
                builder.setMessage("Your score is " + score);
                builder.setTitle("End of Game");

                final AlertDialog dialog = builder.create();
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

        score = intent.getIntExtra("score", 5);

        imageView1 = findViewById(R.id.image1);

        frameLayout = findViewById(R.id.frameLayout);
        player2 = MediaPlayer.create(SecondPartOfGame.this, R.raw.click);
        player2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (player2 != null) {
                    player2.release();
                }
            }
        });

        player2.start();
    }

    public void onClicked(View view) {
        score++;

        if (score >= 20) {
            final Handler handler = new Handler();
            final AlertDialog.Builder builder = new AlertDialog.Builder(SecondPartOfGame.this);
            builder.setMessage("Your score is " + score);
            builder.setTitle("End of Game");
            stopPlayer();
            stopEverything();
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(SecondPartOfGame.this, LoginScreen.class);
                    startActivity(intent);
                }
            });

            User user = new User(playerName, score);
            user.setName(playerName);
            user.setScore(score);

            databaseReference.setValue(user);

            final AlertDialog dialog = builder.create();
            stopEverything();
            dialog.show();
        }

        scoreText.setText("Score " + score);
    }

    public void handleImages() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView image : arrayImageView) {
                    image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                int i = random.nextInt(arrayImageView.length);
                arrayImageView[i].setVisibility(View.VISIBLE);

                handler.postDelayed(this, 1000);
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

                        final float translationX = frameLayout.getWidth() * progress;

                        binding.image1.setTranslationX(translationX);
                        binding.image3.setTranslationX(translationX);
                        binding.image4.setTranslationX(translationX);
                        binding.image5.setTranslationX(translationX);
                        binding.image6.setTranslationX(translationX);
                        binding.image7.setTranslationX(translationX);
                        binding.image8.setTranslationX(translationX);
                    }
                });
                animator.start();
                handler2.postDelayed(this, 10000);
            }
        };

        handler2.post(runnable2);

        handler3 = new Handler(Looper.getMainLooper());
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

                        binding.image1.setTranslationY(translationY);
                        binding.image3.setTranslationY(translationY);
                        binding.image4.setTranslationY(translationY);
                        binding.image5.setTranslationY(translationY);
                        binding.image6.setTranslationY(-translationY);
                        binding.image7.setTranslationY(-translationY);
                        binding.image8.setTranslationY(-translationY);
                        binding.image9.setTranslationY(translationY);
                        binding.image10.setTranslationY(-translationY);
                        binding.image12.setTranslationY(-translationY);
                        binding.image13.setTranslationY(-translationY);
                        binding.image14.setTranslationY(translationY);
                        binding.image15.setTranslationY(translationY);

                        binding.image9.setTranslationX(-translationX);
                        binding.image10.setTranslationX(-translationX);
                        binding.image12.setTranslationX(-translationX);
                    }
                });
                animator.start();
            }
        }, 10000);
    }

    public void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public void stopEverything() {
        for (ImageView imageView : arrayImageView) {
            imageView.setClickable(false);
        }
    }
}
