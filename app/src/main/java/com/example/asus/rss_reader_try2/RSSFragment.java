package com.example.asus.rss_reader_try2;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URI;
import java.util.List;

/**
 * Created by ASUS on 25-Sep-15.
 */
public class RSSFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ProgressBar progressBar;
    private ListView listView;
    private View view;
    private int position;
    public static String MAINPOS = "position";

    public static RSSFragment newInstance(int pos) {
        Bundle args = new Bundle();
        args.putInt(RSSService.POS, pos);
        RSSFragment fragment = new RSSFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        position = this.getArguments().getInt(RSSFragment.MAINPOS);
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_layout, container, false);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            listView = (ListView) view.findViewById(R.id.listView);
            listView.setOnItemClickListener(this);
            //int[] colors = {0xffe3f2fd , 0xff42a5f5};
            //listView.setDivider(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors));
            //listView.setDividerHeight(10);

            startService();
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        return view;
    }

    private void startService() {
        Intent intent = new Intent(getActivity(), RSSService.class);
        intent.putExtra(RSSService.RECEIVER, resultReceiver);
        intent.putExtra(RSSService.POS, position);
        getActivity().startService(intent);
    }

    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            progressBar.setVisibility(View.GONE);
            List<RSSItem> items = (List<RSSItem>) resultData.getSerializable(RSSService.ITEMS);
            if (items != null) {
                RSSAdapter adapter = new RSSAdapter(getActivity(), items);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), "Something happened.", Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RSSAdapter adapter = (RSSAdapter) parent.getAdapter();
        RSSItem item = (RSSItem) adapter.getItem(position);
        Uri uri = Uri.parse(item.getLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}


