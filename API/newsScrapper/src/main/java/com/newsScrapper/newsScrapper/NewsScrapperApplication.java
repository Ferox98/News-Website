package com.newsScrapper.newsScrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.newsScrapper.newsScrapper.NewsGenerators.*;

@SpringBootApplication
public class NewsScrapperApplication {

	@Autowired
	private static NewsRepository newsRepository;

	public static void main(String[] args)throws IOException  {
		SpringApplication.run(NewsScrapperApplication.class, args);

		//Fetch News from Multiple sites
		List<News> newsFromVOA = getNewsFromVOA();
		List<News> newsFromNazret = getNewsFromNazret();
		List<News> newsFromCapital = getNewsFromCapital();
		List<News> newsFromBBCAmharic = getNewsFromBBCAmharic();
		List<News> newsFromReporter = getNewsFromReporter();

		//Collect those news
		List<News> newsList = new ArrayList<>();
		newsFromVOA.forEach(newsList::add);
		newsFromNazret.forEach(newsList::add);
		newsFromCapital.forEach(newsList::add);
		newsFromBBCAmharic.forEach(newsList::add);
		newsFromReporter.forEach(newsList::add);

		//
		for (News news:newsList) {
			postNews(news);
		}



	}

	public static void postNews(News news) {

		String POST_URL = "http://localhost:8080/api/newsApi/news";
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> params = new HashMap<>();
		params.put("date", news.getDate());
		params.put("image", news.getImage());
		params.put("link", news.getLink());
		params.put("overview", news.getOverview());
		params.put("title", news.getTitle());
		String result = String.valueOf(restTemplate.postForLocation(POST_URL, news));
	}


}
