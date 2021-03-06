package com.example.asus.rss_reader_try2;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

   // Adapter for the Feed Drawer

    private static final int HEADER_TYPE = 0;
    private static final int ROW_TYPE = 1;

    private List<String> rows;

    Context context;

    public DrawerAdapter(Context context, List<String> rows) {
        this.rows = rows;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_header, parent, false);
            return new ViewHolder(view, viewType, context);
        } else if (viewType == ROW_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_row, parent, false);
            return new ViewHolder(view, viewType, context);
        } else {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.viewType == ROW_TYPE) {
            String rowText = rows.get(position - 1);
            holder.rowText.setText(rowText);
            holder.rowIcon.setImageResource(R.mipmap.bullet_point);
        }
    }

    @Override
    public int getItemCount() {
        return rows.size() + 1;
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_TYPE;
        }
        return ROW_TYPE;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected int viewType;
        Context context;
        @Bind(R.id.row_icon)
        ImageView rowIcon;
        @Bind(R.id.row_text)
        TextView rowText;
        private View v;


        public ViewHolder(View itemView, int viewType, Context context) {
            super(itemView);
            v = itemView;
            this.viewType = viewType;
            this.context = context;

            if (viewType == ROW_TYPE) {
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
