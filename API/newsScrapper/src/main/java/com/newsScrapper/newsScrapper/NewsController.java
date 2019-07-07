package com.newsScrapper.newsScrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/newsApi")
public class NewsController {
    @Autowired
    private NewsRepository newsRepository;

    //Get all news list
    @GetMapping("/news")
    public List<News> getAllNews(){
        return newsRepository.findAll();
    }

    //Get news by id
    @GetMapping("/news/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable(value = "id") Long newsId) throws ResourceNotFoundException{
        News news = newsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("News not found on :: " + newsId));
        return ResponseEntity.ok().body(news);
    }

    //Insert news
    @PostMapping("/news")
    public News createNews(@Valid @RequestBody News news){
        return newsRepository.save(news);
    }


    //Update news
    @PutMapping("/news/{id}")
    public ResponseEntity<News> updateNews(@PathVariable(value = "id") Long newsId, @Valid @RequestBody News newsDetails) throws ResourceNotFoundException{
        News news = newsRepository.findById(newsId).orElseThrow(()->new ResourceNotFoundException("News not found on :: " + newsId));
        news.setDate(newsDetails.getDate());
        news.setImage(newsDetails.getImage());
        news.setLink(newsDetails.getLink());
        news.setOverview(newsDetails.getOverview());
        news.setTitle(newsDetails.getTitle());
        final News updatedNews = newsRepository.save(news);
        return ResponseEntity.ok(updatedNews);
    }

    //Delete News
    @DeleteMapping("/news/{id}")
    public Map<String,Boolean> deleteNews(@PathVariable(value = "id") Long newsId) throws Exception{
        News news = newsRepository.findById(newsId).orElseThrow(()->new ResourceNotFoundException("News not found on :: " + newsId));
        newsRepository.delete(news);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;
    }
}