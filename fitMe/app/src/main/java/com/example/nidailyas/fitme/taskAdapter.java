package com.example.nidailyas.fitme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by NidaI on 4/6/2018.
 */

public class taskAdapter extends RecyclerView.Adapter<taskAdapter.tasksViewHolder> {
    Context context;
    tasksViewHolder holder;
    private ArrayList<Pair> data;


    public taskAdapter(Context context, ArrayList<Pair> data) {
        this.context = context;
        this.data = data;
    }


    //This method inflates view present in the RecyclerView
    @Override
    public tasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        return new tasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(tasksViewHolder holder, int position) {
        holder.txtTitle.setText(data.get(position).getKey());
        holder.txtDesc.setText(data.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clear() {
        final int size = data.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                data.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }

    public class tasksViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtDesc;

        public tasksViewHolder(View itemViem) {
            super(itemViem);
            imgIcon = itemViem.findViewById(R.id.imagIcon);
            txtTitle = itemViem.findViewById(R.id.txtTitle);
            txtDesc = itemViem.findViewById(R.id.txtDesc);
        }

    }


}
