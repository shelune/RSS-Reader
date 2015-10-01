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
    private static final String RSS_URL_SMASHING = "http://www.smashingmagazine.com/feed/";
    private static final String RSS_URL_TECH = "http://feeds.abcnews.com/abcnews/technologyheadlines";
    private static final String RSS_URL_HEALTH = "http://feeds.abcnews.com/abcnews/healthheadlines";
    private static final String RSS_URL_NIGHTLINE = "http://feeds.abcnews.com/abcnews/nightlineheadlines";
    private static final String RSS_URL_ENTERTAINMENT = "http://feeds.abcnews.com/abcnews/entertainmentheadlines";
    private static final String RSS_URL_SPORTS = "http://feeds.abcnews.com/abcnews/sportsheadlines";
    private static final String RSS_URL_TRAVEL = "http://feeds.abcnews.com/abcnews/travelheadlines";
    private static final String RSS_URL_POLITICS = "http://feeds.abcnews.com/abcnews/politicsheadlines";
    private static final String RSS_URL_WORLD = "http://feeds.abcnews.com/abcnews/internationalheadlines";
    private static final String RSS_URL_PCWORLD = "http://www.pcworld.com/index.rss";
    private static final String RSS_URL_DOTA = "http://www.joindota.com/feeds/news";

    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";
    public static final String LINK = "link";

    public RSSService() {
        super("RSSService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<RSSItem> rssItems = null;
        try {
            DOMParser parser = new DOMParser();
            rssItems = parser.parse(getInputStream(RSS_URL_PCWORLD));
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
