package com.example.nidailyas.fitme;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.RecordsViewHolder> {

    private ArrayList<Record> data;

    public RecordsAdapter(ArrayList<Record> data) {
        this.data = data;
    }

    @Override
    public RecordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.record_list_item_layout, parent, false);
        return new RecordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecordsViewHolder holder, int position) {
        Record record = data.get(position);
        final String[] title = {null};
        Date dateWithZeroTime = null;

//        final String[] title = new String[1];
        //String title = data.get(position);
        new HabitManager().getHabitByIdFromDb(new MyCallback<Habit>() {
            @Override
            public void onCallback(Habit habit) {
                title[0] = habit.getHabitName();
                holder.textView_record_habitName.setText(title[0]);
            }
        }, record.getHabitId());


        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date date = record.getExamineDateTime();
        holder.textView_record_date.setText(dateFormat.format(date.getTime()));
        holder.textView_record_time.setText(timeFormat.format(date.getTime()));
        holder.textView_record_result.setText(record.getResult());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecordsViewHolder extends RecyclerView.ViewHolder {
        TextView textView_record_habitName;
        TextView textView_record_result;
        TextView textView_record_time;
        TextView textView_record_date;

        public RecordsViewHolder(View itemView) {
            super(itemView);
            textView_record_habitName = itemView.findViewById(R.id.textView_record_habitName);
            textView_record_result = itemView.findViewById(R.id.textView_record_result);
            textView_record_time = itemView.findViewById(R.id.textView_record_time);
            textView_record_date = itemView.findViewById(R.id.textView_record_date);
        }
    }
}
