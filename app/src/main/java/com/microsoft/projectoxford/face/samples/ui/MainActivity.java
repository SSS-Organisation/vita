package com.microsoft.projectoxford.face.samples.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.microsoft.projectoxford.face.samples.R;


public class MainActivity extends Activity implements View.OnClickListener{


    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;

    private TextView textViewSignin;

    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), TabActivity.class));
        }

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){

        //getting email and password from edit texts
        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering, Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            finish();
                            Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        }else{
                            //display some message here
                            Toast.makeText(MainActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {

        if(view == buttonSignup){
            registerUser();
        }

        if(view == textViewSignin){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

//    private class IdentificationTask extends AsyncTask<UUID, String, IdentifyResult[]> {
//        private boolean mSucceed = true;
//        String mPersonGroupId;
//        IdentificationTask(String personGroupId) {
//            this.mPersonGroupId = personGroupId;
//        }
//
//        @Override
//        protected IdentifyResult[] doInBackground(UUID... params) {
//            String logString = "Request: Identifying faces ";
//            for (UUID faceId: params) {
//                logString += faceId.toString() + ", ";
//            }
//            logString += " in group " + mPersonGroupId;
//            addLog(logString);
//
//            // Get an instance of face service client to detect faces in image.
//            FaceServiceClient faceServiceClient = SampleApp.getFaceServiceClient();
//            try{
//                publishProgress("Getting person group status...");
//
//                TrainingStatus trainingStatus = faceServiceClient.getLargePersonGroupTrainingStatus(
//                        this.mPersonGroupId);     /* personGroupId */
//                if (trainingStatus.status != TrainingStatus.Status.Succeeded) {
//                    publishProgress("Person group training status is " + trainingStatus.status);
//                    mSucceed = false;
//                    return null;
//                }
//
//                publishProgress("Identifying...");
//
//                // Start identification.
//                return faceServiceClient.identityInLargePersonGroup(
//                        this.mPersonGroupId,   /* personGroupId */
//                        params,                  /* faceIds */
//                        1);  /* maxNumOfCandidatesReturned */
//            }  catch (Exception e) {
//                mSucceed = false;
//                publishProgress(e.getMessage());
//                addLog(e.getMessage());
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//            setUiBeforeBackgroundTask();
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//            // Show the status of background detection task on screen.a
//            setUiDuringBackgroundTask(values[0]);
//        }
//
//        @Override
//        protected void onPostExecute(IdentifyResult[] result) {
//            // Show the result on screen when detection is done.
//            setUiAfterIdentification(result, mSucceed);
//        }
//    }
//
//    String mPersonGroupId;
//
//    boolean detected;
//
//    FaceListAdapter mFaceListAdapter;
//
//    PersonGroupListAdapter mPersonGroupListAdapter;
//
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        View v = getLayoutInflater().inflate(R.layout.identify_main, null);
//        ListView listView = (ListView) v.findViewById(R.id.list_person_groups_identify);
//        mPersonGroupListAdapter = new PersonGroupListAdapter();
//        listView.setAdapter(mPersonGroupListAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                setPersonGroupSelected(position);
//            }
//        });
//
//        if (mPersonGroupListAdapter.personGroupIdList.size() != 0) {
//            setPersonGroupSelected(0);
//        } else {
//            setPersonGroupSelected(-1);
//        }
//    }
//
//    void setPersonGroupSelected(int position) {
//        View v = getLayoutInflater().inflate(R.layout.identify_main, null);
//        TextView textView = (TextView) v.findViewById(R.id.text_person_group_selected);
//        if (position > 0) {
//            String personGroupIdSelected = mPersonGroupListAdapter.personGroupIdList.get(position);
//            mPersonGroupListAdapter.personGroupIdList.set(
//                    position, mPersonGroupListAdapter.personGroupIdList.get(0));
//            mPersonGroupListAdapter.personGroupIdList.set(0, personGroupIdSelected);
//            ListView listView = (ListView) findViewById(R.id.list_person_groups_identify);
//            listView.setAdapter(mPersonGroupListAdapter);
//            setPersonGroupSelected(0);
//        } else if (position < 0) {
//            setIdentifyButtonEnabledStatus(false);
//            textView.setTextColor(Color.RED);
//            textView.setText(R.string.no_person_group_selected_for_identification_warning);
//        } else {
//            mPersonGroupId = mPersonGroupListAdapter.personGroupIdList.get(0);
//            String personGroupName = StorageHelper.getPersonGroupName(
//                    mPersonGroupId, MainActivity.this);
//            refreshIdentifyButtonEnabledStatus();
//            textView.setTextColor(Color.BLACK);
//            textView.setText(String.format("Person group to use: %s", personGroupName));
//        }
//    }
//
//    private void setUiBeforeBackgroundTask() {
//        progressDialog.show();
//    }
//
//    // Show the status of background detection task on screen.
//    private void setUiDuringBackgroundTask(String progress) {
//        progressDialog.setMessage(progress);
//
//        setInfo(progress);
//    }
//
//    // Show the result on screen when detection is done.
//    private void setUiAfterIdentification(IdentifyResult[] result, boolean succeed) {
//        progressDialog.dismiss();
//
//        setAllButtonsEnabledStatus(true);
//        setIdentifyButtonEnabledStatus(false);
//
//        if (succeed) {
//            // Set the information about the detection result.
//            setInfo("Identification is done");
//
//            if (result != null) {
//                mFaceListAdapter.setIdentificationResult(result);
//
//                String logString = "Response: Success. ";
//                for (IdentifyResult identifyResult: result) {
//                    logString += "Face " + identifyResult.faceId.toString() + " is identified as "
//                            + (identifyResult.candidates.size() > 0
//                            ? identifyResult.candidates.get(0).personId.toString()
//                            : "Unknown Person")
//                            + ". ";
//                }
//                addLog(logString);
//
//                // Show the detailed list of detected faces.
//                ListView listView = (ListView) findViewById(R.id.list_identified_faces);
//                listView.setAdapter(mFaceListAdapter);
//            }
//        }
//    }
//
//    // Background task of face detection.
//    private class DetectionTask extends AsyncTask<InputStream, String, Face[]> {
//        @Override
//        protected Face[] doInBackground(InputStream... params) {
//            // Get an instance of face service client to detect faces in image.
//            FaceServiceClient faceServiceClient = SampleApp.getFaceServiceClient();
//            try{
//                publishProgress("Detecting...");
//
//                // Start detection.
//                return faceServiceClient.detect(
//                        params[0],  /* Input stream of image to detect */
//                        true,       /* Whether to return face ID */
//                        false,       /* Whether to return face landmarks */
//                        /* Which face attributes to analyze, currently we support:
//                           age,gender,headPose,smile,facialHair */
//                        null);
//            }  catch (Exception e) {
//                publishProgress(e.getMessage());
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//            setUiBeforeBackgroundTask();
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//            // Show the status of background detection task on screen.
//            setUiDuringBackgroundTask(values[0]);
//        }
//
//        @Override
//        protected void onPostExecute(Face[] result) {
//            progressDialog.dismiss();
//
//            setAllButtonsEnabledStatus(true);
//
//            if (result != null) {
//                // Set the adapter of the ListView which contains the details of detected faces.
//                mFaceListAdapter = new FaceListAdapter(result);
//                ListView listView = (ListView) findViewById(R.id.list_identified_faces);
//                listView.setAdapter(mFaceListAdapter);
//
//                if (result.length == 0) {
//                    detected = false;
//                    setInfo("No faces detected!");
//                } else {
//                    detected = true;
//                    setInfo("Click on the \"Identify\" button to identify the faces in image.");
//                }
//            } else {
//                detected = false;
//            }
//
//            refreshIdentifyButtonEnabledStatus();
//        }
//    }
//
//    // Flag to indicate which task is to be performed.
//    private static final int REQUEST_SELECT_IMAGE = 0;
//
//    // The image selected to detect.
//    private Bitmap mBitmap;
//
//    // Progress dialog popped up when communicating with server.
//    ProgressDialog progressDialog;
//
//    // Called when image selection is done.
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        setContentView(R.layout.activity_main);
//        switch (requestCode)
//        {
//            case REQUEST_SELECT_IMAGE:
//                if(resultCode == RESULT_OK) {
//                    detected = false;
//
//                    // If image is selected successfully, set the image URI and bitmap.
//                    Uri imageUri = data.getData();
//                    mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
//                            imageUri, getContentResolver());
//                    if (mBitmap != null) {
//                        // Show the image on screen.
//                        LinearLayout myLinear = (LinearLayout) findViewById(R.id.activity_chooser_view_content);
//                        ImageView imageView = (ImageView) myLinear.findViewById(R.id.image);
//                        imageView.setImageBitmap(mBitmap);
//                    }
//
//                    // Clear the identification result.
//                    FaceListAdapter faceListAdapter = new FaceListAdapter(null);
//                    ListView listView = (ListView) findViewById(R.id.list_identified_faces);
//                    listView.setAdapter(faceListAdapter);
//
//                    // Clear the information panel.
//                    setInfo("");
//
//                    // Start detecting in image.
//                    detect(mBitmap);
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//    // Start detecting in image.
//    private void detect(Bitmap bitmap) {
//        // Put the image into an input stream for detection.
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());
//
//        setAllButtonsEnabledStatus(false);
//
//        // Start a background task to detect faces in the image.
//        new DetectionTask().execute(inputStream);
//    }
//
//    // Called when the "Select Image" button is clicked.
//    public void selectImage(View view) {
//        Intent intent = new Intent(this, SelectImageActivity.class);
//        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
//    }
//
//    // Called when the "Detect" button is clicked.
//    public void identify(View view) {
//        // Start detection task only if the image to detect is selected.
//        if (detected && mPersonGroupId != null) {
//            // Start a background task to identify faces in the image.
//            List<UUID> faceIds = new ArrayList<>();
//            for (Face face:  mFaceListAdapter.faces) {
//                faceIds.add(face.faceId);
//            }
//
//            setAllButtonsEnabledStatus(false);
//
//            new IdentificationTask(mPersonGroupId).execute(
//                    faceIds.toArray(new UUID[faceIds.size()]));
//        } else {
//            // Not detected or person group exists.
//            setInfo("Please select an image and create a person group first.");
//        }
//    }
//
//    public void managePersonGroups(View view) {
//        Intent intent = new Intent(this, PersonGroupListActivity.class);
//        startActivity(intent);
//
//        refreshIdentifyButtonEnabledStatus();
//    }
//
//    public void viewLog(View view) {
//        Intent intent = new Intent(this, IdentificationLogActivity.class);
//        startActivity(intent);
//    }
//
//    // Add a log item.
//    private void addLog(String log) {
//        LogHelper.addIdentificationLog(log);
//    }
//
//    // Set whether the buttons are enabled.
//    private void setAllButtonsEnabledStatus(boolean isEnabled) {
//        Button selectImageButton = (Button) findViewById(R.id.manage_person_groups);
//        selectImageButton.setEnabled(isEnabled);
//
//        Button groupButton = (Button) findViewById(R.id.select_image);
//        groupButton.setEnabled(isEnabled);
//
//        Button identifyButton = (Button) findViewById(R.id.identify);
//        identifyButton.setEnabled(isEnabled);
//
//        Button viewLogButton = (Button) findViewById(R.id.view_log);
//        viewLogButton.setEnabled(isEnabled);
//    }
//
//    // Set the group button is enabled or not.
//    private void setIdentifyButtonEnabledStatus(boolean isEnabled) {
//        View v = getLayoutInflater().inflate(R.layout.identify_main, null);
//        Button button = (Button) v.findViewById(R.id.identify);
//        button.setEnabled(isEnabled);
//    }
//
//    // Set the group button is enabled or not.
//    private void refreshIdentifyButtonEnabledStatus() {
//        if (detected && mPersonGroupId != null) {
//            setIdentifyButtonEnabledStatus(true);
//        } else {
//            setIdentifyButtonEnabledStatus(false);
//        }
//    }
//
//    // Set the information panel on screen.
//    private void setInfo(String info) {
//        TextView textView = (TextView) findViewById(R.id.info);
//        textView.setText(info);
//    }
//
//    // The adapter of the GridView which contains the details of the detected faces.
//    private class FaceListAdapter extends BaseAdapter {
//        // The detected faces.
//        List<Face> faces;
//
//        List<IdentifyResult> mIdentifyResults;
//
//        // The thumbnails of detected faces.
//        List<Bitmap> faceThumbnails;
//
//        // Initialize with detection result.
//        FaceListAdapter(Face[] detectionResult) {
//            faces = new ArrayList<>();
//            faceThumbnails = new ArrayList<>();
//            mIdentifyResults = new ArrayList<>();
//
//            if (detectionResult != null) {
//                faces = Arrays.asList(detectionResult);
//                for (Face face: faces) {
//                    try {
//                        // Crop face thumbnail with five main landmarks drawn from original image.
//                        faceThumbnails.add(ImageHelper.generateFaceThumbnail(
//                                mBitmap, face.faceRectangle));
//                    } catch (IOException e) {
//                        // Show the exception when generating face thumbnail fails.
//                        setInfo(e.getMessage());
//                    }
//                }
//            }
//        }
//
//        public void setIdentificationResult(IdentifyResult[] identifyResults) {
//            mIdentifyResults = Arrays.asList(identifyResults);
//        }
//
//        @Override
//        public boolean isEnabled(int position) {
//            return false;
//        }
//
//        @Override
//        public int getCount() {
//            return faces.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return faces.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            if (convertView == null) {
//                LayoutInflater layoutInflater =
//                        (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = layoutInflater.inflate(
//                        R.layout.item_face_with_description, parent, false);
//            }
//            convertView.setId(position);
//
//            // Show the face thumbnail.
//            ((ImageView)convertView.findViewById(R.id.face_thumbnail)).setImageBitmap(
//                    faceThumbnails.get(position));
//
//            if (mIdentifyResults.size() == faces.size()) {
//                // Show the face details.
//                DecimalFormat formatter = new DecimalFormat("#0.00");
//                if (mIdentifyResults.get(position).candidates.size() > 0) {
//                    String personId =
//                            mIdentifyResults.get(position).candidates.get(0).personId.toString();
//                    String personName = StorageHelper.getPersonName(
//                            personId, mPersonGroupId, MainActivity.this);
//                    String identity = "Person: " + personName + "\n"
//                            + "Confidence: " + formatter.format(
//                            mIdentifyResults.get(position).candidates.get(0).confidence);
//                    ((TextView) convertView.findViewById(R.id.text_detected_face)).setText(
//                            identity);
//                } else {
//                    ((TextView) convertView.findViewById(R.id.text_detected_face)).setText(
//                            R.string.face_cannot_be_identified);
//                }
//            }
//
//            return convertView;
//        }
//    }
//
//    // The adapter of the ListView which contains the person groups.
//    private class PersonGroupListAdapter extends BaseAdapter {
//        List<String> personGroupIdList;
//
//        // Initialize with detection result.
//        PersonGroupListAdapter() {
//            personGroupIdList = new ArrayList<>();
//
//            Set<String> personGroupIds
//                    = StorageHelper.getAllPersonGroupIds(MainActivity.this);
//
//            for (String personGroupId: personGroupIds) {
//                personGroupIdList.add(personGroupId);
//                if (mPersonGroupId != null && personGroupId.equals(mPersonGroupId)) {
//                    personGroupIdList.set(
//                            personGroupIdList.size() - 1,
//                            mPersonGroupListAdapter.personGroupIdList.get(0));
//                    mPersonGroupListAdapter.personGroupIdList.set(0, personGroupId);
//                }
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return personGroupIdList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return personGroupIdList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            if (convertView == null) {
//                LayoutInflater layoutInflater =
//                        (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = layoutInflater.inflate(R.layout.item_person_group, parent, false);
//            }
//            convertView.setId(position);
//
//            // set the text of the item
//            String personGroupName = StorageHelper.getPersonGroupName(
//                    personGroupIdList.get(position), MainActivity.this);
//            int personNumberInGroup = StorageHelper.getAllPersonIds(
//                    personGroupIdList.get(position), MainActivity.this).size();
//            ((TextView)convertView.findViewById(R.id.text_person_group)).setText(
//                    String.format(
//                            "%s (Person count: %d)",
//                            personGroupName,
//                            personNumberInGroup));
//
//            if (position == 0) {
//                ((TextView)convertView.findViewById(R.id.text_person_group)).setTextColor(
//                        Color.parseColor("#3399FF"));
//            }
//
//            return convertView;
//        }
//    }

}