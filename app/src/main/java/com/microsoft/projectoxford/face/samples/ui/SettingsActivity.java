package com.microsoft.projectoxford.face.samples.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.microsoft.projectoxford.face.samples.R;
import com.microsoft.projectoxford.face.samples.persongroupmanagement.PersonGroupListActivity;

public class SettingsActivity extends Fragment {
    private FirebaseAuth firebaseAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view4 = inflater.inflate(R.layout.settings_layout, container, false);

        Button manage_events = (Button) view4.findViewById(R.id.nav_manage_events);
        Button manage_people = (Button) view4.findViewById(R.id.nav_manage_persons_groups);
        Button logout = (Button) view4.findViewById(R.id.nav_logout);
//
//        FirebaseApp.initializeApp(getActivity());
//        firebaseAuth.getInstance();
        //FirebaseUser user = firebaseAuth.getCurrentUser();


        manage_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Event.class);
                startActivity(intent);
            }
        });

        manage_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonGroupListActivity.class);
                startActivity(intent);
            }
        });

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                firebaseAuth.signOut();
//
//                Intent intent = new Intent(getActivity(),LoginActivity.class);
//                startActivity(intent);
//            }
//        });

        return view4;
    }

}
