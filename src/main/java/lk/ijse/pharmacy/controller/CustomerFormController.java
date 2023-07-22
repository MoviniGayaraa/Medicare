package lk.ijse.pharmacy.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.pharmacy.dto.Customer;
import lk.ijse.pharmacy.model.CustomerModel;
import lk.ijse.pharmacy.tm.CustomerTm;
import lk.ijse.pharmacy.util.AlertController;
import lk.ijse.pharmacy.util.ValidateField;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.function.Predicate;

public class CustomerFormController {
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colCusId;

    @FXML
    private TableColumn<?, ?> colFName;

    @FXML
    private TableColumn<?, ?> colLName;

    @FXML
    private TableColumn<?, ?> colLane;

    @FXML
    private TableColumn<?, ?> colStreet;

    @FXML
    private ImageView imgSearch;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtStreet;

    @FXML
    private TextField txtLane;

    @FXML
    private Label lblinvalidcustid;

    @FXML
    private Label lblinvalidcontact;

//    public void visibleFalse(){
//        txtBox2.setVisible(false);
//        btnUpdate.setVisible(false);
//        txtSearch.setVisible(false);
//        imgSearch.setVisible(false);
//        txtBox1.setVisible(false);
//        btnAdd.setVisible(false);
//    }
//
//    public void visibleTrue(){
//        txtBox2.setVisible(true);
//        btnUpdate.setVisible(true);
//        txtSearch.setVisible(true);
//        imgSearch.setVisible(true);
//        txtBox1.setVisible(true);
//        btnAdd.setVisible(true);
//    }

    public void initialize() throws ClassNotFoundException {
        //LoadAllCustpmer();
        getAll();
        setCellValueFactory();

        lblinvalidcustid.setVisible(false);
        lblinvalidcontact.setVisible(false);
    }

//    private void LoadAllCustpmer() {
//        try {
//            tblCustomer.getItems().clear();
//            ArrayList<CustomerTm> allICustomer = CustomerModel.getAllICustomer();
//            for (CustomerTm tm: allICustomer) {
//                tblCustomer.getItems().add(
//                        new CustomerTm(
//                                tm.getCustID(),
//                                tm.getFirstName(),
//                                tm.getLastName(),
//                                tm.getCity(),
//                                tm.getContact()
//                        )
//                );
//            }
//
//        } catch (SQLException | ClassNotFoundException throwables) {
//            throwables.printStackTrace();
//        }
//    }

    public void CustomerAddOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        //visibleFalse();
        //Navigation.onTheTopNavigation(pane, "CustomerAddForm.fxml");
        String id = txtId.getText();
        String fname = txtFirstName.getText();
        String lname = txtLastName.getText();
        String street = txtStreet.getText();
        String city = txtCity.getText();
        String lane = txtLane.getText();
        String contact = txtContact.getText();

        if (id.isEmpty() || fname.isEmpty() || lname.isEmpty() || street.isEmpty() || city.isEmpty() || lane.isEmpty() || contact.isEmpty()) {
            AlertController.errormessage("Customer details not saved.\nPlease make sure to fill out all the fields.");
        }else{

            if (ValidateField.custIdCheck(id)) {
                if(ValidateField.contactCheck(contact)) {
                    Customer customer = new Customer(id, fname, lname, street, city, lane, contact);
                    try {
                        boolean isSaved = CustomerModel.add(customer);
                        if (isSaved) {
                            AlertController.confirmmessage("Customer Added Successfully");
                            txtId.setText("");
                            txtFirstName.setText("");
                            txtLastName.setText("");
                            txtStreet.setText("");
                            txtCity.setText("");
                            txtLane.setText("");
                            txtContact.setText("");

                            getAll();
                        }
                    } catch (SQLIntegrityConstraintViolationException e) {
                        System.out.println(e);
                        new Alert(Alert.AlertType.ERROR, "Duplicate Customer ID").show();
                    } catch (SQLException e) {
                        System.out.println(e);
                        new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
                    }
                }else{
                    lblinvalidcontact.setVisible(true);
                }
            }else{
                lblinvalidcustid.setVisible(true);
                txtId.setStyle("-fx-text-fill: red");
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        //visibleFalse();
        //Navigation.onTheTopNavigation(pane, "CustomerUpdateForm.fxml");
        String id = txtId.getText();
        String fname = txtFirstName.getText();
        String lname = txtLastName.getText();
        String street = txtStreet.getText();
        String city = txtCity.getText();
        String lane = txtLane.getText();
        String contact = txtContact.getText();

        if (id.isEmpty() || fname.isEmpty() || lname.isEmpty() || street.isEmpty() || city.isEmpty() || lane.isEmpty() || contact.isEmpty()) {
            AlertController.errormessage("Customer details not updated.\nPlease make sure to fill out all the fields.");
        }else{

            if (ValidateField.custIdCheck(id)) {
                if(ValidateField.contactCheck(contact)) {
                    try {
                        Customer customer = new Customer(id, fname, lname, street, city, lane, contact);
                        boolean isUpdated = CustomerModel.update(customer);
                        if (isUpdated) {
                            AlertController.confirmmessage("Customer Details Updated");
                            txtId.setText("");
                            txtFirstName.setText("");
                            txtLastName.setText("");
                            txtStreet.setText("");
                            txtCity.setText("");
                            txtLane.setText("");
                            txtContact.setText("");

                            getAll();
                        }
                    } catch (SQLIntegrityConstraintViolationException e) {
                        System.out.println(e);
                        AlertController.errormessage(e.getMessage());
                    } catch (Exception e) {
                        System.out.println(e);
                        AlertController.errormessage("something went wrong!");
                    }
                }else{
                    lblinvalidcontact.setVisible(true);
                }
            }else{
                lblinvalidcustid.setVisible(true);
                txtId.setStyle("-fx-text-fill: red");
            }
        }
    }


    public void searchOnAction(ActionEvent actionEvent) {
        String id = txtSearch.getText();

        txtId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtStreet.setText("");
        txtCity.setText("");
        txtLane.setText("");
        txtContact.setText("");

        try {
            Customer customer = CustomerModel.findById(id);
            if (customer != null) {
                txtId.setText(customer.getCustID());
                txtFirstName.setText(customer.getFirstName());
                txtLastName.setText(customer.getLastName());
                txtStreet.setText(customer.getStreet());
                txtCity.setText(customer.getCity());
                txtLane.setText(customer.getLane());
                txtContact.setText(customer.getContact());

                txtSearch.setText("");
            } else {
                AlertController.errormessage("Customer ID Not Found");
            }
        } catch (Exception e) {
            System.out.println(e);
            AlertController.errormessage("Something Went Wrong");
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String id = txtId.getText();

        boolean result = AlertController.okconfirmmessage("Are you sure you want to remove this customer?");
        if (result == true) {
            try {
                boolean isDeleted = CustomerModel.delete(id);
                if (isDeleted) {
                    AlertController.confirmmessage("Customer Deleted Successfully");
                    txtId.setText("");
                    txtFirstName.setText("");
                    txtLastName.setText("");
                    txtStreet.setText("");
                    txtCity.setText("");
                    txtLane.setText("");
                    txtContact.setText("");

                    getAll();
                }
            } catch (SQLException e) {
                System.out.println(e);
                AlertController.errormessage("something went wrong!");
            }
        }
    }

    private void
    getAll() throws ClassNotFoundException {
        ObservableList<CustomerTm> obList = null;
        try {
            obList = CustomerModel.getAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tblCustomer.setItems(obList);
    }

    private void setCellValueFactory() {
        colCusId.setCellValueFactory(new PropertyValueFactory<>("custID"));
        colFName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colLane.setCellValueFactory(new PropertyValueFactory<>("lane"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    public void imgSearchOnMouseClicked(MouseEvent mouseEvent) {
        txtSearch.requestFocus();
        txtSearch.selectAll();
        txtSearch.fireEvent(new ActionEvent());
    }

    public void txtSearchKeyTyped(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        String searchValue = txtSearch.getText().trim();
        ObservableList<CustomerTm> obList = CustomerModel.getAll();

        if (!searchValue.isEmpty()) {
            ObservableList<CustomerTm> filteredData = obList.filtered(new Predicate<CustomerTm>() {
                @Override
                public boolean test(CustomerTm eventtm) {
                    return eventtm.getCustID().toLowerCase().contains(searchValue.toLowerCase());
                }
            });
            tblCustomer.setItems(filteredData);
        } else {
            tblCustomer.setItems(obList);
        }
    }

    public void tblCustomerOnMouseClicked(MouseEvent mouseEvent) {
        TablePosition pos = tblCustomer.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        // Get the data from the selected row
        ObservableList<TableColumn<CustomerTm, ?>> columns = tblCustomer.getColumns();

        txtId.setText(columns.get(0).getCellData(row).toString());
        txtFirstName.setText(columns.get(1).getCellData(row).toString());
        txtLastName.setText(columns.get(2).getCellData(row).toString());
        txtStreet.setText(columns.get(3).getCellData(row).toString());
        txtCity.setText(columns.get(4).getCellData(row).toString());
        txtLane.setText(columns.get(5).getCellData(row).toString());
        txtContact.setText(columns.get(6).getCellData(row).toString());

        txtSearch.setText("");
    }

    public void txtIdOnMouseClicked(MouseEvent mouseEvent) {
        lblinvalidcustid.setVisible(false);
        txtId.setStyle("-fx-text-fill: black");
    }

    public void txtContactOnMouseClicked(MouseEvent mouseEvent) {
        lblinvalidcontact.setVisible(false);
    }
}
