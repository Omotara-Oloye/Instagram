package com.example.instagram.fragments;

import android.util.Log;

import com.example.instagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment {

    @Override
    public void loadTopPosts() {
        ParseQuery<Post> postsQuery = new ParseQuery<Post>(Post.class);
        postsQuery.include("user");
        postsQuery.setLimit(20);
        postsQuery.addDescendingOrder("createdAt");
        postsQuery.whereEqualTo("user", ParseUser.getCurrentUser());
        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null){
                    for(int x = 0; x < objects.size(); x++){
                        Log.d("PostsFragment", "Post[" + x + "] = "
                                + objects.get(x).getDescription()
                                + "\nusername = " + objects.get(x).getUser().getUsername());

                    }
                    mPosts.addAll(objects);
                    adapter.notifyDataSetChanged();
                }else{
                    Log.e("PostsFragment", "Error with query");
                    e.printStackTrace();
                    return;
                }
            }
        });
    }
}
