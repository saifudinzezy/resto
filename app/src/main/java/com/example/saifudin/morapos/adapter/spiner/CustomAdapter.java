package com.example.saifudin.morapos.adapter.spiner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.saifudin.morapos.R;
import com.example.saifudin.morapos.model.KategoriItem;
import com.example.saifudin.morapos.model.ProdukItem;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;

    List<KategoriItem> data;

    public CustomAdapter(Context context, List<KategoriItem> data) {
        this.context = context;
        this.data = data;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
//        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        /*Glide.with(context)
                .load(BASE_URL_IMAGE_WISATA + data.get(i).getImage())
                .into(icon);*/
        names.setText(data.get(i).getName());
        return view;
    }
}
