package com.example.thirdassignment_filmlist;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Object> {
    public Context _context;
    public int _layout;
    public ArrayList<Film> _objects;


    public MyArrayAdapter(Context context, int layout, ArrayList objects) {
        super(context,layout,objects);
        _context = context;
        _layout = layout;
        _objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View result = convertView;

        if (result == null)
        {
            result = LayoutInflater.from(_context).inflate(_layout,
                    parent, false);
        }
        TextView filmName=result.findViewById(R.id.filmTitle);
        filmName.setText(_objects.get(position).getFilmName());
        TextView filmRating=result.findViewById(R.id.filmRate);
        filmRating.setText("Rating: "+_objects.get(position).getRating());
        TextView filmActors=result.findViewById(R.id.filmActors);
        filmActors.setText(_objects.get(position).getActors());
        ImageView filmPic=result.findViewById(R.id.filmPic);
        Picasso.with(_context).load(_objects.get(position).getImage()).into(filmPic);

        return result;
    }





}

