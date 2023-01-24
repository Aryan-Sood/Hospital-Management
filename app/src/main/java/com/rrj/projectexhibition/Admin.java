package com.rrj.projectexhibition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Admin extends AppCompatActivity {
    private EditText n,p;
    FirebaseAuth auth;
    public Button but3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        n = findViewById(R.id.editTextTextPersonName);
        p = findViewById(R.id.editTextTextPassword);
        but3 = findViewById(R.id.button3);
        auth = FirebaseAuth.getInstance();


        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = n.getText().toString();
                String password = p.getText().toString();
                login(email,password);
            }
        });
    }


    public void test(View v){
        String s1 = n.getText().toString();
        String s2 = p.getText().toString();

        if(s1.equals("") && s2.equals("")){
            Toast.makeText(this, "WELCOME", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,AdminMain.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "INVALID USERNAME OR PASSWORD", Toast.LENGTH_SHORT).show();
        }

    }

    public void login(String email, String password){
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Admin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(Admin.this,AdminMain.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Admin.this, "Please Register", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
