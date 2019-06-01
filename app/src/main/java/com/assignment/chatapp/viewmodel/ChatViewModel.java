package com.assignment.chatapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.assignment.chatapp.model.MessageResponse;
import com.assignment.chatapp.repository.MessageReplyRepository;

import javax.inject.Inject;



public class ChatViewModel extends AndroidViewModel {

    @Inject
    MessageReplyRepository repository;

    MutableLiveData<MessageResponse> messageResponseSuccess = new MutableLiveData<>();
    MutableLiveData<Boolean> messageResponseError = new MutableLiveData<>();

    public ChatViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<MessageResponse> getMessageResponseSuccess() {
        return messageResponseSuccess;
    }

    public MutableLiveData<Boolean> getMessageResponseError() {
        return messageResponseError;
    }

    public void getMessageReply(String message, String externalId){
        repository.getMessageReply(message, externalId, messageResponseSuccess, messageResponseError);
    }

}
