package com.example.thomas.letsgo;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button b3;
    private EditText e1;
    private EditText e2;
    private TextView b4;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        firebaseAuth = FirebaseAuth.getInstance();


        // checking if user is already logged in
        if (firebaseAuth.getCurrentUser()!=null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        b3=(Button)findViewById(R.id.login);
        e1=(EditText) findViewById(R.id.email);
        e2=(EditText) findViewById(R.id.pass);
        b4=(TextView) findViewById(R.id.signup);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }
    private void userLogin(){
        String s1=e1.getText().toString().trim();
        String s2=e2.getText().toString().trim();
        // checking if email is empty

        if(TextUtils.isEmpty(s1)){
            Toast.makeText(this,"Please enter email address",Toast.LENGTH_SHORT).show();
            // stopping the function execution further
            return;
        }
        // checking if password is empty
        if(TextUtils.isEmpty(s2)){
            Toast.makeText(this,"Please enter Password",Toast.LENGTH_SHORT).show();
            // stopping the function execution further
            return;
        }

        // if the email and password are not empty
        // display a progress dialog
        progressDialog.setMessage("Login please wait...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(s1,s2)
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()){
                        // start the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }

                });

    }
    @Override
    public void onClick(View view) {
        if(view==b3){
            userLogin();
        }
        if(view==b4){
            finish();
            startActivity(new Intent(this,Register.class));
        }

    }
}