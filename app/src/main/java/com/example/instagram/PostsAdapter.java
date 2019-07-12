package com.example.instagram;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.fragments.DetailFragment;
import com.example.instagram.models.Post;
import com.parse.ParseFile;

import java.util.List;

import static com.example.instagram.HomeActivity.fragmentManager;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_post, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Post post = posts.get(i);
        viewHolder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvHandel;
        private TextView caption;
        private ImageView ivphoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHandel = itemView.findViewById(R.id.tvHandle);
            caption = itemView.findViewById(R.id.tvDescription);
            ivphoto = itemView.findViewById(R.id.ivImage);
        }
        public void bind(final Post post){
            tvHandel.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if(image != null){
                Glide.with(context)
                        .load(image.getUrl())
                        .into(ivphoto);
            }
            caption.setText(post.getDescription());
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Post", post);
                    Fragment fragment = new DetailFragment();
                    fragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                }
            });
        }
    }
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

}
