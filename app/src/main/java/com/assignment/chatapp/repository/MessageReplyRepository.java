package com.assignment.chatapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.assignment.chatapp.Constants;
import com.assignment.chatapp.Utils;
import com.assignment.chatapp.database.ChatRepository;
import com.assignment.chatapp.model.MessageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageReplyRepository {

    private Context mContext;
    private IChatRestApi iChatRestApi;
    private static MessageReplyRepository instance;
    private ChatRepository chatRepository;


    public static MessageReplyRepository getInstance() {
        if (instance == null) {
            synchronized (MessageReplyRepository.class) {
                if (instance == null) {
                    instance = new MessageReplyRepository();
                }
            }
        }

        return instance;
    }

    public void setVariables(IChatRestApi restApi, Context context) {
        iChatRestApi = restApi;
        mContext = context;
    }

    public void setChatRepository(ChatRepository chatRepository){
        this.chatRepository = chatRepository;
    }


    public void getMessageReply(String message, String externalId, final MutableLiveData<MessageResponse> messageResponseSuccess,
                                final MutableLiveData<Boolean> messageResponseError){
        iChatRestApi.getReplyMessage(Constants.apiKey, message, Constants.chatBotId, externalId).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if(response.isSuccessful()){
                    messageResponseSuccess.setValue(response.body());
                    String message = response.body().getMessage().getMessage();
                    chatRepository.insertChat(message, false, Utils.getTime());
                }else {
                    messageResponseError.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                messageResponseError.setValue(false);
            }
        });
    }

}
