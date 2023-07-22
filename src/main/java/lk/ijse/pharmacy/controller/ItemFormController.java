package lk.ijse.pharmacy.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.pharmacy.dto.Item;
import lk.ijse.pharmacy.model.ItemModel;
import lk.ijse.pharmacy.tm.ItemTM;
import lk.ijse.pharmacy.util.AlertController;
import lk.ijse.pharmacy.util.ValidateField;

import java.sql.SQLException;
import java.time.LocalDate;

public class ItemFormController {

    @FXML
    private DatePicker cmbItemDate;

    @FXML
    private DatePicker cmbItemmfgDate;

    @FXML
    private TableView<ItemTM> mainCOMItem;

    @FXML
    private TableColumn<?, ?> tblMedId;

    @FXML
    private TableColumn<?, ?> tblMedNmae;

    @FXML
    private TableColumn<?, ?> tblQuantityOnHands;

    @FXML
    private TableColumn<?, ?> tblUnitPrice;

    @FXML
    private TableColumn<?, ?> tblexpDate;

    @FXML
    private TableColumn<?, ?> tblmfgDate;

    @FXML
    private TableColumn<?, ?> tbltype;

    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtItemMedName;

    @FXML
    private TextField txtItemQOH;

    @FXML
    private TextField txtItemType;

    @FXML
    private TextField txtItemUnitPrice;

    @FXML
    private TextField txtsearchItem;

    @FXML
    private Label lblinvalidqty;

    @FXML
    private Label wrongitemcodeformat;

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {


        String ItemCode = txtItemCode.getText();

        try {
            boolean isDeleted = ItemModel.delete(ItemCode);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item deleted!").show();
                onActionGetAllItem();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String ItemCode = txtItemCode.getText();
        String ItemMedName = txtItemMedName.getText();
        String ItemType = txtItemType.getText();
        String ItemQOH = txtItemQOH.getText();
        String ItemDate = String.valueOf(cmbItemDate.getValue());
        String ItemmfgDate = String.valueOf(cmbItemmfgDate.getValue());
        String ItemUnitPrice = txtItemUnitPrice.getText();

        if (ItemCode.isEmpty() || ItemMedName.isEmpty() || ItemType.isEmpty() || ItemQOH.isEmpty() || ItemUnitPrice.isEmpty() || cmbItemDate.getEditor().getText().isEmpty() || cmbItemmfgDate.getEditor().getText().isEmpty()) {
            AlertController.errormessage("Please make sure to fill out all the required fields.");
        } else {
            if (ValidateField.itemIdCheck(ItemCode)) {
                if (ValidateField.numberCheck(ItemQOH)) {
                    Item itemAll = new Item(ItemCode, ItemMedName, ItemUnitPrice, ItemType, ItemDate, ItemQOH, ItemmfgDate);

                    try {
                        boolean isUpdated = ItemModel.update(itemAll);
                        // AlertController.animationMesseageCorect("CONFIRMATION","Item updated!");
                        if (isUpdated) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Item updated!").show();
                            onActionGetAllItem();
                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
                        // AlertController.animationMesseagewrong("Error","something went wrong!");
                    }
                } else {
                    lblinvalidqty.setVisible(true);
                }
            } else {
                wrongitemcodeformat.setVisible(true);
            }
        }

    }

    @FXML
    void buttonSaveOnACT(ActionEvent event) throws ClassNotFoundException {

        String ItemCode = txtItemCode.getText();
        String ItemMedName = txtItemMedName.getText();
        String ItemType = txtItemType.getText();
        String ItemQOH = txtItemQOH.getText();
        String ItemUnitPrice = txtItemUnitPrice.getText();
        String ItemDate = String.valueOf(cmbItemDate.getValue());
        String ItemmfgDate = String.valueOf(cmbItemmfgDate.getValue());

        if (ItemCode.isEmpty() || ItemMedName.isEmpty() || ItemType.isEmpty() || ItemQOH.isEmpty() || ItemUnitPrice.isEmpty() || cmbItemDate.getEditor().getText().isEmpty() || cmbItemmfgDate.getEditor().getText().isEmpty()) {
            AlertController.errormessage("Please make sure to fill out all the required fields.");
        } else {
            if (ValidateField.itemIdCheck(ItemCode)) {
                if (ValidateField.numberCheck(ItemQOH)) {
                    Item itemAll = new Item(ItemCode, ItemMedName, ItemUnitPrice, ItemType, ItemDate, ItemQOH, ItemmfgDate);

                    try {
//            boolean isSaved = ItemModel.save(code, description, unitPrice, qtyOnHand);
                        boolean isSaved = ItemModel.save(itemAll);
                        if (isSaved) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Item Added!").show();
                            onActionGetAllItem();
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                        new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
                        // AlertController.animationMesseagewrong("Error","something went wrong!");

                    }
                } else {
                    lblinvalidqty.setVisible(true);
                }
            } else {
                wrongitemcodeformat.setVisible(true);
            }
        }
    }

    @FXML
    void itemIdOnAction(ActionEvent event) {

    }

    @FXML
    void itemOnMouse(MouseEvent event) {
        TablePosition pos = mainCOMItem.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        ObservableList<TableColumn<ItemTM, ?>> columns = mainCOMItem.getColumns();

        txtItemCode.setText(columns.get(0).getCellData(row).toString());
        txtItemMedName.setText(columns.get(1).getCellData(row).toString());
        txtItemUnitPrice.setText(columns.get(2).getCellData(row).toString());
        txtItemType.setText(columns.get(3).getCellData(row).toString());
        txtItemQOH.setText(columns.get(4).getCellData(row).toString());
        cmbItemmfgDate.setValue(LocalDate.parse(columns.get(5).getCellData(row).toString()));
        cmbItemDate.setValue(LocalDate.parse(columns.get(6).getCellData(row).toString()));
    }

    @FXML
    void searchItemOnKey(KeyEvent event) {

    }

    @FXML
    void initialize() throws ClassNotFoundException {
        onActionGetAllItem();
        setCellValuefactory();

        wrongitemcodeformat.setVisible(false);
        lblinvalidqty.setVisible(false);
    }

    void onActionGetAllItem() throws ClassNotFoundException {
        try {
            ObservableList<ItemTM> supList = ItemModel.getAll();
            mainCOMItem.setItems(supList);


        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something went wrong!").show();
            //AlertController.animationMesseagewrong("Error","something went wrong!");
        }

    }

    void setCellValuefactory() {
        tblMedId.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        tblMedNmae.setCellValueFactory(new PropertyValueFactory<>("ItemMedName"));
        tbltype.setCellValueFactory(new PropertyValueFactory<>("ItemType"));
        tblQuantityOnHands.setCellValueFactory(new PropertyValueFactory<>("ItemQOH"));
        tblmfgDate.setCellValueFactory(new PropertyValueFactory<>("ItemmfgDate"));
        tblexpDate.setCellValueFactory(new PropertyValueFactory<>("ItemDate"));
        tblUnitPrice.setCellValueFactory(new PropertyValueFactory<>("ItemUnitPrice"));
    }

    public void txtItemCodeOnMouseClicked(MouseEvent mouseEvent) {
        wrongitemcodeformat.setVisible(false);
    }

    public void txtItemQOHOnMouseClicked(MouseEvent mouseEvent) {
        lblinvalidqty.setVisible(false);
    }
}
