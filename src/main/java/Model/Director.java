package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static watchit.watchit_project.WatchitApp.*;

public class Director implements Serializable {
    private static final long serialVesionUID=1L;
    private String firstName, lastName;
    private Gender directorGender;
    private LocalDate dateOfBirth;
    private String nationality;
    private Map <String,String> socialMediaLinks=new HashMap<>();
    ArrayList <Movie> directorMovies=new ArrayList<>();

    public Director(String fName, String lName, Gender directorGender, LocalDate dateOfBirth, String nationality){
        setFirstName(fName);
        setLastName(lName);
        setDirectorGender(directorGender);
        setDateOfBirth(dateOfBirth);
        setNationality(nationality);
    }

    public void setFirstName(String firstName) {
        this.firstName = capitalizeString(firstName);
    }
    public void setLastName(String lastName) {
        this.lastName = capitalizeString(lastName);
    }
    public void setDirectorGender(Gender directorGender) {
        this.directorGender = directorGender;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public void setNationality(String nationality) {
        this.nationality = capitalizeString(nationality);
    }
    public void setSocialMediaLink(String app, String appLink) {
        app = capitalizeString(app);
        socialMediaLinks.put(app, appLink);
    }
    private String capitalizeString(String string) {
        if (string != null && !string.isEmpty())
            string = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        return string;
    }


    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getFullName(){
        return firstName+" "+lastName;
    }
    public Gender getDirectorGender() {return directorGender;}
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public String getNationality() {
        return nationality;
    }
    public Map<String,String> getSocialMediaLinks() {
        return socialMediaLinks;
    }
    public ArrayList<Movie> getDirectorMovies() {return directorMovies;}

    public static Director search_directorByName(String fName, String lName){
        for(Director director:all_director){
            if(director.getFullName().equalsIgnoreCase(fName+" "+lName)){
                return director;
            }
        }
        return null;
    }
    public static ArrayList<Director> searchDirectorsByQuery(String query) {
        ArrayList <Director> directorsFound=new ArrayList<>();
        for(Director director : all_director) {
            if(director.getFullName().toLowerCase().contains(query.toLowerCase()))
                directorsFound.add(director);
        }
        return directorsFound;
    }

    public void add_movie_to_directorList(Movie movie){
        directorMovies.add(movie);
    }



    public void display_director() {
        System.out.println("Name of director: " + getFullName());
        System.out.println("Gender: "+getDirectorGender());
        System.out.println("Birthdate: "+dateOfBirth.getDayOfMonth()+" "+dateOfBirth.getMonth()+" "+dateOfBirth.getYear());
        System.out.println("nationality :"+getNationality());

        if(!socialMediaLinks.isEmpty()) {
            System.out.println("Social Media Links: ");
            for(String appName : socialMediaLinks.keySet()) {
                String appLink = socialMediaLinks.get(appName);
                System.out.println(appName + " Link: " + appLink);
            }
        }
    }
}

