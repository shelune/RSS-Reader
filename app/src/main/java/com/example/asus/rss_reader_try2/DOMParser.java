package com.example.asus.rss_reader_try2;

import android.util.Xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DOMParser {
    private final String ns = null;

    public List<RSSItem> parse(InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return readFeed(parser);

        } finally {
            inputStream.close();
        }
    }

    private List<RSSItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "rss");
        String title = null;
        String link = null;
        String pubDate = null;
        String img = null;
        Document description = null;
        boolean hasContent = false;
        int eventType = parser.getEventType();
        List<RSSItem> items = new ArrayList<>();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (parser.getName().equalsIgnoreCase("item")) {
                    hasContent = true;
                } else if (parser.getName().equalsIgnoreCase("title")) {
                    if (hasContent)
                        title = readTitle(parser);
                } else if (parser.getName().equalsIgnoreCase("link")) {
                    if (hasContent)
                        link = readLink(parser);
                } else if (parser.getName().equalsIgnoreCase("pubDate")) {
                    if (hasContent)
                        pubDate = readDate(parser);
                } else if (parser.getName().equalsIgnoreCase("description")) {
                    if (hasContent) {
                        description = readDesc(parser);
                        img = readImg(description);
                    }
                }
            } else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item")) {
                hasContent = false;
            }
            eventType = parser.next();

            if (title != null && link != null && pubDate != null && description != null) {
                RSSItem item = new RSSItem(title, link, description, pubDate, img);
                items.add(item);
                title = null;
                link = null;
                pubDate = null;
                description = null;
            }
        }
        return items;
    }
    private String readImg(Document doc) {
        Elements imgElem = doc.select("img[src~=(?i)\\.(png|jpe?g)]");
        return imgElem.attr("src");
    }

    private Document readDesc(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String desc = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return Jsoup.parse(desc);
    }

    private String readDate(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "pubDate");
        String date = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "pubDate");
        return date;
    }


    private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    private String readText(XmlPullParser parser) throws XmlPullParserException, IOException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }

        return result;
    }
}
