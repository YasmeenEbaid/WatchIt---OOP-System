package watchit.watchit_project;

import Controllers.userPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Model.*;

public class WatchitApp extends Application {
    public static ArrayList<Admin> admins=new ArrayList<>();
    public static ArrayList<Subscription>allSubscription=new ArrayList<>();
    public static ArrayList<User>users=new ArrayList<>();
    public static ArrayList<UserWatchRecord>watchRecords=new ArrayList<>();
    public static ArrayList<Cast>all_cast=new ArrayList<>();
    public static ArrayList<Director>all_director=new ArrayList<>();
    public static ArrayList<Movie>all_movies=new ArrayList<>();
    public static ArrayList<Subscription>available_subscriptions=new ArrayList<>();

    static List<Subscription> subscriptions = new ArrayList<>();
    private List<String> filmnames = new ArrayList<>();
    private List<String> allFilmNames = new ArrayList<>(); // Store a copy of all film names to reference after filtering.


    public static Admin currentAdmin;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-screen.fxml"));
            Scene loginScene = new Scene(fxmlLoader.load());

            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Login - JavaFX App");
            primaryStage.setMaximized(true);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {

        FileHandler.readAllData();
//        User user =new User("shams", "shaban","shamss","shams.com","000");
//        users.add(user);
//        User user2 =new User("shams", "shaban","salma","shams.com","000");
//        users.add(user2);
//        users.get(0).add_movie_to_added_to_list_movies(all_movies.get(0));
//        users.get(1).add_movie_to_added_to_list_movies(all_movies.get(5));
        launch();
       FileHandler.writeAllData();




    }
}

