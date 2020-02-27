package com.example.firebasemessaging.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebasemessaging.Activities.ChatboxActivity;
import com.example.firebasemessaging.Models.UserModel;
import com.example.firebasemessaging.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.userViewholder> {
List<UserModel> users;
Context context;
LayoutInflater inflater;
    String ownerurl="default";
    public UserAdapter(List<UserModel> users, String ownerurl, Context context) {
        this.users = users;
        this.context = context;
        this.ownerurl = ownerurl;
    }

    @NonNull
    @Override
    public UserAdapter.userViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.user_items, parent, false);
        return new userViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.userViewholder holder, int position) {
        final UserModel list = users.get(position);
        holder.username.setText(list.getUserName());
        final String url = list.getUserDp();
        if (url.equalsIgnoreCase("default")){
            holder.userDp.setImageResource(R.drawable.userdp);
        }
        else {
            Glide.with(context).load(url).into(holder.userDp);
        }
        holder.usercontainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chattent = new Intent(context, ChatboxActivity.class);
                chattent.putExtra("image", ownerurl);
                chattent.putExtra("uid", list.getUserId());
                view.getContext().startActivity(chattent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class userViewholder extends RecyclerView.ViewHolder {
        LinearLayout usercontainer;
        CircleImageView userDp;
        TextView username;

        public userViewholder(@NonNull View view) {
            super(view);
            username = view.findViewById(R.id.username);
            usercontainer = view.findViewById(R.id.usercontainer);
            userDp = view.findViewById(R.id.userdp);
        }
    }
}
