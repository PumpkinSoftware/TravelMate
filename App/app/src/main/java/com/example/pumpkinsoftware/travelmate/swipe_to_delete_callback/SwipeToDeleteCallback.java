package com.example.pumpkinsoftware.travelmate.swipe_to_delete_callback;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.pumpkinsoftware.travelmate.users_adapter.UsersAdapter;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private UsersAdapter mAdapter;

    public SwipeToDeleteCallback(UsersAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mAdapter.setViewHolder((UsersAdapter.ViewHolder) viewHolder);
        mAdapter.deleteItem(position);
    }
}
