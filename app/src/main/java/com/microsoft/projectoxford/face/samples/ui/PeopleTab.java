package com.microsoft.projectoxford.face.samples.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.samples.R;
import com.microsoft.projectoxford.face.samples.helper.LogHelper;
import com.microsoft.projectoxford.face.samples.helper.SampleApp;
import com.microsoft.projectoxford.face.samples.helper.StorageHelper;
import com.microsoft.projectoxford.face.samples.persongroupmanagement.PersonActivity;
import com.microsoft.projectoxford.face.samples.persongroupmanagement.PersonGroupActivity;

import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PeopleTab extends Fragment {

    Button identify;
    public View view;
    public GridView gridView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.people_layout, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            boolean addNewPersonGroup = bundle.getBoolean("AddNewPersonGroup");
            String oldPersonGroupName = bundle.getString("PersonGroupName");
            String personGroupId = bundle.getString("PersonGroupId");
            boolean personGroupExists = !addNewPersonGroup;
        }

//        initializeGridView();


//        gridView = (GridView) view.findViewById(R.id.gridView_persons);
//        initializeGridView();
//
//        identify = (Button) view.findViewById(R.id.identify);
//        identify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), IdentificationActivity.class);
//                startActivity(intent);
//            }
//        });
        return view;
    }

}