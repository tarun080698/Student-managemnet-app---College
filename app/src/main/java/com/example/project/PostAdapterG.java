package com.example.project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class PostAdapterG extends RecyclerView.Adapter<PostAdapterG.PostViewHolderg> {


    private Context mContext;
    private List<Upload> mUploads;

    public PostAdapterG(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public PostViewHolderg onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.post_activity, parent, false);
        return new PostAdapterG.PostViewHolderg(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostAdapterG.PostViewHolderg postViewHolderg, int i) {
        Upload upload = mUploads.get(i);
        postViewHolderg.uName.setText(upload.getuName());
        postViewHolderg.organis.setText(upload.getOrgan());
        postViewHolderg.pTitle.setText(upload.getPostT());
        postViewHolderg.pDesc.setText(upload.getPostD());
        Picasso.with(mContext).load(upload.getImageUrl())
                .placeholder(R.drawable.ic_watch_later_black_48dp)
                .fit()
                .centerInside()
                .into(postViewHolderg.postP);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class PostViewHolderg extends RecyclerView.ViewHolder {

        public TextView organis, uName, pTime, pTitle, pDesc, buttonViewOption;
        public ImageView proP, postP;

        public PostViewHolderg(@NonNull View itemView) {
            super(itemView);

            uName = itemView.findViewById(R.id.name);
            organis = itemView.findViewById(R.id.org);
            pTime = itemView.findViewById(R.id.time);
            pTitle = itemView.findViewById(R.id.post_title);
            pDesc = itemView.findViewById(R.id.post_desc);
            proP = itemView.findViewById(R.id.pro_pic);
            postP = itemView.findViewById(R.id.post_pic);
        }
    }
}
