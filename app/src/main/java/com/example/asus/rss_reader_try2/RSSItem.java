package com.example.asus.rss_reader_try2;

import org.jsoup.nodes.Document;

/**
 * Created by ASUS on 25-Sep-15.
 */
public class RSSItem {
    private String title;
    private String link;
    private String date;
    private Document description;

    public RSSItem(String title, String link, Document description, String date) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public Document getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

}
