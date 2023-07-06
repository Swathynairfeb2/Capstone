package com.example.planahead_capstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FontAdapter extends ArrayAdapter<String> {
    private LayoutInflater inflater;

    public FontAdapter(Context context, String[] fontNames) {
        super(context, 0, fontNames);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_font, parent, false);

            holder = new ViewHolder();
            holder.fontTextView = convertView.findViewById(R.id.fontTextView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String fontName = getItem(position);

        if (fontName != null) {
            holder.fontTextView.setText(fontName);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView fontTextView;
    }
}
