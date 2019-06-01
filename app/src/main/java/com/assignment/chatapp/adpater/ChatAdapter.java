package com.assignment.chatapp.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.assignment.chatapp.R;
import com.assignment.chatapp.Utils;
import com.assignment.chatapp.model.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<String> mChatMessages;
    private Context mContext;
    private final int TYPE_INCOMING = 1;
    private final int TYPE_OUTGOING = 2;
    private List<Boolean> statusList;
    private List<String> timeList;


    private static final String MSG_TYPE_TEXT = "text";


    public ChatAdapter(Context context, List<String> chatMessages, List<Boolean> messageStatusList, List<String> time)
    {
        mContext = context;
        mChatMessages = chatMessages;
        statusList = messageStatusList;
        timeList = time;
    }



    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_INCOMING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messsage_incoming, parent, false);
            return new IncomingViewHolder(view);
        }
        if (viewType == TYPE_OUTGOING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messsage_outgoing, parent, false);
            return new OutgoingViewHolder(view);
        }

        return super.createViewHolder(parent,viewType);

    }

    @Override
    public void onBindViewHolder(ChatAdapter.ChatViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_INCOMING) {
            String chatMessage = mChatMessages.get(position);
            ((IncomingViewHolder)holder).chatTV.setVisibility(View.VISIBLE);
            ((IncomingViewHolder)holder).chatTV.setText(chatMessage);
            ((IncomingViewHolder)holder).chatIV.setVisibility(View.GONE);
            ((IncomingViewHolder) holder).timeTV.setText(timeList.get(position));
        }

        if(getItemViewType(position) == TYPE_OUTGOING){
            String chatMessage = mChatMessages.get(position);
            ((OutgoingViewHolder)holder).chatTV.setVisibility(View.VISIBLE);
            ((OutgoingViewHolder)holder).chatTV.setText(chatMessage);
            ((OutgoingViewHolder)holder).chatIV.setVisibility(View.GONE);
            ((OutgoingViewHolder) holder).timeTV.setText(timeList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return mChatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        if (messageFromCurrentUser(position)) {
            return TYPE_OUTGOING;
        }

        return TYPE_INCOMING;
    }


    public void clearAllData(){
        mChatMessages.clear();
        statusList.clear();
        timeList.clear();
        notifyDataSetChanged();
    }

    private boolean messageFromCurrentUser(int positon) {
        return statusList.get(positon);
    }

    class ChatViewHolder extends RecyclerView.ViewHolder{

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    class IncomingViewHolder extends ChatViewHolder implements View.OnClickListener {

        TextView chatTV, timeTV;
        ImageView chatIV;
        public IncomingViewHolder(View v) {
            super(v);
            chatTV = (TextView) v.findViewById(R.id.chatTV);
            timeTV = (TextView) v.findViewById(R.id.timeTV);
            chatIV = (ImageView) v.findViewById(R.id.chatIV);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
            }
        }
    }


    class OutgoingViewHolder extends ChatViewHolder implements View.OnClickListener {

        TextView chatTV, timeTV;
        ImageView chatIV, messageStatusIV;

        public OutgoingViewHolder(View v) {
            super(v);
            chatTV = (TextView) v.findViewById(R.id.chatTV);
            timeTV = (TextView) v.findViewById(R.id.timeTV);
            messageStatusIV = (ImageView) v.findViewById(R.id.messageStatusIV);
            chatIV = (ImageView) v.findViewById(R.id.chatIV);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
            }
        }
    }
}
