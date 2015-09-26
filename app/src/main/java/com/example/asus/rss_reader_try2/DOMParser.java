package com.example.asus.rss_reader_try2;

import android.util.Xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
        Document description = null;
        List<RSSItem> items = new ArrayList<>();
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equalsIgnoreCase("title")) {
                title = readTitle(parser);
            } else if (name.equalsIgnoreCase("link")) {
                link = readLink(parser);
            } else if (name.equalsIgnoreCase("pubdate")) {
                pubDate = readDate(parser);
            } else if (name.equalsIgnoreCase("description")) {
                description = readDesc(parser);
            }

            if (title != null && link != null ) {
                RSSItem item = new RSSItem(title, link, description, pubDate);
                items.add(item);
                title = null;
                link = null;
                pubDate = null;
                description = null;
            }
        }
        return items;
    }

    private Document readDesc(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return Jsoup.parseBodyFragment(link);
    }

    private String readDate(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "pubDate");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "pubDate");
        return link;
    }


    private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return link;
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
