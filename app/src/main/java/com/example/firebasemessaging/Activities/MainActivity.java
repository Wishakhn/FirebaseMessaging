package com.example.firebasemessaging.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firebasemessaging.HelperClass;
import com.example.firebasemessaging.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.firebasemessaging.HelperClass.REQUEST_PERMISSION_CODE;
import static com.example.firebasemessaging.HelperClass.displayProgressDialog;
import static com.example.firebasemessaging.HelperClass.hideProgressDialog;
import static com.example.firebasemessaging.HelperClass.makeAlert;

public class MainActivity extends AppCompatActivity {
EditText usernameEdit, passEdit,emailEdit;
CircleImageView dpprof;
TextView alreadyuser;
    private Button Register;
String uname="";
String pass ="";
String email="";
    private String ImageUrl = "default";
    private static  final  int IMAGE_PICKER_REQUEST_CODE = 1001;
FirebaseAuth auth;
DatabaseReference firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListner();
    }

    private void setListner() {
        dpprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HelperClass.checkCameraPermissions(MainActivity.this)){
                    chooseImage();
                }
                else {
                    requestPermissions();
                }

            }
        });
        alreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gologin = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(gologin);
                finish();
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname=usernameEdit.getText().toString().trim();
                pass=passEdit.getText().toString().trim();
                email=emailEdit.getText().toString().trim();
                if (!TextUtils.isEmpty(uname) || !TextUtils.isEmpty(pass) || !TextUtils.isEmpty(email)){
                    displayProgressDialog(MainActivity.this,"Registering....");
                    register(uname,email,pass);
                }
                else {
                    Toast.makeText(MainActivity.this, "Please fill all the fields!!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
void chooseImage(){
    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(i, IMAGE_PICKER_REQUEST_CODE);
}
    private void register(final String uname, String email, String pass) {
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser fUser = auth.getCurrentUser();
                    assert fUser != null;
                    String userId = fUser.getUid();
                    System.out.println("User ID is "+userId);
                    firebaseRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                    HashMap<String,String> hashmap = new HashMap<String, String>();
                    hashmap.put("id",userId);
                    hashmap.put("username",uname);
                    hashmap.put("imageurl",ImageUrl);
                    firebaseRef.setValue(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                hideProgressDialog();
                                Intent gotent = new Intent(MainActivity.this,InboxActivity.class);
                                startActivity(gotent);
                                finish();
                            }
                        }
                    });

                }
                else {
                    hideProgressDialog();
                    Toast.makeText(MainActivity.this, "Sorry!! Unable to register you with provided credentials", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initView() {
        usernameEdit = findViewById(R.id.usernameEdit);
        passEdit = findViewById(R.id.passEdit);
        dpprof = findViewById(R.id.dpprof);
        emailEdit = findViewById(R.id.emailEdit);
        Register = findViewById(R.id.Register);
        alreadyuser = findViewById(R.id.alreadyuser);
        auth = FirebaseAuth.getInstance();
    }
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                "android.permission.CAMERA",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE"}, REQUEST_PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                makeAlert(MainActivity.this,"Sorry !!","Allow requested permissions to access gallery");
            } else {
                chooseImage();

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK){
            assert data != null;
            Uri uri = data.getData();
            ImageUrl = String.valueOf(uri);
            System.out.println("Image Url is :"+ImageUrl);
            Glide.with(MainActivity.this).load(uri).into(dpprof);

        }
    }
}
