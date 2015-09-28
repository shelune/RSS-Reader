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
    private String imgUrl;

    public RSSItem(String title, String link, Document description, String date, String imgUrl) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.date = date;
        this.imgUrl = imgUrl;
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
        String result = date.replace(" +0000","").substring(0, 16);
        return result;
    }

    public String getImg() {
        return imgUrl;
    }

    public void setImg(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
