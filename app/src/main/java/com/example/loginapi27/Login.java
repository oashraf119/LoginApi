package com.example.loginapi27;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.loginapi27.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth auth= FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                validate(binding.edEmail.getText().toString(), binding.edPassword.getText().toString());
                binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Login.this, SignUp.class));
                    }
                });


            }
        });




    }

    public void validate(String email, String password) {
        if(email.isEmpty())
            binding.edEmail.setError("Email is required");
        else if(password.isEmpty())
            binding.edPassword.setError("Password is required");
        else if(!email.contains("@"))
            binding.edEmail.setError("Invalid Email");
        else if(password.length()<6)
            binding.edPassword.setError("Password must be greater than 6 characters");
        else {

            startLogin(binding.edEmail.getText().toString().trim(), binding.edPassword.getText().toString());
            binding.progressBar.setVisibility(View.VISIBLE);
        }
    }
    public void startLogin(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                binding.progressBar.setVisibility(View.GONE);
                startActivity(new Intent(Login.this, WelcomeActivity.class));
                finish();
                Toast.makeText(Login.this,"Login Successfully",Toast.LENGTH_SHORT).show();            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}