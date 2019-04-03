package com.boxnotfound.sinewavegenerator.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.boxnotfound.sinewavegenerator.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WaveformSpinnerAdapter extends ArrayAdapter {

    private int[] waveformImages;
    Context context;

    public WaveformSpinnerAdapter(Context context, int[] images) {
        super(context, R.layout.waveform_spinner_item);
        this.context = context;
        waveformImages = images;
    }

    @Override
    public int getCount() {
        return waveformImages.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.waveform_spinner_item, parent, false);
            viewHolder.waveFormImage = convertView.findViewById(R.id.iv_waveform);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.waveFormImage.setImageResource(waveformImages[position]);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    private static class ViewHolder {
        ImageView waveFormImage;
    }
}
