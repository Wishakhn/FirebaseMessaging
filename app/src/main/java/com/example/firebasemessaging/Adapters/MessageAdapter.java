package com.example.firebasemessaging.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebasemessaging.HelperClass;
import com.example.firebasemessaging.Models.ChatModel;
import com.example.firebasemessaging.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.firebasemessaging.HelperClass.MSG_TYPE_LEFT;
import static com.example.firebasemessaging.HelperClass.MSG_TYPE_RIGHT;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.mesgViewHolder> {
    List<ChatModel> chats;
    Context context;
    LayoutInflater inflater;
    String senderimgurl;
    String geterimgurl;
    FirebaseUser fireUser;
    int type =3;
    public MessageAdapter(List<ChatModel> chats, Context context, String senderimgurl, String geterimgurl) {
        this.chats = chats;
        this.context = context;
        this.senderimgurl = senderimgurl;
        this.geterimgurl = geterimgurl;
    }

    @NonNull
    @Override
    public MessageAdapter.mesgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        if (viewType == MSG_TYPE_RIGHT) {
            View v = inflater.inflate(R.layout.send_msg_item, parent, false);
            return new mesgViewHolder(v);
        } else {
            View v = inflater.inflate(R.layout.recevie_msg_item, parent, false);
            return new mesgViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.mesgViewHolder holder, int position) {
        if (type==MSG_TYPE_RIGHT){
            Glide.with(context).load(senderimgurl).into(holder.msgicon);
        }
        else {
            Glide.with(context).load(geterimgurl).into(holder.msgicon);
        }
        ChatModel chatlist = chats.get(position);
        holder.msgtext.setText(chatlist.getMessage());

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class mesgViewHolder extends RecyclerView.ViewHolder {
        CircleImageView msgicon;
        TextView msgtext;

        public mesgViewHolder(@NonNull View itemView) {
            super(itemView);
            msgicon = itemView.findViewById(R.id.msgicon);
            msgtext = itemView.findViewById(R.id.msgtext);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fireUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chats.get(position).getSender().equals(fireUser.getUid())) {
            type = MSG_TYPE_RIGHT;
            return MSG_TYPE_RIGHT;
        } else {
            type = MSG_TYPE_LEFT;
            return MSG_TYPE_LEFT;
        }
    }
}
