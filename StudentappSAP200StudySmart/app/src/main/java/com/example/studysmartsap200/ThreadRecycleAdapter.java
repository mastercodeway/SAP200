package com.example.studysmartsap200;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

// Horton, J .(2021). Android programming for beginners. 3. Uppl. Birmingham: Packt Publishing.
public class ThreadRecycleAdapter extends RecyclerView.Adapter<ThreadRecycleAdapter.ViewHolder> {

    private ArrayList<ForumThread> threadList;
    private Context context;
    private OnItemClickListener listener;

    public ThreadRecycleAdapter(Context context, ArrayList<ForumThread>threadList) {
        this.context = context;
        this.threadList = threadList;
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
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_thread_forum, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String replies = String.valueOf(threadList.get(position).getReplies());
        holder.title.setText(threadList.get(position).getThreadTitle());
        holder.date.setText(threadList.get(position).getDate());
        holder.username.setText(threadList.get(position).getUser());
        holder.replies.setText(replies);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(clickedPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return threadList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
      // ImageView profile;
      // ImageView favorite;
       TextView title;
       TextView username;
       //TextView replies;
       TextView date;
       TextView replies;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          //  profile = itemView.findViewById(R.id.threadProfileImgView);
            username = itemView.findViewById(R.id.threadTxtViewUsername);
           // favorite = itemView.findViewById(R.id.threadFavoriteImageView);
            title = itemView.findViewById(R.id.threadTitleTxtView);
          //  replies = itemView.findViewById(R.id.threadRepliesTxtView);
            date = itemView.findViewById(R.id.threadDateRepliesTxtView);
            replies = itemView.findViewById(R.id.threadRepliesTxtView);

        }
    }
}
