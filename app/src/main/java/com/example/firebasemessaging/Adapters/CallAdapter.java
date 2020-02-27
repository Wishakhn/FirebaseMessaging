package com.example.firebasemessaging.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasemessaging.Models.CallModel;
import com.example.firebasemessaging.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.callViewholder> {
    List<CallModel> calls = getCalls();
    LayoutInflater inflater;

    private List<CallModel> getCalls() {
        List<CallModel> call = new ArrayList<>();
        call.add(new CallModel(0, R.drawable.image, R.drawable.ic_call_received, "incoming"));
        call.add(new CallModel(0, R.drawable.image, R.drawable.ic_call_made, "outgoing"));
        call.add(new CallModel(0, R.drawable.image, R.drawable.ic_call_missed, "missed"));
        call.add(new CallModel(0, R.drawable.image, R.drawable.ic_call_received, "incoming"));
        call.add(new CallModel(0, R.drawable.image, R.drawable.ic_call_made, "outgoing"));
        call.add(new CallModel(0, R.drawable.image, R.drawable.ic_call_missed, "missed"));
        return call;
    }


    @NonNull
    @Override
    public CallAdapter.callViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.call_items, parent, false);
        return new callViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull callViewholder holder, int position) {
        CallModel list = calls.get(position);
        holder.calername.setText("Ella Michiton");
        holder.calicon.setImageResource(list.getCallicon());
        holder.callicon.setImageResource(list.getImgId());
        holder.calltype.setText(list.getCalltype());
    }

    @Override
    public int getItemCount() {
        return calls.size();
    }

    public class callViewholder extends RecyclerView.ViewHolder {
        TextView calltype;
        TextView calername;
        ImageView calicon;
        CircleImageView callicon;

        public callViewholder(@NonNull View itemView) {
            super(itemView);
            calltype = itemView.findViewById(R.id.calltype);
            calername = itemView.findViewById(R.id.calername);
            calicon = itemView.findViewById(R.id.calicon);
            callicon = itemView.findViewById(R.id.callicon);

        }
    }
}
