package com.microsoft.projectoxford.face.samples.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.samples.R;
import com.microsoft.projectoxford.face.samples.helper.LogHelper;
import com.microsoft.projectoxford.face.samples.helper.SampleApp;
import com.microsoft.projectoxford.face.samples.helper.StorageHelper;
import com.microsoft.projectoxford.face.samples.persongroupmanagement.PersonActivity;
import com.microsoft.projectoxford.face.samples.persongroupmanagement.PersonGroupActivity;
import com.microsoft.projectoxford.face.samples.persongroupmanagement.PersonGroupActivity.PersonGridViewAdapter;

import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    Button identify, faces;
    public View view;
    public GridView gridView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

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

        identify = (Button) view.findViewById(R.id.identify);
        identify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IdentificationActivity.class);
                didTapButton(v);
                startActivity(intent);
            }
        });

//        faces = (Button) view.findViewById(R.id.s);
//        faces.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), PersonGroupActivity.class);
//                startActivity(intent);
//            }
//        });

        return view;
    }

    public void didTapButton(View view) {
        Button button = (Button)view.findViewById(R.id.identify);
        final Animation myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.animation);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        button.startAnimation(myAnim);
    }

//    boolean personGroupExists;
//    String personGroupId;
//    PersonGridViewAdapter personGridViewAdapter = new PersonGridViewAdapter();
//
//    private void initializeGridView() {
//        GridView gridView = (GridView) view.findViewById(R.id.gridView_persons);
//
//        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
//        gridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
//            @Override
//            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                return false;
//            }
//
//            @Override
//            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//                return false;
//            }
//
//            @Override
//            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//                return false;
//            }
//
//            @Override
//            public void onDestroyActionMode(ActionMode mode) {
//
//            }
//
//            @Override
//            public void onItemCheckedStateChanged(ActionMode mode,
//                                                  int position, long id, boolean checked) {
//                personGridViewAdapter.personChecked.set(position, checked);
//
//                GridView gridView = (GridView) view.findViewById(R.id.gridView_persons);
//                gridView.setAdapter(personGridViewAdapter);
//            }
//        });
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (!personGridViewAdapter.longPressed) {
//                    String personId = personGridViewAdapter.personIdList.get(position);
//                    String personName = StorageHelper.getPersonName(
//                            personId, personGroupId, getActivity());
//
//                    Intent intent = new Intent(getContext(), PersonActivity.class);
//                    intent.putExtra("AddNewPerson", false);
//                    intent.putExtra("PersonName", personName);
//                    intent.putExtra("PersonId", personId);
//                    intent.putExtra("PersonGroupId", personGroupId);
//                    startActivity(intent);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        if (personGroupExists) {
//            GridView gridView = (GridView) view.findViewById(R.id.gridView_persons);
//            Intent in = new Intent(getActivity(),PersonGroupActivity.class);
//            gridView.setAdapter(personGridViewAdapter);
//        }
//    }
//
//    public class PersonGridViewAdapter extends BaseAdapter {
//
//        public List<String> personIdList;
//        public List<Boolean> personChecked;
//        public boolean longPressed;
//
//        PersonGridViewAdapter() {
//            longPressed = false;
//            personIdList = new ArrayList<>();
//            personChecked = new ArrayList<>();
//
//            Set<String> personIdSet = StorageHelper.getAllPersonIds(personGroupId, getActivity().getApplicationContext());
//            for (String personId: personIdSet) {
//                personIdList.add(personId);
//                personChecked.add(false);
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return personIdList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return personIdList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            // set the item view
//            if (convertView == null) {
//                LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = layoutInflater.inflate(R.layout.item_person, parent, false);
//            }
//            convertView.setId(position);
//
//            String personId = personIdList.get(position);
//            Set<String> faceIdSet = StorageHelper.getAllFaceIds(personId, getActivity());
//            if (!faceIdSet.isEmpty()) {
//                Iterator<String> it = faceIdSet.iterator();
//                Uri uri = Uri.parse(StorageHelper.getFaceUri(it.next(), getActivity()));
//                ((ImageView)convertView.findViewById(R.id.image_person)).setImageURI(uri);
//            } else {
//                Drawable drawable = getResources().getDrawable(R.drawable.select_image);
//                ((ImageView)convertView.findViewById(R.id.image_person)).setImageDrawable(drawable);
//            }
//
//            // set the text of the item
//            String personName = StorageHelper.getPersonName(personId, personGroupId, getActivity());
//            ((TextView)convertView.findViewById(R.id.text_person)).setText(personName);
//
//            // set the checked status of the item
//            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_person);
//            if (longPressed) {
//                checkBox.setVisibility(View.VISIBLE);
//
//                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        personChecked.set(position, isChecked);
//                    }
//                });
//                checkBox.setChecked(personChecked.get(position));
//            } else {
//                checkBox.setVisibility(View.INVISIBLE);
//            }
//
//            return convertView;
//        }
//    }

}