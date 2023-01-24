package com.rrj.projectexhibition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class AppointmentFinal extends AppCompatActivity {
    private TextView t9,t7,t22,t8,t6;
    DatabaseReference ref1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_final);
        t9 = findViewById(R.id.textView9);
        t7 = findViewById(R.id.textView7);
        t22 = findViewById(R.id.textView22);
        t8 = findViewById(R.id.textView8);
        t6=findViewById(R.id.textView6);
        t6.setText("1");
        t8.setText("C-501");


        Intent intent = getIntent();
        String n = intent.getStringExtra("Z");
        String c = intent.getStringExtra("C");  //doctor name
        String x = intent.getStringExtra("M");
//        ref1 = FirebaseDatabase.getInstance().getReference(x).child("DOCTOR");
//
//        ref1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot item:snapshot.getChildren())
//                    t8.setText(item.getKey()
//                            .toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference textRef = rootRef.child("app_billing").child("text");
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference(x).child("DOCTOR").child(c).child("CABIN");
        ref1.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    String text = snapshot.getValue(String.class);
                    t8.setText(text);
                } else {
                    Log.d("TAG", task.getException().getMessage()); //Don't ignore potential errors!
                }
            }
        });


        Date d = new Date();
        String s = d.toString();
        t9.setText(n);
        t7.setText(c);
        t22.setText(s);
    }
}