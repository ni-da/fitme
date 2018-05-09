package com.example.nidailyas.fitme;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.RecordsViewHolder> {

    private ArrayList<String> data;

    public RecordsAdapter(ArrayList<String> data) {
        this.data = data;
    }

    @Override
    public RecordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.record_list_item_layout, parent, false);
        return new RecordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordsViewHolder holder, int position) {
        String title = data.get(position);
        holder.textView_recordInfo.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecordsViewHolder extends RecyclerView.ViewHolder {
        TextView textView_recordInfo;
        public RecordsViewHolder(View itemView) {
            super(itemView);
            textView_recordInfo = itemView.findViewById(R.id.textView_recordInfo);
        }
    }
}
