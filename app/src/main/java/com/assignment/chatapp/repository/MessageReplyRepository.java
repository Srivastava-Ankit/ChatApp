package com.assignment.chatapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.assignment.chatapp.Constants;
import com.assignment.chatapp.model.MessageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageReplyRepository {

    private Context mContext;
    private IChatRestApi iChatRestApi;
    private static MessageReplyRepository instance;


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


    public void getMessageReply(String message, String externalId, final MutableLiveData<MessageResponse> messageResponseSuccess,
                                final MutableLiveData<Boolean> messageResponseError){
        iChatRestApi.getReplyMessage(Constants.apiKey, message, Constants.chatBotId, externalId).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if(response.isSuccessful()){
                    messageResponseSuccess.setValue(response.body());
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
