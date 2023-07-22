package lk.ijse.pharmacy.controller;

import javafx.event.ActionEvent;
import lk.ijse.pharmacy.util.Navigation;


import java.io.IOException;

public class HomepageFormController {

    public void homeOnAction(ActionEvent actionEvent) {
        try {
            Navigation.switchNavigation("LoginUser.fxml",actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
