package com.example.firebasemessaging.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firebasemessaging.Fragments.CallFragment;
import com.example.firebasemessaging.Fragments.ChatFragment;
import com.example.firebasemessaging.Fragments.ProfileFragment;
import com.example.firebasemessaging.HelperClass;
import com.example.firebasemessaging.Models.UserModel;
import com.example.firebasemessaging.R;
import com.example.firebasemessaging.SplashScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class InboxActivity extends AppCompatActivity {
    FrameLayout fragment;
    FragmentTransaction transaction;
    CardView logout;
    Handler handler;
    ImageView chats;
    ImageView calls;
    ImageView prof;
    CircleImageView logindp;
    TextView loginanme;
    FirebaseUser user;
    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        initView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        setLisner();
    }

    private void setLisner() {
        HelperClass.displayProgressDialog(InboxActivity.this, "Loading User....");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HelperClass.hideProgressDialog();
                UserModel umodel = dataSnapshot.getValue(UserModel.class);
                System.out.println("User model data is ::"+umodel);
                System.out.println("User model id is ::"+umodel.getUserId());
                System.out.println("User model name is ::"+umodel.getUserName());
                assert umodel != null;
                String dpurl = umodel.getUserDp();
                System.out.println("Dp Url is :"+dpurl);
                loginanme.setText(umodel.getUserName());
                if (dpurl.equals("default")) {
                    logindp.setImageResource(R.drawable.userdp);
                } else {
                    Glide.with(InboxActivity.this).load(umodel.getUserDp()).into(logindp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                HelperClass.hideProgressDialog();
                HelperClass.makeAlert(InboxActivity.this, "Sorry !!", databaseError.getMessage());
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelperClass.displayProgressDialog(InboxActivity.this, "Loging out....");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        logOut();
                    }
                }, 1000);

            }
        });
    }

    private void initView() {
        handler = new Handler();
        fragment = findViewById(R.id.fragment);
        logout = findViewById(R.id.logout);
        prof = findViewById(R.id.prof);
        calls = findViewById(R.id.calls);
        chats = findViewById(R.id.chats);
        loginanme = findViewById(R.id.loginanme);
        logindp = findViewById(R.id.logindp);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        ChatFragment chatfrag = new ChatFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, chatfrag);
        transaction.commit();

    }

    private void logOut() {
        HelperClass.hideProgressDialog();
        FirebaseAuth.getInstance().signOut();
        Intent goback = new Intent(InboxActivity.this, SplashScreen.class);
        goback.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(goback);
    }

    public void changeFragment(View view) {

        switch (view.getId()) {
            case R.id.chats:
                chats.setBackgroundColor(getResources().getColor(R.color.selected));
                calls.setBackgroundColor(getResources().getColor(R.color.maincolor));
                prof.setBackgroundColor(getResources().getColor(R.color.maincolor));
                ChatFragment chat = new ChatFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment, chat);
                transaction.commit();
                break;

            case R.id.calls:
                calls.setBackgroundColor(getResources().getColor(R.color.selected));
                chats.setBackgroundColor(getResources().getColor(R.color.maincolor));
                prof.setBackgroundColor(getResources().getColor(R.color.maincolor));
                CallFragment calllog = new CallFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment, calllog);
                transaction.commit();
                break;

            case R.id.prof:
                prof.setBackgroundColor(getResources().getColor(R.color.selected));
                chats.setBackgroundColor(getResources().getColor(R.color.maincolor));
                calls.setBackgroundColor(getResources().getColor(R.color.maincolor));
                ProfileFragment staus = new ProfileFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment, staus);
                transaction.commit();
                break;
            default:
        }
    }
}
