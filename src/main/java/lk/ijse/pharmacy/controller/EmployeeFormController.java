package lk.ijse.pharmacy.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.pharmacy.dto.Customer;
import lk.ijse.pharmacy.dto.Employee;
import lk.ijse.pharmacy.model.CustomerModel;
import lk.ijse.pharmacy.model.EmployeeModel;
import lk.ijse.pharmacy.tm.CustomerTm;
import lk.ijse.pharmacy.tm.EmployeeTm;
import lk.ijse.pharmacy.util.AlertController;
import lk.ijse.pharmacy.util.ValidateField;

public class EmployeeFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colFirstName;

    @FXML
    private TableColumn<?, ?> colLane;

    @FXML
    private TableColumn<?, ?> colLastName;

    @FXML
    private TableColumn<?, ?> colStreet;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLane;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtSearchId;

    @FXML
    private Label lblinvalidcontact;

    @FXML
    private Label lblinvalidcustid;

    @FXML
    private TextField txtStreet;

    private Employee employee;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        String fname = txtFirstName.getText();
        String lname = txtLastName.getText();
        String street = txtStreet.getText();
        String city = txtCity.getText();
        String lane = txtLane.getText();
        String contact = txtContact.getText();

        if (id.isEmpty() || fname.isEmpty() || lname.isEmpty() || street.isEmpty() || city.isEmpty() || lane.isEmpty() || contact.isEmpty()) {
            AlertController.errormessage("Employee details not saved.\nPlease make sure to fill out all the fields.");
        }else{

            if (ValidateField.employeeIdCheck(id)) {
                if(ValidateField.contactCheck(contact)) {

        employee.setEmpId(txtEmployeeId.getText());
        employee.setFirstName(txtFirstName.getText());
        employee.setLastName(txtLastName.getText());
        employee.setStreet(txtStreet.getText());
        employee.setCity(txtCity.getText());
        employee.setLane(txtLane.getText());
        employee.setContact(txtContact.getText());

        try {
            boolean isSaved = EmployeeModel.save(employee);

            if(isSaved){
                setCellValueFactory();
                getAll();
                clearTxtField();
                AlertController.confirmmessage("Employee added successfuly");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
                }else{
                    lblinvalidcontact.setVisible(true);
                }
            }else{
                lblinvalidcustid.setVisible(true);
                txtEmployeeId.setStyle("-fx-text-fill: red");
            }
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        boolean result = AlertController.okconfirmmessage("Are you sure you want to delete ?");
        if(result) {
            try {
                String empId = txtEmployeeId.getText();

                boolean isDeleted = EmployeeModel.delete(empId);
                if (isDeleted) {
                    setCellValueFactory();
                    getAll();
                    clearTxtField();
                } else {
                    AlertController.errormessage("Not Deleted");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        String fname = txtFirstName.getText();
        String lname = txtLastName.getText();
        String street = txtStreet.getText();
        String city = txtCity.getText();
        String lane = txtLane.getText();
        String contact = txtContact.getText();

        if (id.isEmpty() || fname.isEmpty() || lname.isEmpty() || street.isEmpty() || city.isEmpty() || lane.isEmpty() || contact.isEmpty()) {
            AlertController.errormessage("Employee details not updated.\nPlease make sure to fill out all the fields.");
        }else{

            if (ValidateField.employeeIdCheck(id)) {
                if(ValidateField.contactCheck(contact)) {

        employee.setEmpId(txtEmployeeId.getText());
        employee.setFirstName(txtFirstName.getText());
        employee.setLastName(txtLastName.getText());
        employee.setStreet(txtStreet.getText());
        employee.setCity(txtCity.getText());
        employee.setLane(txtLane.getText());
        employee.setContact(txtContact.getText());

        try {
            boolean isUpdated = EmployeeModel.update(employee);
            if(isUpdated) {
                setCellValueFactory();
                getAll();
                clearTxtField();
                AlertController.confirmmessage("Employee updated successfuly");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
                }else{
                    lblinvalidcontact.setVisible(true);
                }
            }else{
                lblinvalidcustid.setVisible(true);
                txtEmployeeId.setStyle("-fx-text-fill: red");
            }
        }

    }

    @FXML
    void imgSearchIconOnMouseClickedAction(MouseEvent event) {
        String id = txtSearchId.getText();

        txtEmployeeId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtStreet.setText("");
        txtCity.setText("");
        txtLane.setText("");
        txtContact.setText("");

        try {
            Employee employee = EmployeeModel.findById(id);
            if (employee != null) {
                txtEmployeeId.setText(employee.getEmpId());
                txtFirstName.setText(employee.getFirstName());
                txtLastName.setText(employee.getLastName());
                txtStreet.setText(employee.getStreet());
                txtCity.setText(employee.getCity());
                txtLane.setText(employee.getLane());
                txtContact.setText(employee.getContact());

                txtSearchId.setText("");
            } else {
                AlertController.errormessage("Customer ID Not Found");
            }
        } catch (Exception e) {
            System.out.println(e);
            AlertController.errormessage("Something Went Wrong");
        }
    }

    @FXML
    void tableOnMouseClickedAction(MouseEvent event) {
        TablePosition pos=tblEmployee.getSelectionModel().getSelectedCells().get(0);
        int row=pos.getRow();

        ObservableList<TableColumn<EmployeeTm,?>> columns=tblEmployee.getColumns();

        txtEmployeeId.setText(columns.get(0).getCellData(row).toString());
        txtFirstName.setText(columns.get(1).getCellData(row).toString());
        txtLastName.setText(columns.get(2).getCellData(row).toString());
        txtStreet.setText((columns.get(3).getCellData(row).toString()));
        txtCity.setText(columns.get(4).getCellData(row).toString());
        txtLane.setText(columns.get(5).getCellData(row).toString());
        txtContact.setText(columns.get(6).getCellData(row).toString());
    }

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {
        String id = txtSearchId.getText();

        txtEmployeeId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtStreet.setText("");
        txtCity.setText("");
        txtLane.setText("");
        txtContact.setText("");

        try {
            Employee employee = EmployeeModel.findById(id);
            if (employee != null) {
                txtEmployeeId.setText(employee.getEmpId());
                txtFirstName.setText(employee.getFirstName());
                txtLastName.setText(employee.getLastName());
                txtStreet.setText(employee.getStreet());
                txtCity.setText(employee.getCity());
                txtLane.setText(employee.getLane());
                txtContact.setText(employee.getContact());

                txtSearchId.setText("");
            } else {
                AlertController.errormessage("Customer ID Not Found");
            }
        } catch (Exception e) {
            System.out.println(e);
            AlertController.errormessage("Something Went Wrong");
        }
    }

    public void txtSearchKeyTyped(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        String searchValue = txtSearchId.getText().trim();
        ObservableList<EmployeeTm> obList = EmployeeModel.getAll();

        if (!searchValue.isEmpty()) {
            ObservableList<EmployeeTm> filteredData = obList.filtered(new Predicate<EmployeeTm>() {
                @Override
                public boolean test(EmployeeTm eventtm) {
                    return eventtm.getEmpId().toLowerCase().contains(searchValue.toLowerCase());
                }
            });
            tblEmployee.setItems(filteredData);
        } else {
            tblEmployee.setItems(obList);
        }
    }

    @FXML
    void txtContactOnMouseClicked(MouseEvent event) {
        lblinvalidcontact.setVisible(false);
    }

    @FXML
    void txtIdOnMouseClicked(MouseEvent event) {
        lblinvalidcustid.setVisible(false);
        txtEmployeeId.setStyle("-fx-text-fill: black");
    }

    @FXML
    void initialize() {
        employee = new Employee();

        setCellValueFactory();
        getAll();

        lblinvalidcontact.setVisible(false);
        lblinvalidcustid.setVisible(false);
    }

    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colStreet.setCellValueFactory(new PropertyValueFactory<>("street"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colLane.setCellValueFactory(new PropertyValueFactory<>("lane"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private void getAll() {
        ObservableList<EmployeeTm> obList = null;
        try {
            obList = EmployeeModel.getAll();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        tblEmployee.setItems(obList);
    }

    private void clearTxtField() {
        txtEmployeeId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtStreet.setText("");
        txtCity.setText("");
        txtLane.setText("");
        txtContact.setText("");
    }
}
