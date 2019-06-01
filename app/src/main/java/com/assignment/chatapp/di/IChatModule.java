package com.assignment.chatapp.di;

import android.content.Context;

import com.assignment.chatapp.Constants;
import com.assignment.chatapp.repository.IChatRestApi;
import com.assignment.chatapp.repository.MessageReplyRepository;
import com.assignment.chatapp.scope.UserScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class IChatModule {

    @Provides
    @UserScope
    public IChatRestApi providesIChatRestApi(Retrofit retrofit) {
        return retrofit.create(IChatRestApi.class);
    }

    @Provides
    @UserScope
    MessageReplyRepository provideIChatRepository(IChatRestApi iChatRestApi,Context context){

        MessageReplyRepository messageReplyRepository = MessageReplyRepository.getInstance();
        messageReplyRepository.setVariables(iChatRestApi,context);

        return messageReplyRepository;
    }
}
