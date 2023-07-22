package lk.ijse.pharmacy.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.pharmacy.dto.Supplier;
import lk.ijse.pharmacy.model.SupplierModel;
import lk.ijse.pharmacy.tm.SupplierTm;
import lk.ijse.pharmacy.util.AlertController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {
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
    private TableView<SupplierTm> tblCustomer;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLane;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtStreet;
    public void CustomerAddOnAction(ActionEvent event) {
        String id = txtId.getText();
        String fname = txtFirstName.getText();
        String lname = txtLastName.getText();
        String street = txtStreet.getText();
        String city = txtCity.getText();
        String lane = txtLane.getText();
        String contact = txtContact.getText();

        Supplier supplier = new Supplier(id, fname, lname, street, city, lane, contact);

        try {
            boolean isSaved = SupplierModel.add(supplier);
            if (isSaved) {
                AlertController.confirmmessage("Supplier Added Successfully");
                txtId.setText("");
                txtFirstName.setText("");
                txtLastName.setText("");
                txtStreet.setText("");
                txtCity.setText("");
                txtLane.setText("");
                txtContact.setText("");

                getAll();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String fname = txtFirstName.getText();
        String lname = txtLastName.getText();
        String street = txtStreet.getText();
        String city = txtCity.getText();
        String lane = txtLane.getText();
        String contact = txtContact.getText();
        Supplier supplier = new Supplier(id, fname, lname, street, city, lane, contact);
        try {
            boolean isUpdated = SupplierModel.update(supplier);
            if (isUpdated) {
                AlertController.confirmmessage("Supplier Details Updated");
                txtId.setText("");
                txtFirstName.setText("");
                txtLastName.setText("");
                txtStreet.setText("");
                txtCity.setText("");
                txtLane.setText("");
                txtContact.setText("");

                getAll();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();
        boolean result = AlertController.okconfirmmessage("Are you sure you want to remove this customer?");

        try {
            boolean isDeleted = SupplierModel.delete(id);
            if (isDeleted) {
                AlertController.confirmmessage("Supplier Deleted Successfully");
                txtId.setText("");
                txtFirstName.setText("");
                txtLastName.setText("");
                txtStreet.setText("");
                txtCity.setText("");
                txtLane.setText("");
                txtContact.setText("");

                getAll();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void searchOnAction(ActionEvent event) {
        String id = txtSearch.getText();

        txtId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtStreet.setText("");
        txtCity.setText("");
        txtLane.setText("");
        txtContact.setText("");
        try {
            Supplier supplier = SupplierModel.findById(id);
            if (supplier != null) {
                txtId.setText(supplier.getSupID());
                txtFirstName.setText(supplier.getFirstName());
                txtLastName.setText(supplier.getLastName());
                txtStreet.setText(supplier.getStreet());
                txtCity.setText(supplier.getCity());
                txtLane.setText(supplier.getLane());
                txtContact.setText(supplier.getContact());

                txtSearch.setText("");
            } else {
                AlertController.errormessage("Supplier ID Not Found");
            }
        } catch (SQLException throwables) {

            AlertController.errormessage("Something Went Wrong");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    private void setCellValueFactory() {
        colCusId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colFName.setCellValueFactory(new PropertyValueFactory<>("supFName"));
        colLName.setCellValueFactory(new PropertyValueFactory<>("supLName"));
        colStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colLane.setCellValueFactory(new PropertyValueFactory<>("lane"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }

    private void getAll() {
        try {
            ObservableList<SupplierTm> obList = SupplierModel.getAll();

            tblSupplier.setItems(obList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void tblCustomerOnMouseClicked(MouseEvent mouseEvent) {
        TablePosition pos = tblSupplier.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        // Get the data from the selected row
        ObservableList <TableColumn<SupplierTm, ?>> columns = tblSupplier.getColumns();

        txtId.setText(columns.get(0).getCellData(row).toString());
        txtFirstName.setText(columns.get(1).getCellData(row).toString());
        txtLastName.setText(columns.get(2).getCellData(row).toString());
        txtStreet.setText(columns.get(3).getCellData(row).toString());
        txtCity.setText(columns.get(4).getCellData(row).toString());
        txtLane.setText(columns.get(5).getCellData(row).toString());
        txtContact.setText(columns.get(6).getCellData(row).toString());

        txtSearch.setText("");
    }

    public void imgSearchOnAction(MouseEvent mouseEvent) {

    }

    public void imgSearchOnMouseClicked(MouseEvent mouseEvent) {
        txtSearch.requestFocus();
        txtSearch.selectAll();
        txtSearch.fireEvent(new ActionEvent());
    }
}
