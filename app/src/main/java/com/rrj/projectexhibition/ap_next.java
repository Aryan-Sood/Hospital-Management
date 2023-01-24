package com.rrj.projectexhibition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ap_next extends AppCompatActivity {
    private TextView t1,t2;
    private Button b1,b2,b3;
    private Spinner s1;
    int hour1,minute1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ap_next);

        t1 = findViewById(R.id.textView24);
        t2 = findViewById(R.id.textView25);
        b1 = findViewById(R.id.button4);
        b2 = findViewById(R.id.button8);
        s1 = findViewById(R.id.spinner4);
        b3 = findViewById(R.id.button9);

        Intent intent1 = getIntent();
        String n = intent1.getStringExtra("R");
        String a = intent1.getStringExtra("A");
        String b = intent1.getStringExtra("B");
        String age = intent1.getStringExtra("AGE");
        String g = intent1.getStringExtra("G");
        String email = intent1.getStringExtra("EMAIL");
        String mob = intent1.getStringExtra("MOB");

        t1.setText(a);
        t2.setText(b);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ap_next.this,DoctorInfo.class);

                intent.putExtra("R",n);
                intent.putExtra("AGE",age);

                intent.putExtra("A",a);
                intent.putExtra("B",b);
                intent.putExtra("G",g);

                intent.putExtra("D",s1.getSelectedItem().toString());
                intent.putExtra("EMAIL",email);
                intent.putExtra("MOB",mob);

                startActivity(intent);
            }
        });


    }
    public void test90(View v){
        String s = t1.getText().toString();

        ArrayList<String> list_2 = new ArrayList<String>();
        ArrayAdapter<String> adapter_2 = new ArrayAdapter<>(ap_next.this, android.R.layout.simple_spinner_dropdown_item, list_2);//Connecting spinner to the arraylist of major area
        s1.setAdapter(adapter_2);

        list_2.clear();
        list_2.add("CHOOSE DOCTOR");
        fetchdata_2(s,list_2,adapter_2);
        Toast.makeText(ap_next.this, "SUCCESS", Toast.LENGTH_SHORT).show();
    }

    public void fetchdata_2(String a,ArrayList<String> l,ArrayAdapter<String> ada){
        DatabaseReference ebref = FirebaseDatabase.getInstance().getReference(a).child("DOCTOR");
        ValueEventListener listener_2 = ebref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mydata : snapshot.getChildren()){
                    l.add(mydata.getKey().toString());
                }
                ada.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void totime(View v) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                ap_next.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int fethour, int fetminute) {
                        hour1 = fethour;
                        minute1 = fetminute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0, 0, 0, hour1, minute1);
                        b3.setText(DateFormat.format("hh:mm aa", calendar));
                    }
                }, 12, 0, false
        );
        timePickerDialog.updateTime(hour1, minute1);
        timePickerDialog.show();
    }

}