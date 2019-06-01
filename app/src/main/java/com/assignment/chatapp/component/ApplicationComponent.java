package com.assignment.chatapp.component;


import android.content.Context;

import com.assignment.chatapp.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {


    Retrofit provideRetrofitClient();
    Context providesContext();

}
