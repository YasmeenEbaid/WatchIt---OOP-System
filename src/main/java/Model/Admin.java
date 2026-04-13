package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import static watchit.watchit_project.WatchitApp.*;

public class Admin extends Person implements Serializable {
    private static final long serialVesionUID=1L;

    public Admin(  String username, String password, String first_name,
                   String last_name,String email){
        setID();
        setUsername(username);
        setPassword(password);
        setFirst_name(first_name);
        setLast_name(last_name);
        setEmail(email);
    }
    public void setID() {
        this.ID = counter + 2023170000;
    }


    /*  public static boolean add_admin(String username, String password, String first_name,String last_name,String email) {
          if (searchUsernameIsValid(username)) {
              if ((isEmailCorrect(email)) && (isEmailUnique(email))) {
                  admins.add(new Admin(username, password, first_name, last_name, email));
                  return true;
              } else {
                  throw new IllegalArgumentException("the Email is not correct");
                  return false;
              }
          }
         else{ return false;}
      }*/
    public static Admin login(String s, String password){// can be username or admin
        for (Admin admin : admins) {
            if (admin.getUsername().equals(s) && admin.getPassword().equals(password)) {
                return admin;
            }
            else if(admin.getEmail().equals(s) && admin.getPassword().equals(password)){
                return admin;
            }
        }
        System.out.println("We couldn't verify your login details. Please check your username and password and try again.");
        // forgot password???
        return null;

    }


    public static Cast add_cast(String fn, String ln, Gender directorGender, LocalDate dateOfBirth, String nationality){
        Cast check=Cast.search_castByName(fn, ln);
        if(check==null) {
            check=new Cast(fn,ln,directorGender,dateOfBirth,nationality);
            all_cast.add(check); }
        return check;
    }
    public static Director add_director(Director director){
        Director check=Director.search_directorByName(director.getFirstName(), director.getLastName());
        if(check==null) {
            all_director.add(director);
            return director;}
        return check;
    }
    public static void add_movie(String title, String releaseDate, int duration, double budget, double revenue, String poster,
                                 String genre, String language, Director director, ArrayList<Cast>casts,String country , double rate){

        all_movies.add(new Movie(title,releaseDate,duration,budget,revenue,poster,genre,language,director,casts,country,rate));
        System.out.println("done");
        director.directorMovies.add(all_movies.get(Movie.counterMovie-1));
        for (Cast cast:casts){
            cast.castMovies.add(all_movies.get(Movie.counterMovie-1));
        }
    }
    public static void delete_movie(Movie movie){
        all_movies.remove(movie);
        for(Director director:all_director){
            if(director.directorMovies.equals(movie)){
                director.directorMovies.remove(movie);
            }
        }
        for(Cast cast:all_cast){
            if(cast.castMovies.equals(movie)){
                cast.castMovies.remove(movie);
            }
        }
    }
    public static void displayAdminWatchedRecord(){
        for(User user:users){
            for(UserWatchRecord userWatchRecord: user.user_watch_record_list){
                userWatchRecord.display_for_admin();
            }
        }
    }
    public void display_all_users(){
        if(users.isEmpty()){
            System.out.println("No Users Available");
            System.out.println("If this seems incorrect, please check your data source or try again later.");
        }
        else{
            for(User user:users){
                user.display_user_info(user);
                System.out.println("_");
            }
        }
    }
    public void search_for_user(String s) {// s can be username,id,first name,last name,email,full name
        if(users.isEmpty()){
            System.out.println("No users are currently available. Please try again later.");
            return;
        }
        boolean z=false;
        for(int i=0;i<users.size();i++){
            if((users.get(i).getFirst_name().equalsIgnoreCase(s))||
                    (users.get(i).getLast_name().equalsIgnoreCase(s))||
                    (users.get(i).getID()==Long.parseLong(s))||
                    (users.get(i).getUsername().equals(s))||(users.get(i).getEmail().equals(s))||
                    ((s.toLowerCase().contains(users.get(i).getFirst_name().toLowerCase()))&&(s.toLowerCase().contains(users.get(i).getLast_name().toLowerCase()))))
            {
                z=true;
                users.get(i).display_user_info(users.get(i));
                System.out.println("_");
            }
        }

        if(!z){
            System.out.println("No matching user found.");
        }
    }
}
