package com.newsScrapper.newsScrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsGenerators {
    public static List<News> getNewsFromVOA() throws IOException {
        String url = "https://amharic.voanews.com/z/3661";
        Document page = getPageFromUrl(url);

        List<News> newsList = new ArrayList<>();
        String imageUrl, linkUrl, date, title, overview;

        Element newsHeadline = page.getElementsByClass("media-block horizontal with-date has-img size-2").first();
        imageUrl = newsHeadline.select("img").first().attr("data-src");
        title = newsHeadline.getElementsByClass("title").first().text();
        date = newsHeadline.getElementsByClass("date").first().text();
        overview = newsHeadline.getElementsByClass("perex").first().text();
        linkUrl = newsHeadline.select("a").attr("href");
        newsList.add(createNews(date,title,overview,imageUrl,linkUrl, "VOA Amharic"));

        Elements ordinaryNewsItems = page.getElementById("ordinaryItems").select("li");
        for (Element newsItem:ordinaryNewsItems) {
            imageUrl = newsItem.select("img").first().attr("data-src");
            title = newsItem.getElementsByClass("title").first().text();
            date = newsItem.getElementsByClass("date").first().text();
            overview = newsItem.getElementsByClass("perex").text();
            linkUrl = newsItem.select("a").attr("href");
            newsList.add(createNews(date,title,overview,imageUrl,linkUrl, "VOA Amharic"));
        }

        return newsList;
    }
    public static List<News> getNewsFromNazret() throws IOException {
        String url = "https://www.nazret.com/category/news/";
        final Document page = getPageFromUrl(url);

        List<News> newsList = new ArrayList<>();
        Elements newsItems = page.getElementsByClass("td-block-span6");
        for (Element newsItem: newsItems) {
            String imageUrl, linkUrl, date, title, overview = "";
            imageUrl = newsItem.select("img").attr("src");
            linkUrl = newsItem.select("a").attr("href");
            title = newsItem.select("a").attr("title");
            date = newsItem.getElementsByClass("entry-date updated td-module-date").first().text();
            newsList.add(createNews(date,title,overview,imageUrl,linkUrl, "Nazret"));
        }
        return newsList;
    }
    public static List<News> getNewsFromBBCAmharic() throws IOException {
        String url = "https://www.bbc.com/amharic/topics/e986aff5-6b26-4638-b468-371d1d9617b4";
        final Document page = getPageFromUrl(url);
        List<News> newsList = new ArrayList<>();
        Elements newsElements = page.getElementsByClass("eagle-item faux-block-link");
        for (Element newsItem:newsElements) {
            String imageUrl, linkUrl, date, title, overview;
            imageUrl = newsItem.select("img").attr("src");
            linkUrl = newsItem.select("a").attr("href");
            title = newsItem.getElementsByClass("title-link__title-text").first().text();
            overview = newsItem.getElementsByClass("eagle-item__summary").first().text();
            date = newsItem.getElementsByClass("date date--v2").first().text();
            newsList.add(createNews(date,title,overview,imageUrl,linkUrl, "BBC Amharic"));
        }
        return newsList;
    }
    public static List<News> getNewsFromCapital() throws IOException {
        String url = "https://www.capitalethiopia.com/category/capital/";
        final Document page = Jsoup.connect(url).get();
        List<News> newsList = new ArrayList<>();
        Elements newsElements = page.getElementsByClass("td-block-span6");
        for(Element newsItem: newsElements) {
            String imageUrl, linkUrl, date, title, overview;
            imageUrl = newsItem.select("img").attr("src");
            linkUrl = newsItem.select("a").attr("href");
            title = newsItem.getElementsByClass("entry-title td-module-title").first().text();
            date = newsItem.getElementsByClass("td-post-date").first().text();
            overview = newsItem.getElementsByClass("td-post-category").first().text();
            newsList.add(createNews(date,title,overview,imageUrl,linkUrl, "Capital"));
        }
        return newsList;
    }
    public static List<News> getNewsFromReporter() throws IOException {
        String url = "https://www.ethiopianreporter.com/zena";
        final Document page = getPageFromUrl(url);
        List<News> newsList = new ArrayList<>();
        Elements newsElements = page.getElementsByClass("item");
        for(Element newsItem:newsElements){
            String imageUrl, linkUrl, date, title, overview;
            imageUrl = newsItem.select("img").attr("src");
            title = newsItem.getElementsByClass("post-title").first().select("span").text();
            date = newsItem.getElementsByClass("post-created").first().text();
            linkUrl = newsItem.select("a").attr("href");
            overview = newsItem.getElementsByClass("field field--name-body field--type-text-with-summary field--label-hidden field__item").first().text();
            newsList.add(createNews(date,title,overview,imageUrl,linkUrl, "Reporter"));
        }
        return newsList;
    }
    public static List<News> getNewsFromReuters() throws IOException {
        String url = "https://af.reuters.com/news/archive/ethiopiaNews";
        final Document page = getPageFromUrl(url);
        List<News> newsList = new ArrayList<>();
        Elements newsElements = page.getElementsByClass("headlineMed standalone");
        for(Element newsItem:newsElements){
            String imageUrl, linkUrl, date, title, overview;
            date = newsItem.getElementsByClass("timestamp").first().text();
            linkUrl = newsItem.select("a").attr("href");
            title =  newsItem.select("a").text();
            imageUrl = "";
            overview = "";
            newsList.add(createNews(date,title,overview,imageUrl,linkUrl, "Reuters"));
        }
        return newsList;
    }
    public static Document getPageFromUrl(String url) throws IOException{
        InputStream is =new URL(url).openStream();
        final Document page = Jsoup.parse(is, "utf-8",url);
        return page;
    }

    public static News createNews(String date, String title, String overview, String image, String link, String media){
        News news = new News();
        news.setTitle(title);
        news.setOverview(overview);
        news.setDate(date);
        news.setImage(image);
        news.setLink(link);
        news.setMedia(media);
        return news;
    }
}
