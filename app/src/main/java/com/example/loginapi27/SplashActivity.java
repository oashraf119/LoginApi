package com.example.loginapi27;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.loginapi27.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    FirebaseAuth auth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(auth.getCurrentUser()!=null){
                    startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this,Login.class));
                    finish();
                }
            }
        },3000);


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}