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
import java.util.List;

/**
 * Created by ASUS on 25-Sep-15.
 */
public class RSSService extends IntentService {
    private static final String RSS_URL = "http://www.smashingmagazine.com/feed/ ";
    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";

    public RSSService() {
        super("RSSService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.w(Constants.TAG, "Service started");
        List<RSSItem> rssItems = null;
        try {
            DOMParser parser = new DOMParser();
            rssItems = parser.parse(getInputStream(RSS_URL));
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
            Log.w(Constants.TAG, "Error retrieving the input stream", e);
            return null;
        }
    }
}
