package com.assignment.chatapp.module;

import android.app.Application;
import android.content.Context;

import com.assignment.chatapp.Constants;
import com.assignment.chatapp.IntegerTypeAdapter;
import com.assignment.chatapp.Urls;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;


import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private final Context mContext;
    private final int TIME_OUT_DURATION = 60;

    public ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return mContext;
    }


    @Provides
    @Singleton
    public Retrofit provideRetrofitClient(@Named(Constants.GSON_OF_GOOGLE) Gson gson, OkHttpClient okHttpClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.END_POINT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    @Provides
    @Singleton
    public OkHttpClient provideHttpClient(final Context context) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(TIME_OUT_DURATION, TimeUnit.SECONDS);
        httpClient.readTimeout(TIME_OUT_DURATION, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(logging);

        OkHttpClient client = httpClient.build();

        return client;
    }

    @Provides
    @Singleton
    @Named(Constants.GSON_OF_RETROFIT)
    public Gson provideRetrofitGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    @Named(Constants.GSON_OF_GOOGLE)
    public Gson provideGoogleGson() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerTypeAdapter())
                .create();

        return gson;
    }


}
