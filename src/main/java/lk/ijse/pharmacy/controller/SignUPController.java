package lk.ijse.pharmacy.controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lk.ijse.pharmacy.dto.User;
import lk.ijse.pharmacy.model.UserModel;
import lk.ijse.pharmacy.util.AlertController;
import lk.ijse.pharmacy.util.Navigation;
import lk.ijse.pharmacy.util.ValidateField;

public class SignUPController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink AlreadyHaveAnAccountHyperLink;

    @FXML
    private TextField confirmPassword;

    @FXML
    private TextField emailTxt;

    @FXML
    private TextField passwordTxt;

    @FXML
    private JFXButton signUPBtn;

    @FXML
    private TextField userNameTxt;

    @FXML
    void hyperLinkOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("LoginUser.fxml",event);

    }

    @FXML
    void signBtnOnAction(ActionEvent event) {
         if(userNameTxt.getText().isEmpty() || passwordTxt.getText().isEmpty() ||
                confirmPassword.getText().isEmpty() || !ValidateField.emailCheck(emailTxt.getText())){

             if(!ValidateField.emailCheck(emailTxt.getText())){
                 emailTxt.setStyle("-fx-border-color: red; -fx-border-width: 3 3 3 3;");
                 AlertController.errormessage("Invalid Emaill");
             }else {
                 if(userNameTxt.getText().isEmpty()){
                     userNameTxt.setStyle("-fx-border-color: red; -fx-border-width: 3 3 3 3;");
                 }else if(passwordTxt.getText().isEmpty()){
                     passwordTxt.setStyle("-fx-border-color: red; -fx-border-width: 3 3 3 3;");
                 }else if(confirmPassword.getText().isEmpty()){
                     confirmPassword.setStyle("-fx-border-color: red; -fx-border-width: 3 3 3 3;");
                 }
                 //AlertController.errormessage("please fill all empty fields before signup for a account");

             }

        }else{
             String username = userNameTxt.getText();
             String password = passwordTxt.getText();
             String email = emailTxt.getText();

             User user = new User(username, password, email);

             if (passwordTxt.getText().equals(confirmPassword.getText())) {
                 boolean isSaved = false;
                 try {
                     isSaved = UserModel.save(user);
                     if (isSaved) {
                         Navigation.switchNavigation("LoginUser.fxml",event);
                         emailTxt.setText("");
                         userNameTxt.setText("");
                         passwordTxt.setText("");
                         confirmPassword.setText("");

                     }
                 } catch (Exception throwables) {
                     throwables.printStackTrace();
                 }


             }else {
                 AlertController.errormessage("password not same");
             }

        }
    }

    @FXML
    void initialize() {


    }

    @FXML
    void txtConPasswordOnMouseClicked(MouseEvent event) {
        confirmPassword.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void txtEmailOnMouseClicke(MouseEvent event) {
        emailTxt.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void txtPasswordOnMouseCllicked(MouseEvent event) {
        passwordTxt.setStyle("-fx-border-color: transparent");
    }

    @FXML
    void txtUserNameOnMouseClicked(MouseEvent event) {
        userNameTxt.setStyle("-fx-border-color: transparent");
    }

}
