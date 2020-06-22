package com.solutions.friendy;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.widget.Toast;

import com.cometchat.pro.core.AppSettings;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.omninos.util_data.AppPreferences;
import com.omninos.util_data.CommonUtils;
import com.solutions.friendy.Retrofit.StringContract;

import listeners.CometChatCallListener;
import timber.log.Timber;


public class App extends Application {
    private static final String TAG = "App";
    protected LocationManager locationManager;

    private Context context;

    private static AppPreferences appPreference;

    private static Singleton singleton;

    public static App appInstance;

    public static App getInstance()
    {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        appPreference = AppPreferences.init(context, "friendy");
        singleton=new Singleton();

        AppSettings appSettings=new AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(StringContract.AppDetails.REGION).build();
        CometChat.init(this, StringContract.AppDetails.APP_ID,appSettings,new CometChat.CallbackListener<String>() {

            @Override
            public void onSuccess(String s) {
//
            }

            @Override
            public void onError(CometChatException e) {
                Toast.makeText(App.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Timber.d("onError: %s", e.getMessage());
            }

        });

        CometChatCallListener.addCallListener(TAG,this);
        createNotificationChannel();

    }

    public static AppPreferences getAppPreference() {
        return appPreference;
    }


    public static Singleton getSinlton() {
        return singleton;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("2", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        CometChatCallListener.removeCallListener(TAG);

    }

}