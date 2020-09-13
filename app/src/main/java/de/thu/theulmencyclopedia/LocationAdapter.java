package de.thu.theulmencyclopedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationAdapter extends BaseAdapter {
    Location[] locations;

    public LocationAdapter(Location[] locations) {
        this.locations = locations;
    }

    @Override
    public int getCount() {
        return locations.length;
    }

    @Override
    public Object getItem(int position) {
        return locations[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        Location location = locations[position];

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_location, null, false);
        }

        TextView textLocation = (TextView) convertView.findViewById(R.id.textLocation);
        textLocation.setText(location.getName());

        ImageView imageLocation = (ImageView) convertView.findViewById(R.id.imageLocation);
        String imageName = "image_location_" + location.getId();
        int imageId = context.getResources().getIdentifier(imageName,
                "drawable", context.getPackageName());
        imageLocation.setImageResource(imageId);

        return convertView;
    }
}
