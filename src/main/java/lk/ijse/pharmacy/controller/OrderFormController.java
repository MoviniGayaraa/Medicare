package lk.ijse.pharmacy.controller;

import com.jfoenix.controls.JFXButton;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.pharmacy.db.DBConnection;
import lk.ijse.pharmacy.dto.Item;
import lk.ijse.pharmacy.dto.PlaceOrder;
import lk.ijse.pharmacy.model.CustomerModel;
import lk.ijse.pharmacy.model.EmployeeModel;
import lk.ijse.pharmacy.model.ItemModel;
import lk.ijse.pharmacy.model.OrderModel;
import lk.ijse.pharmacy.tm.PlaceOrderTM;
import lk.ijse.pharmacy.util.AlertController;
import lk.ijse.pharmacy.util.ValidateField;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class OrderFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView addicon;

    @FXML
    private Label balancelbl;

    @FXML
    private JFXButton btnAddCart;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private ComboBox<String> cmbcustid;

    @FXML
    private ComboBox<String> cmbitemcode;

    @FXML
    private TableColumn<?, ?> colaction;

    @FXML
    private TableColumn<?, ?> colcategory;

    @FXML
    private TableColumn<?, ?> colitemcode;

    @FXML
    private TableColumn<?, ?> colitemname;

    @FXML
    private TableColumn<?, ?> colquantity;

    @FXML
    private TableColumn<?, ?> colquantity_unitprice;

    @FXML
    private TableColumn<?, ?> colunitprice;

    @FXML
    private Label lblchangingcategory;

    @FXML
    private Label lblchangingcusname;

    @FXML
    private Label lblchangingitmname;

    @FXML
    private Label lblchangingqtyonhands;

    @FXML
    private Label lblchangingunitprice;

    @FXML
    private Label lblcustomername;

    @FXML
    private Label lblitemcategory;

    @FXML
    private Label lblitemname;

    @FXML
    private Label lblitemqtyonhand;

    @FXML
    private Label lblitmunitprice;

    @FXML
    private Label lblmoreneeded;

    @FXML
    private Label lblorderdate;

    @FXML
    private Label lblorderid;

    @FXML
    private Label lbltotalpay;

    @FXML
    private TextField orderqty;

    @FXML
    private RadioButton radiodelivery;

    @FXML
    private ImageView seeorderdet;

    @FXML
    private TableView<PlaceOrderTM> tblplaceOrder;

    @FXML
    private TextField txtpaidamount;

    @FXML
    private Label txtmoremoney;

    @FXML
    private ComboBox<String> cmbEmpID;

    private void generateNextOrderId() {
        try {
            String id = OrderModel.getNextOrderId();
            lblorderid.setText(id);
        } catch (Exception e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Exception!").show();
        }
    }

    private void loadEmployeeIDs() throws ClassNotFoundException {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = EmployeeModel.loadIds();

            for (String code : codes) {
                obList.add(code);
            }
            cmbEmpID.setItems(obList);
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    private void loadItemCodes() throws ClassNotFoundException {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = ItemModel.loadCodes();

            for (String code : codes) {
                obList.add(code);
            }
            cmbitemcode.setItems(obList);
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    Item item;
    @FXML
    void cmbitemcodeOnAction(ActionEvent event) {
        String itemcode = cmbitemcode.getValue();

        try {
            item = ItemModel.findById(itemcode);
            lblchangingitmname.setText(item.getItemMedName());
            lblchangingunitprice.setText(String.valueOf(item.getItemUnitPrice()));
            lblchangingcategory.setText(item.getItemType());

            if (Integer.parseInt(item.getItemQOH()) > 0) {
                lblchangingqtyonhands.setText(String.valueOf(item.getItemQOH()));
            } else {
                lblchangingqtyonhands.setText("Out Of Stock");
                AlertController.errormessage("item " + item.getItemMedName() + " out of stock");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void loadCustomerIds() throws ClassNotFoundException {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> ids = CustomerModel.loadIds();

            for (String id : ids) {
                obList.add(id);
            }
            cmbcustid.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void cmbcustidOnAction(ActionEvent event) {
        String cust_id = cmbcustid.getValue();

        try {
            String name = CustomerModel.getCustName(cust_id);
            lblchangingcusname.setText(name);
        } catch (Exception e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "Exception!").show();
        }
    }

    private void setCellValueFactory() {
        colitemcode.setCellValueFactory(new PropertyValueFactory<>("itemcode"));
        colitemname.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        colunitprice.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        colcategory.setCellValueFactory(new PropertyValueFactory<>("type"));
        colquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colquantity_unitprice.setCellValueFactory(new PropertyValueFactory<>("total"));
        colaction.setCellValueFactory(new PropertyValueFactory<>("removebtn"));
    }

    @FXML
    void addiconOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void btnDeleteMousePressed(MouseEvent event) {

    }

    private ObservableList<PlaceOrderTM> obList = FXCollections.observableArrayList();
    @FXML
    void btnaddcartOnAction(ActionEvent event) {
        String itemcode = cmbitemcode.getValue();
        String itemname = lblchangingitmname.getText();
        String unitprice = lblchangingunitprice.getText();
        String type = lblchangingcategory.getText();
        Integer quantity = 0;
        try {
            quantity = Integer.valueOf(orderqty.getText());
        }catch(Exception e){
            System.out.println(e);
        }
        Double total = Double.parseDouble(lblchangingunitprice.getText()) * quantity;
        Button btnremove = new Button("Remove");
        btnremove.setCursor(Cursor.HAND);

        if (ValidateField.numberCheck(orderqty.getText())) {
            if (quantity > Integer.parseInt(lblchangingqtyonhands.getText())) {
                AlertController.errormessage("Item " + itemname + " out of stock or not enough stock");
            } else {
                setRemoveBtnOnAction(btnremove); /* set action to the btnRemove */

                if (!obList.isEmpty()) {
                    int newval= Integer.parseInt(lblchangingqtyonhands.getText())-Integer.parseInt(orderqty.getText());
                    lblchangingqtyonhands.setText(String.valueOf(newval));
                    for (int i = 0; i < tblplaceOrder.getItems().size(); i++) {
                        if (colitemcode.getCellData(i).equals(itemcode)) {
                            quantity += (int) colquantity.getCellData(i);
                            total = quantity * Double.parseDouble(unitprice);

                            obList.get(i).setQuantity(quantity);
                            obList.get(i).setTotal(total);

                            tblplaceOrder.refresh();
                            calculateNetTotal();
                            return;
                        }
                    }
                }else{
                    int newval= Integer.parseInt(lblchangingqtyonhands.getText())-Integer.parseInt(orderqty.getText());
                    lblchangingqtyonhands.setText(String.valueOf(newval));
                }

                PlaceOrderTM tm = new PlaceOrderTM(itemcode, itemname, Double.parseDouble(unitprice), type, quantity, total, btnremove);

                obList.add(tm);
                tblplaceOrder.setItems(obList);
                calculateNetTotal();
                orderqty.setText("");
            }
        } else {
            AlertController.errormessage("Wrong input format for item quantity field");
        }
    }

    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {
                int index = tblplaceOrder.getSelectionModel().getSelectedIndex();
                obList.remove(index);

                tblplaceOrder.refresh();
                calculateNetTotal();
            }

        });
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblplaceOrder.getItems().size(); i++) {
            double total = (double) colquantity_unitprice.getCellData(i);
            netTotal += total;
        }
        lbltotalpay.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnplaceorderOnAction(ActionEvent event) {


        String orderid = lblorderid.getText();
        String custid = cmbcustid.getValue();
        String ordpay = lbltotalpay.getText();
        String empid = cmbEmpID.getValue();

        List<PlaceOrder> placeOrderList = new ArrayList<>();

        for (int i = 0; i < tblplaceOrder.getItems().size(); i++) {
            PlaceOrderTM placeOrderTM = obList.get(i);

            PlaceOrder placeOrder = new PlaceOrder(
                    placeOrderTM.getItemcode(),
                    placeOrderTM.getQuantity()
            );
            placeOrderList.add(placeOrder);
        }

        boolean isPlaced = false;
        if (ValidateField.priceCheck(ordpay)) {
            try {
                isPlaced = OrderModel.placeOrder(orderid, custid, ordpay,empid, placeOrderList);
                if (isPlaced) {
                    AlertController.confirmmessage("Order Placed");
                    boolean result = AlertController.okconfirmmessage("Do you want the bill ?");

                    if (result) {
                        String printcash = txtpaidamount.getText();
                        String balance = balancelbl.getText();

                        Map<String, Object> parameters = new HashMap<>();
                        parameters.put("param1", printcash);
                        parameters.put("param2", balance);

                        InputStream resource = this.getClass().getResourceAsStream("/reports/PlaceOrderBill.jrxml");
                        try {
                            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
                            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConnection.getInstance().getConnection());
                            JasperViewer.viewReport(jasperPrint, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    try {

                        generateNextOrderId();
                        cmbcustid.setValue(null);
                        cmbitemcode.setValue(null);
                        lblchangingcusname.setText("");
                        lblchangingitmname.setText("");
                        lblchangingunitprice.setText("");
                        lblchangingcategory.setText("");
                        lblchangingqtyonhands.setText("");
                        lbltotalpay.setText("0/=");
                        radiodelivery.setSelected(false);
                        tblplaceOrder.getItems().clear();
                        txtpaidamount.setText("");
                        balancelbl.setText("");
                        lblmoreneeded.setVisible(false);
                        txtmoremoney.setText("");

                        btnPlaceOrder.setDisable(true);
                    }catch (Exception e){

                    }

//                    boolean result = AlertController.okconfirmmessage("Do you want the bill ?");
//
//                    if (result) {
//                        Map<String, Object> parameters = new HashMap<>();
//                        parameters.put("param1", printcash);
//                        parameters.put("param2", balance);
//
//                        InputStream resource = this.getClass().getResourceAsStream("/lk.ijse.project_wineverse.reports/placeorder.jrxml");
//                        try {
//                            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
//                            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConnection.getInstance().getConnection());
//                            JasperViewer.viewReport(jasperPrint, false);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
                } else {
                    AlertController.errormessage("Order Not Placed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
            }
        } else {
            AlertController.errormessage("Wrong input for paid amount field");
        }
    }

    @FXML
    void radiodeliveryOnAction(ActionEvent event) {

    }

    @FXML
    void seeorderdetOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void tblOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void txtpaidamountKeyTyped(KeyEvent event) {
        if (!txtpaidamount.getText().isEmpty()) {
            if (ValidateField.priceCheck(txtpaidamount.getText())) {
                double balance = Double.parseDouble(txtpaidamount.getText()) - Double.parseDouble(lbltotalpay.getText());
                if (balance >= 0) {
                    txtpaidamount.setStyle("-fx-text-fill: black");
                    balancelbl.setText(String.valueOf(balance));
                    lblmoreneeded.setVisible(false);
                    txtmoremoney.setText("");
                    btnPlaceOrder.setDisable(false);
                } else if (balance < 0) {
                    txtpaidamount.setStyle("-fx-text-fill: black");
                    btnPlaceOrder.setDisable(true);
                    double positbalance = Math.abs(balance);
                    lblmoreneeded.setVisible(true);
                    txtmoremoney.setText(positbalance + "/=");
                }
            } else {
                btnPlaceOrder.setDisable(true);
                txtpaidamount.setStyle("-fx-text-fill: red");
            }
        }
    }

    @FXML
    void initialize() throws ClassNotFoundException {
        assert addicon != null : "fx:id=\"addicon\" was not injected: check your FXML file 'Order.fxml'.";
        assert balancelbl != null : "fx:id=\"balancelbl\" was not injected: check your FXML file 'Order.fxml'.";
        assert btnAddCart != null : "fx:id=\"btnAddCart\" was not injected: check your FXML file 'Order.fxml'.";
        assert btnPlaceOrder != null : "fx:id=\"btnPlaceOrder\" was not injected: check your FXML file 'Order.fxml'.";
        assert cmbcustid != null : "fx:id=\"cmbcustid\" was not injected: check your FXML file 'Order.fxml'.";
        assert cmbitemcode != null : "fx:id=\"cmbitemcode\" was not injected: check your FXML file 'Order.fxml'.";
        assert colaction != null : "fx:id=\"colaction\" was not injected: check your FXML file 'Order.fxml'.";
        assert colcategory != null : "fx:id=\"colcategory\" was not injected: check your FXML file 'Order.fxml'.";
        assert colitemcode != null : "fx:id=\"colitemcode\" was not injected: check your FXML file 'Order.fxml'.";
        assert colitemname != null : "fx:id=\"colitemname\" was not injected: check your FXML file 'Order.fxml'.";
        assert colquantity != null : "fx:id=\"colquantity\" was not injected: check your FXML file 'Order.fxml'.";
        assert colquantity_unitprice != null : "fx:id=\"colquantity_unitprice\" was not injected: check your FXML file 'Order.fxml'.";
        assert colunitprice != null : "fx:id=\"colunitprice\" was not injected: check your FXML file 'Order.fxml'.";
        assert lblchangingcategory != null : "fx:id=\"lblchangingcategory\" was not injected: check your FXML file 'Order.fxml'.";
        assert lblchangingcusname != null : "fx:id=\"lblchangingcusname\" was not injected: check your FXML file 'Order.fxml'.";
        assert lblchangingitmname != null : "fx:id=\"lblchangingitmname\" was not injected: check your FXML file 'Order.fxml'.";
        assert lblchangingqtyonhands != null : "fx:id=\"lblchangingqtyonhands\" was not injected: check your FXML file 'Order.fxml'.";
        assert lblchangingunitprice != null : "fx:id=\"lblchangingunitprice\" was not injected: check your FXML file 'Order.fxml'.";
        assert lblcustomername != null : "fx:id=\"lblcustomername\" was not injected: check your FXML file 'Order.fxml'.";
        assert lblitemcategory != null : "fx:id=\"lblitemcategory\" was not injected: check your FXML file 'Order.fxml'.";
        assert lblitemname != null : "fx:id=\"lblitemname\" was not injected: check your FXML file 'Order.fxml'.";
        assert lblitemqtyonhand != null : "fx:id=\"lblitemqtyonhand\" was not injected: check your FXML file 'Order.fxml'.";
        assert lblitmunitprice != null : "fx:id=\"lblitmunitprice\" was not injected: check your FXML file 'Order.fxml'.";
        assert lblmoreneeded != null : "fx:id=\"lblmoreneeded\" was not injected: check your FXML file 'Order.fxml'.";
        assert lblorderdate != null : "fx:id=\"lblorderdate\" was not injected: check your FXML file 'Order.fxml'.";
        assert lblorderid != null : "fx:id=\"lblorderid\" was not injected: check your FXML file 'Order.fxml'.";
        assert lbltotalpay != null : "fx:id=\"lbltotalpay\" was not injected: check your FXML file 'Order.fxml'.";
        assert orderqty != null : "fx:id=\"orderqty\" was not injected: check your FXML file 'Order.fxml'.";
        assert radiodelivery != null : "fx:id=\"radiodelivery\" was not injected: check your FXML file 'Order.fxml'.";
        assert seeorderdet != null : "fx:id=\"seeorderdet\" was not injected: check your FXML file 'Order.fxml'.";
        assert tblplaceOrder != null : "fx:id=\"tblplaceOrder\" was not injected: check your FXML file 'Order.fxml'.";
        assert txtpaidamount != null : "fx:id=\"txtpaidamount\" was not injected: check your FXML file 'Order.fxml'.";

        loadCustomerIds();
        loadItemCodes();
        loadEmployeeIDs();
        generateNextOrderId();
        setCellValueFactory();

        lblorderdate.setText(String.valueOf(LocalDate.now()));

        lblmoreneeded.setVisible(false);
        btnPlaceOrder.setDisable(true);
    }

    public void cmbEmpIDOnAction(ActionEvent actionEvent) {
    }
}
