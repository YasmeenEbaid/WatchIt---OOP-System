package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import static watchit.watchit_project.WatchitApp.*;

public class Cast implements Serializable {
    private static final long serialVesionUID=1L;
    private String firstName, lastName;
    private Gender castGender;
    private LocalDate dateOfBirth;
    private String nationality;
    private Map <String,String> socialMediaLinks=new HashMap<>();
    ArrayList <Movie> castMovies=new ArrayList<>();
    public Cast(String fn,String ln,Gender castGender,LocalDate dateOfBirth,String nationality){
        setFirstName(fn);
        setLastName(ln);
        setCastGender(castGender);
        setDateOfBirth(dateOfBirth);
        setNationality(nationality);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setCastGender(Gender castGender) {
        this.castGender = castGender;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    public void setSocialMediaLink(String app, String appLink) {
        socialMediaLinks.put(app, appLink);
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public Gender getCastGender() {return castGender;}
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public String getNationality() {
        return nationality;
    }
    public String getFullName(){
        return firstName+" "+lastName;
    }
    public Map<String, String> getSocialMediaLinks() {return socialMediaLinks;}
    public ArrayList<Movie> getCastMovies() {return castMovies;}

    public static Model.Cast search_castByName(String fn, String ln){
        for(Model.Cast cast:all_cast){
            if(fn.equals(cast.getFirstName())&&ln.equals(cast.getLastName())){
                return cast;
            }
        }
        return null;
    }
    ///

    public static ArrayList<Cast> searchCastByQuery(String query) {
        ArrayList <Cast> ActorsFound=new ArrayList<>();
        for(Cast cast : all_cast) {
            if(cast.getFullName().toLowerCase().contains(query.toLowerCase()))
                ActorsFound.add(cast);
        }
        return ActorsFound;
    }

    public void display_cast(){
        System.out.println("Name of cast :"+getFirstName()+" "+getLastName());
        System.out.println("Gender :"+getCastGender());
        System.out.println("birth date :"+getDateOfBirth());
        System.out.println("nationality :"+getNationality());

        if(!socialMediaLinks.isEmpty()) {
            System.out.println("Social Media Links: ");
            for(String appName : socialMediaLinks.keySet()) {
                String appLink = socialMediaLinks.get(appName);
                System.out.println(appName + " Link: " + appLink);
            }
        }
        System.out.println("===============================");
    }
}



