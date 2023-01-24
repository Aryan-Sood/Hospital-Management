package com.rrj.projectexhibition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Appointment extends AppCompatActivity {
    private Spinner s1,s2,s3;
    private EditText e1,e2,e3,e4;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
        }

        s1 = findViewById(R.id.spinner);
        s2 = findViewById(R.id.spinner2);
        s3 = findViewById(R.id.spinner3);

        e1 = findViewById(R.id.editTextTextPersonName5);
        e2 = findViewById(R.id.editTextNumber);
        e3 = findViewById(R.id.editTextTextPersonName8);
        e4 = findViewById(R.id.editTextNumber2);


        ArrayList arr = new ArrayList();
        ArrayAdapter ada = new ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,arr);
        s1.setAdapter(ada);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                arr.clear();
                arr.add("CHOOSE INFECTIOUS AREA");
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    arr.add(ds.getKey().toString());
                }
                ada.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
            }
        });

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = s1.getSelectedItem().toString();

                ArrayList<String> list_2 = new ArrayList<String>();
                ArrayAdapter<String> adapter_2 = new ArrayAdapter<>(Appointment.this, android.R.layout.simple_spinner_dropdown_item, list_2);//Connecting spinner to the arraylist of major area
                s2.setAdapter(adapter_2);

                list_2.clear();
                list_2.add("CHOOSE DISEASE");
                fetchdata_2(s,list_2,adapter_2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void testz(View v){
        String n1 = e1.getText().toString(); // NAME
        String n2 = e2.getText().toString(); // AGE

        String t1 = s1.getSelectedItem().toString(); // MAJOR AREA
        String t2 = s2.getSelectedItem().toString(); // DISEASE
        String t3 = s3.getSelectedItem().toString(); // GENDER

        String email = e3.getText().toString();
        String mob = e4.getText().toString();

        if(n1.isEmpty()==false && n2.isEmpty()==false){
            Intent intent = new Intent(this,ap_next.class);

            intent.putExtra("R",n1);
            intent.putExtra("AGE",n2);

            intent.putExtra("A",t1);
            intent.putExtra("B",t2);
            intent.putExtra("G",t3);

            intent.putExtra("EMAIL",email);
            intent.putExtra("MOB",mob);

            startActivity(intent);
            Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "ENTER MANDATORY FIELDS", Toast.LENGTH_SHORT).show();
        }
    }


    public void fetchdata_2(String a,ArrayList<String> l,ArrayAdapter<String> ada){
        DatabaseReference ebref = FirebaseDatabase.getInstance().getReference(a).child("DISEASE");
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

}























//        if(t1.equals("HEART") && t2.equals("CHOOSE DISEASE")==false){
//            intent.putExtra("D","DR. VIJAY KHAN");
//            intent.putExtra("M","EZETIMIBE ROSUVASTATIN");
//            intent.putExtra("T","2:00 PM to 5:00 PM");
//            startActivity(intent);
//        }
//        else if(t1.equals("BRAIN") && t2.equals("CHOOSE DISEASE")==false){
//            intent.putExtra("D","DR. RAHUL JAIN");
//            intent.putExtra("M","FLUNARIZINE");
//            intent.putExtra("T","4:00 PM to 7:00 PM");
//            startActivity(intent);
//        }
//        else if(t1.equals("LUNGS") && t2.equals("CHOOSE DISEASE")==false){
//            intent.putExtra("D","DR. MAQSOOD IQBAL");
//            intent.putExtra("M","INDACATAROL");
//            intent.putExtra("T","1:00 PM to 3:00 PM");
//            startActivity(intent);
//        }
//        else if(t1.equals("NOSE EAR THROAT") && t2.equals("CHOOSE DISEASE")==false){
//            intent.putExtra("D","DR. AKSHAY SINHA");
//            intent.putExtra("M","AZITHROMYCIN");
//            intent.putExtra("T","6:00 PM to 7:00 PM");
//            startActivity(intent);
//        }
//        else if(t1.equals("STOMACH") && t2.equals("CHOOSE DISEASE")==false){
//            intent.putExtra("D","DR. RASHMI SODHIYA");
//            intent.putExtra("M","DOXYCYCLINE");
//            intent.putExtra("T","5:00 PM to 8:00 PM");
//            startActivity(intent);
//        }
//        else if(t1.equals("BONE") && t2.equals("CHOOSE DISEASE")==false){
//            intent.putExtra("D","DR. ROUNAK SINGH");
//            intent.putExtra("M","ALANDRONATE");
//            intent.putExtra("T","8:00 PM to 10:00 PM");
//            startActivity(intent);
//        }
//        else if(t1.equals("PSYCHIATRIST") && t2.equals("CHOOSE DISEASE")==false){
//            intent.putExtra("D","DR. SAFIQUE KHAN");
//            intent.putExtra("M","CITALOPRAM");
//            intent.putExtra("T","5:00 PM to 7:00 PM");
//            startActivity(intent);
//        }
//        else if(t2.equals("CHOOSE DISEASE") && t1.equals("CHOOSE MAJOR AREA")){
//            Toast.makeText(this, "PLEASE FILL CHOICES", Toast.LENGTH_SHORT).show();
//        }