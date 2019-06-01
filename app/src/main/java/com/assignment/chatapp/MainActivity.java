package com.assignment.chatapp;

import android.arch.lifecycle.Observer;
import android.content.Context;
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


public class MainActivity extends AppCompatActivity {

    final public static String TAG = "MainActivity";

    private ActivityMainBinding binding;
    private ChatViewModel chatViewModel;
    private String externalId = "Ankit";
    private IChatComponent iChatComponent;
    private ApplicationComponent applicationComponent;
    private RecyclerView mRecyclerView;
    private ChatAdapter mChatAdapter;
    private List<String> messageList = new ArrayList<>();
    private List<Boolean> messageStatusList = new ArrayList<>();
    final private Context mContext = this;


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

        binding.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.editText.getText().toString();
                if(!message.isEmpty()) {
                    messageList.add(message);
                    messageStatusList.add(true);
                    onSendButtonClicked();
                    mChatAdapter = new ChatAdapter(mContext, messageList, messageStatusList);
                    mRecyclerView.setAdapter(mChatAdapter);
                } else {
                    Toast.makeText(mContext, "Please Type Message", Toast.LENGTH_LONG).show();
                }
            }
        });

        ObserveMessageResponseSuccess();
        ObserveMessageResponseError();

        initRecyclerView();
    }



    private void ObserveMessageResponseSuccess() {
        chatViewModel.getMessageResponseSuccess().observe(this, new Observer<MessageResponse>() {
            @Override
            public void onChanged(@Nullable MessageResponse messageResponse) {
                if(messageResponse.getMessage() != null) {
                    messageList.add(messageResponse.getMessage().getMessage());
                    messageStatusList.add(false);
                    mChatAdapter = new ChatAdapter(mContext, messageList, messageStatusList);
                    mRecyclerView.setAdapter(mChatAdapter);
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
        chatViewModel.getMessageReply(message,externalId);
    }


    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.chatsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }


    private void onSendButtonClicked() {
        String message = binding.editText.getText().toString();
        binding.editText.setText("");
        getReplyMessage(message);
    }
}
