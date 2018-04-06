package com.example.nidailyas.fitme;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by NidaI on 4/6/2018.
 */

public class taskAdapter extends RecyclerView.Adapter<taskAdapter.tasksViewHolder> {
    private String[] data;
    public taskAdapter(String[] data){
        this.data = data;
    }
    @Override
    public tasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        return new tasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(tasksViewHolder holder, int position) {
        String title = data[position];
        holder.txtTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class tasksViewHolder extends RecyclerView.ViewHolder{
        ImageView imgIcon;
        TextView txtTitle;

        public tasksViewHolder(View itemViem){
            super(itemViem);
            imgIcon = (ImageView) itemViem.findViewById(R.id.imagIcon);
            txtTitle = (TextView) itemViem.findViewById(R.id.txtTitle);
        }
    }
}
