package com.example.nidailyas.fitme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CardAdapter extends ArrayAdapter<TipPair> {

    public CardAdapter(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageView imageView = convertView.findViewById(R.id.imageView_tip);
        imageView.setImageResource(getItem(position).getImage());

        TextView textview_tipTitle = convertView.findViewById(R.id.textview_tipTitle);
        textview_tipTitle.setText(getItem(position).getTitle());

        TextView textview_tipText = convertView.findViewById(R.id.textview_tipText);
        textview_tipText.setText(getItem(position).getText());

        return convertView;
    }
}
