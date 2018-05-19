package com.example.nidailyas.fitme;

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

public class taskAdapter extends
        RecyclerView.Adapter<taskAdapter.tasksViewHolder> {

    private ArrayList<Habit> data;


    public taskAdapter(ArrayList<Habit> data) {
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
    public void onBindViewHolder(final tasksViewHolder holder, int position) {
        Habit habit = data.get(position);

        new HabitManager().getHabitByIdFromDb(new MyCallback<Habit>() {
                                                  @Override
                                                  public void onCallback(Habit habit1) {
                                                      holder.txtTitle.setText(habit1.getHabitName());
                                                      holder.txtDesc.setText(habit1.getDescription());
                                                      new HabitManager().getHabitTimesByHabitId(new MyCallback<ArrayList<String>>() {
                                                          @Override
                                                          public void onCallback(ArrayList<String> element) {
                                                              for (String i : element) {
                                                                  holder.txtTimes.append(i + " \n");
                                                              }
                                                          }
                                                      }, habit1.getHabitId());
                                                      switch (habit1.getHabitName()) {
                                                          case "Run":
                                                              holder.imgIcon.setImageResource(R.drawable.ic_directions_run_white_50dp);
                                                              break;
                                                          case "Walk":
                                                              holder.imgIcon.setImageResource(R.drawable.ic_directions_walk_white_50dp);
                                                              break;
                                                          case "Weight":
                                                              holder.imgIcon.setImageResource(R.drawable.ic_scale);
                                                              break;
                                                          case "Bp":
                                                              holder.imgIcon.setImageResource(R.drawable.ic_bp);
                                                              break;
                                                          case "Drink water":
                                                              holder.imgIcon.setImageResource(R.drawable.ic_water_drop);
                                                              break;
                                                          case "yoga":
                                                              holder.imgIcon.setImageResource(R.drawable.ic_yoga);
                                                              break;

                                                      }
                                                  }
                                              }
                , habit.habitId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class tasksViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtDesc;
        TextView txtTimes;

        public tasksViewHolder(View itemViem) {
            super(itemViem);
            imgIcon = itemViem.findViewById(R.id.imagIcon);
            txtTitle = itemViem.findViewById(R.id.txtTitle);
            txtDesc = itemViem.findViewById(R.id.txtDesc);
            txtTimes = itemViem.findViewById(R.id.txtTimes);
        }

    }


}
