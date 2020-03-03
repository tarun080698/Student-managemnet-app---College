package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class HomeAfterS extends AppCompatActivity {


    private EditText editTextTitle;
    private EditText editTextDescription;
    private FirebaseAuth auth;
    private TextView textViewData;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "MainActivity";
    private CollectionReference notebookRef = db.collection("Activities");


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_after_s);

        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        textViewData = findViewById(R.id.text_view_data);


        findViewById(R.id.signOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent i = new Intent(getApplicationContext(), LoginS.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        notebookRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Note note = documentSnapshot.toObject(Note.class);
                    note.setDocumentId(documentSnapshot.getId());
                }
            }
        });
    }


    public void addNote(View v) {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        Note note = new Note(title, description);
        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(HomeAfterS.this, "Please add details properly", Toast.LENGTH_SHORT).show();
        } else {
            notebookRef.add(note);
            Toast.makeText(HomeAfterS.this, "Activity added", Toast.LENGTH_SHORT).show();
        }
        editTextTitle.setText("");
        editTextDescription.setText("");
    }

    public void loadNotes(View v) {
        notebookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Note note = documentSnapshot.toObject(Note.class);
                            note.setDocumentId(documentSnapshot.getId());

                            String title = note.getTitle();
                            String description = note.getDescription();

                            data += title + "\n\t\t" + description + "\n\n";
                        }
                        if (data.isEmpty()) {
                            data = "Nothing to show :(";
                            textViewData.setText(data);
                        } else
                            textViewData.setText(data);
                    }
                });
        Toast.makeText(HomeAfterS.this, "Loading data...", Toast.LENGTH_SHORT).show();
    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }
}