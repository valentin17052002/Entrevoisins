package com.openclassrooms.entrevoisins.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.Activities.ProfilNeighbourActivity;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteFavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    private final List<Neighbour> mNeighbours;
    private int WhatfragmentlOCAL;
    public static final String DETAIL_NEIGHBOUR = "detailNeighbour";

    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items, int Whatfragment) {
        mNeighbours = items;
        WhatfragmentlOCAL = Whatfragment;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        holder.mDeleteButton.setOnClickListener(v -> {
            if (WhatfragmentlOCAL == 0) {
                EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
                EventBus.getDefault().post(new DeleteFavoriteNeighbourEvent(neighbour));

            } else {
                EventBus.getDefault().post(new DeleteFavoriteNeighbourEvent(neighbour));
            }
        });

        holder.itemView.setOnClickListener(v -> {
            final Context context = holder.itemView.getContext();
            Neighbour detailNeighbour = neighbour;
            launchDetailNeighbour(detailNeighbour, context);
        });

    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void launchDetailNeighbour(Neighbour neighbour, Context context) {
        final Context myContext = context;
        Intent intent = new Intent(myContext, ProfilNeighbourActivity.class);
        Neighbour detailNeighbour = neighbour;
        intent.putExtra(DETAIL_NEIGHBOUR, detailNeighbour);
        context.startActivity(intent);
    }
}
