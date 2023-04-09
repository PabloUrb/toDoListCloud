package com.example.todolistcloud;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    public Button loginButton;
    public Button signupButton;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    public static String userEmail;
    EditText textEmail;
    EditText textPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString("message", "Integracion de firebase complete");
        mFirebaseAnalytics.logEvent("InitScreen",bundle);
        mAuth = FirebaseAuth.getInstance();

        loginButton = (Button) findViewById(R.id.loginButton);
        textEmail = findViewById(R.id.editTextTextEmail);
        textPassword = findViewById(R.id.editTextTextPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = textEmail.getText().toString();
                String pass = textPassword.getText().toString();
                System.out.println("email :: "+email);
                System.out.println("pass :: "+pass);

                if(!email.isEmpty() && !pass.isEmpty()) {
                    System.out.println("ENTRA");
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                System.out.println("SUCCESS");
                                userEmail = email.replace(".","1");
                                startActivity(new Intent(MainActivity.this, MainActivity2.class));
                            } else {
                                System.out.println("FAIL");
                                Toast.makeText(MainActivity.this, "ALGO A FALLADO", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
        signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity4.class));
            }
        });
    }
    private void showAlert(){
        Toast.makeText(MainActivity.this, "ALGO A FALLADO", Toast.LENGTH_SHORT).show();
    }

}