package com.example.asus.rss_reader_try2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ASUS on 25-Sep-15.
 */
public class RSSAdapter extends BaseAdapter {

    private List<RSSItem> items;
    private final Context context;

    public RSSAdapter(Context context, List<RSSItem> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.rss_item, null);
            holder = new ViewHolder();
            holder.itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
            holder.pubDate = (TextView) convertView.findViewById(R.id.pubDate);
            holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemTitle.setText(items.get(position).getTitle());
        holder.pubDate.setText(items.get(position).getDate());
        if (items.get(position).getImg().length() == 0) {
            Picasso.with(context).load(R.mipmap.placeholder).fit().into(holder.thumbnail);
        } else {
            Picasso.with(context).load(items.get(position).getImg()).fit().into(holder.thumbnail);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView itemTitle;
        TextView pubDate;
        ImageView thumbnail;

    }
}

