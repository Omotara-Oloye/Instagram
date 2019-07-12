package com.example.instagram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.R;
import com.example.instagram.models.Post;

public class DetailFragment extends Fragment {

    private ImageView ivImageDetail;
    private TextView tvDescriptionDetail;
    private TextView tvTimestamp;
    private TextView tvHandelDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivImageDetail = view.findViewById(R.id.ivImageDetail);
        tvDescriptionDetail = view.findViewById(R.id.tvDescriptionDetail);
        tvTimestamp = view.findViewById(R.id.tvTimestamp);
        tvHandelDetail = view.findViewById(R.id.tvHandleDetail);

        Post post = getArguments().getParcelable("Post");

        tvHandelDetail.setText(post.getUser().getUsername());
        tvDescriptionDetail.setText(post.getDescription());
        tvTimestamp.setText(post.getCreatedAt().toString());

        if(post.getImage().getUrl() != null){
            Glide.with(this)
                    .load(post.getImage().getUrl())
                    .into(ivImageDetail);
        }



    }
}
