package com.example.instagram;

import android.app.Application;

import com.example.instagram.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("omotara_insta")
                .clientKey("my_left_toe")
                .server("https://omotara-oloye-fbu-instagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
