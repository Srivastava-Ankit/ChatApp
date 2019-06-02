package com.assignment.chatapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class NetworkChangeReceiver extends BroadcastReceiver {
    public static ConnectivityReceiverListener connectivityReceiverListener;

    public NetworkChangeReceiver(){
        super();
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {

        connectivityReceiverListener.onNetworkConnectionChanged(Utils.hasActiveInternetConnection(context));
    }


    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}