package com.microsoft.projectoxford.face.samples.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.microsoft.projectoxford.face.samples.R;
import com.microsoft.projectoxford.face.samples.persongroupmanagement.PersonGroupListActivity;


public class InformationActivity extends AppCompatActivity {

    EditText name, age, occupation, caretaker_name, caretaker_contact, emergency_contact,area;
    Button next;
    String email;
    private DatabaseReference mReference;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        next = (Button) findViewById(R.id.next);

        email = getIntent().getStringExtra("email");

        FirebaseApp.initializeApp(this);
        mReference = FirebaseDatabase.getInstance().getReference("Patient");
        // mReference = mDatabase.getReference("Patient");


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        //Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();
    }

    private void registerUser() {

        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        occupation = (EditText) findViewById(R.id.occupation);
        caretaker_name = (EditText) findViewById(R.id.caretaker_name);
        caretaker_contact = (EditText) findViewById(R.id.caretaker_contact);
        emergency_contact = (EditText) findViewById(R.id.emergency_contact);
        area = (EditText) findViewById(R.id.area);

        final String patientName = name.getText().toString().trim();
        final String patientAge = age.getText().toString().trim();
        final String patientOccupation = occupation.getText().toString().trim();
        final String patientCaretaker_name = caretaker_name.getText().toString().trim();
        final String patientCaretaker_contact = caretaker_contact.getText().toString().trim();
        final String patientEmergency_contact = emergency_contact.getText().toString().trim();
        final String patientArea = area.getText().toString().trim();

        if(TextUtils.isEmpty(patientName)){
            Toast.makeText(getApplicationContext(),"Name is required", Toast.LENGTH_SHORT).show();
        }
        else{
            String id = mReference.push().getKey();
            Patient patient = new Patient(email, patientName, patientAge, patientOccupation, patientCaretaker_name, patientCaretaker_contact, patientEmergency_contact);
            mReference.child(id).setValue(patient);
            finish();
            Intent intent = new Intent(getApplicationContext(), PersonGroupListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }

    }
}
