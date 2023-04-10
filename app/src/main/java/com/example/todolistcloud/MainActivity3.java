package com.example.todolistcloud;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolistcloud.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.Reference;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

import Objects.Tarea;

public class MainActivity3 extends AppCompatActivity {

    DatePicker simpleDatePicker;
    Button submit;
    EditText mEdit;
    private FirebaseFirestore db;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        db = FirebaseFirestore.getInstance();

        simpleDatePicker = (DatePicker) findViewById(R.id.simpleDatePicker);
        submit = (Button) findViewById(R.id.submitButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get the values for day of month , month and year from a date picker
                mEdit = findViewById(R.id.editTextTextPersonName);
                String sUsername = mEdit.getText().toString();
                int day = simpleDatePicker.getDayOfMonth();
                int month = simpleDatePicker.getMonth();
                int year = simpleDatePicker.getYear();
                Toast.makeText(getApplicationContext(), sUsername + "\n" +day + "\n" + month + "\n" + year, Toast.LENGTH_LONG).show();
                Integer i = MainActivity2.tareas.size();
                Date date = new Date( year, month, day);
                try {

                    MainActivity2.jsonTarea.put(sUsername, date);

                    /*SharedPreferences.Editor editor = com.example.todolistcloud.MainActivity2.sharedPref.edit();
                    editor.putString(String.valueOf(1), String.valueOf(com.example.todolistcloud.MainActivity2.jsonTarea));
                    editor.apply();*/

                    CollectionReference collection = db.collection("tareas");
                    AggregateQuery snapshot = collection.count();
                    Map<String, Object> docData = new HashMap<>();

                    snapshot.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                // Count fetched successfully
                                AggregateQuerySnapshot snapshot = task.getResult();
                                System.out.println("SUCCESS");
                                System.out.println("snapshot.getCount() :: "+snapshot.getCount()+1);
                                System.out.println("snapshot.getCount() :: "+Integer.valueOf((int) snapshot.getCount())+1);
                                System.out.println("users/"+MainActivity.userEmail);
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users/"+MainActivity.userEmail);
                                //DatabaseReference ref2 = database.getReference("users/"+MainActivity.userEmail);
                                docData.put("id", String.valueOf(snapshot.getCount()+1));
                                docData.put("name", sUsername);
                                docData.put("date", String.valueOf(date));
                                docData.put("user", db.document("users/"+MainActivity.userEmail));
                                Tarea t = new Tarea();
                                System.out.println(date);
                                t.setDate(Date.valueOf(String.valueOf(date)));
                                t.setName(sUsername);
                                t.setId(String.valueOf(snapshot.getCount()+1));
                                MainActivity2.tareas.add(t);
                                try {
                                    MainActivity2.jsonTarea.put(sUsername, String.valueOf(date));
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                db.collection("tareas").document(String.valueOf(snapshot.getCount()+1)).set(docData);
                                switchActivities();
                            } else {
                                System.out.println("FAIL");
                            }
                        }
                    });




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }
    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, MainActivity2.class);
        startActivity(switchActivityIntent);
    }
    public static int validaInt(String number){
        int result = 0;
        try{
            if(number != null){
                result = Integer.parseInt(number);
            }
        }catch(NumberFormatException nfe){
            nfe.printStackTrace();
        }
        return result;
    }

}