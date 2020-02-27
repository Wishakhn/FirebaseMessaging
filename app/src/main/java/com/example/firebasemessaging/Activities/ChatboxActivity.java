package com.example.firebasemessaging.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firebasemessaging.Adapters.MessageAdapter;
import com.example.firebasemessaging.HelperClass;
import com.example.firebasemessaging.Models.ChatModel;
import com.example.firebasemessaging.Models.UserModel;
import com.example.firebasemessaging.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatboxActivity extends AppCompatActivity {
    CircleImageView logindp;
    TextView loginanme;
    Intent gettent;
    FirebaseUser user;
    DatabaseReference ref;
    EditText sendmsgEdit;
    ImageButton sendbtn;
    RecyclerView msgCycler;
    MessageAdapter adapter;
    List<ChatModel> messages;
    String msg;
    String sender = "senderid";
    String reciver = "userid";
    String senderurl = "default";
    String geterurl = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbox);
        initView();
        setListner();
    }

    private void initView() {
        gettent = getIntent();
        logindp = findViewById(R.id.logindp);
        loginanme = findViewById(R.id.loginanme);
        sendbtn = findViewById(R.id.sendbtn);
        msgCycler = findViewById(R.id.msgCycler);
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        sender = user.getUid();
        msgCycler.setLayoutManager(new LinearLayoutManager(ChatboxActivity.this, LinearLayoutManager.VERTICAL, false));
        sendmsgEdit = findViewById(R.id.sendmsgEdit);
        sendmsgEdit.setEnabled(false);
        if (gettent != null) {
            senderurl = gettent.getStringExtra("image");
            reciver = gettent.getStringExtra("uid");
            System.out.println("Reciver id is :"+reciver);
            ref = FirebaseDatabase.getInstance().getReference("Users").child(reciver);
        }


    }

    private void setListner() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                loginanme.setText(userModel.getUserName());
                geterurl = userModel.getUserDp();
                if (geterurl.equals("default")) {
                    logindp.setImageResource(R.drawable.userdp);
                } else {
                    Glide.with(ChatboxActivity.this).load(geterurl).into(logindp);
                }
                loadallMessages(sender, reciver, senderurl, geterurl);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatboxActivity.this, "Request Canceled somehow", Toast.LENGTH_SHORT).show();

            }
        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg = sendmsgEdit.getText().toString().trim();
                if (sendbtn.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_send_blue).getConstantState()) {
                    editing(msg);
                } else if (sendbtn.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_send_grey).getConstantState()) {
                    HelperClass.startEditing(sendmsgEdit, ChatboxActivity.this);
                    sendbtn.setImageResource(R.drawable.ic_send_blue);
                    sendmsgEdit.setEnabled(true);
                }
            }
        });
    }

    private void loadallMessages(final String sender, final String reciver, final String senderurl, final String geterurl) {
        messages = new ArrayList<>();
        DatabaseReference reffernce = FirebaseDatabase.getInstance().getReference("Chats");
        reffernce.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();
                for (DataSnapshot snapShot: dataSnapshot.getChildren()){
                    ChatModel chatModel = snapShot.getValue(ChatModel.class);
                    System.out.println("Model Chat data :"+chatModel.getGetter()+" sender:: "+chatModel.getSender()+" msg ::"+chatModel.getMessage());
                    if (chatModel.getSender().equals(sender) && chatModel.getGetter().equals(reciver) ||
                            chatModel.getSender().equals(reciver) && chatModel.getGetter().equals(sender)){
                        messages.add(chatModel);
                    }
                }
                adapter = new MessageAdapter(messages,ChatboxActivity.this,senderurl,geterurl);
                adapter.notifyDataSetChanged();
                msgCycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void editing(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            HelperClass.stopEditing(sendmsgEdit, ChatboxActivity.this);
            sendbtn.setImageResource(R.drawable.ic_send_grey);
            sendMessage(sender, reciver, msg, geterurl);
            sendmsgEdit.setText("");
            sendmsgEdit.setEnabled(false);
            sendmsgEdit.setHint("Type a message...");
            sendmsgEdit.setHintTextColor(getResources().getColor(R.color.grey));
        } else {
            sendmsgEdit.setHint("Type something first...");
            sendmsgEdit.setHintTextColor(getResources().getColor(R.color.maincolor));
        }
    }

    private void sendMessage(String sender, String reciver, String msg, String gurl) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("sender", sender);
        hashmap.put("getter", reciver);
        hashmap.put("message", msg);
        hashmap.put("urlImage", gurl);
        databaseReference.child("Chats").push().setValue(hashmap);

    }
}
