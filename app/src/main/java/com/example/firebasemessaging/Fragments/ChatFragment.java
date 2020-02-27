package com.example.firebasemessaging.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebasemessaging.Adapters.ChatAdapter;
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
import java.util.List;

import static com.example.firebasemessaging.HelperClass.makeAlert;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

private RecyclerView chatCycler;
private ChatAdapter adapter;
List<UserModel> chats;
List<String> listlog;
String ownerurl="default";
FirebaseUser firebaseUser;
    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_chat, container, false);
        listlog = new ArrayList<>();
        chats = new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        chatCycler = v.findViewById(R.id.chatCycler);
        chatCycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        getChatlist();
        return v;
    }
    private void getChatlist() {

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            UserModel userModel = dataSnapshot.getValue(UserModel.class);
                assert userModel != null;
                ownerurl = userModel.getUserDp();
                loadChats(ownerurl);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                makeAlert(getContext(),"Sorry !!",databaseError.getMessage());
            }
        });
    }

    private void loadChats(final String ownerurl) {
    DatabaseReference dataReff = FirebaseDatabase.getInstance().getReference("Chats");
    dataReff.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot snapShot: dataSnapshot.getChildren()){
                ChatModel chatModel = snapShot.getValue(ChatModel.class);
                System.out.println("Model Chat data :"+chatModel.getGetter()+" sender:: "+chatModel.getSender()+" msg ::"+chatModel.getMessage());
                if (chatModel.getSender().equals(firebaseUser.getUid()))
                {
                    listlog.add(chatModel.getGetter());
                }
                if (chatModel.getGetter().equals(firebaseUser.getUid()))
                {
                    listlog.add(chatModel.getSender());
                }
                System.out.println("Enter lists :"+listlog.size());
            }
            readmessages(ownerurl);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    }

    private void readmessages(final String nerurl) {

        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("Users");
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chats.clear();
                for (DataSnapshot shots : dataSnapshot.getChildren()){
                    UserModel model = shots.getValue(UserModel.class);
                    for (String id : listlog){
                        assert model != null;
                        if (model.getUserId().equals(id)){
                           if (!chats.isEmpty() && chats.size() !=0){
                               for (UserModel userIs : chats){
                                   if (!model.getUserId().equals(userIs)){
                                       chats.add(model);
                                   }

                               }
                           }
                           else {
                               chats.add(model);

                           }
                        }

                    }

                }
                adapter = new ChatAdapter(getContext(),chats, nerurl);
                adapter.notifyDataSetChanged();
                chatCycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
