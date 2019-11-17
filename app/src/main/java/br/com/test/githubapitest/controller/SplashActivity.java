package br.com.test.githubapitest.controller;

import androidx.appcompat.app.AppCompatActivity;
import br.com.test.githubapitest.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, ScanActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, SPLASH_DELAY
        );
    }
}
