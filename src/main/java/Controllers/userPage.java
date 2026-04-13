package Controllers;

import Model.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import watchit.watchit_project.WatchitApp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class userPage {
    public static User currentUser;
    private String currentPage="Home Page";

    public Scene createPage(String pageTitle, Stage primaryStage) {
        currentPage=pageTitle;
        HBox header = createHeader(primaryStage);

        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(0);
        content.setPadding(new Insets(0));
        content.setStyle("-fx-background-color:black;");

        if ("Home Page".equals(pageTitle)) {
            List<Movie> recentMovies = User.recent_movies(); // recent movies display
            GridPane recentMoviegrid = createMovieGrid(recentMovies,primaryStage);
            Label recentMovieLabel=new Label("Recent Movies");
            recentMovieLabel.setFont(Font.font("Arial", FontWeight.BOLD,25));
            recentMovieLabel.setTextFill(Color.WHITE);
            VBox labelBox=new VBox(0);
            labelBox.setPadding(new Insets(0,0,10,100));
            labelBox.getChildren().add(recentMovieLabel);


            List<Movie> topRated = User.top_rated_movies();  //top rated movies display
            GridPane topRatedgrid = createMovieGrid(topRated,primaryStage);
            Label topRatedLabel=new Label("Top Rated");
            topRatedLabel.setFont(Font.font("Arial", FontWeight.BOLD,25));
            topRatedLabel.setTextFill(Color.WHITE);
            VBox labelBox2=new VBox(0);
            labelBox2.setPadding(new Insets(50,0,10,100));
            labelBox2.getChildren().add(topRatedLabel);
            content.getChildren().addAll(labelBox,recentMoviegrid,labelBox2,topRatedgrid);

            List<Movie> recommendation = currentUser.Recommendation(); // display recommended movies ...if the user first time to signup there wont be recommendations
            if (!recommendation.isEmpty()){
                Label recommendationLabel=new Label("Recommendation");
                recommendationLabel.setFont(Font.font("Arial", FontWeight.BOLD,25));
                recommendationLabel.setTextFill(Color.WHITE);
                GridPane recommendationgrid = createMovieGrid(recommendation,primaryStage);
                VBox labelBox3=new VBox(0);
                labelBox3.setPadding(new Insets(50,0,10,100));
                labelBox3.getChildren().add(recommendationLabel);
                content.getChildren().addAll(labelBox3,recommendationgrid);
            }

        }
        if ("Watchlist Page".equals(pageTitle)) {
            List<Movie>watchlist=currentUser.getWatchlist();
            GridPane watchlistGrid=createMovieGrid(watchlist,primaryStage);
            content.getChildren().add(watchlistGrid);
        }
        if ("Movies Page".equals(pageTitle)){
            GridPane allMoviesGrid=createMovieGrid(WatchitApp.all_movies,primaryStage);
            content.getChildren().add(allMoviesGrid);
        }
        if ("Directors Page".equals(pageTitle)){
            GridPane allDirectorsGrid=createDirctorGrid(WatchitApp.all_director,primaryStage);
            content.getChildren().add(allDirectorsGrid);
        }
        if ("Actors Page".equals(pageTitle)){
            GridPane allCastGrid=createCastGrid(WatchitApp.all_cast,primaryStage);
            content.getChildren().add(allCastGrid);
        }
        if ("History Page".equals(pageTitle)){
            Label searchResultMessage=new Label();

            GridPane grid = createWatchRecordGrid(currentUser.getUser_watch_record_list(), primaryStage);

            if (currentUser.getUser_watch_record_list().isEmpty()) {
                Label noMoviesFoundLabel = new Label("It looks like your movie history is empty. Grab some popcorn and start watching!");
                noMoviesFoundLabel.setTextFill(Color.RED);
                noMoviesFoundLabel.setStyle("-fx-font-size: 16px;");
                noMoviesFoundLabel.setWrapText(true);
                noMoviesFoundLabel.setAlignment(Pos.CENTER);
                VBox messageBox = new VBox(noMoviesFoundLabel);
                messageBox.setAlignment(Pos.CENTER);
                content.getChildren().add(messageBox);
                searchResultMessage.setVisible(true);
            } else {
                content.getChildren().add(grid);
                searchResultMessage.setVisible(false);
            }
        }

        // ScrollPane for content
        if ("Category Page".equals(pageTitle)){
            content.getChildren().add(createCategoryGrid(primaryStage));

        }

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setStyle("-fx-background-color:black; -fx-background:black;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER );

        Platform.runLater(() -> {
            // Use lookup to get the vertical scrollbar and apply styles
            Node verticalScrollBar = scrollPane.lookup(".scroll-bar:vertical");
            if (verticalScrollBar != null) {
                verticalScrollBar.setStyle(
                        "-fx-background-color:transparent;" +
                                "-fx-opacity:0.7;" +
                                "-fx-border-color:gold;" +
                                "-fx-border-width:2px;" +
                                "-fx-pref-width:5px;"
                );
            } else {
                // Handle the case when the vertical scrollbar is not found
                System.out.println("Vertical scrollbar not found.");
            }
        });

        // Create a root layout with the header and content
        VBox root = new VBox(header, scrollPane);
        root.setStyle("-fx-background-color: black;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // Return the scene
        // return new Scene(root, 1565, 700);
        double screenWidth= Screen.getPrimary().getVisualBounds().getWidth();
        double screenHight=Screen.getPrimary().getVisualBounds().getHeight();

        return new Scene(root,screenWidth,screenHight);

    }

    private HBox createHeader(Stage primaryStage) {
        // Header Section
        HBox header = new HBox(20); // Adjusted spacing between items
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: black;");

        // Logo
        ImageView appLogo = loadImage("/filmsposter-Copy/watch it logo.png", 100, 100);
        HBox.setMargin(appLogo, new Insets(0, 10, 0, 20));

        // Navigation Buttons
        HBox homeButton = createNavButton("/filmsposter-Copy/home.jpg", "Home", primaryStage, "Home Page");
        HBox moviesButton = createNavButton("/filmsposter-Copy/movie.jpg", "Movies", primaryStage, "Movies Page");
        HBox categoryButton = createNavButton("/filmsposter-Copy/category.jpg", "Category", primaryStage, "Category Page");
        HBox historyButton = createNavButton("/filmsposter-Copy/history.png", "History", primaryStage, "History Page");
        HBox watchlistButton = createNavButton("/filmsposter-Copy/mylist.jpg", "Watchlist", primaryStage, "Watchlist Page");
        HBox myAccButton = createNavButton("/filmsposter-Copy/myacc.png", "My Account", primaryStage, "My Account");
        HBox actorListButton = createNavButton("/filmsposter-Copy/team.png", "Actors", primaryStage, "Actors Page");
        HBox directorListButton = createNavButton("/filmsposter-Copy/team.png", "Directors", primaryStage, "Directors Page");

        // Filter Icon and Text (Three Dots Style)
        HBox filterBox = new HBox(10);
        Text filterText = new Text("Filter based on");
        filterText.setFill(Color.WHITE);
        filterText.setStyle("-fx-font-size: 12px;");

        // The three dots icon (ellipsis) for toggling filter options
        ImageView filterIcon = loadImage("/filmsposter-Copy/filter.png", 30, 30); // Assuming a three dots icon
        filterBox.getChildren().addAll(filterIcon, filterText);
        filterBox.setAlignment(Pos.CENTER_LEFT);

        // Initially the filter options are hidden
        VBox filterOptions = new VBox(10);
        filterOptions.setSpacing(10);
        filterOptions.setVisible(false); // Hidden by default
        filterOptions.setStyle("-fx-background-color: #fff; -fx-padding: 10px;");

        TextField ratingOption = new TextField();
        ratingOption.setPromptText("Rating");
        ratingOption.setPrefWidth(100);

        TextField durationOption = new TextField();
        durationOption.setPromptText("Duration");
        durationOption.setPrefWidth(100);

        TextField languageOption = new TextField();
        languageOption.setPromptText("Language");
        languageOption.setPrefWidth(100);

        Button enter = new Button("Enter");

        filterOptions.getChildren().addAll(ratingOption, durationOption, languageOption, enter);
        filterOptions.setAlignment(Pos.CENTER_LEFT);

        // Toggle filter options visibility when the filter icon (three dots) is clicked
        filterBox.setOnMouseClicked(event -> {
            boolean isVisible = filterOptions.isVisible();
            filterOptions.setVisible(!isVisible); // Toggle visibility of the filter options
            filterBox.getScene().getRoot().layout(); // Trigger layout update if needed
        });

        // Event handling for Enter button in filter
        enter.setOnAction(event -> {
            ArrayList<Movie> arr = new ArrayList<>();

            // Check and parse the filter options correctly
            if (ratingOption.getText() != null && !ratingOption.getText().isEmpty()) {
                double r = Double.parseDouble(ratingOption.getText());
                arr.addAll(currentUser.filter_by_rating(r));
            }

            if (durationOption.getText() != null && !durationOption.getText().isEmpty()) {
                int d = Integer.parseInt(durationOption.getText());
                arr.addAll(currentUser.filter_by_duration(d));
            }

            if (languageOption.getText() != null && !languageOption.getText().isEmpty()) {
                arr.addAll(currentUser.filter_by_language(languageOption.getText()));
            }
            ArrayList<Movie> ans= new ArrayList<>();
            for(Movie movie:arr){
                if(!ans.contains(movie)){
                    ans.add(movie);
                }
            }
            // If any movies are found, update the grid with new content
            Label searchResultMessage=new Label();
            GridPane grid = createMovieGrid(ans, primaryStage);

            ScrollPane scrollPane = (ScrollPane) primaryStage.getScene().lookup(".scroll-pane");
            VBox content = (VBox) scrollPane.getContent();

            content.getChildren().clear();

            if (ans.isEmpty()) {
                Label noMoviesFoundLabel = new Label("No matches movies found.");
                noMoviesFoundLabel.setTextFill(Color.RED);
                noMoviesFoundLabel.setStyle("-fx-font-size: 16px;");
                noMoviesFoundLabel.setWrapText(true);
                noMoviesFoundLabel.setAlignment(Pos.CENTER);

                VBox messageBox = new VBox(noMoviesFoundLabel);
                messageBox.setAlignment(Pos.CENTER);
                content.getChildren().add(messageBox);
                searchResultMessage.setVisible(true);
            } else {
                content.getChildren().add(grid);
                searchResultMessage.setVisible(false);
            }
            ans.clear();
            arr.clear();
        });

        // Search bar setup
        HBox searchBarBox = new HBox(10);
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search...");
        searchBar.setPrefWidth(200);
        ImageView searchIcon = loadImage("/filmsposter-Copy/search.png", 30, 30);
        searchBarBox.getChildren().addAll(searchIcon, searchBar);
        searchBarBox.setAlignment(Pos.CENTER_LEFT);

        Label searchResultMessage = new Label();
        searchResultMessage.setTextFill(Color.RED);
        searchResultMessage.setStyle("-fx-font-size: 16px;");
        searchResultMessage.setVisible(false);
        searchResultMessage.setWrapText(true);

        searchBar.setOnKeyReleased(event -> {
            String searchQuery = searchBar.getText().trim();
            if(searchQuery.isEmpty()){
                Scene currentPageScene=createPage(currentPage, primaryStage);
                primaryStage.setScene(currentPageScene);
            }
            else if ("Directors Page".equals(currentPage))
                filterDirectors(searchQuery, primaryStage, searchResultMessage);
            else if ("Actors Page".equals(currentPage))
                filterActors(searchQuery, primaryStage, searchResultMessage);
            else
                filterFilms(searchQuery, primaryStage, searchResultMessage);
        });

        // Wrap buttons in an HBox for even padding
        HBox navButtons = new HBox(20); // Adjusted spacing between buttons
        navButtons.getChildren().addAll(myAccButton, homeButton, moviesButton, categoryButton, historyButton, watchlistButton, actorListButton, directorListButton, searchBarBox, filterBox);
        navButtons.setAlignment(Pos.CENTER_LEFT);

        // Add the filter options and filter box to the header
        header.getChildren().addAll(appLogo, navButtons);
        header.setAlignment(Pos.CENTER_LEFT);

        // Add the filter options menu after the filter box (it will appear under it when visible)
        header.getChildren().add(filterOptions);

        header.getChildren().add(searchResultMessage);
        HBox.setMargin(searchResultMessage, new Insets(5, 0, 0, 0));

        return header;
    }
    private HBox createNavButton(String iconPath, String buttonText, Stage primaryStage, String targetPage) {
        // Create the icon
        ImageView icon = loadImage(iconPath, 30, 30);

        // Create the text
        Text label = new Text(buttonText);
        label.setFill(Color.WHITE);

        // Combine icon and text in an HBox
        HBox buttonContainer = new HBox(5); // Spacing between icon and text
        buttonContainer.getChildren().addAll(icon, label);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setOnMouseClicked(event ->
                primaryStage.setScene(createPage(targetPage, primaryStage)));

        return buttonContainer;
    }
    private ImageView loadImage(String imagePath, int width, int height) {
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            Image image = new Image(imageUrl.toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            return imageView;
        } else {
            System.out.println("Image not found for path: " + imagePath);
            return new ImageView(); // Empty placeholder if the image is missing
        }
    }

    private GridPane createMovieGrid(List<Movie> movies , Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setHgap(70);
        grid.setVgap(50);
        grid.setPadding(new Insets(10, 50, 10, 100));
        grid.setStyle("-fx-background-color:transparent;");

        int col = 0;
        int row = 0;
        for (Movie movie : movies) {
            // Load the movie poster image with a fixed size
            ImageView moviePoster = loadImage(movie.getPoster(), 250, 250);

            // Create a clickable label or VBox
            VBox movieBox = new VBox(5);
            movieBox.setAlignment(Pos.CENTER);
            Text movieTitle=new Text(movie.getTitle());
            movieTitle.setFill(Color.WHITE);
            movieTitle.setStyle("-fx-font-size:16 px ; -fx-font-weight:bold;");
            movieBox.getChildren().addAll(moviePoster, movieTitle);
            movieBox.setStyle("-fx-background-color:black;");


            // Add an event listener for clicks on the movie box
            movieBox.setOnMouseClicked(event -> {
                // Display movie details when clicked
                displayMovie(movie,primaryStage);
            });
            movieBox.setOnMouseEntered(event ->{
                moviePoster.setOpacity(0.5);
            } );
            movieBox.setOnMouseExited(event -> {
                moviePoster.setOpacity(1.0);
            });

            // Add the movie box to the grid
            grid.add(movieBox, col, row);

            col++;
            if (col > 3) {  // 4 columns in the grid
                col = 0;
                row++;
            }
        }

        grid.setStyle("-fx-background-color:black;");
        return grid;
    }
    private void displayMovie(Movie movie, Stage primaryStage) {
        // Create a new layout to display the movie details
        VBox detailsLayout = new VBox(20);
        detailsLayout.setPadding(new Insets(20));
        detailsLayout.setStyle("-fx-background-color: black; -fx-padding: 20px;");
        detailsLayout.setMaxWidth(800);
        detailsLayout.setFillWidth(true); // Ensure it fills the width

        // Movie poster at the top (width large, height smaller)
        ImageView moviePoster = loadImage(movie.getPoster(), 700, 400); // Large width, smaller height
        moviePoster.setStyle("-fx-border-color: black; -fx-border-width: 3;"); // Border for poster

        // Create a container to set the black background behind the poster
        StackPane posterContainer = new StackPane();
        posterContainer.setStyle("-fx-background-color: black; -fx-padding: 10px;"); // Black background for poster container
        posterContainer.getChildren().add(moviePoster);

        // Create a title text for the movie at the top
        Text titleText = new Text(movie.getTitle());
        titleText.setFill(Color.GOLD);
        titleText.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

        // Movie details text (Initially populated with movie details)
        VBox textDetails = new VBox(10);
        textDetails.setAlignment(Pos.TOP_LEFT);
        textDetails.setStyle("-fx-background-color: black; -fx-padding: 10px;");
        textDetails.setFillWidth(true); // Ensure text details fills the width

        // Create movie details (Displayed immediately)
        Text releaseDate = new Text("Release Date: " + movie.getReleaseDate());
        Text duration = new Text("Duration: " + movie.getDuration() + " minutes");
        Text country = new Text("Country: " + movie.getCountry());
        Text language = new Text("Language: " + movie.getLanguage());
        Text genre = new Text("Genre: " + movie.getGenre());
        Text rating = new Text("Rating: " + movie.getRate() + "/5");
        Text director = new Text("Director: " + (movie.getDirectorObj() != null ? movie.getDirectorObj().getFullName() : "Unknown"));

        // Apply white color and larger font to all text elements
        releaseDate.setFill(Color.WHITE);
        duration.setFill(Color.WHITE);
        country.setFill(Color.WHITE);
        language.setFill(Color.WHITE);
        genre.setFill(Color.WHITE);
        rating.setFill(Color.WHITE);
        director.setFill(Color.WHITE);

        // Set font size for text
        releaseDate.setStyle("-fx-font-size: 18px;");
        duration.setStyle("-fx-font-size: 18px;");
        country.setStyle("-fx-font-size: 18px;");
        language.setStyle("-fx-font-size: 18px;");
        genre.setStyle("-fx-font-size: 18px;");
        rating.setStyle("-fx-font-size: 18px;");
        director.setStyle("-fx-font-size: 18px;");

        // Add Movie details to the textDetails VBox
        textDetails.getChildren().addAll(
                titleText, releaseDate, duration, country, language, genre, rating, director
        );

        // Add Cast details (Initially empty)
        Text castText = new Text("Cast:");
        castText.setFill(Color.GOLD);
        castText.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

        // Prepare cast details as a list
        List<Text> castDetails = new ArrayList<>();
        if (!movie.getCasts().isEmpty()) {
            for (Cast cast : movie.getCasts()) {
                castDetails.add(new Text("   - " + cast.getFullName()));
                castDetails.add(new Text("      Gender: " + cast.getCastGender()));
                castDetails.add(new Text("      Nationality: " + cast.getNationality()));
                castDetails.add(new Text("      Date of Birth: " + cast.getDateOfBirth()));
            }
        } else {
            castDetails.add(new Text("   No cast members available."));
        }

        // Create buttons to toggle between movie details and cast details
        Button movieDetailsButton = new Button("Movie Details");
        movieDetailsButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 20px;");
        movieDetailsButton.setOnAction(event -> {
            textDetails.getChildren().clear(); // Clear existing details
            textDetails.getChildren().addAll(
                    titleText, releaseDate, duration, country, language, genre, rating, director
            );
        });

        Button castDetailsButton = new Button("Cast");
        castDetailsButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 20px; ");
        castDetailsButton.setOnAction(event -> {
            textDetails.getChildren().clear(); // Clear existing details
            textDetails.getChildren().addAll(castText); // Add the cast details
            if (!movie.getCasts().isEmpty()) {
                for (Cast cast : movie.getCasts()) {
                    Text castName = new Text("   - " + cast.getFullName());
                    castName.setFill(Color.WHITE);
                    castName.setStyle("-fx-font-size: 18px;");
                    textDetails.getChildren().add(castName);
                }
            } else {
                Text noCast = new Text("   No cast members available.");
                noCast.setFill(Color.WHITE);
                noCast.setStyle("-fx-font-size: 14px;");
                textDetails.getChildren().add(noCast);
            }
        });

        // Create "Add to Watchlist" button and check if the movie is in the watchlist
        Button addToWatchlistButton = new Button(getButtonSymbolForWatchlist(movie));
        addToWatchlistButton.setStyle("-fx-background-color: Black ; -fx-text-fill: white; -fx-font-size: 20px;");

        // Set action for button click to add/remove movie from watchlist
        addToWatchlistButton.setOnAction(event -> {
            if (currentUser.is_movie_in_watchlist(movie)) {
                // Remove the movie from the watchlist
                currentUser.remove_movie_from_added_to_list_movies(movie);
                addToWatchlistButton.setText("Add to list +");
            } else {
                // Add the movie to the watchlist
                currentUser.add_movie_to_added_to_list_movies(movie);
                addToWatchlistButton.setText("Remove from list x");
            }
        });

        // Create the rateBox and initially hide it
        // Create the rateBox and initially hide it
        HBox rateBox = new HBox(10);
        rateBox.setVisible(false);
        HBox ratedBox = new HBox(10);
        ratedBox.setVisible(false);// Initially hidden

// Create a label for the rating
        Text ratingLabel = new Text("Rate");
        ratingLabel.setFill(Color.WHITE);
        ratingLabel.setStyle("-fx-font-size: 20px;");

// Create empty star images
        ImageView emptyStar1 = loadImage("/filmsposter-Copy/white-star.png", 30, 30);
        ImageView emptyStar2 = loadImage("/filmsposter-Copy/white-star.png", 30, 30);
        ImageView emptyStar3 = loadImage("/filmsposter-Copy/white-star.png", 30, 30);
        ImageView emptyStar4 = loadImage("/filmsposter-Copy/white-star.png", 30, 30);
        ImageView emptyStar5 = loadImage("/filmsposter-Copy/white-star.png", 30, 30);

// Create filled star images
        ImageView filledStar1 = loadImage("/filmsposter-Copy/yellow-star.png", 30, 30);
        ImageView filledStar2 = loadImage("/filmsposter-Copy/yellow-star.png", 30, 30);
        ImageView filledStar3 = loadImage("/filmsposter-Copy/yellow-star.png", 30, 30);
        ImageView filledStar4 = loadImage("/filmsposter-Copy/yellow-star.png", 30, 30);
        ImageView filledStar5 = loadImage("/filmsposter-Copy/yellow-star.png", 30, 30);

        AtomicBoolean z= new AtomicBoolean(false);
        emptyStar1.setOnMouseClicked(event ->
                {
                    if(!z.get()){
                        currentUser.user_watch_record_list.removeLast();
                        currentUser.watched_movie(movie,1);
                        rateBox.setVisible(false);
                        ratedBox.setVisible(true);
                        ratedBox.getChildren().addAll(ratingLabel, filledStar1, emptyStar2, emptyStar3, emptyStar4, emptyStar5);
                        ratedBox.setAlignment(Pos.CENTER_LEFT);
                    }
                    z.set(true);
                }
        );
        emptyStar2.setOnMouseClicked(event ->
                {
                    if(!z.get()){
                        currentUser.user_watch_record_list.removeLast();
                        currentUser.watched_movie(movie,2);
                        rateBox.setVisible(false);
                        ratedBox.setVisible(true);
                        ratedBox.getChildren().addAll(ratingLabel, filledStar1, filledStar2, emptyStar3, emptyStar4, emptyStar5);
                        ratedBox.setAlignment(Pos.CENTER_LEFT);
                    }
                    z.set(true);
                }
        );
        emptyStar3.setOnMouseClicked(event ->
                {
                    if(!z.get()){
                        currentUser.user_watch_record_list.removeLast();
                        currentUser.watched_movie(movie,3);
                        rateBox.setVisible(false);
                        ratedBox.setVisible(true);
                        ratedBox.getChildren().addAll(ratingLabel, filledStar1, filledStar2, filledStar3, emptyStar4, emptyStar5);
                        ratedBox.setAlignment(Pos.CENTER_LEFT);
                    }
                    z.set(true);
                }
        );
        emptyStar4.setOnMouseClicked(event ->
                {
                    if(!z.get()){
                        currentUser.user_watch_record_list.removeLast();
                        currentUser.watched_movie(movie,4);
                        rateBox.setVisible(false);
                        ratedBox.setVisible(true);
                        ratedBox.getChildren().addAll(ratingLabel, filledStar1, filledStar2, filledStar3, filledStar4, emptyStar5);
                        ratedBox.setAlignment(Pos.CENTER_LEFT);
                    }
                    z.set(true);
                }
        );
        emptyStar5.setOnMouseClicked(event ->
                {
                    if(!z.get()){
                        currentUser.user_watch_record_list.removeLast();
                        currentUser.watched_movie(movie,5);
                        rateBox.setVisible(false);
                        ratedBox.setVisible(true);
                        ratedBox.getChildren().addAll(ratingLabel, filledStar1, filledStar2, filledStar3, filledStar4, filledStar5);
                        ratedBox.setAlignment(Pos.CENTER_LEFT);
                    }
                    z.set(true);
                }
        );

// AtomicInteger to store the rating

// Add the rating label and stars to the rateBox
        rateBox.getChildren().addAll(ratingLabel, emptyStar1, emptyStar2, emptyStar3, emptyStar4, emptyStar5);
        rateBox.setAlignment(Pos.CENTER_LEFT);

// Watch button
        Button watchButton = new Button("Watch");
        watchButton.setStyle("-fx-background-color: Black ; -fx-text-fill: white; -fx-font-size: 20px;");

        watchButton.setOnAction(event -> {

            // Toggle visibility of the rateBox
            ScrollPane scrollPane = (ScrollPane) primaryStage.getScene().lookup(".scroll-pane");
            Label searchResultMessage=new Label();
            VBox content = (VBox) scrollPane.getContent();


            if((currentUser.getSub()==null)||(!currentUser.getSub().isActive())){
                Label noMoviesFoundLabel = new Label("This movie is available for premium subscribers only. Subscribe now to access this content!");
                noMoviesFoundLabel.setTextFill(Color.RED);
                noMoviesFoundLabel.setStyle("-fx-font-size: 16px;");
                noMoviesFoundLabel.setWrapText(true);
                noMoviesFoundLabel.setAlignment(Pos.CENTER);

                VBox messageBox = new VBox(noMoviesFoundLabel);
                messageBox.setAlignment(Pos.CENTER);
                content.getChildren().add(messageBox);
                searchResultMessage.setVisible(true);
            }
            else {
                searchResultMessage.setVisible(false);
                // Toggle visibility of the rateBox
                boolean isVisible = rateBox.isVisible();
                rateBox.setVisible(!isVisible);  // Toggle visibility of the rateBox
                rateBox.getScene().getRoot().layout(); // Ensure layout is updated
                currentUser.watched_movie(movie);
            }
            // If rating is provided, save the rating for the movie
        });


// Add the watch button to the scene (this part depends on your layout)





        // HBox for the movie poster and details, to place them side by side
        HBox layoutWithPosterAndDetails = new HBox(20);
        layoutWithPosterAndDetails.setStyle("-fx-background-color: black;");
        layoutWithPosterAndDetails.setAlignment(Pos.CENTER_LEFT);
        layoutWithPosterAndDetails.getChildren().addAll(posterContainer, textDetails);

        // HBox for buttons placed under the movie poster and details
        HBox buttonsContainer = new HBox(30);
        buttonsContainer.setStyle("-fx-background-color: black;");
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.getChildren().addAll(movieDetailsButton, castDetailsButton, addToWatchlistButton,watchButton,rateBox,ratedBox);

        // Wrap the layout with poster, movie details, and buttons inside a VBox
        VBox mainLayout = new VBox(20);
        mainLayout.setStyle("-fx-background-color: black;");
        mainLayout.getChildren().addAll(layoutWithPosterAndDetails, buttonsContainer);

        // Create the header (which will stay the same across pages)
        HBox header = createHeader(primaryStage);
        header.setStyle("-fx-background-color: black;");

        // Create the full layout with header and content
        VBox fullLayout = new VBox(header, mainLayout);
        fullLayout.setStyle("-fx-background-color: black;");

        // ScrollPane to allow vertical scrolling
        ScrollPane scrollPane = new ScrollPane(fullLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true); // Ensure it fills the available height
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setStyle("-fx-background-color: black;");

        // Set the new scene with updated content on the same stage
        primaryStage.setScene(new Scene(scrollPane, primaryStage.getWidth(), primaryStage.getHeight()));
    }
    private List<Movie> filterFilms(String query, Stage primaryStage, Label searchResultMessage) {
        // Get the list of all movies to filter
        List<Movie> allMovies = User.getAllMovies();
        List<Movie> filteredMovies = new ArrayList<>();

        String processedQuery = query.trim();

        if (processedQuery.isEmpty()) {
            filteredMovies.addAll(allMovies);
        } else {
            List<Movie>exactMatch=User.searchMovieByTitle(processedQuery);
            filteredMovies.addAll(exactMatch);
        }

        // Update the grid with the filtered movies
        GridPane grid = createMovieGrid(filteredMovies, primaryStage);

        ScrollPane scrollPane = (ScrollPane) primaryStage.getScene().lookup(".scroll-pane");
        VBox content = (VBox) scrollPane.getContent();

        content.getChildren().clear();

        if (filteredMovies.isEmpty()) {
            Label noMoviesFoundLabel = new Label("No movies found for: " + query);
            noMoviesFoundLabel.setTextFill(Color.RED);
            noMoviesFoundLabel.setStyle("-fx-font-size: 16px;");
            noMoviesFoundLabel.setWrapText(true);
            noMoviesFoundLabel.setAlignment(Pos.CENTER);

            VBox messageBox = new VBox(noMoviesFoundLabel);
            messageBox.setAlignment(Pos.CENTER);
            content.getChildren().add(messageBox);
            searchResultMessage.setVisible(true);
        } else {
            content.getChildren().add(grid);
            searchResultMessage.setVisible(false);
        }

        return filteredMovies;
    }

    private GridPane createCastGrid(List<Cast> actors , Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setHgap(70);
        grid.setVgap(50);
        grid.setPadding(new Insets(10, 50, 10, 100));
        grid.setStyle("-fx-background-color:transparent;");

        int col = 0;
        int row = 0;
        for (Cast actor : actors) {
            ImageView actorImage = new ImageView();
            if (actor.getCastGender()==Gender.MALE)
                actorImage.setImage(new Image("/filmsposter-Copy/Male.jpg"));
            else
                actorImage.setImage(new Image("/filmsposter-Copy/Female.jpg"));
            actorImage.setFitWidth(250);
            actorImage.setFitHeight(250);

            Label actorNameLabel = new Label(actor.getFullName());
            actorNameLabel.setStyle("-fx-font-size:16 px ; -fx-font-weight:bold;");
            VBox actorBox = new VBox(actorImage, actorNameLabel);
            actorBox.setAlignment(Pos.CENTER);

            actorBox.setOnMouseClicked(event -> {
                // Display movie details when clicked
                showActorDetails(actor,primaryStage);
            });
            actorBox.setOnMouseEntered(event ->{
                actorImage.setOpacity(0.5);
            } );
            actorBox.setOnMouseExited(event -> {
                actorImage.setOpacity(1.0);
            });

            grid.add(actorBox, col, row);

            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }
        grid.setStyle("-fx-background-color:black;");
        return grid;
    }
    private void showActorDetails(Cast actor, Stage primaryStage) {
        // Create a new layout to display the actor details
        VBox detailsLayout = new VBox(20);
        detailsLayout.setPadding(new Insets(20));
        detailsLayout.setStyle("-fx-background-color: black; -fx-padding: 20px;");
        detailsLayout.setMaxWidth(800);
        detailsLayout.setFillWidth(true);

        ImageView actorImage = new ImageView();
        if (actor.getCastGender() == Gender.MALE)
            actorImage.setImage(new Image("/filmsposter-Copy/Male.jpg"));
        else
            actorImage.setImage(new Image("/filmsposter-Copy/Female.jpg"));
        actorImage.setFitWidth(600);
        actorImage.setFitHeight(400);
        actorImage.setStyle("-fx-border-color: black; -fx-border-width: 3;");
        StackPane imageContainer = new StackPane();
        imageContainer.setStyle("-fx-background-color: black; -fx-padding: 10px;"); // Black background for poster container
        imageContainer.getChildren().add(actorImage);

        // Director details text (Initially populated with movie details)
        VBox textDetails = new VBox(10);
        textDetails.setAlignment(Pos.TOP_LEFT);
        textDetails.setStyle("-fx-background-color: black; -fx-padding: 10px;");
        textDetails.setFillWidth(true);
        // Create title text for the director
        Text nameText = new Text(actor.getFullName());
        Text genderText = new Text("Gender: " + (actor.getCastGender() == Gender.MALE ? "Male" : "Female"));
        Text birthDateText = new Text("Birthdate: " + actor.getDateOfBirth().getDayOfMonth() + " "
                + actor.getDateOfBirth().getMonth() + " " + actor.getDateOfBirth().getYear());
        Text nationalityText = new Text("Nationality: " + actor.getNationality());
        Text socialMediaText = new Text("Social Media Links: ");
        // Social Media Links
        if (!actor.getSocialMediaLinks().isEmpty()) {
            Map<String, String> links = actor.getSocialMediaLinks();
            for (String appName : links.keySet()) {
                String appLink = links.get(appName);
                Text appText = new Text(appName + " Link: " + appLink);
                appText.setFill(Color.WHITE);
                appText.setStyle("-fx-font-size: 18px;");
            }
        }
        nameText.setFill(Color.GOLD);
        genderText.setFill(Color.WHITE);
        birthDateText.setFill(Color.WHITE);
        nationalityText.setFill(Color.WHITE);
        socialMediaText.setFill(Color.WHITE);
        nameText.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
        genderText.setStyle("-fx-font-size: 18px;");
        birthDateText.setStyle("-fx-font-size: 18px;");
        nationalityText.setStyle("-fx-font-size: 18px;");
        socialMediaText.setStyle("-fx-font-size: 18px;");
        // Add Director details to the textDetails VBox
        textDetails.getChildren().addAll(nameText, genderText, birthDateText, nationalityText, socialMediaText);

        // Create buttons to toggle between movie details and cast details
        Button actorDetailsButton = new Button("Actor Details");
        actorDetailsButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 20px;");
        actorDetailsButton.setOnAction(event -> {
            textDetails.getChildren().clear();
            textDetails.getChildren().addAll(nameText, genderText, birthDateText, nationalityText, socialMediaText);
        });

        // Create buttons to toggle between movie details and cast details
        Button movieDetailsButton = new Button("Actor Movies");
        movieDetailsButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 20px;");
        movieDetailsButton.setOnAction(event -> {
            textDetails.getChildren().clear(); // Clear existing details
            GridPane movieGrid = createMovieGrid(actor.getCastMovies(), primaryStage);
            textDetails.getChildren().add(movieGrid);
        });

        // HBox for the movie poster and details, to place them side by side
        HBox layoutWithImageAndDetails = new HBox(20);
        layoutWithImageAndDetails.setStyle("-fx-background-color: black;");
        layoutWithImageAndDetails.setAlignment(Pos.CENTER_LEFT);
        layoutWithImageAndDetails.getChildren().addAll(imageContainer, textDetails);
        // HBox for buttons placed under the movie poster and details
        HBox buttonsContainer = new HBox(30);
        buttonsContainer.setStyle("-fx-background-color: black;");
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.getChildren().addAll(actorDetailsButton, movieDetailsButton);
        // Wrap the layout with poster, movie details, and buttons inside a VBox
        VBox mainLayout = new VBox(20);
        mainLayout.setStyle("-fx-background-color: black;");
        mainLayout.getChildren().addAll(layoutWithImageAndDetails, buttonsContainer);
        // Create the header (which will stay the same across pages)
        HBox header = createHeader(primaryStage);
        header.setStyle("-fx-background-color: black;");
        // Create the full layout with header and content
        VBox fullLayout = new VBox(header, mainLayout);
        fullLayout.setStyle("-fx-background-color: black;");
        // ScrollPane to allow vertical scrolling
        ScrollPane scrollPane = new ScrollPane(fullLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true); // Ensure it fills the available height
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setStyle("-fx-background-color: black;");
        // Set the new scene with updated content on the same stage
        primaryStage.setScene(new Scene(scrollPane, primaryStage.getWidth(), primaryStage.getHeight()));
    }
    private void filterActors(String query, Stage primaryStage, Label searchResultMessage) {
        ArrayList<Cast> filteredActors = new ArrayList<>();
        String processedQuery = query.trim();

        if (processedQuery.isEmpty())
            filteredActors.addAll(WatchitApp.all_cast);
        else {
            ArrayList<Cast>exactMatch=Cast.searchCastByQuery(processedQuery);
            filteredActors.addAll(exactMatch);
        }

        GridPane grid = createCastGrid(filteredActors, primaryStage);

        ScrollPane scrollPane = (ScrollPane) primaryStage.getScene().lookup(".scroll-pane");
        VBox content = (VBox) scrollPane.getContent();

        content.getChildren().clear();

        if (filteredActors.isEmpty()) {
            Label noDirectorsFoundLabel = new Label("No Actors found for: " + query);
            noDirectorsFoundLabel.setTextFill(Color.RED);
            noDirectorsFoundLabel.setStyle("-fx-font-size: 16px;");
            noDirectorsFoundLabel.setWrapText(true);
            noDirectorsFoundLabel.setAlignment(Pos.CENTER);

            VBox messageBox = new VBox(noDirectorsFoundLabel);
            messageBox.setAlignment(Pos.CENTER);
            content.getChildren().add(messageBox);
            searchResultMessage.setVisible(true);
        } else {
            content.getChildren().add(grid);
            searchResultMessage.setVisible(false);
        }
    }

    private GridPane createDirctorGrid(List<Director> directors , Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setHgap(70);
        grid.setVgap(50);
        grid.setPadding(new Insets(10, 50, 10, 100));
        grid.setStyle("-fx-background-color:transparent;");

        int col = 0;
        int row = 0;
        for (Director director : directors) {
            ImageView directorImage = new ImageView();
            if (director.getDirectorGender()== Gender.MALE)
                directorImage.setImage(new Image("/filmsposter-Copy/Male.jpg"));
            else
                directorImage.setImage(new Image("/filmsposter-Copy/Female.jpg"));
            directorImage.setFitWidth(250);
            directorImage.setFitHeight(250);

            Label directorNameLabel = new Label(director.getFullName());
            directorNameLabel.setStyle("-fx-font-size:16 px ; -fx-font-weight:bold;");
            VBox directorBox = new VBox(directorImage, directorNameLabel);
            directorBox.setAlignment(Pos.CENTER);

            directorBox.setOnMouseClicked(event -> {
                // Display movie details when clicked
                showDirectorDetails(director,primaryStage);
            });
            directorBox.setOnMouseEntered(event ->{
                directorImage.setOpacity(0.5);
            } );
            directorBox.setOnMouseExited(event -> {
                directorImage.setOpacity(1.0);
            });

            grid.add(directorBox, col, row);

            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }
        grid.setStyle("-fx-background-color:black;");
        return grid;
    }
    private void showDirectorDetails(Director director, Stage primaryStage) {
        // Create a new layout to display the movie details
        VBox detailsLayout = new VBox(20);
        detailsLayout.setPadding(new Insets(20));
        detailsLayout.setStyle("-fx-background-color: black; -fx-padding: 20px;");
        detailsLayout.setMaxWidth(800);
        detailsLayout.setFillWidth(true);

        ImageView directorImage = new ImageView();
        if (director.getDirectorGender() == Gender.MALE)
            directorImage.setImage(new Image("/filmsposter-Copy/Male.jpg"));
        else
            directorImage.setImage(new Image("/filmsposter-Copy/Female.jpg"));
        directorImage.setFitWidth(600);
        directorImage.setFitHeight(400);
        directorImage.setStyle("-fx-border-color: black; -fx-border-width: 3;");
        StackPane imageContainer = new StackPane();
        imageContainer.setStyle("-fx-background-color: black; -fx-padding: 10px;"); // Black background for poster container
        imageContainer.getChildren().add(directorImage);

        // Director details text (Initially populated with movie details)
        VBox textDetails = new VBox(10);
        textDetails.setAlignment(Pos.TOP_LEFT);
        textDetails.setStyle("-fx-background-color: black; -fx-padding: 10px;");
        textDetails.setFillWidth(true);
        // Create title text for the director
        Text nameText = new Text(director.getFullName());
        Text genderText = new Text("Gender: " + (director.getDirectorGender() == Gender.MALE ? "Male" : "Female"));
        Text birthDateText = new Text("Birthdate: " + director.getDateOfBirth().getDayOfMonth() + " "
                + director.getDateOfBirth().getMonth() + " " + director.getDateOfBirth().getYear());
        Text nationalityText = new Text("Nationality: " + director.getNationality());
        Text socialMediaText = new Text("Social Media Links: ");
        // Social Media Links
        if (!director.getSocialMediaLinks().isEmpty()) {
            Map<String, String> links = director.getSocialMediaLinks();
            for (String appName : links.keySet()) {
                String appLink = links.get(appName);
                Text appText = new Text(appName + " Link: " + appLink);
                appText.setFill(Color.WHITE);
                appText.setStyle("-fx-font-size: 18px;");
            }
        }
        nameText.setFill(Color.GOLD);
        genderText.setFill(Color.WHITE);
        birthDateText.setFill(Color.WHITE);
        nationalityText.setFill(Color.WHITE);
        socialMediaText.setFill(Color.WHITE);
        nameText.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
        genderText.setStyle("-fx-font-size: 18px;");
        birthDateText.setStyle("-fx-font-size: 18px;");
        nationalityText.setStyle("-fx-font-size: 18px;");
        socialMediaText.setStyle("-fx-font-size: 18px;");
        // Add Director details to the textDetails VBox
        textDetails.getChildren().addAll(nameText, genderText, birthDateText, nationalityText, socialMediaText);

        // Create buttons to toggle between movie details and cast details
        Button directorDetailsButton = new Button("Director Details");
        directorDetailsButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 20px;");
        directorDetailsButton.setOnAction(event -> {
            textDetails.getChildren().clear();
            textDetails.getChildren().addAll(nameText, genderText, birthDateText, nationalityText, socialMediaText);
        });

        // Create buttons to toggle between movie details and cast details
        Button movieDetailsButton = new Button("Director Movies");
        movieDetailsButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 20px;");
        movieDetailsButton.setOnAction(event -> {
            textDetails.getChildren().clear(); // Clear existing details
            GridPane movieGrid = createMovieGrid(director.getDirectorMovies(), primaryStage);
            textDetails.getChildren().add(movieGrid);
        });

        // HBox for the movie poster and details, to place them side by side
        HBox layoutWithImageAndDetails = new HBox(20);
        layoutWithImageAndDetails.setStyle("-fx-background-color: black;");
        layoutWithImageAndDetails.setAlignment(Pos.CENTER_LEFT);
        layoutWithImageAndDetails.getChildren().addAll(imageContainer, textDetails);
        // HBox for buttons placed under the movie poster and details
        HBox buttonsContainer = new HBox(30);
        buttonsContainer.setStyle("-fx-background-color: black;");
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.getChildren().addAll(directorDetailsButton, movieDetailsButton);
        // Wrap the layout with poster, movie details, and buttons inside a VBox
        VBox mainLayout = new VBox(20);
        mainLayout.setStyle("-fx-background-color: black;");
        mainLayout.getChildren().addAll(layoutWithImageAndDetails, buttonsContainer);
        // Create the header (which will stay the same across pages)
        HBox header = createHeader(primaryStage);
        header.setStyle("-fx-background-color: black;");
        // Create the full layout with header and content
        VBox fullLayout = new VBox(header, mainLayout);
        fullLayout.setStyle("-fx-background-color: black;");
        // ScrollPane to allow vertical scrolling
        ScrollPane scrollPane = new ScrollPane(fullLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true); // Ensure it fills the available height
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setStyle("-fx-background-color: black;");
        // Set the new scene with updated content on the same stage
        primaryStage.setScene(new Scene(scrollPane, primaryStage.getWidth(), primaryStage.getHeight()));
    }
    private void filterDirectors(String query, Stage primaryStage, Label searchResultMessage) {
        ArrayList<Director> filteredDirectors = new ArrayList<>();

        String processedQuery = query.trim();

        if (processedQuery.isEmpty())
            filteredDirectors.addAll(WatchitApp.all_director);
        else {
            ArrayList<Director>exactMatch=Director.searchDirectorsByQuery(processedQuery);
            filteredDirectors.addAll(exactMatch);
        }

        GridPane grid = createDirctorGrid(filteredDirectors, primaryStage);

        ScrollPane scrollPane = (ScrollPane) primaryStage.getScene().lookup(".scroll-pane");
        VBox content = (VBox) scrollPane.getContent();

        content.getChildren().clear();

        if (filteredDirectors.isEmpty()) {
            Label noDirectorsFoundLabel = new Label("No Directors found for: " + query);
            noDirectorsFoundLabel.setTextFill(Color.RED);
            noDirectorsFoundLabel.setStyle("-fx-font-size: 16px;");
            noDirectorsFoundLabel.setWrapText(true);
            noDirectorsFoundLabel.setAlignment(Pos.CENTER);

            VBox messageBox = new VBox(noDirectorsFoundLabel);
            messageBox.setAlignment(Pos.CENTER);
            content.getChildren().add(messageBox);
            searchResultMessage.setVisible(true);
        } else {
            content.getChildren().add(grid);
            searchResultMessage.setVisible(false);
        }
    }

    private GridPane createWatchRecordGrid(List<UserWatchRecord> movies , Stage primaryStage) {//shahd
        GridPane grid = new GridPane();
        grid.setHgap(70);
        grid.setVgap(50);
        grid.setPadding(new Insets(10, 50, 10, 100));
        grid.setStyle("-fx-background-color:transparent;");

        int col = 0;
        int row = 0;
        for (UserWatchRecord record : movies) {
            // Load the movie poster image with a fixed size
            ImageView moviePoster = loadImage(record.getMovie().getPoster(), 250, 250);

            // Create a clickable label or VBox
            VBox movieBox = new VBox(5);
            movieBox.setAlignment(Pos.CENTER);
            Text movieTitle=new Text(record.getMovie().getTitle());
            Text watchDate = new Text(record.getFormattedWatchDate());
            movieTitle.setFill(Color.WHITE);
            movieTitle.setStyle("-fx-font-size:16 px ; -fx-font-weight:bold;");
            watchDate.setFill(Color.WHITE);
            watchDate.setStyle("-fx-font-size:16 px ;");
            movieBox.getChildren().addAll(moviePoster, movieTitle,watchDate);
            movieBox.setStyle("-fx-background-color:black;");


            // Add an event listener for clicks on the movie box
            movieBox.setOnMouseClicked(event -> {
                // Display movie details when clicked
                displayMovie(record.getMovie(),primaryStage);
            });
            movieBox.setOnMouseEntered(event ->{
                moviePoster.setOpacity(0.5);
            } );
            movieBox.setOnMouseExited(event -> {
                moviePoster.setOpacity(1.0);
            });

            // Add the movie box to the grid
            grid.add(movieBox, col, row);

            col++;
            if (col > 3) {  // 4 columns in the grid
                col = 0;
                row++;
            }
        }

        grid.setStyle("-fx-background-color:black;");
        return grid;
    }

    private String getButtonSymbolForWatchlist(Movie movie) {
        // Check if the movie is in the watchlist and return the corresponding symbol
        return currentUser.is_movie_in_watchlist(movie) ? "Remove from list x" : "Add to list +";
    }

    private GridPane createCategoryGrid(Stage primaryStage) {
        HBox header=createHeader(primaryStage);
        GridPane grid = new GridPane();
        grid.setHgap(70);
        grid.setVgap(50);
        grid.setPadding(new Insets(10, 50, 10, 100));
        grid.setStyle("-fx-background-color:black;  -fx-background:black; -fx-border-color:black;-fx-border-width:0px;");

        List<String> categories = List.of("Action", "Romance", "Comedy", "Drama", "Horror", "Animation");
        int col = 0;
        int row = 0;

        for (String category : categories) {
            // Create a poster for each category
            ImageView categoryPoster = loadImage("/filmsposter-Copy/geners.jpg", 250, 250);

            VBox categoryBox = new VBox(5);
            categoryBox.setAlignment(Pos.CENTER);
            Text categoryText = new Text(category);
            categoryText.setFill(Color.WHITE);
            categoryText.setStyle("-fx-font-size:16 px ; -fx-font-weight:bold;");
            categoryBox.getChildren().addAll(categoryPoster, categoryText);

            categoryBox.setStyle("-fx-background-color:black; -fx-background:black;");

            categoryBox.setOnMouseEntered(event -> {
                categoryPoster.setOpacity(0.5);
            });

            categoryBox.setOnMouseExited(event -> {
                categoryPoster.setOpacity(1.0);
            });

            categoryBox.setOnMouseClicked(event -> {
                // Filter movies based on category
                List<Movie> filteredMovies = User.filter_by_genre(category);

                GridPane movieGrid = createMovieGrid(filteredMovies, primaryStage);
                ScrollPane scrollPane = new ScrollPane(movieGrid);
                scrollPane.setStyle("-fx-border-color:black;-fx-border-width:0px;-fx-background-color:black; -fx-background:black;");
                VBox movieLyout=new VBox(header,scrollPane);
                movieLyout.setStyle("-fx-background-color:black; -fx-background:black;");
                VBox.setVgrow(scrollPane,Priority.ALWAYS);
                primaryStage.setScene(new Scene(movieLyout,primaryStage.getWidth(),primaryStage.getHeight()));
            });

            grid.add(categoryBox, col, row);
            col++;
            if (col > 3) {  // 3 columns in the grid
                col = 0;
                row++;
            }
        }

        grid.setStyle("-fx-background-color:black; -fx-background:black;-fx-border-color:black;-fx-border-width:0px;");
        return grid;
    }
}
