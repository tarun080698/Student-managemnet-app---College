package com.example.project;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener onItemClickListener;

    public PostAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.post_activity, parent, false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder postViewHolder, int i) {
        final Upload upload = mUploads.get(i);
        postViewHolder.uName.setText(upload.getuName());
        postViewHolder.organis.setText(upload.getOrgan());
        postViewHolder.pTitle.setText(upload.getPostT());
        postViewHolder.pDesc.setText(upload.getPostD());
        Picasso.with(mContext).load(upload.getImageUrl())
                .placeholder(R.drawable.ic_watch_later_black_48dp)
                .fit()
                .centerInside()
                .into(postViewHolder.postP);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView organis, uName, pTime, pTitle, pDesc;
        public ImageView proP, postP;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            uName = itemView.findViewById(R.id.name);
            organis = itemView.findViewById(R.id.org);
            pTime = itemView.findViewById(R.id.time);
            pTitle = itemView.findViewById(R.id.post_title);
            pDesc = itemView.findViewById(R.id.post_desc);
            proP = itemView.findViewById(R.id.pro_pic);
            postP = itemView.findViewById(R.id.post_pic);

            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("");
            MenuItem delete = menu.add(Menu.NONE, 1, 1, "Delete");
            SpannableString s = new SpannableString("");
            s.setSpan(new BackgroundColorSpan(Color.BLACK), 2, s.length(), 0);
            //            add share here if you want to
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            onItemClickListener.onDeleteClick(position);
                    }
                    return true;
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener {

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}
