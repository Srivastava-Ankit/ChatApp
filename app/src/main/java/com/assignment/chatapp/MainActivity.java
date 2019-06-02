package com.assignment.chatapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.assignment.chatapp.adpater.ChatAdapter;
import com.assignment.chatapp.component.ApplicationComponent;
import com.assignment.chatapp.database.Chat;
import com.assignment.chatapp.database.ChatRepository;
import com.assignment.chatapp.databinding.ActivityMainBinding;
import com.assignment.chatapp.di.DaggerIChatComponent;
import com.assignment.chatapp.di.IChatComponent;
import com.assignment.chatapp.di.IChatModule;
import com.assignment.chatapp.model.Message;
import com.assignment.chatapp.model.MessageResponse;
import com.assignment.chatapp.module.ApplicationModule;
import com.assignment.chatapp.viewmodel.ChatViewModel;
import com.assignment.chatapp.component.DaggerApplicationComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class MainActivity extends AppCompatActivity implements NetworkChangeReceiver.ConnectivityReceiverListener{

    final public static String TAG = "MainActivity";

    private ActivityMainBinding binding;
    private ChatViewModel chatViewModel;
    private String externalId = "Ankit";
    private IChatComponent iChatComponent;
    private ApplicationComponent applicationComponent;
    private RecyclerView mRecyclerView;
    private ChatAdapter mChatAdapter;
    final private Context mContext = this;
    private ChatRepository chatRepository;
    private Boolean isInternetConnected;

    private List<Chat> pendingChat = new ArrayList<>();
    private List<Integer> pending =  new ArrayList<>();
    private NetworkChangeReceiver networkChangeReceiver;

    private List<Chat> chatList = new ArrayList<>();
    private List<Chat> pendingChatList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        chatViewModel =ViewModelProviders.of(this).get(ChatViewModel.class);
        iChatComponent = DaggerIChatComponent.builder()
                .iChatModule(new IChatModule())
                .applicationComponent(applicationComponent)
                .build();

        iChatComponent.inject(chatViewModel);
        chatRepository = new ChatRepository(getApplicationContext());
        initRecyclerView();
        networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        mContext.registerReceiver(networkChangeReceiver, intentFilter);
        binding.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.editText.getText().toString();
                if (!message.isEmpty()) {
                    onSendButtonClicked(message);
                } else {
                    Toast.makeText(mContext, "Please Type Message", Toast.LENGTH_LONG).show();
                }
            }
        });

        ObserveMessageResponseSuccess();
        ObserveMessageResponseError();


    }



    private void ObserveMessageResponseSuccess() {
        chatViewModel.getMessageResponseSuccess().observe(this, new Observer<MessageResponse>() {
            @Override
            public void onChanged(@Nullable MessageResponse messageResponse) {
                if(messageResponse.getMessage() != null) {
                 Log.d("Ankit Response", "Got it");
                }
            }
        });

    }


    private void ObserveMessageResponseError() {
        chatViewModel.getMessageResponseError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Log.d(TAG, "Response Error");
            }
        });
    }

    public void getReplyMessage(String message){
        chatViewModel.getMessageReply(message,externalId, chatRepository);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContext.unregisterReceiver(networkChangeReceiver);
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.chatsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        getAllChatHistory();
    }


    private void onSendButtonClicked(String message) {
        binding.editText.setText("");

        if (Utils.hasActiveInternetConnection(mContext)) {
            getReplyMessage(message);
            isInternetConnected = true;
        } else {
            isInternetConnected= false;
            Toast.makeText(mContext, "Please connect to your internet", Toast.LENGTH_LONG).show();
        }
        chatRepository.insertChat(message, true, isInternetConnected, Utils.getTime());
    }

    public void getAllChatHistory(){
        chatRepository.getChats().observe(this, new Observer<List<Chat>>() {
            @Override
            public void onChanged(@Nullable List<Chat> chats) {
                chatList = chats;
                mChatAdapter = new ChatAdapter(mContext,chatList);
                mRecyclerView.setAdapter(mChatAdapter);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected) {
            for(Chat chat : chatList){
                if(!chat.getSentStatus() && chat.getChatStatus()) {
                    chat.setSentStatus(true);
                    chat.setTime(Utils.getTime());
                    getReplyMessage(chat.getMessage());
                    chatRepository.updateChat(chat);
                }
            }
        }
    }



    public void setConnectivityListener(NetworkChangeReceiver.ConnectivityReceiverListener listener) {
        NetworkChangeReceiver.connectivityReceiverListener = listener;
    }


   public void retrysentMessage() {
       chatRepository.getPendingChats().observe(this, new Observer<List<Chat>>() {
           @Override
           public void onChanged(@Nullable List<Chat> chats) {
               pendingChat = chats;
               updatePendingChat();
           }
       });
   }



   public void updatePendingChat(){

        for(Chat chat: pendingChat){

        }
   }
}




