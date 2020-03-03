package com.example.project;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddPost extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText name, postTitle, postDesc;
    private ImageView postPic;
    private ProgressBar progressBar;
    public String organization;
    Spinner org;
    private Uri uriImage;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabsaeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        org = findViewById(R.id.spinner);
        org.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> list = new ArrayList<String>();
        list.add("Staff Teacher");
        list.add("Vivekanand Education Society Information Technology");
        list.add("Cultural Council");
        list.add("Computer Society of India");
        list.add("Vesit PhotoCircle");
        list.add("IEEE");
        list.add("ISA");
        list.add("ISTE");
        list.add("Vesit Ressinance");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        org.setAdapter(dataAdapter);

        //      assignment of every id with variable
        FloatingActionButton post = findViewById(R.id.post);
        name = findViewById(R.id.name);
        postTitle = findViewById(R.id.post_title);
        postDesc = findViewById(R.id.post_desc);
        postPic = findViewById(R.id.post_pic);
        progressBar = findViewById(R.id.upload_progress);


        //Uploads is a folder in Firebase storage where images will be stored
        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");
        mDatabsaeRef = FirebaseDatabase.getInstance().getReference("Uploads");
//      buttons
        ImageButton chooseImage = findViewById(R.id.post_pic);
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(AddPost.this, "Upload in progress", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(AddPost.this, HomeActivityL.class);
                    startActivity(i);
                }
            }
        });
    }

    private StorageTask uploadTask;

    private void openFileChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImage = data.getData();
            Picasso.with(this).load(uriImage).into(postPic);
        }
        uploadFile();
    }

    //Gets the extension of uploaded image
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (uriImage != null) {
            Toast.makeText(AddPost.this, "Wait, Image is being uploaded", Toast.LENGTH_SHORT).show();
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "."
                    + getFileExtension(uriImage));
            uploadTask = fileReference.putFile(uriImage).addOnSuccessListener
                    (new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 1000);
                            Toast.makeText(AddPost.this, "Upload Success", Toast.LENGTH_LONG).show();
                            //I have done something different if it doesn't work then go to 14:20 video part3
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful()) ;
                            Uri downloadUrl = urlTask.getResult();
                            Upload upload = new Upload(name.getText().toString().trim(), organization,
                                    postTitle.getText().toString().trim(), postDesc.getText().toString().trim(), downloadUrl.toString());
                            String uploadId = mDatabsaeRef.push().getKey();
                            assert uploadId != null;
// some string you see in realtime is this id and using upload the data with
// uplaod is sent to it and then our recyclerview uses these id's as a single post
                            mDatabsaeRef.child(uploadId).setValue(upload);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddPost.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int) progress);
                }
            });
        } else {
            Toast.makeText(AddPost.this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        organization = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
