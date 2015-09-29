package com.example.asus.rss_reader_try2;

import java.util.List;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {
    private static final int HEADER_TYPE = 0;
    private static final int ROW_TYPE = 1;

    private List<String> rows;
    public DrawerAdapter(List<String> rows) {
        this.rows = rows;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_header, parent, false);
            return new ViewHolder(view, viewType);
        } else if (viewType == ROW_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_row, parent, false);
            return new ViewHolder(view, viewType);
        } else {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder.viewType == ROW_TYPE) {
            String rowText = rows.get(position - 1);
            holder.rowText.setText(rowText);
            holder.rowIcon.setImageResource(R.mipmap.ic_launcher);
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

        @Bind(R.id.row_icon) ImageView rowIcon;
        @Bind(R.id.row_text) TextView rowText;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;

            if (viewType == ROW_TYPE) {
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
