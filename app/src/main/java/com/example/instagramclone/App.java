package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("H3ICrkU3176YBrAQoq1zP80s30kTBfP4yMr9n4sB")
                // if defined
                .clientKey("MERmTJNesTa0HXUs5w9DEVIxekq2Y2z8wNP7tmJf")
                .server("https://parseapi.back4app.com/")
                .build()
        );

    }
}
