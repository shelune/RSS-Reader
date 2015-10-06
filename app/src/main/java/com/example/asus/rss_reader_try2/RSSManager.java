package com.example.asus.rss_reader_try2;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class RSSManager {
    private LinkedHashMap<String,String> list = new LinkedHashMap<>();
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

    public void newList() {
        list.clear();
        list.put("SMASHING MAGAZINE", RSS_URL_SMASHING);
        list.put("TECHNOLOGY", RSS_URL_TECH);
        list.put("HEALTH", RSS_URL_HEALTH);
        list.put("NIGHTLINE", RSS_URL_NIGHTLINE);
        list.put("ENTERTAINMENT", RSS_URL_ENTERTAINMENT);
        list.put("SPORTS", RSS_URL_SPORTS);
        list.put("TRAVEL", RSS_URL_TRAVEL);
        list.put("POLITICS", RSS_URL_POLITICS);
        list.put("WORLD", RSS_URL_WORLD);
        list.put("PCWORLD", RSS_URL_PCWORLD);
        list.put("DOTA", RSS_URL_DOTA);
    }

    public String getLink(int position) {
        String result = (new ArrayList<String>(list.values())).get(position);
        return result;
    }

    public String getTitle(int position) {
        String result = (new ArrayList<String>(list.keySet())).get(position);
        return result;
    }

}
