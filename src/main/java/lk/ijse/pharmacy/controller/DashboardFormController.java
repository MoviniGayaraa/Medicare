package lk.ijse.pharmacy.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lk.ijse.pharmacy.model.CustomerModel;
import lk.ijse.pharmacy.model.EmployeeModel;
import lk.ijse.pharmacy.model.OrderDetailModel;
import lk.ijse.pharmacy.model.OrderModel;
import lk.ijse.pharmacy.util.Navigation;

import java.sql.SQLException;
import java.time.LocalDate;

public class DashboardFormController {

    @FXML
    private JFXButton btnHome;

    @FXML
    private Label lblTotalCustomer;

    @FXML
    private Label lblTotalEmployee;

    @FXML
    private Label lblTotalSales;

    @FXML
    private Label lbldate;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private PieChart pieChart;




    public Pane pane;

    public void btnCustomerOnAction(ActionEvent actionEvent) {
        Navigation.onTheTopNavigation(pane, "Customer.fxml");
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) {
        Navigation.onTheTopNavigation(pane, "Employee.fxml");

    }

    public void btnSupplierOnAction(ActionEvent actionEvent) {
        Navigation.onTheTopNavigation(pane, "Supplier.fxml");

    }

    public void btnOrderOnAction(ActionEvent actionEvent) {
        Navigation.onTheTopNavigation(pane, "Order.fxml");

    }

    public void btnItemOnAction(ActionEvent actionEvent) {
        Navigation.onTheTopNavigation(pane, "Item.fxml");
    }

    public void btnBillFormOnAction(ActionEvent actionEvent) {
        Navigation.onTheTopNavigation(pane, "Bill.fxml");
    }

//    public void btnHomeOnaction(ActionEvent actionEvent) {
//        Navigation.onTheTopNavigation(pane, "Dashboard.fxml");
//
//    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {
        Navigation.navigation("Dashboard.fxml");
        pane.getScene().getWindow().hide();
    }

    public void EmployeeAddOnAction(ActionEvent actionEvent) {
    }


    private void countTotalCust(){
        try {
            int count = CustomerModel.getTotCustomers();
            lblTotalCustomer.setText(String.valueOf(count));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void countTotalEmp(){
        try {
            int count = EmployeeModel.getTotEmployee();
            lblTotalEmployee.setText(String.valueOf(count));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void countTotalSales(){
        try {
            int count = OrderModel.getTotalSales();
            lblTotalSales.setText(String.valueOf(count));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        countTotalCust();
        countTotalEmp();
        countTotalSales();
        lineChart();
        setDataToPieChart();

        lbldate.setText(String.valueOf(LocalDate.now()));
    }

    public void setDataToPieChart() {
        ObservableList<PieChart.Data> pieChartData = null;
        try {
            pieChartData = OrderDetailModel.getDataToPieChart();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        pieChart.setData(pieChartData);
    }

    void lineChart() {
        lineChart.setTitle("Income Chart 2023");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");


        try {
            XYChart.Series series = OrderModel.lineChartData();
            series.setName("Income Chart");
            lineChart.getData().add(series);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnReportsFormOnAction(ActionEvent event) {
        Navigation.onTheTopNavigation(pane, "reports_form.fxml");
    }
}






