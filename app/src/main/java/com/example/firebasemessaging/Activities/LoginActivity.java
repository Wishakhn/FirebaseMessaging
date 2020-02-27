package com.example.firebasemessaging.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasemessaging.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.firebasemessaging.HelperClass.displayProgressDialog;
import static com.example.firebasemessaging.HelperClass.hideProgressDialog;
import static com.example.firebasemessaging.HelperClass.makeAlert;

public class LoginActivity extends AppCompatActivity {
    EditText  passEnter,emailEnter;
    TextView noaccount;
    Button Login;
    String pass ="";
    String email="";
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListner();
    }
    private void initView() {
        passEnter = findViewById(R.id.passEnter);
        emailEnter = findViewById(R.id.emailEnter);
        Login = findViewById(R.id.Login);
        noaccount = findViewById(R.id.noaccount);
        auth = FirebaseAuth.getInstance();
    }

    private void setListner() {
        noaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goreg = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(goreg);
                finish();
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pass=passEnter.getText().toString().trim();
                email=emailEnter.getText().toString().trim();
                if ( !TextUtils.isEmpty(pass) || !TextUtils.isEmpty(email)){
                    displayProgressDialog(LoginActivity.this,"LogingIn please wait...");
                    loginUser(email,pass);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void loginUser(String email, String pass) {
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    hideProgressDialog();
                    Intent gotent = new Intent(LoginActivity.this,InboxActivity.class);
                    startActivity(gotent);
                    finish();
                }
                else {
                    hideProgressDialog();
                    makeAlert(LoginActivity.this,"Sorry!!", "unable to login user..");
                }
            }
        });
    }


}
