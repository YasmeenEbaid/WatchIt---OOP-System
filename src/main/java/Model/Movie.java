package Model;

import Model.Director;
import Model.UserWatchRecord;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static watchit.watchit_project.WatchitApp.all_movies;
import static watchit.watchit_project.WatchitApp.watchRecords;
import static watchit.watchit_project.WatchitApp.users;

public class Movie implements Serializable {
    private static final long serialVesionUID=1L;
    private int movieID;
    private String title;
    private String releaseDate;
    private int duration;
    private String country;
    private double budget;
    private double revenue;
    private String poster;
    private String language ;
    private String genre ;
    private Director directorObj;
    private double rate ;
    public static int  counterMovie=0;
    ArrayList<Cast>casts;
    public Movie(String title,String releaseDate,int duration, double budget,double revenue,
                 String poster, String genre,String language,Director director, ArrayList<Cast>casts,String country,double rate){
        setMovieID();
        setTitle(title);
        setReleaseDate(releaseDate);
        setDuration(duration);
        setDirectorObj(Admin.add_director(director));
        setCountry(country);
        setBudget(budget);
        setRevenue(revenue) ;
        setPoster(poster);
        setGenres(genre);
        setLanguage(language) ;
        setCasts(casts);
        setCountry(country);
        setRate(rate);
        counterMovie++;
    }

    //setter

    public void setMovieID() {
        this.movieID = 1000+counterMovie;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setBudget(double budget) {
        this.budget = budget;
    }
    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public void setGenres(String genre) {
        this.genre = genre;
    }
    public void setDirectorObj(Director directorObj) {
        this.directorObj = directorObj;
    }
    public void setCasts(ArrayList<Cast> casts) {this.casts = new ArrayList<>(casts);}
    public void setRate(double rate){this.rate=rate;}

    //getter
    public int getMovieID() {
        return movieID;
    }
    public String getTitle() {
        return title;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public int getDuration() {
        return duration;
    }
    public String getCountry() {
        return country;
    }
    public double getBudget() {
        return budget;
    }
    public double getRevenue() {
        return revenue;
    }
    public String getPoster() {
        return poster;
    }
    public String getLanguage() {
        return language;
    }
    public String getGenre() {
        return genre;
    }
    public Director getDirectorObj() {
        return directorObj;
    }
    public double getRate() {
        int counter=1;
        for(User user:users){
            for(UserWatchRecord userWatchRecord:user.user_watch_record_list) {
                if (userWatchRecord.getMovie().getTitle().equals(this.getTitle())) {
                    if((userWatchRecord.getRating()>=1)&&(userWatchRecord.getRating()>=1)) {
                        rate += userWatchRecord.getRating();
                        counter++;
                    }
                }
            }}

        return (counter==0)? rate : (rate/counter);
    }
    public ArrayList<Cast> getCasts() {
        return casts;
    }



    public void displayMovie() {
        System.out.println("========== Movie Details ==========");
        System.out.println("Movie ID       : " + getMovieID());
        System.out.println("Title          : " + getTitle());
        System.out.println("Release Date   : " + getReleaseDate());
        System.out.println("Duration       : " + getDuration() + " minutes");
        System.out.println("Country        : " + getCountry());
        System.out.println("Language       : " + getLanguage());
        System.out.println("Genre          : " + getGenre());
        System.out.println("Budget         : $" + getBudget());
        System.out.println("Revenue        : $" + getRevenue());
        System.out.println("Rating         : " + getRate() + "/5");
        System.out.println("Poster URL     : " + getPoster());
        System.out.println("Director       : " + (getDirectorObj() != null ? directorObj.getFullName() : "Unknown"));
        System.out.println("Cast           :");

        // عرض الممثلين إذا كان هناك ممثلين
        if (!getCasts().isEmpty()) {
            for (Cast cast : casts) {
                System.out.println("   - " + cast.getFullName() );
                System.out.println("         # " + cast.getCastGender() );
                System.out.println("         # " + cast.getNationality() );
                System.out.println("         # " + cast.getDateOfBirth() );
            }
        } else {
            System.out.println("   No cast members available.");
        }

        System.out.println("===================================");
    }
    @Override
    public String toString(){
        return  "Movie { "+
                "title =' "+title+'\''+
                ", release date= "+ releaseDate+
                ", genre= ' "+genre+'\''+
                '}';
    }
}


