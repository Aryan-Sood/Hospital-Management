package com.rrj.projectexhibition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class DoctorInfo extends AppCompatActivity {
    private Button b1,b2;
    private TextView t3,t10,t11,t,t2,t5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        t3 = findViewById(R.id.textView3);
        t10 = findViewById(R.id.textView10);
        t11 = findViewById(R.id.textView11);
        t = findViewById(R.id.textView);
        t2 = findViewById(R.id.textView2);
        t5 = findViewById(R.id.textView5);
        b1 = findViewById(R.id.button6);


        Intent intent = getIntent();
        String n = intent.getStringExtra("R");
        String a = intent.getStringExtra("A");
        String b = intent.getStringExtra("B");
        String age = intent.getStringExtra("AGE");
        String g = intent.getStringExtra("G");
        String doc = intent.getStringExtra("D");
        String email = intent.getStringExtra("EMAIL");
        String mob = intent.getStringExtra("MOB");

        t3.setText(n+"   "+g+"  "+age);
        t10.setText(a);
        t11.setText(b);
        t2.setText(doc);

        String d = intent.getStringExtra("D");
        String m = intent.getStringExtra("M");//major
        String time = intent.getStringExtra("T");

        t.setText(m);
        t2.setText(d);
        t5.setText(time);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test1();
                Email(email);
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
                        sendSMS();
                    else{
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                        sendSMS();
                    }
                }
                Toast.makeText(DoctorInfo.this, "APPOINTMENT CONFIRMED", Toast.LENGTH_SHORT).show();
                Toast.makeText(DoctorInfo.this, "CONFIRMATION EMAIL IS SENT", Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference ebref = FirebaseDatabase.getInstance().getReference(a).child("MEDICINE");
        ValueEventListener listener_2 = ebref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mydata1 : snapshot.getChildren()) {
                    t.setText(mydata1.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference ref6 = FirebaseDatabase.getInstance().getReference(a).child("DOCTOR").child(doc);
        ValueEventListener listen_2 = ref6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mydata : snapshot.getChildren()) {
                    t5.setText(mydata.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show();
    }

    public void test1(){

        String a = t.getText().toString();//major
        String d = t2.getText().toString();  //doctor name
        String n = t3.getText().toString();  //name
        Intent intent1 = new Intent(DoctorInfo.this,AppointmentFinal.class);
        intent1.putExtra("Z",n);  //
        intent1.putExtra("C",d);  //
        intent1.putExtra("M",a);   //major area
        startActivity(intent1);
    }
    public void test2(View v){
        Intent intent2 = new Intent(this,MainActivity.class);
        startActivity(intent2);
    }
    public void Email(String email){
        try {
            String stringSenderEmail = "yourmail@gmail.com";
            String stringReceiverEmail = email;
            String stringPasswordSenderEmail = "password";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Subject: Android App email");
                    mimeMessage.setText("Hello Sir, \n\nYour appointment is confirmed. \n\n Best Wishes\nXYZ HOSPITAL");
//            mimeMessage.setText(e2.getText().toString());
//            Toast.makeText(getApplicationContext(), "EMAIL SENT SUCCESSFULLY", Toast.LENGTH_LONG).show();


            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
    private void sendSMS(){
        Intent intent = getIntent();
        String phoneNo = intent.getStringExtra("MOB");
        String SMS = "HELLO";
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo,null,SMS,null,null);
            Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Message Not Sent", Toast.LENGTH_SHORT).show();
        }
    }
}