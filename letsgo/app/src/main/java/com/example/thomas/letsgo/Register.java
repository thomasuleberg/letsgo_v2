package com.example.thomas.letsgo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText e1;
    private  EditText e2;
    private EditText e3;
    private Button b1;
    private TextView b2;
    private ProgressDialog progressDialog;

    // define firebaseauth objects

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // initializing firebase auth object
        firebaseAuth= FirebaseAuth.getInstance();


        if (firebaseAuth.getCurrentUser()!=null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        progressDialog=new ProgressDialog(this);

        // initializing views

        e1=(EditText) findViewById(R.id.email);
        e2=(EditText) findViewById(R.id.pass);
        e3 = (EditText) findViewById(R.id.username);
        b2=(TextView) findViewById(R.id.signin);
        b1=(Button)findViewById(R.id.register);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
    }
    private void Register(){
        String s1=e1.getText().toString().trim();
        String s2=e2.getText().toString().trim();
        String s3 = e3.getText().toString().trim();
// checking if username is empty

        if(TextUtils.isEmpty(s3)){
            Toast.makeText(this,"Please enter username",Toast.LENGTH_SHORT).show();
            return;
        }

        // checking if email is empty
        if(TextUtils.isEmpty(s1)){
            Toast.makeText(this,"Please enter email adress",Toast.LENGTH_SHORT).show();
            return;
        }

        // checking if password is empty
        if(TextUtils.isEmpty(s2)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            // stoping the function  for execution further
            return;
        }

        // if validation are ok
        // we will first show a progressDialog
        progressDialog.setMessage("Register please wait...");
        progressDialog.show();
        // creating new user
        firebaseAuth.createUserWithEmailAndPassword(s1,s2)
                .addOnCompleteListener(this, task -> {
                    //checking successful
                    if (task.isSuccessful()) {
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(Register.this, "Registration error", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                });

    }
    @Override
    public void onClick(View view) {
        if(view==b1){
            Register();
        }
        if(view==b2){
            //skal åpens Login activity
            startActivity(new Intent(this,LoginActivity.class));
        }

    }
}

