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
        Boolean hasContent = false;
        List<RSSItem> items = new ArrayList<>();

        int eventType = parser.getEventType();

        // try to read through the feed and extract the tag contents
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eventName = parser.getName();
            if (eventType == XmlPullParser.START_TAG) {
                if (eventName.equalsIgnoreCase("item")) {
                    hasContent = true;
                }
                else if (eventName.equalsIgnoreCase("title")) {
                    if (hasContent)
                        title = readTitle(parser);
                } else if (eventName.equalsIgnoreCase("link")) {
                    if (hasContent)
                        link = readLink(parser);
                } else if (eventName.equalsIgnoreCase("pubDate")) {
                    if (hasContent)
                        pubDate = readDate(parser);
                } else if (eventName.equalsIgnoreCase("description")) {
                    if (hasContent) {
                        description = readDesc(parser);
                        if (description.select("img").size() > 0) {
                            img = readImg(description);
                        }
                    }
                } else if (eventName.equals("media:thumbnail")) {
                    img = readThumbnail(parser);
                }

            } else if (eventType == XmlPullParser.END_TAG && eventName.equalsIgnoreCase("item")) {
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

    private String readContentEncodedImg(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "content:encoded");
        String content = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "content:encoded");
        return content;
    }

    private String readDate(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "pubDate");
        String date = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "pubDate");
        return date.substring(0, 22);
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

    private String readThumbnail(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "media:thumbnail");
        String thumbnail = parser.getAttributeValue(null, "url");
        parser.nextTag();
        return thumbnail;
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
