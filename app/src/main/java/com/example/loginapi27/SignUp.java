package com.example.loginapi27;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.loginapi27.databinding.ActivityLoginBinding;
import com.example.loginapi27.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth auth= FirebaseAuth.getInstance();
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(binding.edEmail.getText().toString().trim(),binding.edPhone.getText().toString().trim(),binding.edName.getText().toString(),binding.edPassword.getText().toString());
            }
        });




    }

    public void createAccount(String email,String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(SignUp.this,"Account Created",Toast.LENGTH_SHORT).show();
                String id = mRef.push().getKey();
                mRef.child("user").child(id).setValue(new User(id,binding.edName.getText().toString(),binding.edPhone.getText().toString(),binding.edEmail.getText().toString()));
                startActivity(new Intent(SignUp.this, Login.class));
                mRef.child("user").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            User user = dataSnapshot.getValue(User.class);


                            Toast.makeText(SignUp.this, user.getName().toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(SignUp.this, user.getPhone().toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(SignUp.this, user.getEmail().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                finish();
            }
        } ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(SignUp.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void validate(String email,String phone,String name, String password) {
        if(email.isEmpty())
            binding.edEmail.setError("Email is required");
        else if(password.isEmpty())
            binding.edPassword.setError("Password is required");
        else if(!email.contains("@"))
            binding.edEmail.setError("Invalid Email");
        else if(password.length()<6)
            binding.edPassword.setError("Password must be greater than 6 characters");
        else if (name.isEmpty()) {
            binding.edName.setError("Name is required");
        } else if (phone.isEmpty()) {
            binding.edPhone.setError("Phone is required");
        } else {

            createAccount(binding.edEmail.getText().toString(), binding.edPassword.getText().toString());
            binding.progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}