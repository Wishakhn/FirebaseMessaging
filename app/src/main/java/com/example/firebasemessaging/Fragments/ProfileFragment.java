package com.example.firebasemessaging.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebasemessaging.Adapters.UserAdapter;
import com.example.firebasemessaging.HelperClass;
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
public class ProfileFragment extends Fragment {
RecyclerView userCycler;
UserAdapter adapter;
List<UserModel> userList;
    String ownerurl="default";
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View pv =  inflater.inflate(R.layout.fragment_profile, container, false);
        userList = new ArrayList<>();
        userCycler = pv.findViewById(R.id.userCycler);
        userCycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new UserAdapter(userList,ownerurl,getContext());


        readUsers();
        return pv;
    }

    private void readUsers() {
        HelperClass.displayProgressDialog(getContext(),"Loading Contacts...");
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Users");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                System.out.println("Database is :"+dataSnapshot.getChildren());
                HelperClass.hideProgressDialog();
                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    System.out.println("Login User is "+userModel);
                    assert userModel != null;
                    System.out.println("Loginn Id with getter"+userModel.getUserId());
                    System.out.println("Loginn Id without getter"+userModel.id);
                    if (!userModel.getUserId().equals(firebaseUser.getUid())){
                        userList.add(userModel);
                        System.out.println("user list is "+userList);
                    }
                    else {
                        ownerurl = userModel.getUserDp();
                    }

                }

                adapter.notifyDataSetChanged();
                userCycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                HelperClass.hideProgressDialog();
                makeAlert(getContext(),"Sorry !!",databaseError.getMessage());
            }
        });
    }

}
