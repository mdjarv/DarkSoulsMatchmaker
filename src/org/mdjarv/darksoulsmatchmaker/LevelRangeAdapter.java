package org.mdjarv.darksoulsmatchmaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LevelRangeAdapter extends ArrayAdapter<LevelRange> {
    private ArrayList<LevelRange> objects;

    public LevelRangeAdapter(Context context, int textViewResourceId, ArrayList<LevelRange> objects) {
        super(context, textViewResourceId);
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LevelRange levelRange = objects.get(position);

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.level_range, null);
        }

        TextView minLevel = (TextView) v.findViewById(R.id.minLevel);
        TextView maxLevel = (TextView) v.findViewById(R.id.maxLevel);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);

        minLevel.setText("" + levelRange.getMin());
        maxLevel.setText("" + levelRange.getMax());
        imageView.setImageResource(levelRange.getImageId());

        return v;
    }
}
