package com.rrj.projectexhibition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class AdminMain extends AppCompatActivity {
    private EditText e1,e2,e3,e4,e5,e6,e7,e8;
    public Button b3;
    int hour1;
    int minute1;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        e1 = findViewById(R.id.editTextTextPersonName2); // MAJOR AREA
        e2 = findViewById(R.id.editTextTextPersonName3); // DISEASE
        e3 = findViewById(R.id.editTextTextPersonName4); // DOCTOR
        e4 = findViewById(R.id.editTextTextPersonName5); // MEDICINE
        e5 = findViewById(R.id.editTextTextPersonName6); // DESIGNATION
        e6 = findViewById(R.id.editTextTextPersonName7); // FROM
        e7 = findViewById(R.id.editTextTextPersonName9); // TO
        e8 = findViewById(R.id.editTextTextPersonName10); // CABIN NO.
        b3 = findViewById(R.id.button3);
        auth = FirebaseAuth.getInstance();

        e6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AdminMain.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int fethour, int fetminute) {
                                hour1 = fethour;
                                minute1 = fetminute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, hour1, minute1);
                                e6.setText(DateFormat.format("hh:mm aa", calendar));
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(hour1, minute1);
                timePickerDialog.show();
            }
        });
        e7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AdminMain.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int fethour, int fetminute) {
                                hour1 = fethour;
                                minute1 = fetminute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, hour1, minute1);
                                e7.setText(DateFormat.format("hh:mm aa", calendar));
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(hour1, minute1);
                timePickerDialog.show();
            }
        });

    }


    public void test(View v){
        String s1 = e1.getText().toString().toUpperCase(Locale.ROOT);
        String s2 = e2.getText().toString().toUpperCase(Locale.ROOT);
        String s3 = e3.getText().toString().toUpperCase(Locale.ROOT);
        String s4 = e4.getText().toString().toUpperCase(Locale.ROOT);
        String s5 = e5.getText().toString().toUpperCase(Locale.ROOT);
        String s6 = e8.getText().toString().toUpperCase(Locale.ROOT);
        String s7 = e6.getText().toString();
        String s8 = e7.getText().toString();

        FirebaseDatabase.getInstance().getReference(s1).child("DISEASE").child(s2).setValue("");
        FirebaseDatabase.getInstance().getReference(s1).child("DOCTOR").child(s3).setValue("");
        FirebaseDatabase.getInstance().getReference(s1).child("MEDICINE").child(s4).setValue("");
        FirebaseDatabase.getInstance().getReference(s1).child("DOCTOR").child(s3).child("CABIN").setValue(s6);
        FirebaseDatabase.getInstance().getReference(s1).child("DOCTOR").child(s3).child("TIME SLOT").setValue(s7+"--"+s8);
        FirebaseDatabase.getInstance().getReference(s1).child("DOCTOR").child(s3).child("DESIGNATION").setValue(s5);

        Toast.makeText(this, "ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
    }


}