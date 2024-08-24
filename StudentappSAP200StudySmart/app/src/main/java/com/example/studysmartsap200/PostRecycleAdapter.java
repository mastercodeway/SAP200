package com.example.studysmartsap200;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Horton, J .(2021). Android programming for beginners. 3. Uppl. Birmingham: Packt Publishing.. Användt strukturer hur man skapar en recycleview adapter.
public class PostRecycleAdapter extends RecyclerView.Adapter<PostRecycleAdapter.ViewHolder> {

    private ArrayList<Post> posts;
    private Context context;
    private OnItemClickListener listener;

    public PostRecycleAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Inställningsmetod för att sätta lyssnaren
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_post_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.date.setText(post.getDate());
        holder.username.setText(post.getUserName());
        holder.content.setText(post.getPostContent());
        holder.likes.setText(String.valueOf(post.getLikes()));

        // Om positionen är noll, visa titeln och antalet svar bara första posten ska ha titel. Vilket är start tråd inlägget.
        if (position == 0) {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(post.getPostTitle());
            holder.replies.setVisibility(View.VISIBLE);
            holder.replies.setText(String.valueOf(post.getReplies()));
        } else {
            // Annars göm titeln och antalet svar
            holder.title.setVisibility(View.GONE);
            holder.replies.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // ImageView profile;
        // ImageView favorite;
        TextView title;
        TextView username;
        //TextView replies;
        TextView date;
        TextView replies;
        TextView content;
        TextView likes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //  profile = itemView.findViewById(R.id.threadProfileImgView);
            username = itemView.findViewById(R.id.postTxtViewUsername);
            // favorite = itemView.findViewById(R.id.threadFavoriteImageView);
            title = itemView.findViewById(R.id.postTitleTxtView);
            replies = itemView.findViewById(R.id.postRepliesTxtView);
            date = itemView.findViewById(R.id.postDateRepliesTxtView);
            content = itemView.findViewById(R.id.postContentTxtView);
            likes = itemView.findViewById(R.id.postLikesTxtView);

        }
    }
}
