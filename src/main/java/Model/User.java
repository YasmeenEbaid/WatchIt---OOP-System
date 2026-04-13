package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static watchit.watchit_project.WatchitApp.*;

public class User extends Person implements Serializable {
    private static final long serialVesionUID=1L;
    Subscription sub;
    public ArrayList<UserWatchRecord> user_watch_record_list = new ArrayList();

    // ArrayList<Movie> added_to_list_movies = new ArrayList();
    private ArrayList<Movie> added_to_list_movies = new ArrayList();//salma

    public User(String fn, String ln, String un, String email, String password) {
        setID();
        setUsername(un);
        setFirst_name(fn);
        setLast_name(ln);
        setEmail(email);
        setPassword(password);
    }


    @Override
    public void setID() {
        this.ID = counter + 2024170000;
    }

    public long get_remaining_days(){
        long daysBetween = ChronoUnit.DAYS.between(this.sub.getEndDate(), this.sub.getStartDate());
        return daysBetween;
    }
    public long get_remaining_movies(){
        int x=this.sub.getMaxMovies()-this.sub.getNumberOfMoviesWatched();
        return x;
    }
    public Subscription getSub(){
        return this.sub;
    }
    public ArrayList<UserWatchRecord> getUser_watch_record_list(){return this.user_watch_record_list;}

  /*  public User login(String s, String password) {
        for (User user : users) {
            if (user.getUsername().equals(s) && user.getPassword().equals(password)) {
                if(user.sub!=null){
                    this.sub.alertEnd();
                }
                return user;
            }
            else if (user.getEmail().equals(s) && user.getPassword().equals(password)) {
                if(user.sub!=null){
                    this.sub.alertEnd();
                }
                return user;
            }
        }
        System.out.println("We couldn't verify your login details. Please check your username and password and try again.");
        // forgot password???
        return null;

    }*/

    public static User login(String s, String password) {
        for (User user : users) {
            if (user.getUsername().equals(s) && user.getPassword().equals(password))
                return user;
            else if (user.getEmail().equals(s) && user.getPassword().equals(password))
                return user;
        }
        System.out.println("We couldn't verify your login details. Please check your username and password and try again.");
        // forgot password???
        return null;
    }


    public void buy_subscription(Subscription s){
        this.sub=new Subscription(this,s);
    }

    /*public static Movie searchMovieByTitle(String title) {
        for (Movie movie : all_movies) {
            if (movie.getTitle().equalsIgnoreCase(title))
                return movie;
        }
        return null;
    }*/


   /* public void add_movie_to_added_to_list_movies(Movie movie) {
        if (this.added_to_list_movies.contains(movie)) {
            System.out.println("This movie is already in the list.");
        } else {
            this.added_to_list_movies.add(movie);
        }
    } Salma*/

    public void watched_movie(Movie movie, int rating) {
        if (this.sub == null) {
            System.out.println("This movie is available for premium subscribers only. Subscribe now to access this content!");
        } else if (this.sub.able_to_watch_a_movie()) {
            this.user_watch_record_list.add(new UserWatchRecord(this.getID(), movie, rating));
            watchRecords.add(new UserWatchRecord(this.getID(), movie, rating));
            if((user_watch_record_list.size()%this.sub.getMaxMovies()==0))
            {
                this.sub.setNumberOfMoviesWatched(this.sub.getMaxMovies());
            }
            else{this.sub.setNumberOfMoviesWatched(this.user_watch_record_list.size()%this.sub.getMaxMovies());//}
            }
        }

    }

    public void watched_movie(Movie movie) {
        if (this.sub == null) {
            System.out.println("This movie is available for premium subscribers only. Subscribe now to access this content!");
        } else if (this.sub.able_to_watch_a_movie()) {
            this.user_watch_record_list.add(new UserWatchRecord(this.getID(), movie));watchRecords.add(new UserWatchRecord(this.getID(), movie));
            if((user_watch_record_list.size()%this.sub.getMaxMovies()==0))
            {
                this.sub.setNumberOfMoviesWatched(this.sub.getMaxMovies());
            }
            else{this.sub.setNumberOfMoviesWatched(this.user_watch_record_list.size()%this.sub.getMaxMovies());//}
            }
        }
    }

    public void delete_movie_from_added_to_list_movies(Movie movie) {
        this.added_to_list_movies.remove(movie);
    }

    public void displayUserWatchedRecord() {
        for (UserWatchRecord userWatchRecord : user_watch_record_list) {
            userWatchRecord.display_for_user();
        }
    }

    public static List<Movie> top_rated_movies(){
        List<Movie> topRatedList=new ArrayList<>();
        for (Movie movie:all_movies){
            if(movie.getRate()>3.5){
                topRatedList.add(movie);
            }
        }
        return topRatedList;
    }

    public ArrayList<Movie> Recommendation(){
        ArrayList<Movie> movies=new ArrayList<>();
        ArrayList<String> visited=new ArrayList<>();
        for(UserWatchRecord userWatchRecord:user_watch_record_list){
            if(!visited.contains(userWatchRecord.getMovie().getGenre())){
                visited.add(userWatchRecord.getMovie().getGenre());
            }
        }
        for(String s:visited){
            movies.addAll(filter_by_genre(s));
        }
        ArrayList<Movie> ans=new ArrayList<>();
        for(Movie movie:movies){
            if(!ans.contains(movie)){
                ans.add(movie);
            }
        }
        return ans;
    }
    /* public void recent_movies(){
         for (Movie movie:all_movies){
             String date1Str = "2024-01-01"; // نص يمثل التاريخ (YYYY-MM-DD)
             LocalDate date1 = LocalDate.parse(date1Str);

             LocalDate date2 = LocalDate.parse(movie.getReleaseDate());
             if (date2.isAfter(date1)){
                 movie.displayMovie();
             }
         }
     }*/
    public void display_user_info(User user){
        System.out.println("Name: " + user.getFull_name());
        System.out.println("Username: " + user.getUsername());
        System.out.println("ID: " + user.getID());
        System.out.println("Email: " + user.getEmail());
        if(user.sub!=null){
            long daysBetween = ChronoUnit.DAYS.between(user.sub.getEndDate(), user.sub.getStartDate());
            int x=user.sub.getMaxMovies()-user.sub.getNumberOfMoviesWatched();
            if(daysBetween==0){
                System.out.println("Subscription: This subscription expired on "+user.sub.getEndDate());
            }
            else if(x==0){
                System.out.println("Subscription: This user has already reached the maximum limit of "+user.sub.getMaxMovies()+" movies.");
            }
            else{
                System.out.println("Subscription: This subscription will expire on "+user.sub.getEndDate());
                System.out.println("              This user has "+daysBetween+" days remaining.");
                System.out.println("              This user can watch "+x+" more movies.");
            }
        }
        else{
            System.out.println("Subscription: This user has not subscribed yet.");
        }

    }


    /* public void display_my_acc_info(){
         System.out.println("Name: " + this.getFull_name());
         System.out.println("Username: " + this.getUsername());
         System.out.println("ID: " + this.getID());
         System.out.println("Email: " + this.getEmail());
         if(this.sub!=null){
             long daysBetween = ChronoUnit.DAYS.between(this.sub.getEndDate(), this.sub.getStartDate());
             int x=this.sub.getMaxMovies()-this.sub.getNumberOfMoviesWatched();
             if(daysBetween==0){
                 System.out.println("Subscription: Your subscription expired on "+this.sub.getEndDate());
             }
             else if(x==0){
                 System.out.println("Subscription: You have already reached the maximum limit of "+this.sub.getMaxMovies()+" movies.");
             }
             else{
                 System.out.println("Subscription: Your subscription will expire on "+this.sub.getEndDate());
                 System.out.println("              You have "+daysBetween+" days remaining.");
                 System.out.println("              You can watch "+x+" more movies.");
             }
         }
         else{
             System.out.println("Subscription: You have not subscribed yet. Subscribe now to enjoy unlimited access!");
         }

     }*/
    /*public ArrayList<Movie> filter_by_genre(String s){
        ArrayList<Movie> movies=new ArrayList<>();
        for(Movie movie:all_movies){
            if(movie.getGenre().equals(s)){
                movies.add(movie);
            }
        }
        return movies;
    }*/
    public ArrayList<Movie> filter_by_language(String s){
        ArrayList<Movie> movies=new ArrayList<>();
        for(Movie movie:all_movies){
            if(movie.getLanguage().equalsIgnoreCase(s)){
                movies.add(movie);
            }
        }
        return movies;
    }
    public ArrayList<Movie> filter_by_rating(double rate){
        ArrayList<Movie> movies=new ArrayList<>();
        for(Movie movie:all_movies){
            if(movie.getRate()>=rate){
                movies.add(movie);
            }
        }
        return movies;
    }
    public ArrayList<Movie> filter_by_duration(int d){
        int first=d-10; int last=d+10;
        ArrayList<Movie> movies=new ArrayList<>();
        for(Movie movie:all_movies){
            if((movie.getDuration()>=first)&&(movie.getDuration()<=last)){
                movies.add(movie);
            }
        }
        return movies;
    }

    //salma
    public static List<Movie> getAllMovies() {
        return all_movies;
    }

    public  static List<Movie> recent_movies(){
        List<Movie>recentmovies=new ArrayList<>();
        for (Movie movie:all_movies){
            String date1Str = "2020-01-01"; // نص يمثل التاريخ (YYYY-MM-DD)
            LocalDate date1 = LocalDate.parse(date1Str);

            LocalDate date2 = LocalDate.parse(movie.getReleaseDate());
            if (date2.isAfter(date1)){
                // movie.displayMovie();
                recentmovies.add(movie);
            }

        }
        return recentmovies;
    }

    public List<Movie> getWatchlist() {return added_to_list_movies;}

    public boolean is_movie_in_watchlist(Movie movie) {
        return added_to_list_movies.contains(movie);
    }

    public void remove_movie_from_added_to_list_movies(Movie movie) {
        added_to_list_movies.remove(movie);
    }

    public void add_movie_to_added_to_list_movies(Movie movie) {
        if (added_to_list_movies.contains(movie)) {
            System.out.println("This movie is already in the list.");
        } else {
            added_to_list_movies.add(movie);
        }
    }

    public static ArrayList<Movie> filter_by_genre(String s){
        ArrayList<Movie> movies=new ArrayList<>();
        for(Movie movie:all_movies){
            String[] movieGenres=movie.getGenre().split("-");
            for (String genre:movieGenres){
                if(genre.trim().equalsIgnoreCase(s)){
                    movies.add(movie);
                }
            }}
        return movies;
    }

    public static List<Movie>  searchMovieByTitle(String title) {
        List<Movie> matchingmovies=new ArrayList<>();
        for (Movie movie : all_movies) {
            if (movie.getTitle().toLowerCase().contains(title.toLowerCase())) {
                matchingmovies.add(movie);
            }
        }
        return matchingmovies;
    }

    @Override
    public String toString(){
        return  "User { "+
                "user ID =' "+ID+'\''+
                ",first name =' "+first_name+'\''+
                ", last name = "+ last_name+
                ", username = ' "+username+'\''+
                ", email =' "+email+'\''+
                ", password =' "+password+'\''+
                ",subscription =' "+sub+'\''+
                ",History page=' "+user_watch_record_list+'\''+
                ",added to list  =' "+added_to_list_movies+'\''+ '}';
    }

}

