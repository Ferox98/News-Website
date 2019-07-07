package com.newsScrapper.newsScrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Data
@Table(name = "news")
@NoArgsConstructor
@AllArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Nullable
    @Lob
    private String date;

    @Nullable
    @Lob
    private String title;

    @Nullable
    @Lob
    private String overview;

    @Nullable
    @Lob
    private String image;

    @Nullable
    @Lob
    private String link;

    private String Media;

    @Override
    public String toString() {
        return String.format("\n\nNews ID: %d\nDate: %s\nTitle: %s\nOverview: %s\nImage: %s\nLink: %s",id,date,title,overview, image, link);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMedia() {
        return Media;
    }

    public void setMedia(String media) {
        Media = media;
    }
}
