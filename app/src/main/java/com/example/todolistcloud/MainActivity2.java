package com.example.todolistcloud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolistcloud.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.jar.Attributes;

import Objects.Tarea;

public class MainActivity2 extends AppCompatActivity {

    DatePicker simpleDatePicker;
    Button submit;
    EditText mEdit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // initiate the date picker and a button
        simpleDatePicker = (DatePicker) findViewById(R.id.simpleDatePicker);
        submit = (Button) findViewById(R.id.submitButton);
        // perform click event on submit button
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
                Integer i = com.example.todolistcloud.MainActivity.tareas.size();
                Date date = new Date( year, month, day);
                try {

                    com.example.todolistcloud.MainActivity.jsonTarea.put(sUsername, date);

                    SharedPreferences.Editor editor = com.example.todolistcloud.MainActivity.sharedPref.edit();
                    editor.putString(String.valueOf(1), String.valueOf(com.example.todolistcloud.MainActivity.jsonTarea));
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                switchActivities();
            }
        });

    }
    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, com.example.todolistcloud.MainActivity.class);
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