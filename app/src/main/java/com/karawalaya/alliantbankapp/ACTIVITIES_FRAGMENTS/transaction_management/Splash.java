package com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.transaction_management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

//import com.karawalaya.alliantbankapp.DAO_SERVICE.CommonDBHelper;
import com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.user_management.Login;
import com.karawalaya.alliantbankapp.DAO_SERVICE.DBHelper;
import com.karawalaya.alliantbankapp.R;

public class Splash extends AppCompatActivity {
//    private static int SPLASH_TIMEOUT = 5000;
    ImageView splashImage;
    TextView splashText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Initializing the database
        DBHelper db = new DBHelper(this);

        splashImage = (ImageView) findViewById(R.id.splashImage);
        splashText = (TextView) findViewById(R.id.splashText);

        Animation animation = AnimationUtils.loadAnimation(Splash.this, R.anim.splash_animation);
        splashImage.startAnimation(animation);
        splashText.startAnimation(animation);

        final Intent intent = new Intent(this, Login.class);
        Thread timer = new Thread() {
            @Override
            public void run() {
                try{
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        };

        timer.start();
    }
}
