package Controllers;

import Model.Admin;
import Model.User;
import watchit.watchit_project.WatchitApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML
    private Label PasswordError;
    @FXML
    private Label usernameError;
    @FXML
    private Label loginEror;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Hyperlink signupHyperlink;

    @FXML
    void Login(ActionEvent event) {
        usernameError.setText("");
        PasswordError.setText("");
        loginEror.setText("");

        if(usernameTextField.getText().isEmpty() && passwordField.getText().isEmpty()) {
            usernameError.setText("This field is required");
            PasswordError.setText("This field is required");
        }
        else if(usernameTextField.getText().isEmpty()) {
            usernameError.setText("This field is required");
        }
        else if(passwordField.getText().isEmpty()) {
            PasswordError.setText("This field is required");
        }
        else {
            User user = User.login(usernameTextField.getText().trim(), passwordField.getText().trim());
            Admin admin = Admin.login(usernameTextField.getText(), passwordField.getText());
            System.out.println(user);

            if(user!=null) {
                userPage.currentUser=user;
                navigateToPage(event);

            }
            else if(admin!=null) {
                WatchitApp.currentAdmin=admin;
                //هيروح لصفحة الادمن
            }
            else
                loginEror.setText("Incorrect username or password");
        }
    }

    @FXML
    void SignUpScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignUpScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) signupHyperlink.getScene().getWindow();

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    private void navigateToPage(ActionEvent event) {
        Stage stage = (Stage) usernameTextField.getScene().getWindow();
        userPage user = new userPage();
        Scene scene = user.createPage("Home Page",stage);
        stage.setScene(scene);
    }
}

