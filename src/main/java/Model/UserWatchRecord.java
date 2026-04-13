package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserWatchRecord implements Serializable {
    private static final long serialVesionUID=1L;
    private long userId;
    private Movie movie;
    private LocalDateTime watchDate;
    private Integer rating;
    public UserWatchRecord(long userId, Movie movie, Integer rating) {
        this.userId = userId;
        this.movie = movie;
        this.watchDate = LocalDateTime.now();
        setRating(rating);
    }
    public UserWatchRecord(long userId, Movie movie) {
        this(userId, movie, null);
    }

    public void setRating(Integer rating) {
        if (rating == null || (rating >= 1 && rating <= 5)) {
            this.rating = rating;
        } else {
            throw new IllegalArgumentException("Rating must be between 1 and 5 or null.");
        }
    }

    public double getRating() {
        return rating;
    }

    public long getUserId() {
        return userId;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getFormattedWatchDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return watchDate.format(formatter);
    }

    public void display_for_user() {
        System.out.println("Movie: " + this.movie.getTitle());
        System.out.println("Watch Date: " + getFormattedWatchDate());
        System.out.println("Rating: " + (rating != null ? getRating() : "Not Rated"));
    }

    public void display_for_admin() {
        System.out.println("User ID :"+this.getUserId());
        System.out.println("Movie: " + this.movie.getTitle());
        System.out.println("Watch Date: " + getFormattedWatchDate());
        System.out.println("Rating: " + (rating != null ? getRating() : "Not Rated"));
    }
}

