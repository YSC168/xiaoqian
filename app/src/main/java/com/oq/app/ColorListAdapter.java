package com.oq.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import java.util.*;
import android.graphics.*;
import android.widget.*;
import java.net.*;
import java.io.*;

public class ColorListAdapter extends RecyclerView.Adapter<ColorListAdapter.ViewHolder>{
    private Context mContext;
    private List<ColorList> ColorList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
     final   ImageView colorImage;
        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            colorImage = (ImageView) view.findViewById(R.id.color_image);
        }
    }
    public ColorListAdapter(List<ColorList> colorList) {
        ColorList = colorList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.picture_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//a=String.valueOf(getRandomColor());
					//Toast.makeText(mContext,a, Toast.LENGTH_SHORT).show();
				}
			});
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ColorList color = ColorList.get(position);
        holder.colorImage.setBackgroundColor(getRandomColor());
    }
	public int getRandomColor() {
        Random rand = new Random();
        return Color.argb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

    }
    @Override
    public int getItemCount() {
        return ColorList.size();
    }
}


