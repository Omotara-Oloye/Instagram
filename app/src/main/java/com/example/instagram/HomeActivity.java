package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.instagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String imagePath = "";
    private EditText descriptionInput;
    private Button createButton;
    private Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        descriptionInput = findViewById(R.id.description_et);
        createButton = findViewById(R.id.create_btn);
        refreshButton = findViewById(R.id.refresh_btn);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String description = descriptionInput.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                final File file = new File(imagePath);
                final ParseFile parseFile = new ParseFile(file);

                createPost(description, parseFile, user);
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTopPosts();
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                final Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                ParseUser currentUser = ParseUser.getCurrentUser();
            }
        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Come Back Soon!", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


    }

    private void createPost(String description, ParseFile imageFile, ParseUser user){
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Log.d("HomeActivity", "Create Post Sucess!");
                }else{
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadTopPosts(){
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();


        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null){
                    for(int x = 0; x < objects.size(); x++){
                        Log.d("HomeActivity", "Post[" + x + "] = "
                                + objects.get(x).getDescription()
                                + "\nusername = " + objects.get(x).getUser().getUsername()
                        );
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });
    }

}
