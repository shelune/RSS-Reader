package com.example.asus.rss_reader_try2;

import java.util.ArrayList;

public class RSSManager {
    private static ArrayList<String> list = new ArrayList<>();
    private static final RSSManager manager = new RSSManager();

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

    public RSSManager() {
        newList();
    }

    public static RSSManager getInstance() {
        return manager;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public String getLink(int position) {
        return list.get(position);
    }

    public void newList() {
        list.clear();
        list.add(RSS_URL_SMASHING);
        list.add(RSS_URL_TECH);
        list.add(RSS_URL_HEALTH);
        list.add(RSS_URL_ENTERTAINMENT);
        list.add(RSS_URL_SPORTS);
        list.add(RSS_URL_TRAVEL);
        list.add(RSS_URL_POLITICS);
        list.add(RSS_URL_WORLD);
        list.add(RSS_URL_NIGHTLINE);
        list.add(RSS_URL_PCWORLD);
        list.add(RSS_URL_DOTA);
    }
}
