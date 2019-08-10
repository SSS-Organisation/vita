package com.microsoft.projectoxford.face.samples.ui;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microsoft.projectoxford.face.samples.R;
import com.microsoft.projectoxford.face.samples.persongroupmanagement.PersonGroupListActivity;

import java.util.ArrayList;

//        View v = getLayoutInflater().inflate(R.layout.nav_header_navigation,null);
////        View v = navigationView.getHeaderView(0);
//        TextView receive1 = (TextView) v.findViewById(R.id.myName);
//        TextView receive2 = (TextView) v.findViewById(R.id. myAge);
//        receive2.setText(getIntent().getStringExtra("Age"));
//        TextView receive3 = (TextView) v.findViewById(R.id.myArea);
//        receive3.setText(getIntent().getStringExtra("Area"));
//        TextView receive4 = (TextView) v.findViewById(R.id.myOccupation);
//        receive4.setText(getIntent().getStringExtra("Occupation"));
//
//        SharedPreferences prefs = getSharedPreferences("my_prefs_names",MODE_PRIVATE);
//        String restoredText = prefs.getString("text",null);
//        if (restoredText != null) {
//            String name = prefs.getString("name", null);
//            String age = prefs.getString("age", null);
//            String area = prefs.getString("area", null);
//            String occupation = prefs.getString("occupation", null);
//        }
//
//        receive1.setText(prefs.getString("name", null));
//    }
//@Override
//public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//        }

public class TabActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentManager FM;
    FragmentTransaction FT;
    private TabLayout tablayout;
    private AppBarLayout appBarLayout;
    //private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    ArrayList<String> userDataList;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mReference;
    TextView navUsername, navEmail, navAge, navOccupation, navCaretaker, navCaretaker_contact, navEmergency_contact;
    Menu menu;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navView);
        menu = navigationView.getMenu();

        FM = getSupportFragmentManager();
        FT = FM.beginTransaction();
        FT.replace(R.id.containerView, new TabFragment()).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();

                if (item.getItemId() == R.id.nav_home) {
                    FragmentTransaction fragmentTransaction = FM.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
                }

                if (item.getItemId() == R.id.nav_timeline) {
                    FragmentTransaction fragmentTransaction = FM.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new TimelineActivity()).commit();
                }

                if (item.getItemId() == R.id.nav_settings) {
                    FragmentTransaction fragmentTransaction1 = FM.beginTransaction();
                    fragmentTransaction1.replace(R.id.containerView, new SettingsActivity()).commit();

                }

//                if (item.getItemId() == R.id.nav_manage_persons_groups) {
//                    startActivity(new Intent(getApplicationContext(), PersonGroupListActivity.class));
//                }
//
//                if (item.getItemId() == R.id.nav_manage_events) {
//                    startActivity(new Intent(getApplicationContext(), Event.class));
//                }
//
                if (item.getItemId() == R.id.nav_logout) {
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                return false;
            }
        });

        View headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.name);
        navEmail = (TextView) headerView.findViewById(R.id.email);
        navAge = (TextView) headerView.findViewById(R.id.age);
        navOccupation = (TextView) headerView.findViewById(R.id.occupation);
        navCaretaker = (TextView) headerView.findViewById(R.id.caretaker_name);
        navCaretaker_contact = (TextView) headerView.findViewById(R.id.caretaker_contact);
        navEmergency_contact = (TextView) headerView.findViewById(R.id.emergency_contact);

        FirebaseApp.initializeApp(this);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid();
        //Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_LONG).show();
        mReference = FirebaseDatabase.getInstance().getReference("Patient");


        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        navEmail.setText(user.getEmail());

        mReference.orderByChild("email").equalTo(user.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userDataList = new ArrayList<String>();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Patient userDetails = ds.getValue(Patient.class);
                    userDataList.add(userDetails.getPatientName());
                    userDataList.add(userDetails.getPatientAge());
                    userDataList.add(userDetails.getPatientOccupation());
                    userDataList.add(userDetails.getPatientCaretaker_name());
                    userDataList.add(userDetails.getPatientCaretaker_contact());
                    userDataList.add(userDetails.getPatientEmergency_contact());
                    //Log.i("name", userDataList.get(1));
                    navUsername.setText(userDataList.get(0));
                    navAge.setText("Age: "+userDataList.get(1)+" years");
                    navOccupation.setText("Occupation: "+userDataList.get(2));
                    navCaretaker.setText("Caretaker: "+userDataList.get(3));
                    navCaretaker_contact.setText("Caretaker contact: "+userDataList.get(4));
                    navEmergency_contact.setText("Emergency contact: "+userDataList.get(5));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }


    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu){
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userDataList = new ArrayList<String>();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Patient userDetails = ds.getValue(Patient.class);
                    userDataList.add(userDetails.getPatientName());
                    userDataList.add(userDetails.getPatientAge());
                    userDataList.add(userDetails.getPatientOccupation());
                    userDataList.add(userDetails.getPatientCaretaker_name());
                    userDataList.add(userDetails.getPatientCaretaker_contact());
                    userDataList.add(userDetails.getPatientEmergency_contact());
                    //Log.i("name", userDataList.get(1));
                    navUsername.setText(userDataList.get(0));
                    navAge.setText("Age: "+userDataList.get(1)+" years");
                    navOccupation.setText("Occupation: "+userDataList.get(2));
                    navCaretaker.setText("Caretaker: "+userDataList.get(3));
                    navCaretaker_contact.setText("Caretaker contact: "+userDataList.get(4));
                    navEmergency_contact.setText("Emergency contact: "+userDataList.get(5));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onPrepareOptionsMenu(menu);
        return true;
    }
    */

}

