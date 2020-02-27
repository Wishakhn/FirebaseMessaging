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

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.chatViewholder> {
    List<UserModel> chats;
    Context context;
    LayoutInflater inflater;
String ownerurl;
    public ChatAdapter(Context context, List<UserModel> chats, String ownerurl) {
        this.context = context;
        this.ownerurl = ownerurl;
        this.chats = chats;
    }

    @NonNull
    @Override
    public ChatAdapter.chatViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.chat_items, parent, false);
        return new chatViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.chatViewholder holder, int position) {
        final UserModel list = chats.get(position);
        if (list.getUserDp().equals("default")) {
            holder.chaticon.setImageResource(R.drawable.userdp);
        } else {
            Glide.with(context).load(list.getUserDp()).into(holder.chaticon);
        }
        holder.chatmsg.setText("Hello How are you...");
        holder.chatname.setText(list.getUserName());
        holder.container.setOnClickListener(new View.OnClickListener() {
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
        return chats.size();
    }

    public class chatViewholder extends RecyclerView.ViewHolder {
        CircleImageView chaticon;
        TextView chatname;
        TextView chatmsg;
        LinearLayout container;

        public chatViewholder(@NonNull View item) {
            super(item);
            chaticon = item.findViewById(R.id.chaticon);
            chatname = item.findViewById(R.id.chatname);
            chatmsg = item.findViewById(R.id.chatmsg);
            container = item.findViewById(R.id.container);
        }
    }
}
