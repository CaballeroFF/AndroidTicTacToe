package com.caballero.tictactoe.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.caballero.tictactoe.R;

public class SpinnerAdapter extends ArrayAdapter<String> {

    private int[] spinnerImages;
    private Context context;

    public SpinnerAdapter(@NonNull Context context, int[] resources) {
        super(context, R.layout.spinner_item);
        this.context = context;
        spinnerImages = resources;
    }

    @Override
    public int getCount() {
        return spinnerImages.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.spinner_item, parent, false);
            viewHolder.image = convertView.findViewById(R.id.spinner_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.image.setImageResource(spinnerImages[position]);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    private static class ViewHolder {
        ImageView image;
    }
}
