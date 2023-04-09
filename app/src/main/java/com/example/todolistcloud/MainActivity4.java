package com.example.todolistcloud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity4 extends AppCompatActivity {
    EditText textName;
    EditText textEmail;
    EditText textPassword;
    public Button signupBut;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        signupBut = (Button) findViewById(R.id.signupButton);
        textName = findViewById(R.id.editTextTextPersonName);
        textEmail = findViewById(R.id.editTextTextEmail);
        textPassword = findViewById(R.id.editTextTextPassword);
        signupBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = textName.getText().toString();
                String email = textEmail.getText().toString();
                String pass = textPassword.getText().toString();

                System.out.println("name :: "+name);
                System.out.println("email :: "+email);
                System.out.println("pass :: "+pass);

                Map<String, Object> docData = new HashMap<>();
                docData.put("name", name);
                docData.put("email", email);
                docData.put("pass", pass);


                if(!email.isEmpty() && !pass.isEmpty()){
                    System.out.println("ENTRA");
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                System.out.println("SUCCESS");
                                CollectionReference collection = db.collection("users");
                                AggregateQuery snapshot = collection.count();
                                snapshot.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            // Count fetched successfully
                                            AggregateQuerySnapshot snapshot = task.getResult();
                                            System.out.println("SUCCESS");
                                            System.out.println("snapshot.getCount() :: "+snapshot.getCount()+1);
                                            System.out.println("snapshot.getCount() :: "+snapshot.getCount()+1);
                                            String changedEmail = email.replace(".","1");
                                            db.collection("users").document(changedEmail).set(docData);
                                        } else {
                                            System.out.println("FAIL");
                                        }
                                    }
                                });




                                startActivity(new Intent(MainActivity4.this, MainActivity2.class));
                                //db.collection("users").document(email).set(docData);
                            } else {
                                System.out.println("FAIL");
                                Toast.makeText(MainActivity4.this, "ALGO A FALLADO", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });


    }
}