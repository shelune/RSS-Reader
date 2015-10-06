package com.example.asus.rss_reader_try2;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.provider.SyncStateContract;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RSSService extends IntentService {

    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";
    public static final String LINK = "link";
    public static final String POS = "position";

    public RSSService() {
        super("RSSService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<RSSItem> rssItems = null;
        try {
            DOMParser parser = new DOMParser();
            int position = intent.getIntExtra(POS, 0);
            rssItems = parser.parse(getInputStream(RSSManager.getInstance().getLink(position)));
        } catch (XmlPullParserException e) {
            Log.w(e.getMessage(), e);
        } catch (IOException e) {
            Log.w(e.getMessage(), e);
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) rssItems);
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(0, bundle);
    }

    public InputStream getInputStream(String link) {
        try {
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }
}
