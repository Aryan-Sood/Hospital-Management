package com.rrj.projectexhibition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
    }

    public void openActivity(View v){
        Toast.makeText(this, "PUBLIC PAGE", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,Appointment.class);
        startActivity(intent);
    }
    public void openActivity2(View v){
        Toast.makeText(this, "ADMIN PAGE", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,Admin.class);
        startActivity(intent);
    }
}