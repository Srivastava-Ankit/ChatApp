package com.assignment.chatapp.repository;

import com.assignment.chatapp.Urls;
import com.assignment.chatapp.model.MessageResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface IChatRestApi {


    @GET (Urls.END_POINT)
    Call<MessageResponse> getReplyMessage(@Query("apiKey") String apiKey, @Query("message") String message,
                                          @Query("chatBotID") String chatBotID,  @Query("externalID") String externalID);
}
