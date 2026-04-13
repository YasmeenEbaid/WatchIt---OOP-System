//package Controllers;
//
//import static watchit.watchit_project.WatchitApp.users;
//import Model.User;
//import Model.Person;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//
//
//public class SignUpController {
//    @FXML
//    private Label emptyError;
//    @FXML
//    private Label usernameError;
//    @FXML
//    private Label emailError;
//    @FXML
//    private TextField fNameTextField;
//    @FXML
//    private TextField lastNameTextField;
//    @FXML
//    private TextField usernameTextField;
//    @FXML
//    private TextField emailTextField;
//    @FXML
//    private PasswordField passwordField;
//
//    @FXML
//    void signUp(ActionEvent event) {
//        emptyError.setText("");
//        usernameError.setText("");
//        emailError.setText("");
//        User user = new User();
//        boolean existError=false;
//        if(fNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty() || usernameTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || passwordField.getText().isEmpty()){
//            emptyError.setText("All fields are required");
//            existError=true;
//        }
//        if(!usernameTextField.getText().isEmpty()&&!User.searchUsernameIsValid(usernameTextField.getText())) {
//            usernameError.setText("Username taken. Please choose another");
//            existError=true;}
//
//        if(!emailTextField.getText().isEmpty()&&!Person.isEmailCorrect(emailTextField.getText())) {
//            emailError.setText("Please enter a valid email");
//            existError=true;}
//
//        if(!existError){
//            users.add(new User(fNameTextField.getText(),lastNameTextField.getText(),usernameTextField.getText(),emailTextField.getText(),passwordField.getText()));
//            //ينتقل للصفحة بتاعت الاشتراكات
//        }
//    }
//}
//
//
//
//
